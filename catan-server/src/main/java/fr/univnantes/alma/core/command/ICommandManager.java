package fr.univnantes.alma.core.command;

import fr.univnantes.alma.core.exception.UnregisteredCommandException;
import fr.univnantes.alma.core.notification.INotificationJSON;
import fr.univnantes.alma.core.command.executor.ICommandExecutor;
import org.springframework.lang.NonNull;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.function.Function;

/**
 * Interface representing a command manager
 */
public interface ICommandManager {

    /**
     * Gets the command
     *
     * @param name the name
     * @return the command
     * @throws UnregisteredCommandException if the command does not exist
     */
    @NonNull
    ICommand getCommand(@NonNull String name) throws UnregisteredCommandException;

    /**
     * Checks if the command exist
     *
     * @param name the name
     * @return true if the command exist, false otherwise
     */
    boolean hasCommand(@NonNull String name);

    /**
     * Adds a command
     *
     * @param name the name
     * @param action the action
     */
    void addCommand(@NonNull String name, @Nonnull Function<ICommandExecutor, INotificationJSON> action);

    /**
     * Removes a command
     *
     * @param command the command
     */
    void removeCommand(@NonNull ICommand command);

    /**
     * Executes a command
     *
     * @param command the command
     * @param parameters the parameters
     * @return the command result json information
     */
    @NonNull
    INotificationJSON execute(@NonNull ICommand command, @NonNull List<Object> parameters);

}