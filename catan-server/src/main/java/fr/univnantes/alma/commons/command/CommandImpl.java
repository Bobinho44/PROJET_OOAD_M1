package fr.univnantes.alma.commons.command;

import fr.univnantes.alma.core.notification.NotificationJSON;
import fr.univnantes.alma.commons.command.executor.CommandExecutorImpl;
import fr.univnantes.alma.core.command.Command;
import fr.univnantes.alma.core.command.executor.CommandExecutor;
import org.springframework.lang.NonNull;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

/**
 * Implementation of a command
 */
public class CommandImpl implements Command {

    /**
     * Fields
     */
    private final String name;
    private final Function<CommandExecutor, NotificationJSON> action;

    /**
     * Creates a new command
     *
     * @param action the action
     */
    public CommandImpl(@NonNull String name, @Nonnull Function<CommandExecutor, NotificationJSON> action) {
        Objects.requireNonNull(name, "name cannot be null!");
        Objects.requireNonNull(action, "action cannot be null!");

        this.name = name;
        this.action = action;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @Nonnull String getName() {
        return name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @Nonnull NotificationJSON execute(@Nonnull List<Object> parameters) {
        Objects.requireNonNull(parameters, "parameters cannot be null!");

        return action.apply(new CommandExecutorImpl(parameters));
    }

}
