package fr.univnantes.alma.commons.town;

import java.util.List;

import fr.univnantes.alma.Player;
import fr.univnantes.alma.commons.dock.Dock;
import fr.univnantes.alma.commons.road.BuildableRoadArea;
import fr.univnantes.alma.core.town.Town;

public class BuildableTownArea {
    private final List<BuildableTownArea> neighbours;
    private final List<BuildableRoadArea> roadsAround;
    private Town town = null;
    private final Dock dock;

    public BuildableTownArea(List<BuildableTownArea> neighbours, List<BuildableRoadArea> roadsAround, Dock dock) {
        this.neighbours = neighbours;
        this.roadsAround = roadsAround;
        this.dock = dock;
    }


    public Town getTown(){
        return town;
    }

    public boolean placeColony(Colony colony, Player p){
        if(constructableColony(p)) {
            this.town = colony;
            return true;
        }
        return false;
    }

    public boolean constructableColony(Player p){
        if(town != null)
            return false;
        for (BuildableTownArea neighbour : neighbours) {
            if(neighbour.getTown() != null)
                return false;
        }
        for (BuildableRoadArea roadAround: roadsAround) {
            if(roadAround.getRoad().getPlayer() == p)
                return true;
        }
        return false;
    }

    public boolean placeCity(City city,Player p){
        if(constructableColony(p)) {
            this.town = city;
            return true;
        }
        return false;
    }

    public boolean constructableCity(Player p){
        if(town != null){
            return town.toString() == "Colony";
        }
        return false;
    }

}
