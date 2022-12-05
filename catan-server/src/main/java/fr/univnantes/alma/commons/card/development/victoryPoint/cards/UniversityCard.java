package fr.univnantes.alma.commons.card.development.victoryPoint.cards;

import fr.univnantes.alma.commons.annotation.CardAmount;
import fr.univnantes.alma.commons.card.development.victoryPoint.VictoryPointCard;

/**
 * Class representing the victory point card: university
 */
@CardAmount(1)
public class UniversityCard extends VictoryPointCard {

    /**
     * Creates a new university card
     */
    public UniversityCard() {
        super("University", "catan-web/catan-client/src/assets/special-card/University.png");
    }

}