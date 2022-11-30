package fr.univnantes.alma.commons.road;

import fr.univnantes.alma.Player;
import fr.univnantes.alma.core.buildArea.BuildableArea;

import java.util.List;

public class BuildableRoadArea<road> implements BuildableArea {
    private Road road = null;
    final private List<BuildableRoadArea> neighboursRoad;

    public BuildableRoadArea(List<BuildableRoadArea> neighboursRoad) {
        this.neighboursRoad = neighboursRoad;
    }

    public Road getRoad() {
        return road;
    }

    public Road placeRoad(){
        return null;
    }

    public boolean isPlacable(Player p){
        boolean b = false;
        for (BuildableRoadArea neighbourRoad:neighboursRoad) {
            if(neighbourRoad.getRoad().getPlayer() == p){
                b = true;
                break;
            }
        }
        return road == null && b;
    }
}
