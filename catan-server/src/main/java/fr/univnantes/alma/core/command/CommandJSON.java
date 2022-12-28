package fr.univnantes.alma.core.command;

import org.springframework.lang.NonNull;

import java.util.List;

/**
 * Interface representing a command JSON information
 */
public interface CommandJSON {

    /**
     * Gets the name
     *
     * @return the name
     */
    @NonNull String getName();

    /**
     * Gets the parameters
     *
     * @return the parameters
     */
    @NonNull List<Object> getParameters();

    /**
     * Checks if the sender must be the active player
     *
     * @return true if the sender must be the active player, false otherwise
     */
    boolean isNeedActive();

}