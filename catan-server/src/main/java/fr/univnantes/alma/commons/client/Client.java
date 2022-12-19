package fr.univnantes.alma.commons.client;

import fr.univnantes.alma.commons.game.GameJSON;
import fr.univnantes.alma.commons.player.PlayerJSON;
import fr.univnantes.alma.core.game.GameManager;

import java.util.UUID;

public class Client {

    private final PlayerJSON player;
    private GameJSON game;
    public Client() {
        this.player = new PlayerJSON(UUID.randomUUID());
    }

    public void requestJoinGame(GameManager gameManager) {
        //game = gameManager.join(player);
    }

    public UUID getGame() {
        return game.getUuid();
    }
}
