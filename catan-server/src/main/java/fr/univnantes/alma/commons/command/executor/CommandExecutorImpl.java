package fr.univnantes.alma.commons.command.executor;

import fr.univnantes.alma.core.command.executor.CommandExecutor;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Implementation of a command executor
 */
public class CommandExecutorImpl implements CommandExecutor {

    /**
     * Fields
     */
    private final List<Object> parameters;

    /**
     * Creates a new command executor
     *
     * @param parameters Parameters.
     */
    public CommandExecutorImpl(@Nonnull List<Object> parameters) {
        this.parameters = parameters;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @Nonnull List<Object> getParameters() {
        return this.parameters;
    }

}