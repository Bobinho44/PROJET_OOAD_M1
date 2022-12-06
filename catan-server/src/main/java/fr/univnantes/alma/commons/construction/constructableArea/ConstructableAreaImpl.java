package fr.univnantes.alma.commons.construction.constructableArea;

import fr.univnantes.alma.core.construction.type.Building;
import fr.univnantes.alma.commons.dock.Dock;
import fr.univnantes.alma.core.construction.Construction;
import fr.univnantes.alma.core.construction.constructStrategy.ConstructStrategy;
import fr.univnantes.alma.core.construction.constructableArea.ConstructableArea;
import fr.univnantes.alma.core.construction.lootStrategy.LootStrategy;
import fr.univnantes.alma.core.construction.type.Road;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public class ConstructableAreaImpl<T extends Construction> implements ConstructableArea<T> {

    private final List<ConstructableArea<Building>> buildingNeighbours;
    private final List<ConstructableArea<Road>> roadNeighbours;
    private T construction;
    private ConstructStrategy<T> constructStrategy;
    private LootStrategy<T> lootStrategy;
    private final Dock dock;

    public ConstructableAreaImpl(List<ConstructableArea<Building>> buildingNeighbours, List<ConstructableArea<Road>> roadNeighbours, Dock dock, ConstructStrategy<T> constructStrategy, LootStrategy<T> lootStrategy) {
        this.buildingNeighbours = buildingNeighbours;
        this.roadNeighbours = roadNeighbours;
        this.constructStrategy = constructStrategy;
        this.lootStrategy = lootStrategy;
        this.dock = dock;
    }

    public @NonNull List<ConstructableArea<Building>> getBuildingNeighbours() {
        return buildingNeighbours;
    }

    public @NonNull List<ConstructableArea<Road>> getRoadNeighbours() {
        return roadNeighbours;
    }

    public @NonNull Optional<T> getConstruction(){
        return Optional.ofNullable(construction);
    }

    public void setConstruction(@NonNull T construction) {
        this.construction = construction;
    }

    public void setConstructStrategy(@NonNull ConstructStrategy<T> constructStrategy) {
        this.constructStrategy = constructStrategy;
    }

    public ConstructStrategy<T> getConstructStrategy() {
        return constructStrategy;
    }

    public boolean isConstructable(@NonNull T construction) {
        return constructStrategy.isConstructable(this, construction);
    }

    public void construct(@NonNull T construction) {
        constructStrategy.construct(this, construction);
    }

    public int getLootAmount() {
        return lootStrategy.getLootAmount(this);
    }

}
