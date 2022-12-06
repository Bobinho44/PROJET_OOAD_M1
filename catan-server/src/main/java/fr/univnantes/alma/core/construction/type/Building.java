package fr.univnantes.alma.core.construction.type;

import fr.univnantes.alma.core.player.Player;
import fr.univnantes.alma.core.construction.Construction;
import org.springframework.lang.NonNull;

public abstract class Building implements Construction {

    /**
     * Fields
     */
    private final Player owner;

    /**
     * Creates a new building
     *
     * @param owner the owner
     */
    public Building(@NonNull Player owner) {
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
