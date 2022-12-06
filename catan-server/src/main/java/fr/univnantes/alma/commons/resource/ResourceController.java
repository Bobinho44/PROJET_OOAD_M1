package fr.univnantes.alma.commons.resource;

import fr.univnantes.alma.commons.annotation.CardAmount;
import fr.univnantes.alma.commons.utils.reflection.ReflectionUtils;
import fr.univnantes.alma.core.ressource.Resource;
import fr.univnantes.alma.core.ressource.ResourceManager;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.stream.Collectors;

public class ResourceController implements ResourceManager {

    /**
     * Fields
     */
    public final List<Resource> resources;

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
    private @NonNull List<Resource> createResourceDeck() {
        return ReflectionUtils.getClassesOf(Resource.class, "fr.univnantes.alma.commons.resource.type").stream()
                .map(type -> ReflectionUtils.getInstancesOf(type).amount(type.getAnnotation(CardAmount.class).value()))
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canPickResource(@NonNull Resource resource, int amount) {
        return resources.stream()
                .filter(pickableResource -> pickableResource.isSimilar(resource))
                .anyMatch(pickSpecialCard -> pickSpecialCard.getAmount() >= amount);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeResource(@NonNull Resource resource, int amount) {
        resources.stream()
                .filter(pickableResource -> pickableResource.isSimilar(resource))
                .filter(pickSpecialCard -> pickSpecialCard.getAmount() >= amount)
                .findFirst()
                .ifPresent(pickableResource -> pickableResource.decreaseAmount(amount));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addResource(@NonNull Resource resource, int amount) {
        resources.stream()
                .filter(pickableResource -> pickableResource.isSimilar(resource))
                .filter(pickSpecialCard -> pickSpecialCard.getAmount() >= amount)
                .findFirst()
                .ifPresent(pickableResource -> pickableResource.increaseAmount(amount));
    }

}