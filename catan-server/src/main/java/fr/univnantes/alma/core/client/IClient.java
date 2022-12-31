package fr.univnantes.alma.core.client;

import fr.univnantes.alma.core.game.IGameJSON;
import fr.univnantes.alma.core.notification.INotificationJSON;
import fr.univnantes.alma.core.player.IPlayerJSON;
import org.springframework.lang.NonNull;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;

/**
 * Interface representing a client
 */
public interface IClient {

    /**
     * Gets the player information
     *
     * @return the player information
     */
    @NonNull
    IPlayerJSON getPlayer();

    /**
     * Gets the game information
     *
     * @return the game information
     */
    @NonNull Optional<IGameJSON> getGame();

    /**
     * Executes a command
     *
     * @param name the name
     * @param parameters the parameters
     * @return the result command information
     */
    @NonNull
    INotificationJSON executeCommand(@Nonnull String name, @NonNull List<Object> parameters);

    /**
     * Updates the game information
     *
     * @param gameJSON the actual game information
     */
    void updateGameInformation(@NonNull IGameJSON gameJSON);

    /**
     * Replies to the previous command
     *
     * @param notificationJSON the previous command result information
     */
    void reply(@NonNull INotificationJSON notificationJSON);

}