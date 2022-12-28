package fr.univnantes.alma.core.construction.type;

import fr.univnantes.alma.core.player.Player;
import fr.univnantes.alma.core.construction.Construction;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.Objects;
import java.util.UUID;

/**
 * Abstract Class representing a building
 */
public abstract class Building implements Construction {

    /**
     * Fields
     */
    private final UUID uuid;
    private final Player owner;

    /**
     * Creates a new building
     *
     * @param owner the owner
     */
    public Building(@NonNull Player owner) {
        Objects.requireNonNull(owner, "owner cannot be null!");

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
        if (!(o instanceof Building)) return false;

        return uuid.equals(((Building) o).getUUID());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return uuid.hashCode();
    }

}