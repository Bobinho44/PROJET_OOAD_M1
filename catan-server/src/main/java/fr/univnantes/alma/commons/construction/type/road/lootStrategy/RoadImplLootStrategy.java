package fr.univnantes.alma.commons.construction.type.road.lootStrategy;

import fr.univnantes.alma.core.construction.type.Road;
import fr.univnantes.alma.core.construction.constructableArea.Area;
import fr.univnantes.alma.core.construction.lootStrategy.LootStrategy;
import org.springframework.lang.NonNull;

import java.util.Objects;

/**
 * Implementation of a road loot strategy: roadImpl loot strategy
 */
public class RoadImplLootStrategy implements LootStrategy<Road> {

    /**
     * Singleton
     */
    public static final RoadImplLootStrategy ROAD_LOOT_STRATEGY = new RoadImplLootStrategy();

    /**
     * {@inheritDoc}
     */
    @Override
    public int getLootAmount(@NonNull Area<Road> area) {
        Objects.requireNonNull(area, "area cannot be null!");

        return 0;
    }

}