package fr.univnantes.alma.core.command.executor;

import javax.annotation.Nonnull;
import java.util.List;

public interface CommandExecutor {

    /**
     * Gets all parameters
     *
     * @return all parameters
     */
    @Nonnull List<Object> getParameters();

}