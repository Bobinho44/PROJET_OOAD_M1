package fr.univnantes.alma.core.command;

import org.springframework.lang.NonNull;

import java.util.List;

/**
 * Interface representing a command JSON information
 */
public interface ICommandJSON {

    /**
     * Gets the name
     *
     * @return the name
     */
    @NonNull String name();

    /**
     * Gets the parameters
     *
     * @return the parameters
     */
    @NonNull List<Object> parameters();

    /**
     * Checks if the sender must be the active player
     *
     * @return true if the sender must be the active player, false otherwise
     */
    boolean needActive();

}