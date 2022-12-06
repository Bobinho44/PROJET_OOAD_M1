package fr.univnantes.alma.core.game;

import fr.univnantes.alma.core.card.type.DevelopmentCard;
import fr.univnantes.alma.core.card.type.SpecialCard;
import fr.univnantes.alma.core.player.Player;
import fr.univnantes.alma.core.ressource.Resource;
import fr.univnantes.alma.core.trade.Trade;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.UUID;

public interface Game {

    void rollDice();

    /**
     * Moves the thief
     *
     * @param uuid the territory uuid
     */
    void moveThief(@NonNull UUID uuid);

    /**
     * Steals a card from another player
     *
     * @param stealer the stealer uuid
     * @param stolen the stealer uuid
     */
    void stealCardFromPlayer(@NonNull UUID stealer, @NonNull UUID stolen);

    /**
     * Discards half of the player's cards
     *
     * @param player the player uuid
     */
    void discardHalfCards(@NonNull UUID player);

    /**
     * Builds a road
     *
     * @param player the player uuid
     * @param buildableRoadArea the buildable road area uuid
     */
    void buildRoad(@NonNull UUID player, @NonNull UUID buildableRoadArea);

    /**
     * Builds a colony
     *
     * @param player the player uuid
     * @param buildableColonyArea the buildable colony area uuid
     */
    void buildColony(@NonNull UUID player, @NonNull UUID buildableColonyArea);

    /**
     * Builds a city
     *
     * @param player the player uuid
     * @param buildableCityArea the buildable city area uuid
     */
    void buildCity(@NonNull UUID player, @NonNull UUID buildableCityArea);

    /**
     * Buys a development card
     *
     * @param player the player uuid
     */
    void buyDevelopmentCard(@NonNull UUID player);

    /**
     * TODO voir comment on communique la carte du client vers le serveur
     * Play a development card
     *
     * @param player the player uuid
     */
    void playDevelopmentCard(@NonNull UUID player, @NonNull DevelopmentCard developmentCard);

    /**
     * Trades with another player
     *
     * @param trade the trade
     */
    void tradeWithPlayer(@NonNull Trade trade);

    /**
     * Trades with the bank
     *
     * @param trade the trade
     */
    void tradeWithBank(@NonNull Trade trade);

    /**
     * Finishes the tour
     */
    void finishTheTour();
}