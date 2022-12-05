package fr.univnantes.alma.core.card;

import fr.univnantes.alma.core.player.Player;
import org.springframework.lang.NonNull;

/**
 * Interface representing cards
 */
public interface Card {

    /**
     * Applies the use effect of the card
     *
     * @param player the affected player
     */
    void useEffect(@NonNull Player player);

    /**
     * Applies the get effect of the card
     *
     * @param player the affected player
     */
    void getEffect(@NonNull Player player);

    /**
     * Applies the loose effect of the card
     *
     * @param player the affected player
     */
    void looseEffect(@NonNull Player player);

}