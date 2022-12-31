package fr.univnantes.alma.commons.command;

import fr.univnantes.alma.core.command.ICommand;
import fr.univnantes.alma.core.exception.UnregisteredCommandException;
import fr.univnantes.alma.core.notification.INotificationJSON;
import fr.univnantes.alma.core.command.ICommandManager;
import fr.univnantes.alma.core.command.executor.ICommandExecutor;
import org.springframework.lang.NonNull;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.function.Function;

/**
 * Implementation of a command manager
 */
public class CommandManager implements ICommandManager {

    /**
     * Fields
     */
    private final Map<String, ICommand> commands = new HashMap<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull ICommand getCommand(@NonNull String name) throws UnregisteredCommandException {
        Objects.requireNonNull(name, "name cannot be null!");

        return Optional.ofNullable(commands.get(name)).orElseThrow(UnregisteredCommandException::new);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasCommand(@NonNull String name) {
        Objects.requireNonNull(name, "name cannot be null!");

        return Optional.ofNullable(commands.get(name)).isPresent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addCommand(@NonNull String name, @Nonnull Function<ICommandExecutor, INotificationJSON> action) {
        Objects.requireNonNull(name, "name cannot be null!");
        Objects.requireNonNull(action, "action cannot be null!");

        commands.put(name, new Command(name, action));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeCommand(@NonNull ICommand command) {
        Objects.requireNonNull(command, "command cannot be null!");

        commands.remove(command.getName());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull INotificationJSON execute(@NonNull ICommand command, @NonNull List<Object> parameters) {
        Objects.requireNonNull(command, "command cannot be null!");
        Objects.requireNonNull(parameters, "parameters cannot be null!");

        return command.execute(parameters);
    }

}