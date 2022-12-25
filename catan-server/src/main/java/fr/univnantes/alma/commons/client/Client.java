package fr.univnantes.alma.commons.client;

import fr.univnantes.alma.commons.command.CommandJSON;
import fr.univnantes.alma.commons.game.GameJSON;
import fr.univnantes.alma.core.notification.NotificationJSON;
import fr.univnantes.alma.commons.player.PlayerJSON;
import fr.univnantes.alma.commons.territory.TerritoryJSON;
import org.springframework.lang.NonNull;

import javax.annotation.Nonnull;
import java.util.*;

public class Client {

    private final ClientController clientController;
    private final PlayerJSON player;
    private GameJSON game;

    public Client(@Nonnull ClientController clientController) {
        this.clientController = clientController;
        this.player = new PlayerJSON(UUID.randomUUID());
    }

    public PlayerJSON getPlayer() {
        return player;
    }

    public GameJSON getGame() {
        return game;
    }

    public void requestJoinGame() {
        game = clientController.join(this);
    }

    public NotificationJSON executeCommand(@Nonnull String name, List<Object> parameters) {
        return clientController.executeCommand(new CommandJSON(game, name, parameters, true));
    }

    public void updateGameInformation(@NonNull GameJSON gameJSON) {
        game = gameJSON;
    }

    private UUID getOtherPlayer() {
        return game.getPlayers().stream()
                .map(PlayerJSON::getUuid)
                .filter(uuid -> !uuid.equals(player.getUuid()))
                .findFirst()
                .orElseThrow();
    }

    private TerritoryJSON getTerritory() {
        return game.getTerritories().stream()
                .filter(territory -> !territory.hasThief())
                .findFirst()
                .orElseThrow();
    }

    public void reply(NotificationJSON notificationJSON) {
        PlayerJSON sender = (PlayerJSON) notificationJSON.getInformationReply().get(0);
        String command = (String) notificationJSON.getInformationReply().get(1);

        switch ((String) notificationJSON.getInformationReply().get(1)) {
            case "placeTwoFreeRoad" ->
                    clientController.executeCommand(new CommandJSON(game, command, List.of(sender), true));
            case "takeTwoResources" ->
                    clientController.executeCommand(new CommandJSON(game, command, List.of(sender, "Wheat"), true));
            case "moveThiefAndTakeCard" ->
                    clientController.executeCommand(new CommandJSON(game, command, List.of(sender, getTerritory(), getOtherPlayer()), true));
            case "stealResourceFromAllPlayers" ->
                    clientController.executeCommand(new CommandJSON(game, command, List.of(sender, "Wheat"), true));
            case "discardMoveThiefAndSteal" ->
                    clientController.executeCommand(new CommandJSON(game, command, List.of(sender, "Wheat", getTerritory(), getOtherPlayer()), true));
            case "RespondTrade" ->
                    clientController.executeCommand(new CommandJSON(game, "acceptTrade", List.of(sender, notificationJSON.getInformationReply().get(2)), false));
        }
    }

}