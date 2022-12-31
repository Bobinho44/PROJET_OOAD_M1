package fr.univnantes.alma.commons.client;

import fr.univnantes.alma.commons.notification.NotificationNoReplyJSON;
import fr.univnantes.alma.core.client.IClient;
import fr.univnantes.alma.core.client.IClientManager;
import fr.univnantes.alma.core.command.ICommandJSON;
import fr.univnantes.alma.core.game.IGameJSON;
import fr.univnantes.alma.core.exception.UndefinedClientGameException;
import fr.univnantes.alma.core.notification.INotificationJSON;
import fr.univnantes.alma.core.game.IGameManager;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Implementation of a client manager
 */
public class ClientManager implements IClientManager {

    /**
     * Fields
     */
    private final List<IClient> clients = new ArrayList<>();
    private final IGameManager gameManager;

    /**
     * Creates a new client manager
     *
     * @param gameManager the game manager
     */
    public ClientManager(@NonNull IGameManager gameManager) {
        Objects.requireNonNull(gameManager, "gameManager cannot be null!");

        this.gameManager = gameManager;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void connect(@NonNull IClient client) {
        Objects.requireNonNull(client, "client cannot be null!");

        clients.add(client);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isConnected(@NonNull IClient client) {
        Objects.requireNonNull(client, "client cannot be null!");

        return clients.contains(client);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void disconnect(@NonNull IClient client) {
        Objects.requireNonNull(client, "client cannot be null!");

        clients.remove(client);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void join(@NonNull IClient client) {
        Objects.requireNonNull(client, "client cannot be null!");

        client.updateGameInformation(gameManager.join(client.getPlayer()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull IGameJSON getGame(@NonNull IClient client) throws UndefinedClientGameException {
        Objects.requireNonNull(client, "client cannot be null!");

        return client.getGame().orElseThrow(UndefinedClientGameException::new);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasGame(@NonNull IClient client) {
        Objects.requireNonNull(client, "client cannot be null!");

        return client.getGame().isPresent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull INotificationJSON executeCommand(@NonNull IClient client, @NonNull ICommandJSON commandJSON) {
        Objects.requireNonNull(client, "client cannot be null!");
        Objects.requireNonNull(commandJSON, "commandJSON cannot be null!");

        //Checks if the client has game
        if (!hasGame(client)) {
            return NotificationNoReplyJSON.CLIENT_HAS_NO_GAME;
        }

        //Gets server data
        IGameJSON gameJSON = getGame(client);

        //Executes the command
        INotificationJSON notificationJSON = gameManager.executeCommand(gameJSON, commandJSON);

        //Checks if the command need a reply
        if (notificationJSON.needReply()) {
            askPlayerReply(notificationJSON);
        }

        //Updates the game information of all players in the same game
        else {
            IGameJSON updatedGameJSON = gameManager.updateInformation(gameJSON);
            clients.stream()
                    .filter(this::hasGame)
                    .filter(testedClient -> getGame(testedClient).getUUID().equals(gameJSON.getUUID()))
                    .forEach(inGameClient -> inGameClient.updateGameInformation(updatedGameJSON));
        }

        return notificationJSON;
    }

    /**
     * Asks a reply to a client
     *
     * @param notificationJSON the notification information
     */
    private void askPlayerReply(@NonNull INotificationJSON notificationJSON) {
        Objects.requireNonNull(notificationJSON, "notificationJSON cannot be null!");

        //Ask a reply to the notification receiver (first argument in reply information)
        clients.stream()
                .filter(client -> client.getPlayer().equals(notificationJSON.replyInformation().get(0)))
                .findFirst()
                .ifPresent(receiver -> receiver.reply(notificationJSON));
    }

}