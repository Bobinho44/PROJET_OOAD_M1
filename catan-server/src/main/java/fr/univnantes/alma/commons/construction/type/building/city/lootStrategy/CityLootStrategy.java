package fr.univnantes.alma.commons.construction.type.building.city.lootStrategy;

import fr.univnantes.alma.core.construction.type.Building;
import fr.univnantes.alma.core.construction.constructableArea.Area;
import fr.univnantes.alma.core.construction.lootStrategy.LootStrategy;
import org.springframework.lang.NonNull;

import java.util.Objects;

/**
 * Implementation of a building loot strategy: city loot strategy
 */
public class CityLootStrategy implements LootStrategy<Building> {

    /**
     * Singleton
     */
    public static final CityLootStrategy CITY_LOOT_STRATEGY = new CityLootStrategy();

    /**
     * {@inheritDoc}
     */
    @Override
    public int getLootAmount(@NonNull Area<Building> area) {
        Objects.requireNonNull(area, "area cannot be null!");

        return 2;
    }

}