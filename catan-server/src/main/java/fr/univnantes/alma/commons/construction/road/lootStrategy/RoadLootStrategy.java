package fr.univnantes.alma.commons.construction.road.lootStrategy;

import fr.univnantes.alma.commons.construction.road.Road;
import fr.univnantes.alma.core.construction.constructableArea.ConstructableArea;
import fr.univnantes.alma.core.construction.lootStrategy.LootStrategy;

public class RoadLootStrategy implements LootStrategy<Road> {

    public static final RoadLootStrategy ROAD_LOOT_STRATEGY = new RoadLootStrategy();

    @Override
    public int getLootAmount(ConstructableArea<Road> area) {
        return 0;
    }

}