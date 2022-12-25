package fr.univnantes.alma.commons.command;

import fr.univnantes.alma.core.notification.NotificationJSON;
import fr.univnantes.alma.commons.command.executor.CommandExecutorImpl;
import fr.univnantes.alma.core.command.Command;
import fr.univnantes.alma.core.command.executor.CommandExecutor;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.function.Function;

/**
 * Implementation of a command
 */
public class CommandImpl implements Command {

    /**
     * Fields
     */
    private final Function<CommandExecutor, NotificationJSON> action;

    /**
     * Creates a new command
     *
     * @param action the action
     */
    public CommandImpl(@Nonnull Function<CommandExecutor, NotificationJSON> action) {
        this.action = action;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @Nonnull NotificationJSON execute(@Nonnull List<Object> parameters) {
        return action.apply(new CommandExecutorImpl(parameters));
    }

}
