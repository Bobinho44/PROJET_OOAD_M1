package fr.univnantes.alma.commons.game;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import fr.univnantes.alma.commons.command.CommandJSON;
import fr.univnantes.alma.commons.notification.NotificationNoReplyJSON;
import fr.univnantes.alma.commons.player.PlayerJSON;
import fr.univnantes.alma.commons.resource.ResourceJSON;
import fr.univnantes.alma.core.command.ICommandJSON;
import fr.univnantes.alma.core.game.IGame;
import fr.univnantes.alma.core.game.IGameJSON;
import fr.univnantes.alma.core.player.IPlayerJSON;
import fr.univnantes.alma.core.resource.IResourceJSON;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests of the game manager
 */
class GameManagerTest {

    static {
       ((Logger) LoggerFactory.getLogger("org.reflections")).setLevel(Level.OFF);
    }

    /**
     * Fields
     */
    private final GameManager gameManager = new GameManager();
    private final IGame game1 = new Game(UUID.randomUUID());
    private final IGame game2 = new Game(UUID.randomUUID());
    private final IGameJSON gameJSON1 = new GameJSON(game1.getUUID());
    private final IGameJSON gameJSON2 = new GameJSON(game2.getUUID());
    private final IPlayerJSON playerJSON1 = new PlayerJSON(UUID.randomUUID());
    private final IPlayerJSON playerJSON2 = new PlayerJSON(UUID.randomUUID());
    private final IPlayerJSON playerJSON3 = new PlayerJSON(UUID.randomUUID());

    /**
     * Setup
     */
    @BeforeEach
    public void setup() {
        gameManager.addGame(gameJSON1);
    }

    @Test
    public void hasGameTest() {
        assertTrue(gameManager.hasGame(gameJSON1));
        assertFalse(gameManager.hasGame(gameJSON2));
    }

    @Test
    public void addGameTest() {
        gameManager.addGame(gameJSON2);

        assertTrue(gameManager.hasGame(gameJSON2));
    }

    @Test
    public void removeGameTest() {
        gameManager.removeGame(game1);

        assertFalse(gameManager.hasGame(gameJSON1));
    }

    @Test
    public void joinTest() {
        assertEquals(gameJSON1, gameManager.join(playerJSON1));
        assertEquals(gameJSON1, gameManager.join(playerJSON2));
        assertNotEquals(gameJSON1, gameManager.join(playerJSON3));
    }

    @Test
    public void leaveTest() {
        gameManager.join(playerJSON1);
        gameManager.join(playerJSON2);

        gameManager.leave(gameJSON1, playerJSON1);

        assertEquals(gameJSON1, gameManager.join(playerJSON3));
    }

    @Test
    public void executeCommandTest() {
        gameManager.join(playerJSON1);
        IResourceJSON resourceJSON = new ResourceJSON("Wheat", 5);
        ICommandJSON commandJSON = new CommandJSON("giveResources", List.of(playerJSON1, resourceJSON), false);

        assertEquals(NotificationNoReplyJSON.COMMAND_SUCCESS, gameManager.executeCommand(gameJSON1, commandJSON));
        assertEquals(1, gameManager.updateInformation(gameJSON1).getPlayers().get(0).getResources().size());
        assertEquals(resourceJSON, gameManager.updateInformation(gameJSON1).getPlayers().get(0).getResources().get(0));
    }

}