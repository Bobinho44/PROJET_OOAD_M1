package fr.univnantes.alma.core.client;

import fr.univnantes.alma.core.game.GameJSON;
import fr.univnantes.alma.core.command.CommandJSON;
import fr.univnantes.alma.core.exception.UndefinedClientGameException;
import fr.univnantes.alma.core.notification.NotificationJSON;
import org.springframework.lang.NonNull;

/**
 * Interface representing a client manager
 */
public interface ClientManager {

    /**
     * Connects the client
     *
     * @param client the client
     */
    void connect(@NonNull Client client);

    /**
     * Checks if the client is connected
     *
     * @param client the client
     * @return true if the client is connected, false otherwise
     */
    boolean isConnected(@NonNull Client client);

    /**
     * Disconnects the client
     *
     * @param client the client
     */
    void disconnect(@NonNull Client client);

    /**
     * Makes the client join a game
     *
     * @param client the client
     */
    void join(@NonNull Client client);

    /**
     * Gets the game of the client
     *
     * @param client the client
     * @return the game of the client
     * @throws UndefinedClientGameException if the client does not have game
     */
    @NonNull GameJSON getGame(@NonNull Client client) throws UndefinedClientGameException;

    /**
     * Checks if the client has a game
     *
     * @param client the client
     * @return true if the client has a game, false otherwise
     */
    boolean hasGame(@NonNull Client client);

    /**
     * Makes the client execute the command
     *
     * @param client the client
     * @param commandJSON the command information
     * @return the command result information
     */
    @NonNull NotificationJSON executeCommand(@NonNull Client client, @NonNull CommandJSON commandJSON);

}