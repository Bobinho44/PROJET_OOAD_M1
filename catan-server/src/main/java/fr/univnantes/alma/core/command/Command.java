package fr.univnantes.alma.core.command;

import fr.univnantes.alma.core.notification.NotificationJSON;

import javax.annotation.Nonnull;
import java.util.List;

public interface Command {

    /**
     * Executes a command
     *
     * @param parameters the parameters
     * @return the command result json information
     */
    @Nonnull NotificationJSON execute(@Nonnull List<Object> parameters);

}