package fr.univnantes.alma.commons.card.development.victoryPoint.cards;

import fr.univnantes.alma.commons.annotation.CardAmount;
import fr.univnantes.alma.commons.card.development.victoryPoint.VictoryPointCard;

/**
 * Class representing the victory point card: marketplace
 */
@CardAmount(1)
public class MarketplaceCard extends VictoryPointCard {

    /**
     * Creates a new marketplace card
     */
    public MarketplaceCard() { super("Marketplace", "catan-web/catan-client/src/assets/special-card/Marketplace.png"); }

}