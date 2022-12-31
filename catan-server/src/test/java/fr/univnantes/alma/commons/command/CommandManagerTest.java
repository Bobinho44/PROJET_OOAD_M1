package fr.univnantes.alma.commons.command;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import fr.univnantes.alma.commons.notification.NotificationNoReplyJSON;
import fr.univnantes.alma.core.command.ICommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests of the command manager
 */
class CommandManagerTest {

    static {
        ((Logger) LoggerFactory.getLogger("org.reflections")).setLevel(Level.OFF);
    }

    /**
     * Fields
     */
    private final CommandManager commandManager = new CommandManager();
    private ICommand command1;

    /**
     * Setup
     */
    @BeforeEach
    public void setup() {
        commandManager.addCommand("test1", action -> {
            int parameter = (int) action.parameters().get(0);

            return switch (parameter) {
                case 1 -> NotificationNoReplyJSON.PLAYER_HAS_NO_DEVELOPMENT_CARD;
                case 5 -> NotificationNoReplyJSON.GAME_NOT_FOUND;
                default -> NotificationNoReplyJSON.COMMAND_SUCCESS;
            };
        });

        command1 = commandManager.getCommand("test1");
    }

    @Test
    public void hasCommandTest() {
        assertTrue(commandManager.hasCommand("test1"));
        assertEquals(command1, commandManager.getCommand("test1"));
    }

    @Test
    public void addCommandTest() {
        commandManager.addCommand("test2", action -> {
            System.out.println("Test2");

            return NotificationNoReplyJSON.COMMAND_SUCCESS;
        });

        assertTrue(commandManager.hasCommand("test1"));
        assertTrue(commandManager.hasCommand("test2"));
    }

    @Test
    public void removeCommandTest() {
        commandManager.removeCommand(command1);

        assertFalse(commandManager.hasCommand("test1"));
    }

    @Test
    public void executeTest() {
        assertThrows(IndexOutOfBoundsException.class, () -> command1.execute(Collections.emptyList()));
        assertEquals(NotificationNoReplyJSON.PLAYER_HAS_NO_DEVELOPMENT_CARD, command1.execute(List.of(1)));
        assertEquals(NotificationNoReplyJSON.GAME_NOT_FOUND, command1.execute(List.of(5)));
        assertEquals(NotificationNoReplyJSON.COMMAND_SUCCESS, command1.execute(List.of(0)));
    }

}