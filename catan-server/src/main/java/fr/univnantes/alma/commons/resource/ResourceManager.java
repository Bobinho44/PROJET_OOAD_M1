package fr.univnantes.alma.commons.resource;

import fr.univnantes.alma.commons.utils.reflection.ReflectionUtils;
import fr.univnantes.alma.commons.configuration.Configuration;
import fr.univnantes.alma.core.exception.InvalidInformationException;
import fr.univnantes.alma.core.resource.IResource;
import fr.univnantes.alma.core.resource.IResourceJSON;
import fr.univnantes.alma.core.resource.IResourceManager;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation of a resource manager
 */
public class ResourceManager implements IResourceManager {

    /**
     * Fields
     */
    public final Map<String, IResource> resources;

    /**
     * Creates a new resource manager
     */
    public ResourceManager() {
        this.resources = createResourceDeck();
    }

    /**
     * Creates the resource deck
     *
     * @return the resource deck
     */
    private @NonNull Map<String, IResource> createResourceDeck() {
        return ReflectionUtils.getSubClassesOf(Resource.class, "fr.univnantes.alma.commons.resource.type").stream()
                .map(resource -> ReflectionUtils.getInstancesOf(resource).amount(Configuration.getResourceAmount(resource.getSimpleName())))
                .collect(Collectors.toMap(IResource::getName, resource -> resource));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull List<IResourceJSON> getResourcesInformation() {
        return resources.values().stream()
                .map(resource -> (IResourceJSON) new ResourceJSON(resource.getName(), resource.getAmount()))
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull IResource generateResource(@NonNull IResourceJSON resourceJSON) throws InvalidInformationException {
        Objects.requireNonNull(resourceJSON, "resourceJSON cannot be null!");

        return ReflectionUtils.getSubClassesOf(Resource.class, "fr.univnantes.alma.commons.resource.type").stream()
                .map(type -> ReflectionUtils.getInstancesOf(type).amount(resourceJSON.amount()))
                .filter(resource -> resource.getName().equals(resourceJSON.name()))
                .findFirst()
                .orElseThrow(InvalidInformationException::new);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull List<IResource> generateResources(@NonNull List<IResourceJSON> resourcesJSON) {
        Objects.requireNonNull(resourcesJSON, "resourcesJSON cannot be null!");

        return resourcesJSON.stream()
                .map(this::generateResource)
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasResource(@NonNull IResource resource) {
        Objects.requireNonNull(resource, "resource cannot be null!");

        return Optional.of(resources.get(resource.getName()))
                .map(r -> r.getAmount() >= resource.getAmount())
                .orElse(false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasResources(@NonNull List<IResource> resources) {
        Objects.requireNonNull(resources, "resources cannot be null!");

        return resources.stream().allMatch(this::hasResource);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addResource(@NonNull IResource resource) {
        Objects.requireNonNull(resource, "resource cannot be null!");

        Optional.of(resources.get(resource.getName()))
                .ifPresent(r -> r.increaseAmount(resource.getAmount()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addResources(@NonNull List<IResource> resources) {
        Objects.requireNonNull(resources, "resources cannot be null!");

        resources.forEach(this::addResource);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeResource(@NonNull IResource resource) {
        Objects.requireNonNull(resource, "resource cannot be null!");

        Optional.of(resources.get(resource.getName()))
                .ifPresent(r -> r.decreaseAmount(resource.getAmount()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeResources(@NonNull List<IResource> resources) {
        Objects.requireNonNull(resources, "resources cannot be null!");

        resources.forEach(this::removeResource);
    }

}