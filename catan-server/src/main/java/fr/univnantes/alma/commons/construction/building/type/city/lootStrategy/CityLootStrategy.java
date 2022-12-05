package fr.univnantes.alma.commons.construction.building.type.city.lootStrategy;

import fr.univnantes.alma.commons.construction.building.Building;
import fr.univnantes.alma.core.construction.constructableArea.ConstructableArea;
import fr.univnantes.alma.core.construction.lootStrategy.LootStrategy;

public class CityLootStrategy implements LootStrategy<Building> {

    public static final CityLootStrategy CITY_LOOT_STRATEGY = new CityLootStrategy();

    @Override
    public int getLootAmount(ConstructableArea<Building> area) {
        return 2;
    }

}