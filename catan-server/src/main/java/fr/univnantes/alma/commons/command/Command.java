package fr.univnantes.alma.commons.command;

import fr.univnantes.alma.core.command.ICommand;
import fr.univnantes.alma.core.command.executor.ICommandExecutor;
import fr.univnantes.alma.core.notification.INotificationJSON;
import fr.univnantes.alma.commons.command.executor.CommandExecutor;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

/**
 * Implementation of a command
 */
public class Command implements ICommand {

    /**
     * Fields
     */
    private final String name;
    private final Function<ICommandExecutor, INotificationJSON> action;

    /**
     * Creates a new command
     *
     * @param action the action
     */
    public Command(@NonNull String name, @Nonnull Function<ICommandExecutor, INotificationJSON> action) {
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
    public @Nonnull INotificationJSON execute(@Nonnull List<Object> parameters) {
        Objects.requireNonNull(parameters, "parameters cannot be null!");

        return action.apply(new CommandExecutor(parameters));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(@Nullable Object o) {
        if (this == o) return true;
        if (!(o instanceof Command command)) return false;
        return Objects.equals(name, command.name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

}