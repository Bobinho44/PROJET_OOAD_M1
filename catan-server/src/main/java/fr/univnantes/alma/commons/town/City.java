package fr.univnantes.alma.commons.town;

import fr.univnantes.alma.Player;
import fr.univnantes.alma.core.ressource.Resource;
import fr.univnantes.alma.core.town.Town;

import java.util.ArrayList;
import java.util.List;

public class City implements Town {
    final Player player;

    public City(Player player) {
        this.player = player;
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public List<Resource> giveRessource(Resource typeRessource) {
        List<Resource> ressources = new ArrayList<>();
        //TODO: prend 2 ressource dans les ressources dans la banque et les retourne
        return ressources;
    }

    @Override
    public String toString() {
        return "City";
    }
}
