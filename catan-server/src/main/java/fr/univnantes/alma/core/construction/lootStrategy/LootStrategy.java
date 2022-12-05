package fr.univnantes.alma.core.construction.lootStrategy;

import fr.univnantes.alma.core.construction.Construction;
import fr.univnantes.alma.core.construction.constructableArea.ConstructableArea;

public interface LootStrategy <T extends Construction> {

    int getLootAmount(ConstructableArea<T> area);

}