package fr.univnantes.alma.commons.client;

import fr.univnantes.alma.commons.command.CommandJSONImpl;
import fr.univnantes.alma.core.game.GameJSON;
import fr.univnantes.alma.commons.player.PlayerJSONImpl;
import fr.univnantes.alma.core.client.Client;
import fr.univnantes.alma.core.notification.NotificationJSON;
import fr.univnantes.alma.core.player.PlayerJSON;
import fr.univnantes.alma.core.territory.TerritoryJSON;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import javax.annotation.Nonnull;
import java.util.*;

/**
 * Implementation of a client
 */
public class ClientImpl implements Client {

    /**
     * Fields
     */
    private final ClientController clientController;
    private final PlayerJSON player;
    private GameJSON game;

    /**
     * Creates a new client
     *
     * @param clientController the client controller
     */
    public ClientImpl(@Nonnull ClientController clientController) {
        Objects.requireNonNull(clientController, "clientController cannot be null!");

        this.clientController = clientController;
        this.player = new PlayerJSONImpl(UUID.randomUUID());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull PlayerJSON getPlayer() {
        return player;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull Optional<GameJSON> getGame() {
        return Optional.ofNullable(game);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull NotificationJSON executeCommand(@Nonnull String name, @NonNull List<Object> parameters) {
        Objects.requireNonNull(name, "name cannot be null!");
        Objects.requireNonNull(parameters, "parameters cannot be null!");

        return clientController.executeCommand(this, new CommandJSONImpl(name, parameters, true));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateGameInformation(@NonNull GameJSON gameJSON) {
        Objects.requireNonNull(gameJSON, "gameJSON cannot be null!");

        game = gameJSON;
    }

    private @NonNull UUID getOtherPlayer() {
        return game.getPlayers().stream()
                .map(PlayerJSON::getUUID)
                .filter(uuid -> !uuid.equals(player.getUUID()))
                .findFirst()
                .orElseThrow();
    }

    private @NonNull TerritoryJSON getTerritory() {
        return game.getTerritories().stream()
                .filter(territory -> !territory.hasThief())
                .findFirst()
                .orElseThrow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void reply(@NonNull NotificationJSON notificationJSON) {
        Objects.requireNonNull(notificationJSON, "notificationJSON cannot be null!");

        PlayerJSON sender = (PlayerJSON) notificationJSON.replyInformation().get(0);
        String command = (String) notificationJSON.replyInformation().get(1);

        switch ((String) notificationJSON.replyInformation().get(1)) {
            case "placeTwoFreeRoad" ->
                    clientController.executeCommand(this, new CommandJSONImpl(command, List.of(sender), true));
            case "takeTwoResources" ->
                    clientController.executeCommand(this, new CommandJSONImpl(command, List.of(sender, "Wheat"), true));
            case "moveThiefAndTakeCard" ->
                    clientController.executeCommand(this, new CommandJSONImpl(command, List.of(sender, getTerritory(), getOtherPlayer()), true));
            case "stealResourceFromAllPlayers" ->
                    clientController.executeCommand(this, new CommandJSONImpl(command, List.of(sender, "Wheat"), true));
            case "discardMoveThiefAndSteal" ->
                    clientController.executeCommand(this, new CommandJSONImpl(command, List.of(sender, "Wheat", getTerritory(), getOtherPlayer()), true));
            case "RespondTrade" ->
                    clientController.executeCommand(this, new CommandJSONImpl("acceptTrade", List.of(sender, notificationJSON.replyInformation().get(2)), false));
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
        if (!(o instanceof ClientImpl)) return false;

        return Objects.equals(player.getUUID(), ((ClientImpl) o).getPlayer().getUUID());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return player.getUUID().hashCode();
    }

}