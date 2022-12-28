package fr.univnantes.alma.commons.client;

import fr.univnantes.alma.commons.notification.NotificationNoReplyJSON;
import fr.univnantes.alma.core.client.Client;
import fr.univnantes.alma.core.client.ClientManager;
import fr.univnantes.alma.core.command.CommandJSON;
import fr.univnantes.alma.core.game.GameJSON;
import fr.univnantes.alma.core.exception.UndefinedClientGameException;
import fr.univnantes.alma.core.notification.NotificationJSON;
import fr.univnantes.alma.core.game.GameManager;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Implementation of a client manager
 */
public class ClientController implements ClientManager {

    /**
     * Fields
     */
    private final List<Client> clients = new ArrayList<>();
    private final GameManager gameManager;

    /**
     * Creates a new client manager
     *
     * @param gameManager the game manager
     */
    public ClientController(@NonNull GameManager gameManager) {
        Objects.requireNonNull(gameManager, "gameManager cannot be null!");

        this.gameManager = gameManager;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void connect(@NonNull Client client) {
        Objects.requireNonNull(client, "client cannot be null!");

        clients.add(client);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isConnected(@NonNull Client client) {
        Objects.requireNonNull(client, "client cannot be null!");

        return clients.contains(client);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void disconnect(@NonNull Client client) {
        Objects.requireNonNull(client, "client cannot be null!");

        clients.remove(client);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void join(@NonNull Client client) {
        Objects.requireNonNull(client, "client cannot be null!");

        client.updateGameInformation(gameManager.join(client.getPlayer()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull GameJSON getGame(@NonNull Client client) throws UndefinedClientGameException {
        Objects.requireNonNull(client, "client cannot be null!");

        return client.getGame().orElseThrow(UndefinedClientGameException::new);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasGame(@NonNull Client client) {
        Objects.requireNonNull(client, "client cannot be null!");

        return client.getGame().isPresent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull NotificationJSON executeCommand(@NonNull Client client, @NonNull CommandJSON commandJSON) {
        Objects.requireNonNull(client, "client cannot be null!");
        Objects.requireNonNull(commandJSON, "commandJSON cannot be null!");

        //Checks if the client has game
        if (!hasGame(client)) {
            return NotificationNoReplyJSON.CLIENT_HAS_NO_GAME;
        }

        //Gets server data
        GameJSON gameJSON = getGame(client);

        //Executes the command
        NotificationJSON notificationJSON = gameManager.executeCommand(gameJSON, commandJSON);

        //Checks if the command need a reply
        if (notificationJSON.needReply()) {
            askPlayerReply(notificationJSON);
        }

        //Updates the game information of all players in the same game
        GameJSON updatedGameJSON = gameManager.updateInformation(gameJSON);
        clients.stream()
                .filter(this::hasGame)
                .filter(testedClient -> getGame(testedClient).getUUID().equals(gameJSON.getUUID()))
                .forEach(inGameClient -> inGameClient.updateGameInformation(updatedGameJSON));

        return notificationJSON;
    }

    /**
     * Asks a reply to a client
     *
     * @param notificationJSON the notification information
     */
    private void askPlayerReply(@NonNull NotificationJSON notificationJSON) {
        Objects.requireNonNull(notificationJSON, "notificationJSON cannot be null!");

        //Ask a reply to the notification receiver (first argument in reply information)
        clients.stream()
                .filter(client -> client.getPlayer().equals(notificationJSON.replyInformation().get(0)))
                .findFirst()
                .ifPresent(receiver -> receiver.reply(notificationJSON));
    }

}