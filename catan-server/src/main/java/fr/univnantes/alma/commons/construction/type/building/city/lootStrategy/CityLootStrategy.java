package fr.univnantes.alma.commons.construction.type.building.city.lootStrategy;

import fr.univnantes.alma.core.construction.type.Building;
import fr.univnantes.alma.core.construction.constructableArea.ConstructableArea;
import fr.univnantes.alma.core.construction.lootStrategy.LootStrategy;
import org.springframework.lang.NonNull;

public class CityLootStrategy implements LootStrategy<Building> {

    /**
     * Singleton
     */
    public static final CityLootStrategy CITY_LOOT_STRATEGY = new CityLootStrategy();

    /**
     * {@inheritDoc}
     */
    @Override
    public int getLootAmount(@NonNull ConstructableArea<Building> area) {
        return 2;
    }

}