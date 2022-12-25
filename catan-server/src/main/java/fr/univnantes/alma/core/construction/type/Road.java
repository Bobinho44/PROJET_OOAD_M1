package fr.univnantes.alma.core.construction.type;

import fr.univnantes.alma.core.player.Player;
import fr.univnantes.alma.core.construction.Construction;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.UUID;

public abstract class Road implements Construction {

    /**
     * Fields
     */
    private final UUID uuid;
    private final Player owner;

    /**
     * Creates a new road
     *
     * @param owner the owner
     */
    public Road(@NonNull Player owner) {
        this.uuid = UUID.randomUUID();
        this.owner = owner;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull UUID getUUID() {
        return uuid;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull Player getOwner() {
        return owner;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(@Nullable Object o) {
        if (this == o) return true;
        if (!(o instanceof Road)) return false;

        return uuid.equals(((Road) o).getUUID());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return uuid.hashCode();
    }

}