package fr.univnantes.alma.commons.construction.building;

import fr.univnantes.alma.core.player.Player;
import fr.univnantes.alma.core.construction.Construction;
import org.springframework.lang.NonNull;

public abstract class Building implements Construction {

    private final Player owner;

    public Building(@NonNull Player owner) {
        this.owner = owner;
    }

    @Override
    public Player getOwner() {
        return owner;
    }
}
