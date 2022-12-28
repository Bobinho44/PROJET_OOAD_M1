package fr.univnantes.alma.commons.client;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import fr.univnantes.alma.commons.game.GameJSONImpl;
import fr.univnantes.alma.core.game.GameJSON;
import fr.univnantes.alma.commons.notification.NotificationNoReplyJSON;
import fr.univnantes.alma.commons.notification.NotificationReplyJSON;
import fr.univnantes.alma.core.client.Client;
import fr.univnantes.alma.core.command.CommandJSON;
import fr.univnantes.alma.core.game.Game;
import fr.univnantes.alma.core.game.GameManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests of the client controller
 */
class ClientControllerTest {

    static {
        ((Logger) LoggerFactory.getLogger("org.reflections")).setLevel(Level.OFF);
    }

    /**
     * Fields
     */
    private final GameManager gameManager = mock(GameManager.class);
    private final ClientController clientController = new ClientController(gameManager);
    private final Client client1 = new ClientImpl(clientController);
    private final Client client2 = new ClientImpl(clientController);
    private final GameJSON gameJSON = new GameJSONImpl(UUID.randomUUID());
    private final Game game = mock(Game.class);

    /**
     * Setup
     */
    @BeforeEach
    public void setup() {
        when(gameManager.join(any())).thenReturn(gameJSON);
        when(gameManager.updateInformation(gameJSON)).thenReturn(gameJSON);

        when(gameManager.hasGame(eq(gameJSON))).thenReturn(true);
        when(gameManager.executeCommand(eq(gameJSON), any(CommandJSON.class))).thenAnswer(invocation -> {
            CommandJSON commandJSON = invocation.getArgument(1);
            return game.executeCommand(commandJSON.getName(), commandJSON.getParameters());
        });

        when(game.executeCommand(eq("test"), eq(List.of(1)))).thenReturn(NotificationNoReplyJSON.COMMAND_SUCCESS);
        when(game.executeCommand(eq("test"), eq(List.of(5)))).thenReturn(NotificationNoReplyJSON.RESOURCE_NOT_FOUND);
        when(game.executeCommand(eq("testThrow"), eq(List.of(5, 8)))).thenReturn(new NotificationReplyJSON(List.of(client1.getPlayer(), "testThrow")));

        clientController.connect(client1);
        clientController.join(client1);
    }

    @Test
    public void connectTest() {
        clientController.connect(client2);

        assertTrue(clientController.isConnected(client1));
        assertTrue(clientController.isConnected(client2));
    }

    @Test
    public void isConnectedTest() {
        assertTrue(clientController.isConnected(client1));
        assertFalse(clientController.isConnected(client2));
    }

    @Test
    public void disconnectTest() {
        clientController.disconnect(client1);

        assertFalse(clientController.isConnected(client1));
    }

    @Test
    public void joinTest() {
        clientController.connect(client2);

        clientController.join(client2);

        assertTrue(clientController.isConnected(client2));
        assertEquals(client1.getGame(), client2.getGame());
        assertTrue(clientController.hasGame(client2));
        assertTrue(gameManager.hasGame(clientController.getGame(client2)));
    }

    @Test
    public void hasGameTest() {
        assertTrue(clientController.hasGame(client1));
        assertFalse(clientController.hasGame(client2));
    }

    @Test
    public void executeCommandTest() {
        assertEquals(NotificationNoReplyJSON.COMMAND_SUCCESS, client1.executeCommand("test", List.of(1)));
        assertEquals(NotificationNoReplyJSON.RESOURCE_NOT_FOUND, client1.executeCommand("test", List.of(5)));
        assertThrows(RuntimeException.class, () -> client1.executeCommand("testThrow", List.of(5, 8)));
    }

}