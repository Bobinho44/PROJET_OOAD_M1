package fr.univnantes.alma.commons.command.executor;

import fr.univnantes.alma.core.command.executor.CommandExecutor;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Objects;

/**
 * Implementation of a command executor
 */
public record CommandExecutorImpl(List<Object> parameters) implements CommandExecutor {

    /**
     * Creates a new command executor
     *
     * @param parameters Parameters.
     */
    public CommandExecutorImpl(@Nonnull List<Object> parameters) {
        Objects.requireNonNull(parameters, "parameters cannot be null!");

        this.parameters = parameters;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @Nonnull List<Object> parameters() {
        return this.parameters;
    }

}