package fr.univnantes.alma.commons.construction.type.building.colony.lootStrategy;

import fr.univnantes.alma.core.construction.type.Building;
import fr.univnantes.alma.core.construction.constructableArea.ConstructableArea;
import fr.univnantes.alma.core.construction.lootStrategy.LootStrategy;
import org.springframework.lang.NonNull;

public class ColonyLootStrategy implements LootStrategy<Building> {

    /**
     * Singleton
     */
    public static final ColonyLootStrategy COLONY_LOOT_STRATEGY = new ColonyLootStrategy();

    /**
     * {@inheritDoc}
     */
    @Override
    public int getLootAmount(@NonNull ConstructableArea<Building> area) {
        return 1;
    }

}