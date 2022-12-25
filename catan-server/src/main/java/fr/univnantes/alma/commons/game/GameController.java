package fr.univnantes.alma.commons.game;

import fr.univnantes.alma.commons.command.CommandJSON;
import fr.univnantes.alma.core.notification.NotificationJSON;
import fr.univnantes.alma.commons.notification.NotificationNoReplyJSON;
import fr.univnantes.alma.commons.player.PlayerJSON;
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
        return Optional.ofNullable(games.get(gameJSON.getUUID())).orElseThrow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasGame(@NonNull GameJSON gameJSON) {
        return Optional.ofNullable(games.get(gameJSON.getUUID())).isPresent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addGame(@NonNull GameJSON gameJSON) {
        games.put(gameJSON.getUUID(), new GameImpl(gameJSON.getUUID()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeGame(@NonNull Game game) {
        games.remove(game.getUUID());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull GameJSON join(@NonNull PlayerJSON playerJSON) {
        return games.entrySet().stream()
                .filter(game -> !game.getValue().isFull()).findFirst()
                .map(game -> {
                    game.getValue().addPlayer(playerJSON);

                    return new GameJSON(game.getKey());
                })
                .orElseGet(() -> {
                    GameJSON gameJSON = new GameJSON(UUID.randomUUID());
                    addGame(gameJSON);
                    getGame(gameJSON).addPlayer(playerJSON);

                    return updateInformation(gameJSON);
                });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void leave(@NonNull GameJSON gameJSON, @NonNull PlayerJSON playerJSON) {
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

        if (!hasGame(gameJSON)) {
            return NotificationNoReplyJSON.GAME_NOT_FOUND;
        }

        Game game = getGame(gameJSON);

        if (commandJSON.isNeedActive() && !game.canPlay((PlayerJSON) commandJSON.getCommandParameters().get(0))) {
            return NotificationNoReplyJSON.PLAYER_CAN_NOT_PLAY;
        }

        return game.executeCommand(commandJSON.getCommandName(), commandJSON.getCommandParameters());
    }

    public @NonNull GameJSON updateInformation(@NonNull GameJSON gameJSON) {
        if (!hasGame(gameJSON)) {
            return gameJSON;
        }

        return getGame(gameJSON).getGameInformation();
    }

}