package fr.univnantes.alma.core.client;

import fr.univnantes.alma.core.game.IGameJSON;
import fr.univnantes.alma.core.command.ICommandJSON;
import fr.univnantes.alma.core.exception.UndefinedClientGameException;
import fr.univnantes.alma.core.notification.INotificationJSON;
import org.springframework.lang.NonNull;

/**
 * Interface representing a client manager
 */
public interface IClientManager {

    /**
     * Connects the client
     *
     * @param client the client
     */
    void connect(@NonNull IClient client);

    /**
     * Checks if the client is connected
     *
     * @param client the client
     * @return true if the client is connected, false otherwise
     */
    boolean isConnected(@NonNull IClient client);

    /**
     * Disconnects the client
     *
     * @param client the client
     */
    void disconnect(@NonNull IClient client);

    /**
     * Makes the client join a game
     *
     * @param client the client
     */
    void join(@NonNull IClient client);

    /**
     * Gets the game of the client
     *
     * @param client the client
     * @return the game of the client
     * @throws UndefinedClientGameException if the client does not have game
     */
    @NonNull
    IGameJSON getGame(@NonNull IClient client) throws UndefinedClientGameException;

    /**
     * Checks if the client has a game
     *
     * @param client the client
     * @return true if the client has a game, false otherwise
     */
    boolean hasGame(@NonNull IClient client);

    /**
     * Makes the client execute the command
     *
     * @param client the client
     * @param commandJSON the command information
     * @return the command result information
     */
    @NonNull
    INotificationJSON executeCommand(@NonNull IClient client, @NonNull ICommandJSON commandJSON);

}