package fr.univnantes.alma.core.construction.lootStrategy;

import fr.univnantes.alma.core.construction.IConstruction;
import fr.univnantes.alma.core.construction.constructableArea.IArea;
import org.springframework.lang.NonNull;

/**
 * Interface representing a loot strategy
 */
public interface ILootStrategy<T extends IConstruction> {

    /**
     * Gets the loot amount
     *
     * @param area the area
     * @return the loot amount
     */
    int getLootAmount(@NonNull IArea<T> area);

}