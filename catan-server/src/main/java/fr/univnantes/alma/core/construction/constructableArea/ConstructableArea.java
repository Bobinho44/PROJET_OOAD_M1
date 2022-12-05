package fr.univnantes.alma.core.construction.constructableArea;

import fr.univnantes.alma.commons.construction.building.Building;
import fr.univnantes.alma.commons.construction.road.Road;
import fr.univnantes.alma.commons.dock.Dock;
import fr.univnantes.alma.core.construction.Construction;
import fr.univnantes.alma.core.construction.constructStrategy.ConstructStrategy;
import fr.univnantes.alma.core.construction.lootStrategy.LootStrategy;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public interface ConstructableArea<T extends Construction> {

    @NonNull List<ConstructableArea<Building>> getBuildingNeighbours();

    @NonNull List<ConstructableArea<Road>> getRoadNeighbours();

    @NonNull Optional<T> getConstruction();

    void setConstruction(@NonNull T construction);

    void setConstructStrategy(@NonNull ConstructStrategy<T> constructStrategy);

    ConstructStrategy<T> getConstructStrategy();

    boolean isConstructable(@NonNull T construction);

    void construct(@NonNull T construction);

    int getLootAmount();

}