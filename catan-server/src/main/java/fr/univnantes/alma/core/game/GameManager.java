package fr.univnantes.alma.core.game;

import fr.univnantes.alma.core.construction.type.Building;
import fr.univnantes.alma.core.construction.type.Road;
import fr.univnantes.alma.core.territory.Territory;
import fr.univnantes.alma.core.card.type.DevelopmentCard;
import fr.univnantes.alma.core.construction.constructableArea.ConstructableArea;
import fr.univnantes.alma.core.player.Player;
import fr.univnantes.alma.core.trade.Trade;
import org.springframework.lang.NonNull;

import java.util.UUID;

public interface GameManager {

    /**
     * Builds the board
     */
    void build();


    /**
     * Rolls the dice
     *
     * @return the dice score
     */
    int rollDice();

    /**
     * Moves the thief
     *
     * @param territory the territory
     */
    void moveThief(@NonNull Territory territory);

    /**
     * Builds a road
     *
     * @param player the player
     * @param buildableRoadArea the buildable road area
     */
    void buildRoad(@NonNull Player player, @NonNull ConstructableArea<Road> buildableRoadArea);

    /**
     * Builds a colony
     *
     * @param player the player
     * @param buildableBuildingArea the buildable building area
     */
    void buildColony(@NonNull Player player, @NonNull ConstructableArea<Building> buildableBuildingArea);

    /**
     * Builds a city
     *
     * @param player the player
     * @param buildableBuildingArea the buildable building area
     */
    void buildCity(@NonNull Player player, @NonNull ConstructableArea<Building> buildableBuildingArea);

    /**
     * Buys a development card
     *
     * @param player the player
     */
    void buyDevelopmentCard(@NonNull Player player);

    /**
     * Play a development card
     *
     * @param player the player
     * @param developmentCard the development card
     */
    void playDevelopmentCard(@NonNull Player player, @NonNull DevelopmentCard developmentCard);

    /**
     * TODO
     * Trades with another player
     *
     * @param trade the trade
     */
    void tradeWithPlayer(@NonNull Trade trade);

    /**
     * TODO
     * Trades with the bank
     *
     * @param trade the trade
     */
    void tradeWithBank(@NonNull Trade trade);

    void giveLoot();

}