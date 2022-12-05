package fr.univnantes.alma.commons.card.development.victoryPoint.cards;

import fr.univnantes.alma.commons.annotation.CardAmount;
import fr.univnantes.alma.commons.card.development.victoryPoint.VictoryPointCard;

/**
 * Class representing the victory point card: parliament
 */
@CardAmount(1)
public class ParliamentCard extends VictoryPointCard {

    /**
     * Creates a new parliament card
     */
    public ParliamentCard() {
        super("Parliament", "catan-web/catan-client/src/assets/special-card/Parliament.png");
    }

}