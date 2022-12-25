package fr.univnantes.alma.commons.resource;

import fr.univnantes.alma.commons.utils.reflection.ReflectionUtils;
import fr.univnantes.alma.core.configuration.Configuration;
import fr.univnantes.alma.core.ressource.Resource;
import fr.univnantes.alma.core.ressource.ResourceManager;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation of a resource manager
 */
public class ResourceController implements ResourceManager {

    /**
     * Fields
     */
    public final Map<String, Resource> resources;

    /**
     * Creates a new resource manager
     */
    public ResourceController() {
        this.resources = createResourceDeck();
    }

    /**
     * Creates the resource deck
     *
     * @return the resource deck
     */
    private @NonNull Map<String, Resource> createResourceDeck() {
        return ReflectionUtils.getClassesOf(ResourceImpl.class, "fr.univnantes.alma.commons.resource.type").stream()
                .map(resource -> ReflectionUtils.getInstancesOf(resource).amount(Configuration.getResourceAmount(resource.getSimpleName())))
                .collect(Collectors.toMap(Resource::getName, resource -> resource));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull List<ResourceJSON> getResourcesInformation() {
        return resources.values().stream()
                .map(resource -> new ResourceJSON(resource.getName(), resource.getAmount()))
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull Resource generateResource(@NonNull ResourceJSON resourceJSON) {
        Objects.requireNonNull(resourceJSON, "resourceJSON cannot be null!");

        return ReflectionUtils.getClassesOf(ResourceImpl.class, "fr.univnantes.alma.commons.resource.type").stream()
                .map(type -> ReflectionUtils.getInstancesOf(type).amount(resourceJSON.getAmount()))
                .filter(resource -> resource.getName().equals(resourceJSON.getName()))
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull List<Resource> generateResources(@NonNull List<ResourceJSON> resourcesJSON) {
        Objects.requireNonNull(resourcesJSON, "resourcesJSON cannot be null!");

        return resourcesJSON.stream()
                .map(this::generateResource)
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasResource(@NonNull Resource resource) {
        Objects.requireNonNull(resource, "resource cannot be null!");

        return Optional.of(resources.get(resource.getName()))
                .map(r -> r.getAmount() >= resource.getAmount())
                .orElse(false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasResources(@NonNull List<Resource> resources) {
        Objects.requireNonNull(resources, "resources cannot be null!");

        return resources.stream().allMatch(this::hasResource);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addResource(@NonNull Resource resource) {
        Objects.requireNonNull(resource, "resource cannot be null!");

        Optional.of(resources.get(resource.getName()))
                .ifPresent(r -> r.increaseAmount(resource.getAmount()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addResources(@NonNull List<Resource> resources) {
        Objects.requireNonNull(resources, "resources cannot be null!");

        resources.forEach(this::addResource);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeResource(@NonNull Resource resource) {
        Objects.requireNonNull(resource, "resource cannot be null!");

        Optional.of(resources.get(resource.getName()))
                .ifPresent(r -> r.decreaseAmount(resource.getAmount()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeResources(@NonNull List<Resource> resources) {
        Objects.requireNonNull(resources, "resources cannot be null!");

        resources.forEach(this::removeResource);
    }

}