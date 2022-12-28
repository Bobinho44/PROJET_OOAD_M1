package fr.univnantes.alma.commons.command;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import fr.univnantes.alma.commons.notification.NotificationNoReplyJSON;
import fr.univnantes.alma.core.command.Command;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests of the command controller
 */
class CommandControllerTest {

    static {
        ((Logger) LoggerFactory.getLogger("org.reflections")).setLevel(Level.OFF);
    }

    /**
     * Fields
     */
    private final CommandController commandController = new CommandController();
    private Command command1;

    /**
     * Setup
     */
    @BeforeEach
    public void setup() {
        commandController.addCommand("test1", action -> {
            int parameter = (int) action.parameters().get(0);

            return switch (parameter) {
                case 1 -> NotificationNoReplyJSON.PLAYER_HAS_NO_DEVELOPMENT_CARD;
                case 5 -> NotificationNoReplyJSON.GAME_NOT_FOUND;
                default -> NotificationNoReplyJSON.COMMAND_SUCCESS;
            };
        });

        command1 = commandController.getCommand("test1");
    }

    @Test
    public void hasCommandTest() {
        assertTrue(commandController.hasCommand("test1"));
        assertEquals(command1, commandController.getCommand("test1"));
    }

    @Test
    public void addCommandTest() {
        commandController.addCommand("test2", action -> {
            System.out.println("Test2");

            return NotificationNoReplyJSON.COMMAND_SUCCESS;
        });

        assertTrue(commandController.hasCommand("test1"));
        assertTrue(commandController.hasCommand("test2"));
    }

    @Test
    public void removeCommandTest() {
        commandController.removeCommand(command1);

        assertFalse(commandController.hasCommand("test1"));
    }

    @Test
    public void executeTest() {
        assertThrows(IndexOutOfBoundsException.class, () -> command1.execute(Collections.emptyList()));
        assertEquals(NotificationNoReplyJSON.PLAYER_HAS_NO_DEVELOPMENT_CARD, command1.execute(List.of(1)));
        assertEquals(NotificationNoReplyJSON.GAME_NOT_FOUND, command1.execute(List.of(5)));
        assertEquals(NotificationNoReplyJSON.COMMAND_SUCCESS, command1.execute(List.of(0)));
    }

}