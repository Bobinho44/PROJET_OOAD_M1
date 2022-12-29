package fr.univnantes.alma.core.game;

import fr.univnantes.alma.core.command.CommandJSON;
import fr.univnantes.alma.core.notification.NotificationJSON;
import fr.univnantes.alma.core.player.PlayerJSON;
import org.springframework.lang.NonNull;

/**
 * Interface representing a game manager
 */
public interface GameManager {

    /**
     * Gets a game
     *
     * @param gameJSON the game information
     * @return the optional game
     */
    @NonNull Game getGame(@NonNull GameJSON gameJSON);

    /**
     * Checks if the game exist
     *
     * @param gameJSON the game information
     * @return true if the game exist, false otherwise
     */
    boolean hasGame(@NonNull GameJSON gameJSON);

    /**
     * Adds a new game
     *
     * @param gameJSON the game information
     */
    void addGame(@NonNull GameJSON gameJSON);

    /**
     * Removes a game
     *
     * @param game the game
     */
    void removeGame(@NonNull Game game);

    /**
     * Joins a game
     *
     * @param playerJSON the player json information
     * @return the game json information
     */
    @NonNull GameJSON join(@NonNull PlayerJSON playerJSON);

    /**
     * Leaves a game
     *
     * @param gameJSON the game json information
     * @param playerJSON the player json information
     */
    void leave(@NonNull GameJSON gameJSON, @NonNull PlayerJSON playerJSON);

    /**
     * Executes a command
     *
     * @param gameJSON the game json information
     * @param commandJSON the command json information
     * @return the notification json information
     */
    @NonNull NotificationJSON executeCommand(@NonNull GameJSON gameJSON, @NonNull CommandJSON commandJSON);

    /**
     * Updates the game information
     *
     * @param gameJSON the actual game information
     * @return the updated game information
     */
    @NonNull GameJSON updateInformation(@NonNull GameJSON gameJSON);

}