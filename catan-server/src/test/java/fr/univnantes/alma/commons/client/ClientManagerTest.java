package fr.univnantes.alma.commons.client;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import fr.univnantes.alma.commons.game.GameJSON;
import fr.univnantes.alma.commons.notification.NotificationNoReplyJSON;
import fr.univnantes.alma.commons.notification.NotificationReplyJSON;
import fr.univnantes.alma.core.client.IClient;
import fr.univnantes.alma.core.command.ICommandJSON;
import fr.univnantes.alma.core.game.IGame;
import fr.univnantes.alma.core.game.IGameJSON;
import fr.univnantes.alma.core.game.IGameManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests of the client manager
 */
class ClientManagerTest {

    static {
        ((Logger) LoggerFactory.getLogger("org.reflections")).setLevel(Level.OFF);
    }

    /**
     * Fields
     */
    private final IGameManager gameManager = mock(IGameManager.class);
    private final ClientManager clientManager = new ClientManager(gameManager);
    private final IClient client1 = new Client(clientManager);
    private final IClient client2 = new Client(clientManager);
    private final IGameJSON gameJSON = new GameJSON(UUID.randomUUID());
    private final IGame game = mock(IGame.class);

    /**
     * Setup
     */
    @BeforeEach
    public void setup() {
        when(gameManager.join(any())).thenReturn(gameJSON);
        when(gameManager.updateInformation(gameJSON)).thenReturn(gameJSON);

        when(gameManager.hasGame(eq(gameJSON))).thenReturn(true);
        when(gameManager.executeCommand(eq(gameJSON), any(ICommandJSON.class))).thenAnswer(invocation -> {
            ICommandJSON commandJSON = invocation.getArgument(1);
            return game.executeCommand(commandJSON.name(), commandJSON.parameters());
        });

        when(game.executeCommand(eq("test"), eq(List.of(1)))).thenReturn(NotificationNoReplyJSON.COMMAND_SUCCESS);
        when(game.executeCommand(eq("test"), eq(List.of(5)))).thenReturn(NotificationNoReplyJSON.RESOURCE_NOT_FOUND);
        when(game.executeCommand(eq("testThrow"), eq(List.of(5, 8)))).thenReturn(new NotificationReplyJSON(List.of(client1.getPlayer(), "testThrow")));

        clientManager.connect(client1);
        clientManager.join(client1);
    }

    @Test
    public void connectTest() {
        clientManager.connect(client2);

        assertTrue(clientManager.isConnected(client1));
        assertTrue(clientManager.isConnected(client2));
    }

    @Test
    public void isConnectedTest() {
        assertTrue(clientManager.isConnected(client1));
        assertFalse(clientManager.isConnected(client2));
    }

    @Test
    public void disconnectTest() {
        clientManager.disconnect(client1);

        assertFalse(clientManager.isConnected(client1));
    }

    @Test
    public void joinTest() {
        clientManager.connect(client2);

        clientManager.join(client2);

        assertTrue(clientManager.isConnected(client2));
        assertEquals(client1.getGame(), client2.getGame());
        assertTrue(clientManager.hasGame(client2));
        assertTrue(gameManager.hasGame(clientManager.getGame(client2)));
    }

    @Test
    public void hasGameTest() {
        assertTrue(clientManager.hasGame(client1));
        assertFalse(clientManager.hasGame(client2));
    }

    @Test
    public void executeCommandTest() {
        assertEquals(NotificationNoReplyJSON.COMMAND_SUCCESS, client1.executeCommand("test", List.of(1)));
        assertEquals(NotificationNoReplyJSON.RESOURCE_NOT_FOUND, client1.executeCommand("test", List.of(5)));
        assertThrows(RuntimeException.class, () -> client1.executeCommand("testThrow", List.of(5, 8)));
    }

}