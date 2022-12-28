package fr.univnantes.alma.core.card;

import fr.univnantes.alma.core.command.CommandJSON;
import fr.univnantes.alma.core.notification.NotificationJSON;
import fr.univnantes.alma.core.command.CommandManager;
import fr.univnantes.alma.core.player.Player;
import org.springframework.lang.NonNull;

import java.util.UUID;

/**
 * Interface representing a card
 */
public interface Card {

    /**
     * Gets uuid
     *
     * @return the uuid;
     */
    @NonNull UUID getUUID();

    /**
     * Gets the name
     *
     * @return the name
     */
    @NonNull String getName();

    /**
     * Gets the picture
     *
     * @return the picture
     */
    @NonNull String getPicture();

    /**
     * Applies the use effect
     *
     * @param player         the player
     * @return the command result json information
     */
    @NonNull CommandJSON useEffect(@NonNull Player player);

}