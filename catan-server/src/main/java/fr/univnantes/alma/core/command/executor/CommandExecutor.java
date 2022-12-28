package fr.univnantes.alma.core.command.executor;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Interface representing a command executor
 */
public interface CommandExecutor {

    /**
     * Gets all parameters
     *
     * @return all parameters
     */
    @Nonnull List<Object> parameters();

}