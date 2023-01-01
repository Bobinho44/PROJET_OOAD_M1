package fr.univnantes.alma.core.card.type;

import fr.univnantes.alma.commons.command.CommandJSON;
import fr.univnantes.alma.core.card.ICard;
import fr.univnantes.alma.core.command.ICommandJSON;
import fr.univnantes.alma.core.player.IPlayer;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * Abstract Class representing a special card
 */
public abstract class SpecialCard implements ICard {

    /**
     * Fields
     */
    private final UUID uuid;
    private final String name;
    private final String picture;
    private IPlayer owner;

    /**
     * Creates a new special card
     *
     * @param name    the name
     * @param picture the picture
     */
    public SpecialCard(@NonNull String name, @NonNull String picture) {
        Objects.requireNonNull(name, "name cannot be null!");
        Objects.requireNonNull(picture, "picture cannot be null!");

        this.uuid = UUID.randomUUID();
        this.name = name;
        this.picture = picture;
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
    public @NonNull String getName() {
        return name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull String getPicture() {
        return picture;
    }

    /**
     * Gets owner
     *
     * @return the owner
     */
    public @NonNull Optional<IPlayer> getOwner() {
        return Optional.ofNullable(owner);
    }

    /**
     * Checks if the card has owner
     *
     * @return true if the card has owner, false otherwise
     */
    public boolean hasOwner() {
        return owner != null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull ICommandJSON useEffect(@NonNull IPlayer player) {
        Objects.requireNonNull(player, "player cannot be null!");

        if (hasOwner()) {
            new CommandJSON("updateVictoryPoint", List.of(owner, -1), false);
        }

        this.owner = player;

        return new CommandJSON("updateVictoryPoint", List.of(owner, 1), true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(@Nullable Object o) {
        if (this == o) return true;
        if (!(o instanceof SpecialCard)) return false;

        return Objects.equals(uuid, ((SpecialCard) o).getUUID());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return uuid.hashCode();
    }

}