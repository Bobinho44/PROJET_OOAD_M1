package fr.univnantes.alma.core.town;

import fr.univnantes.alma.Player;
import fr.univnantes.alma.core.ressource.Resource;

import java.util.List;

public interface Town {
    public Player getPlayer();

    public List<Resource> giveRessource(Resource typeRessource);

}
