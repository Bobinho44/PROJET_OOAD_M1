package fr.univnantes.alma.commons.construction.type.building.colony.lootStrategy;

import fr.univnantes.alma.core.construction.type.Building;
import fr.univnantes.alma.core.construction.constructableArea.Area;
import fr.univnantes.alma.core.construction.lootStrategy.LootStrategy;
import org.springframework.lang.NonNull;

import java.util.Objects;

/**
 * Implementation of a building loot strategy: colony loot strategy
 */
public class ColonyLootStrategy implements LootStrategy<Building> {

    /**
     * Singleton
     */
    public static final ColonyLootStrategy COLONY_LOOT_STRATEGY = new ColonyLootStrategy();

    /**
     * {@inheritDoc}
     */
    @Override
    public int getLootAmount(@NonNull Area<Building> area) {
        Objects.requireNonNull(area, "area cannot be null!");

        return 1;
    }

}