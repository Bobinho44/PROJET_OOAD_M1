package fr.univnantes.alma.core.command;

import fr.univnantes.alma.core.notification.INotificationJSON;
import org.springframework.lang.NonNull;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Interface representing a command
 */
public interface ICommand {

    /**
     * Gets the name
     *
     * @return the name
     */
    @NonNull String getName();

    /**
     * Executes a command
     *
     * @param parameters the parameters
     * @return the command result json information
     */
    @Nonnull
    INotificationJSON execute(@Nonnull List<Object> parameters);

}