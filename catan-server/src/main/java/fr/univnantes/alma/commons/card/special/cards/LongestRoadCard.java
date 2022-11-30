package fr.univnantes.alma.commons.card.special.cards;

import fr.univnantes.alma.core.card.type.SpecialCard;

/**
 * Class representing the special card: longest road
 */
public class LongestRoadCard extends SpecialCard {

    /**
     * Creates a new longest road card
     */
    public LongestRoadCard() {
        super("Longest road", "NaN (image pas encore créée)");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getSourcePicture() {
        return this.getFileLocation();
    }
}