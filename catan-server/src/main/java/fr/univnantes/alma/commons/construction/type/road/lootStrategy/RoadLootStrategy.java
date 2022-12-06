package fr.univnantes.alma.commons.construction.type.road.lootStrategy;

import fr.univnantes.alma.core.construction.type.Road;
import fr.univnantes.alma.core.construction.constructableArea.ConstructableArea;
import fr.univnantes.alma.core.construction.lootStrategy.LootStrategy;
import org.springframework.lang.NonNull;

public class RoadLootStrategy implements LootStrategy<Road> {

    /**
     * Singleton
     */
    public static final RoadLootStrategy ROAD_LOOT_STRATEGY = new RoadLootStrategy();

    /**
     * {@inheritDoc}
     */
    @Override
    public int getLootAmount(@NonNull ConstructableArea<Road> area) {
        return 0;
    }

}