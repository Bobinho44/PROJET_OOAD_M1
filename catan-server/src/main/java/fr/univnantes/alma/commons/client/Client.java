package fr.univnantes.alma.commons.client;

import fr.univnantes.alma.commons.command.CommandJSON;
import fr.univnantes.alma.core.game.IGameJSON;
import fr.univnantes.alma.commons.player.PlayerJSON;
import fr.univnantes.alma.core.client.IClient;
import fr.univnantes.alma.core.notification.INotificationJSON;
import fr.univnantes.alma.core.player.IPlayerJSON;
import fr.univnantes.alma.core.territory.ITerritoryJSON;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import javax.annotation.Nonnull;
import java.util.*;

/**
 * Implementation of a client
 */
public class Client implements IClient {

    /**
     * Fields
     */
    private final ClientManager clientController;
    private final IPlayerJSON player;
    private IGameJSON game;

    /**
     * Creates a new client
     *
     * @param clientController the client controller
     */
    public Client(@Nonnull ClientManager clientController) {
        Objects.requireNonNull(clientController, "clientController cannot be null!");

        this.clientController = clientController;
        this.player = new PlayerJSON(UUID.randomUUID());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull IPlayerJSON getPlayer() {
        return player;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull Optional<IGameJSON> getGame() {
        return Optional.ofNullable(game);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull INotificationJSON executeCommand(@Nonnull String name, @NonNull List<Object> parameters) {
        Objects.requireNonNull(name, "name cannot be null!");
        Objects.requireNonNull(parameters, "parameters cannot be null!");

        return clientController.executeCommand(this, new CommandJSON(name, parameters, true));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateGameInformation(@NonNull IGameJSON gameJSON) {
        Objects.requireNonNull(gameJSON, "gameJSON cannot be null!");

        game = gameJSON;
    }

    private @NonNull UUID getOtherPlayer() {
        return game.getPlayers().stream()
                .map(IPlayerJSON::getUUID)
                .filter(uuid -> !uuid.equals(player.getUUID()))
                .findFirst()
                .orElseThrow();
    }

    private @NonNull ITerritoryJSON getTerritory() {
        return game.getTerritories().stream()
                .filter(territory -> !territory.hasThief())
                .findFirst()
                .orElseThrow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void reply(@NonNull INotificationJSON notificationJSON) {
        Objects.requireNonNull(notificationJSON, "notificationJSON cannot be null!");

        IPlayerJSON sender = (IPlayerJSON) notificationJSON.replyInformation().get(0);
        String command = (String) notificationJSON.replyInformation().get(1);

        switch ((String) notificationJSON.replyInformation().get(1)) {
            case "placeTwoFreeRoad" ->
                    clientController.executeCommand(this, new CommandJSON(command, List.of(sender), true));
            case "takeTwoResources" ->
                    clientController.executeCommand(this, new CommandJSON(command, List.of(sender, "Wheat"), true));
            case "moveThiefAndTakeCard" ->
                    clientController.executeCommand(this, new CommandJSON(command, List.of(sender, getTerritory(), getOtherPlayer()), true));
            case "stealResourceFromAllPlayers" ->
                    clientController.executeCommand(this, new CommandJSON(command, List.of(sender, "Wheat"), true));
            case "discardMoveThiefAndSteal" ->
                    clientController.executeCommand(this, new CommandJSON(command, List.of(sender, "Wheat", getTerritory(), getOtherPlayer()), true));
            case "RespondTrade" ->
                    clientController.executeCommand(this, new CommandJSON("acceptTrade", List.of(sender, notificationJSON.replyInformation().get(2)), false));
            case "testThrow" ->
                throw new RuntimeException();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(@Nullable Object o) {
        if (this == o) return true;
        if (!(o instanceof Client client)) return false;
        return Objects.equals(player, client.player);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(player);
    }

}