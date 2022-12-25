package fr.univnantes.alma.commons.command;

import fr.univnantes.alma.core.notification.NotificationJSON;
import fr.univnantes.alma.commons.notification.NotificationNoReplyJSON;
import fr.univnantes.alma.core.command.Command;
import fr.univnantes.alma.core.command.CommandManager;
import fr.univnantes.alma.core.command.executor.CommandExecutor;
import org.springframework.lang.NonNull;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * Implementation of a command manager
 */
public class CommandController implements CommandManager {

    /**
     * Fields
     */
    private final Map<String, Command> commands = new HashMap<>();

    /**
     * Gets a command
     *
     * @param name the name
     * @return the optional command
     */
    private Optional<Command> getCommand(@NonNull String name) {
        return Optional.ofNullable(commands.get(name));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addCommand(@NonNull String name, @Nonnull Function<CommandExecutor, NotificationJSON> action) {
        commands.put(name, new CommandImpl(action));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeCommand(@NonNull String name) {
        commands.remove(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull NotificationJSON execute(@NonNull String name, @NonNull List<Object> parameters) {
        return getCommand(name)
                .map(command -> command.execute(parameters))
                .orElse(NotificationNoReplyJSON.COMMAND_NOT_FOUND);
    }

}