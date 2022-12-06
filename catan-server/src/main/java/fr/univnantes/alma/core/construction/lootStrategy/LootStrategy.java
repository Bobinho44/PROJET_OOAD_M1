package fr.univnantes.alma.core.construction.lootStrategy;

import fr.univnantes.alma.core.construction.Construction;
import fr.univnantes.alma.core.construction.constructableArea.ConstructableArea;
import org.springframework.lang.NonNull;

public interface LootStrategy <T extends Construction> {

    /**
     * Gets the loot amount
     *
     * @param area the area
     * @return the loot amount
     */
    int getLootAmount(@NonNull ConstructableArea<T> area);

}