package fr.univnantes.alma.core.game;

import fr.univnantes.alma.core.notification.INotificationJSON;
import fr.univnantes.alma.core.player.IPlayerJSON;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.UUID;

/**
 * Interface representing a game
 */
public interface IGame {

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
    @NonNull
    IGameJSON getGameInformation();

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
    void addPlayer(@NonNull IPlayerJSON playerJSON);

    /**
     * Removes the player from the game
     *
     * @param playerJSON the player information
     */
    void removePlayer(@NonNull IPlayerJSON playerJSON);

    /**
     * Checks if the player can play
     *
     * @param playerJSON the player information
     * @return true if the player from the game, false otherwise
     */
    boolean canPlay(@NonNull IPlayerJSON playerJSON);

    /**
     * Executes a command
     *
     * @param name the name
     * @param parameters the parameters
     * @return the result notification
     */
    @NonNull
    INotificationJSON executeCommand(@NonNull String name, @NonNull List<Object> parameters);

}