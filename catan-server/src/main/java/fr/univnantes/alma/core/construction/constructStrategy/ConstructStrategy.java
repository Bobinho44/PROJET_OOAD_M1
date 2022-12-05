package fr.univnantes.alma.core.construction.constructStrategy;

import fr.univnantes.alma.core.construction.Construction;
import fr.univnantes.alma.core.construction.constructableArea.ConstructableArea;

public interface ConstructStrategy<T extends Construction> {

    boolean isConstructable(ConstructableArea<T> area, T construction);

    void construct(ConstructableArea<T> area, T construction);

}
