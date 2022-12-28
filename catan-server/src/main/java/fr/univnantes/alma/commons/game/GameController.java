package fr.univnantes.alma.commons.game;

import fr.univnantes.alma.core.command.CommandJSON;
import fr.univnantes.alma.core.game.GameJSON;
import fr.univnantes.alma.core.notification.NotificationJSON;
import fr.univnantes.alma.commons.notification.NotificationNoReplyJSON;
import fr.univnantes.alma.core.player.PlayerJSON;
import fr.univnantes.alma.core.game.Game;
import fr.univnantes.alma.core.game.GameManager;
import org.springframework.lang.NonNull;

import java.util.*;

/**
 * Implementation of a game manager
 */
public final class GameController implements GameManager {

    /**
     * Fields
     */
    private final Map<UUID, Game> games = new HashMap<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull Game getGame(@NonNull GameJSON gameJSON) {
        Objects.requireNonNull(gameJSON, "gameJSON cannot be null!");

        return Optional.ofNullable(games.get(gameJSON.getUUID())).orElseThrow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasGame(@NonNull GameJSON gameJSON) {
        Objects.requireNonNull(gameJSON, "gameJSON cannot be null!");

        return Optional.ofNullable(games.get(gameJSON.getUUID())).isPresent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addGame(@NonNull GameJSON gameJSON) {
        Objects.requireNonNull(gameJSON, "gameJSON cannot be null!");

        games.put(gameJSON.getUUID(), new GameImpl(gameJSON.getUUID()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeGame(@NonNull Game game) {
        Objects.requireNonNull(game, "game cannot be null!");

        games.remove(game.getUUID());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull GameJSON join(@NonNull PlayerJSON playerJSON) {
        Objects.requireNonNull(playerJSON, "playerJSON cannot be null!");

        return games.entrySet().stream()
                .filter(game -> !game.getValue().isFull()).findFirst()

                //Joins an exiting game
                .map(game -> {
                    game.getValue().addPlayer(playerJSON);

                    return new GameJSONImpl(game.getKey());
                })

                //Creates a new game
                .orElseGet(() -> {
                    GameJSON gameJSON = new GameJSONImpl(UUID.randomUUID());
                    addGame(gameJSON);
                    getGame(gameJSON).addPlayer(playerJSON);

                    return (GameJSONImpl) updateInformation(gameJSON);
                });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void leave(@NonNull GameJSON gameJSON, @NonNull PlayerJSON playerJSON) {
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
    public @NonNull NotificationJSON executeCommand(@NonNull GameJSON gameJSON, @NonNull CommandJSON commandJSON) {
        Objects.requireNonNull(gameJSON, "gameJSON cannot be null!");
        Objects.requireNonNull(commandJSON, "commandJSON cannot be null!");

        //Checks if the game is valid
        if (!hasGame(gameJSON)) {
            return NotificationNoReplyJSON.GAME_NOT_FOUND;
        }

        Game game = getGame(gameJSON);

        //Checks if the sender can execute the command
        if (commandJSON.isNeedActive() && !game.canPlay((PlayerJSON) commandJSON.getParameters().get(0))) {
            return NotificationNoReplyJSON.PLAYER_CAN_NOT_PLAY;
        }

        return game.executeCommand(commandJSON.getName(), commandJSON.getParameters());
    }

    public @NonNull GameJSON updateInformation(@NonNull GameJSON gameJSON) {
        Objects.requireNonNull(gameJSON, "gameJSON cannot be null!");

        //Checks if the game is valid
        if (!hasGame(gameJSON)) {
            return gameJSON;
        }

        return getGame(gameJSON).getGameInformation();
    }

}