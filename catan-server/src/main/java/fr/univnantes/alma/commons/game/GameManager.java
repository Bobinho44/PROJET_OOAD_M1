package fr.univnantes.alma.commons.game;

import fr.univnantes.alma.core.command.ICommandJSON;
import fr.univnantes.alma.core.game.IGameJSON;
import fr.univnantes.alma.core.notification.INotificationJSON;
import fr.univnantes.alma.commons.notification.NotificationNoReplyJSON;
import fr.univnantes.alma.core.player.IPlayerJSON;
import fr.univnantes.alma.core.game.IGame;
import fr.univnantes.alma.core.game.IGameManager;
import org.springframework.lang.NonNull;

import java.util.*;

/**
 * Implementation of a game manager
 */
public class GameManager implements IGameManager {

    /**
     * Fields
     */
    private final Map<UUID, IGame> games = new HashMap<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull IGame getGame(@NonNull IGameJSON gameJSON) {
        Objects.requireNonNull(gameJSON, "gameJSON cannot be null!");

        return Optional.ofNullable(games.get(gameJSON.getUUID())).orElseThrow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasGame(@NonNull IGameJSON gameJSON) {
        Objects.requireNonNull(gameJSON, "gameJSON cannot be null!");

        return Optional.ofNullable(games.get(gameJSON.getUUID())).isPresent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addGame(@NonNull IGameJSON gameJSON) {
        Objects.requireNonNull(gameJSON, "gameJSON cannot be null!");

        games.put(gameJSON.getUUID(), new Game(gameJSON.getUUID()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeGame(@NonNull IGame game) {
        Objects.requireNonNull(game, "game cannot be null!");

        games.remove(game.getUUID());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull IGameJSON join(@NonNull IPlayerJSON playerJSON) {
        Objects.requireNonNull(playerJSON, "playerJSON cannot be null!");

        return games.entrySet().stream()
                .filter(game -> !game.getValue().isFull()).findFirst()

                //Joins an exiting game
                .map(game -> {
                    game.getValue().addPlayer(playerJSON);

                    return new GameJSON(game.getKey());
                })

                //Creates a new game
                .orElseGet(() -> {
                    IGameJSON gameJSON = new GameJSON(UUID.randomUUID());
                    addGame(gameJSON);
                    getGame(gameJSON).addPlayer(playerJSON);

                    return (GameJSON) updateInformation(gameJSON);
                });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void leave(@NonNull IGameJSON gameJSON, @NonNull IPlayerJSON playerJSON) {
        Objects.requireNonNull(gameJSON, "gameJSON cannot be null!");
        Objects.requireNonNull(playerJSON, "playerJSON cannot be null!");

        //Checks if the game is valid
        if (!hasGame(gameJSON)) {
            return;
        }

        getGame(gameJSON).removePlayer(playerJSON);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull INotificationJSON executeCommand(@NonNull IGameJSON gameJSON, @NonNull ICommandJSON commandJSON) {
        Objects.requireNonNull(gameJSON, "gameJSON cannot be null!");
        Objects.requireNonNull(commandJSON, "commandJSON cannot be null!");

        //Checks if the game is valid
        if (!hasGame(gameJSON)) {
            return NotificationNoReplyJSON.GAME_NOT_FOUND;
        }

        IGame game = getGame(gameJSON);

        //Checks if the sender can execute the command
        if (commandJSON.needActive() && !game.canPlay((IPlayerJSON) commandJSON.parameters().get(0))) {
            return NotificationNoReplyJSON.PLAYER_CAN_NOT_PLAY;
        }

        return game.executeCommand(commandJSON.name(), commandJSON.parameters());
    }

    /**
     * Updates the game information
     *
     * @param gameJSON the actual game information
     * @return the updated game information
     */
    public @NonNull IGameJSON updateInformation(@NonNull IGameJSON gameJSON) {
        Objects.requireNonNull(gameJSON, "gameJSON cannot be null!");

        //Checks if the game is valid
        if (!hasGame(gameJSON)) {
            return gameJSON;
        }

        return getGame(gameJSON).getGameInformation();
    }

}