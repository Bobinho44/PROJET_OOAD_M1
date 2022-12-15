package fr.univnantes.alma.commons.construction;

import fr.univnantes.alma.commons.annotation.ConstructionCost;
import fr.univnantes.alma.commons.construction.constructableArea.ConstructableAreaImpl;
import fr.univnantes.alma.commons.construction.type.building.colony.constructStrategy.ColonyConstructStrategy;
import fr.univnantes.alma.commons.construction.type.building.colony.lootStrategy.ColonyLootStrategy;
import fr.univnantes.alma.commons.resource.ResourceImpl;
import fr.univnantes.alma.commons.territory.type.PositionConstructableArea;
import fr.univnantes.alma.commons.utils.reflection.ReflectionUtils;
import fr.univnantes.alma.commons.utils.stream.IndexedStream;
import fr.univnantes.alma.core.construction.Construction;
import fr.univnantes.alma.core.construction.ConstructionManager;
import fr.univnantes.alma.core.construction.constructStrategy.ConstructStrategy;
import fr.univnantes.alma.core.construction.constructableArea.ConstructableArea;
import fr.univnantes.alma.core.player.Player;
import fr.univnantes.alma.core.ressource.Resource;
import fr.univnantes.alma.core.territory.Territory;
import org.springframework.core.GenericTypeResolver;
import org.springframework.lang.NonNull;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class ConstructionController implements ConstructionManager {

    /**
     * Fields
     */
    private final Map<UUID, ConstructableArea<? extends Construction>> constructableAreas = new HashMap<>();

    /**
     * Creates a new construction manager
     */
    public ConstructionController(@NonNull List<Territory> territories) {
        

        //TODO createConstructableArea
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends Construction> @NonNull Optional<ConstructableArea<T>> getConstructableArea(@NonNull UUID uuid, @NonNull Class<T> type) {
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
    public <T extends Construction> T getConstruction(@NonNull Player owner, @NonNull Class<T> type) {
        return ReflectionUtils.getInstancesOf(type);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends Construction> @NonNull List<Resource> getConstructionCost(@NonNull Class<T> type) {
        return Arrays.stream(type.getAnnotation(ConstructionCost.class).resources())
                .map(resource -> new ResourceImpl(resource.name()) {}.amount(resource.amount()))
                .toList();
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