package fr.univnantes.alma.commons.card.development.victoryPoint.cards;

import fr.univnantes.alma.commons.card.development.victoryPoint.VictoryPointCard;

/**
 * Class representing the victory point card: church
 */
public class ChurchCard extends VictoryPointCard {

    /**
     * Creates a new church card
     */
    public ChurchCard() {
        super("Church", "catan-web/catan-client/src/assets/special-card/Church.png");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getSourcePicture() {
        return this.getFileLocation();
    }
}