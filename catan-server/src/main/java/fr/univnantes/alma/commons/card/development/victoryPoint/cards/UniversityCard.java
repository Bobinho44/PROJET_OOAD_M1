package fr.univnantes.alma.commons.card.development.victoryPoint.cards;

import fr.univnantes.alma.commons.card.development.victoryPoint.VictoryPointCard;

/**
 * Class representing the victory point card: university
 */
public class UniversityCard extends VictoryPointCard {

    /**
     * Creates a new university card
     */
    public UniversityCard() {
        super("University", "catan-web/catan-client/src/assets/special-card/University.png");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getSourcePicture() {
        return this.getFileLocation();
    }
}