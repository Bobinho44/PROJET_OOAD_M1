package fr.univnantes.alma.core.card.type;

import fr.univnantes.alma.core.notification.NotificationJSON;
import fr.univnantes.alma.commons.notification.NotificationNoReplyJSON;
import fr.univnantes.alma.core.card.Card;
import fr.univnantes.alma.core.command.CommandManager;
import fr.univnantes.alma.core.player.Player;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Abstract Class representing special cards
 */
public abstract class SpecialCard implements Card {


    /**
     * Fields
     */
    private final UUID uuid;
    private final String name;
    private final String picture;
    private Player owner;

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
     * Creates a new special card
     *
     * @param uuid the uuid
     * @param name the name
     * @param picture the picture
     */
    public SpecialCard(@NonNull UUID uuid, @NonNull String name, @NonNull String picture) {
        Objects.requireNonNull(uuid, "uuid cannot be null!");
        Objects.requireNonNull(name, "name cannot be null!");
        Objects.requireNonNull(picture, "picture cannot be null!");

        this.uuid = uuid;
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
    public @NonNull Player getOwner() {
        return owner;
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
     * Sets the owner
     *
     * @param owner the owner
     */
    public void setOwner(@NonNull Player owner) {
        this.owner = owner;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull NotificationJSON useEffect(@NonNull CommandManager commandManager, @NonNull Player player) {
        Objects.requireNonNull(commandManager, "commandManager cannot be null!");
        Objects.requireNonNull(player, "player cannot be null!");

        return NotificationNoReplyJSON.IMPOSSIBLE_ACTION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull NotificationJSON getEffect(@NonNull CommandManager commandManager, @NonNull Player player) {
        Objects.requireNonNull(commandManager, "commandManager cannot be null!");
        Objects.requireNonNull(player, "player cannot be null!");

        return commandManager.execute("addVictoryPoint", List.of(uuid, 1));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull NotificationJSON looseEffect(@NonNull CommandManager commandManager, @NonNull Player player) {
        Objects.requireNonNull(commandManager, "commandManager cannot be null!");
        Objects.requireNonNull(player, "player cannot be null!");

        return commandManager.execute("addVictoryPoint", List.of(uuid, -1));
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