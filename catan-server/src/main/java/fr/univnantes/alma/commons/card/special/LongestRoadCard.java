package fr.univnantes.alma.commons.card.special;

import fr.univnantes.alma.commons.annotation.CardAmount;
import fr.univnantes.alma.core.card.type.SpecialCard;

/**
 * Class representing the special card: longest road
 */
@CardAmount(1)
public class LongestRoadCard extends SpecialCard {

    /**
     * Creates a new longest road card
     */
    public LongestRoadCard() {
        super("Longest road", "picture");
    }

}