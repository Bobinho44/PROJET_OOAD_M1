package fr.univnantes.alma.core.construction.lootStrategy;

import fr.univnantes.alma.core.construction.Construction;
import fr.univnantes.alma.core.construction.constructableArea.Area;
import org.springframework.lang.NonNull;

/**
 * Interface representing a loot strategy
 */
public interface LootStrategy <T extends Construction> {

    /**
     * Gets the loot amount
     *
     * @param area the area
     * @return the loot amount
     */
    int getLootAmount(@NonNull Area<T> area);

}