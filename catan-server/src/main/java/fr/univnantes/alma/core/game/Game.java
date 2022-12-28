package fr.univnantes.alma.core.game;

import fr.univnantes.alma.core.notification.NotificationJSON;
import fr.univnantes.alma.core.player.PlayerJSON;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.UUID;

/**
 * Interface representing a game
 */
public interface Game {

    /**
     * Gets the uuid
     *
     * @return the uuid
     */
    @NonNull UUID getUUID();

    /**
     * Gets the game information
     *
     * @return the game information
     */
    @NonNull GameJSON getGameInformation();

    /**
     * Checks if the game is full
     *
     * @return true if the game is full, false otherwise
     */
    boolean isFull();

    /**
     * Adds the player to the game
     *
     * @param playerJSON the player information
     */
    void addPlayer(@NonNull PlayerJSON playerJSON);

    /**
     * Removes the player from the game
     *
     * @param playerJSON the player information
     */
    void removePlayer(@NonNull PlayerJSON playerJSON);

    /**
     * Checks if the player can play
     *
     * @param playerJSON the player information
     * @return true if the player from the game, false otherwise
     */
    boolean canPlay(@NonNull PlayerJSON playerJSON);

    /**
     * Executes a command
     *
     * @param name the name
     * @param parameters the parameters
     * @return the result notification
     */
    @NonNull NotificationJSON executeCommand(@NonNull String name, @NonNull List<Object> parameters);

}