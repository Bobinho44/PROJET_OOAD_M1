package fr.univnantes.alma.commons.town;

import java.util.List;

import fr.univnantes.alma.Player;
import fr.univnantes.alma.commons.road.BuildableRoadArea;
import fr.univnantes.alma.core.town.Town;

public class BuildableTownArea {
    private final List<BuildableTownArea> neighbours;
    private final List<BuildableRoadArea> roadsAround;
    private Town town = null;

    public BuildableTownArea(List<BuildableTownArea> neighbours, List<BuildableRoadArea> roadsAround) {
        this.neighbours = neighbours;
        this.roadsAround = roadsAround;
    }


    public Town getTown(){
        return town;
    }

    public boolean constructable(Player p){
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

}
