package fr.univnantes.alma.commons.construction.building.type.colony.lootStrategy;

import fr.univnantes.alma.commons.construction.building.Building;
import fr.univnantes.alma.core.construction.constructableArea.ConstructableArea;
import fr.univnantes.alma.core.construction.lootStrategy.LootStrategy;

public class ColonyLootStrategy implements LootStrategy<Building> {

    public static final ColonyLootStrategy COLONY_LOOT_STRATEGY = new ColonyLootStrategy();

    @Override
    public int getLootAmount(ConstructableArea<Building> area) {
        return 1;
    }

}