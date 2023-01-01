package fr.univnantes.alma.commons.client;

import fr.univnantes.alma.commons.command.CommandJSON;
import fr.univnantes.alma.commons.resource.ResourceJSON;
import fr.univnantes.alma.core.game.IGameJSON;
import fr.univnantes.alma.commons.player.PlayerJSON;
import fr.univnantes.alma.core.client.IClient;
import fr.univnantes.alma.core.notification.INotificationJSON;
import fr.univnantes.alma.core.player.IPlayerJSON;
import fr.univnantes.alma.core.resource.IResourceJSON;
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

    /**
     * Gets a resource
     *
     * @return the resource
     */
    private @NonNull IResourceJSON getResource() {
        return game.getResources().stream()
                .filter(resource -> resource.amount() > 0)
                .findAny()
                .map(resource -> new ResourceJSON(resource.name(), 1))
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

            /*
            TODO add replies for all client (stub) command
             */
            case "takeTwoResources" -> {
                IResourceJSON resourceJSON1 = getResource();
                IResourceJSON resourceJSON2 = getResource();

                clientController.executeCommand(this, new CommandJSON(command, List.of(sender, resourceJSON1, resourceJSON2), true));
            }
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