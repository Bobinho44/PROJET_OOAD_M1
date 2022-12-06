package fr.univnantes.alma.core.construction.constructStrategy;

import fr.univnantes.alma.core.construction.Construction;
import fr.univnantes.alma.core.construction.constructableArea.ConstructableArea;
import org.springframework.lang.NonNull;

public interface ConstructStrategy<T extends Construction> {

    /**
     * Checks if the construction is constructable
     *
     * @param area the area
     * @param construction the construction
     * @return true if the construction is constructable, false otherwise
     */
    boolean isConstructable(@NonNull ConstructableArea<T> area, @NonNull T construction);

    /**
     * Constructs the construction
     *
     * @param area the area
     * @param construction the construction
     */
    void construct(@NonNull ConstructableArea<T> area, @NonNull T construction);

}
