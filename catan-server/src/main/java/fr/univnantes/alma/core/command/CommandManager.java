package fr.univnantes.alma.core.command;

import fr.univnantes.alma.core.notification.NotificationJSON;
import fr.univnantes.alma.core.command.executor.CommandExecutor;
import org.springframework.lang.NonNull;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.function.Function;

/**
 * Interface representing a game manager
 */
public interface CommandManager {

    /**
     * Adds a command
     *
     * @param name the name
     * @param action the action
     */
    void addCommand(@NonNull String name, @Nonnull Function<CommandExecutor, NotificationJSON> action);

    /**
     * Removes a command
     *
     * @param name the name
     */
    void removeCommand(@NonNull String name);

    /**
     * Executes a command
     *
     * @param name the name
     * @param parameters the parameters
     * @return the command result json information
     */
    @NonNull NotificationJSON execute(@NonNull String name, @NonNull List<Object> parameters);

}