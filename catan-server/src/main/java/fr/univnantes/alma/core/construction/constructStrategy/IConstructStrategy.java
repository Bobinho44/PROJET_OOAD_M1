package fr.univnantes.alma.core.construction.constructStrategy;

import fr.univnantes.alma.core.construction.IConstruction;
import fr.univnantes.alma.core.construction.constructableArea.IArea;
import org.springframework.lang.NonNull;

/**
 * Interface representing a construct strategy
 */
public interface IConstructStrategy<T extends IConstruction> {

    /**
     * Checks if the construction is constructable
     *
     * @param area the area
     * @param construction the construction
     * @return true if the construction is constructable, false otherwise
     */
    boolean isConstructable(@NonNull IArea<T> area, @NonNull T construction);

    /**
     * Constructs the construction
     *
     * @param area the area
     * @param construction the construction
     */
    void construct(@NonNull IArea<T> area, @NonNull T construction);

}