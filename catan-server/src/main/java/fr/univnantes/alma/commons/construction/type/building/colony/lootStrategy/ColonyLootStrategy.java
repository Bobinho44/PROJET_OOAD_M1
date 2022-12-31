package fr.univnantes.alma.commons.construction.type.building.colony.lootStrategy;

import fr.univnantes.alma.core.construction.type.Building;
import fr.univnantes.alma.core.construction.constructableArea.IArea;
import fr.univnantes.alma.core.construction.lootStrategy.ILootStrategy;
import org.springframework.lang.NonNull;

import java.util.Objects;

/**
 * Implementation of a building loot strategy: colony loot strategy
 */
public class ColonyLootStrategy implements ILootStrategy<Building> {

    /**
     * Singleton
     */
    public static final ColonyLootStrategy COLONY_LOOT_STRATEGY = new ColonyLootStrategy();

    /**
     * {@inheritDoc}
     */
    @Override
    public int getLootAmount(@NonNull IArea<Building> area) {
        Objects.requireNonNull(area, "area cannot be null!");

        return 1;
    }

}