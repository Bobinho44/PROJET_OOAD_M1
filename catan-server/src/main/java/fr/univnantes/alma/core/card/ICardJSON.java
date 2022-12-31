package fr.univnantes.alma.core.card;

import org.springframework.lang.NonNull;

import java.util.UUID;

/**
 * Interface representing a card JSON information
 */
public interface ICardJSON {

    /**
     * Gets the uuid
     *
     * @return the uuid
     */
    @NonNull UUID getUUID();

    /**
     * Gets the type
     *
     * @return the type
     */
    @NonNull String getType();

}