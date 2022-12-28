package fr.univnantes.alma.commons.game;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import fr.univnantes.alma.commons.command.CommandJSONImpl;
import fr.univnantes.alma.commons.notification.NotificationNoReplyJSON;
import fr.univnantes.alma.commons.player.PlayerJSONImpl;
import fr.univnantes.alma.commons.resource.ResourceJSONImpl;
import fr.univnantes.alma.core.command.CommandJSON;
import fr.univnantes.alma.core.game.Game;
import fr.univnantes.alma.core.game.GameJSON;
import fr.univnantes.alma.core.player.PlayerJSON;
import fr.univnantes.alma.core.resource.ResourceJSON;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests of the game controller
 */
class GameControllerTest {

    static {
       ((Logger) LoggerFactory.getLogger("org.reflections")).setLevel(Level.OFF);
    }

    /**
     * Fields
     */
    private final GameController gameController = new GameController();
    private final Game game1 = new GameImpl(UUID.randomUUID());
    private final Game game2 = new GameImpl(UUID.randomUUID());
    private final GameJSON gameJSON1 = new GameJSONImpl(game1.getUUID());
    private final GameJSON gameJSON2 = new GameJSONImpl(game2.getUUID());
    private final PlayerJSON playerJSON1 = new PlayerJSONImpl(UUID.randomUUID());
    private final PlayerJSON playerJSON2 = new PlayerJSONImpl(UUID.randomUUID());
    private final PlayerJSON playerJSON3 = new PlayerJSONImpl(UUID.randomUUID());

    /**
     * Setup
     */
    @BeforeEach
    public void setup() {
        gameController.addGame(gameJSON1);
    }

    @Test
    public void getGameTest() {
    }

    @Test
    public void hasGameTest() {
        assertTrue(gameController.hasGame(gameJSON1));
        assertFalse(gameController.hasGame(gameJSON2));
    }

    @Test
    public void addGameTest() {
        gameController.addGame(gameJSON2);

        assertTrue(gameController.hasGame(gameJSON2));
    }

    @Test
    public void removeGameTest() {
        gameController.removeGame(game1);

        assertFalse(gameController.hasGame(gameJSON1));
    }

    @Test
    public void joinTest() {
        assertEquals(gameJSON1, gameController.join(playerJSON1));
        assertEquals(gameJSON1, gameController.join(playerJSON2));
        assertNotEquals(gameJSON1, gameController.join(playerJSON3));
    }

    @Test
    public void leaveTest() {
        gameController.join(playerJSON1);
        gameController.join(playerJSON2);

        gameController.leave(gameJSON1, playerJSON1);

        assertEquals(gameJSON1, gameController.join(playerJSON3));
    }

    @Test
    public void executeCommandTest() {
        gameController.join(playerJSON1);
        ResourceJSON resourceJSON = new ResourceJSONImpl("Wheat", 5);
        CommandJSON commandJSON = new CommandJSONImpl("giveResources", List.of(playerJSON1, resourceJSON), false);

        assertEquals(NotificationNoReplyJSON.COMMAND_SUCCESS, gameController.executeCommand(gameJSON1, commandJSON));
        assertEquals(1, gameController.updateInformation(gameJSON1).getPlayers().get(0).getResources().size());
        assertEquals(resourceJSON, gameController.updateInformation(gameJSON1).getPlayers().get(0).getResources().get(0));
    }

}