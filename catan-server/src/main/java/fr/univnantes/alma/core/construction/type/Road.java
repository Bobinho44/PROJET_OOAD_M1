package fr.univnantes.alma.core.construction.type;

import fr.univnantes.alma.core.player.Player;
import fr.univnantes.alma.core.construction.Construction;
import org.springframework.lang.NonNull;

public abstract class Road implements Construction {

    /**
     * Fields
     */
    private final Player owner;

    /**
     * Creates a new road
     *
     * @param owner the owner
     */
    public Road(@NonNull Player owner) {
        this.owner = owner;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull Player getOwner() {
        return owner;
    }

}