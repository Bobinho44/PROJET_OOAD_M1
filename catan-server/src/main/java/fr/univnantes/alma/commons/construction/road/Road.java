package fr.univnantes.alma.commons.construction.road;

import fr.univnantes.alma.core.player.Player;
import fr.univnantes.alma.core.construction.Construction;

public class Road implements Construction {
    private final Player owner;

    public Road(Player owner) {
        this.owner = owner;
    }

    @Override
    public Player getOwner() {
        return owner;
    }

}