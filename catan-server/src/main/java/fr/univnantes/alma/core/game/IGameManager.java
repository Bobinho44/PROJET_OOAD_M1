package fr.univnantes.alma.core.game;

import fr.univnantes.alma.core.command.ICommandJSON;
import fr.univnantes.alma.core.notification.INotificationJSON;
import fr.univnantes.alma.core.player.IPlayerJSON;
import org.springframework.lang.NonNull;

/**
 * Interface representing a game manager
 */
public interface IGameManager {

    /**
     * Gets a game
     *
     * @param gameJSON the game information
     * @return the optional game
     */
    @NonNull
    IGame getGame(@NonNull IGameJSON gameJSON);

    /**
     * Checks if the game exist
     *
     * @param gameJSON the game information
     * @return true if the game exist, false otherwise
     */
    boolean hasGame(@NonNull IGameJSON gameJSON);

    /**
     * Adds a new game
     *
     * @param gameJSON the game information
     */
    void addGame(@NonNull IGameJSON gameJSON);

    /**
     * Removes a game
     *
     * @param game the game
     */
    void removeGame(@NonNull IGame game);

    /**
     * Joins a game
     *
     * @param playerJSON the player json information
     * @return the game json information
     */
    @NonNull
    IGameJSON join(@NonNull IPlayerJSON playerJSON);

    /**
     * Leaves a game
     *
     * @param gameJSON the game json information
     * @param playerJSON the player json information
     */
    void leave(@NonNull IGameJSON gameJSON, @NonNull IPlayerJSON playerJSON);

    /**
     * Executes a command
     *
     * @param gameJSON the game json information
     * @param commandJSON the command json information
     * @return the notification json information
     */
    @NonNull
    INotificationJSON executeCommand(@NonNull IGameJSON gameJSON, @NonNull ICommandJSON commandJSON);

    /**
     * Updates the game information
     *
     * @param gameJSON the actual game information
     * @return the updated game information
     */
    @NonNull
    IGameJSON updateInformation(@NonNull IGameJSON gameJSON);

}