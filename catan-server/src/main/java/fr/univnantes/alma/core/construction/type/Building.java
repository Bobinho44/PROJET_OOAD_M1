package fr.univnantes.alma.core.construction.type;

import fr.univnantes.alma.core.player.IPlayer;
import fr.univnantes.alma.core.construction.IConstruction;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.Objects;
import java.util.UUID;

/**
 * Abstract Class representing a building
 */
public abstract class Building implements IConstruction {

    /**
     * Fields
     */
    private final UUID uuid;
    private final IPlayer owner;

    /**
     * Creates a new building
     *
     * @param owner the owner
     */
    public Building(@NonNull IPlayer owner) {
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
    public @NonNull IPlayer getOwner() {
        return owner;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(@Nullable Object o) {
        if (this == o) return true;
        if (!(o instanceof Building building)) return false;
        return Objects.equals(uuid, building.uuid);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }

}