package fr.univnantes.alma.core.construction.constructStrategy;

import fr.univnantes.alma.core.construction.Construction;
import fr.univnantes.alma.core.construction.constructableArea.Area;
import org.springframework.lang.NonNull;

/**
 * Interface representing a construct strategy
 */
public interface ConstructStrategy<T extends Construction> {

    /**
     * Checks if the construction is constructable
     *
     * @param area the area
     * @param construction the construction
     * @return true if the construction is constructable, false otherwise
     */
    boolean isConstructable(@NonNull Area<T> area, @NonNull T construction);

    /**
     * Constructs the construction
     *
     * @param area the area
     * @param construction the construction
     */
    void construct(@NonNull Area<T> area, @NonNull T construction);

}