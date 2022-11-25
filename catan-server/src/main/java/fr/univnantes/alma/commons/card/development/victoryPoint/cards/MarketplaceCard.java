package fr.univnantes.alma.commons.card.development.victoryPoint.cards;

import fr.univnantes.alma.commons.card.development.victoryPoint.VictoryPointCard;

/**
 * Class representing the victory point card: marketplace
 */
public class MarketplaceCard extends VictoryPointCard {

    /**
     * Creates a new marketplace card
     */
    public MarketplaceCard(String name) { super("Marketplace", "catan-web/catan-client/src/assets/special-card/Marketplace.png"); }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getSourcePicture() { return this.getFileLocation(); }
}