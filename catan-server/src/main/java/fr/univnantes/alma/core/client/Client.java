package fr.univnantes.alma.core.client;

import fr.univnantes.alma.core.game.GameJSON;
import fr.univnantes.alma.core.notification.NotificationJSON;
import fr.univnantes.alma.core.player.PlayerJSON;
import org.springframework.lang.NonNull;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;

/**
 * Interface representing a client
 */
public interface Client {

    /**
     * Gets the player information
     *
     * @return the player information
     */
    @NonNull PlayerJSON getPlayer();

    /**
     * Gets the game information
     *
     * @return the game information
     */
    @NonNull Optional<GameJSON> getGame();

    /**
     * Executes a command
     *
     * @param name the name
     * @param parameters the parameters
     * @return the result command information
     */
    @NonNull NotificationJSON executeCommand(@Nonnull String name, @NonNull List<Object> parameters);

    /**
     * Updates the game information
     *
     * @param gameJSON the actual game information
     */
    void updateGameInformation(@NonNull GameJSON gameJSON);

    /**
     * Replies to the previous command
     *
     * @param notificationJSON the previous command result information
     */
    void reply(@NonNull NotificationJSON notificationJSON);

}