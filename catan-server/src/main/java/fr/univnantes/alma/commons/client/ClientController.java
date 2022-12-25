package fr.univnantes.alma.commons.client;

import fr.univnantes.alma.commons.command.CommandJSON;
import fr.univnantes.alma.commons.game.GameJSON;
import fr.univnantes.alma.core.notification.NotificationJSON;
import fr.univnantes.alma.core.game.GameManager;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;

public class ClientController {

    private final List<Client> clients = new ArrayList<>();
    private final GameManager gameManager;


    public ClientController(@NonNull GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public void connect(@NonNull Client client) {
        clients.add(client);
    }

    public void disconnect(@NonNull Client client) {
        clients.remove(client);
    }

    public GameJSON join(@NonNull Client client) {
        return gameManager.join(client.getPlayer());
    }

    public NotificationJSON executeCommand(@NonNull CommandJSON commandJSON) {
        NotificationJSON notificationJSON = gameManager.executeCommand(commandJSON.getGame(), commandJSON);

        if (notificationJSON.needReply()) {
            askPlayerReply(notificationJSON);;
        }

        GameJSON gameJSON = gameManager.updateInformation(commandJSON.getGame());
        clients.stream()
                .filter(client -> client.getGame().getUUID().equals(commandJSON.getGame().getUUID()))
                .forEach(client -> client.updateGameInformation(gameJSON));

        return notificationJSON;
    }

    private void askPlayerReply(@NonNull NotificationJSON notificationJSON) {
        clients.stream()
                .filter(client -> client.getPlayer().equals(notificationJSON.getInformationReply().get(0)))
                .findFirst()
                .ifPresent(receiver -> receiver.reply(notificationJSON));
    }
}
