package fr.univnantes.alma.commons.construction.type.road.lootStrategy;

import fr.univnantes.alma.core.construction.type.Path;
import fr.univnantes.alma.core.construction.constructableArea.IArea;
import fr.univnantes.alma.core.construction.lootStrategy.ILootStrategy;
import org.springframework.lang.NonNull;

import java.util.Objects;

/**
 * Implementation of a road loot strategy: roadImpl loot strategy
 */
public class RoadLootStrategy implements ILootStrategy<Path> {

    /**
     * Singleton
     */
    public static final RoadLootStrategy ROAD_LOOT_STRATEGY = new RoadLootStrategy();

    /**
     * {@inheritDoc}
     */
    @Override
    public int getLootAmount(@NonNull IArea<Path> area) {
        Objects.requireNonNull(area, "area cannot be null!");

        return 0;
    }

}