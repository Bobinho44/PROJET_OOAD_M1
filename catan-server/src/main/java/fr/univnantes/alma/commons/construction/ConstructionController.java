package fr.univnantes.alma.commons.construction;

import fr.univnantes.alma.core.construction.Construction;
import fr.univnantes.alma.core.construction.ConstructionManager;
import fr.univnantes.alma.core.construction.constructStrategy.ConstructStrategy;
import fr.univnantes.alma.core.construction.constructableArea.ConstructableArea;
import fr.univnantes.alma.core.ressource.Resource;
import org.springframework.core.GenericTypeResolver;
import org.springframework.lang.NonNull;

import java.util.*;

public class ConstructionController implements ConstructionManager {

    /**
     * Fields
     */
    private final Map<UUID, ConstructableArea<? extends Construction>> constructableAreas = new HashMap<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends Construction> Optional<ConstructableArea<T>> getConstructableArea(@NonNull UUID uuid, @NonNull Class<T> type) {
        return constructableAreas.entrySet().stream()
                .filter(constructableArea -> constructableArea.getKey().equals(uuid))
                .filter(constructableArea -> GenericTypeResolver.resolveTypeArgument(constructableArea.getValue().getConstructStrategy().getClass(), ConstructStrategy.class).isAssignableFrom(type))
                .map(constructableArea -> (ConstructableArea<T>) constructableArea.getValue())
                .findFirst();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends Construction> List<Resource> getConstructionCost(@NonNull Class<T> type) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <A extends Construction, T extends A> boolean isConstructable(@NonNull ConstructableArea<A> area, @NonNull T construction) {
        return area.isConstructable(construction);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <A extends Construction, T extends A> void construct(@NonNull ConstructableArea<A> area, @NonNull T construction) {
        area.construct(construction);
    }

}