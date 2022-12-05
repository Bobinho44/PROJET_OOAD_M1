package fr.univnantes.alma.commons.card.development.victoryPoint.cards;

import fr.univnantes.alma.commons.annotation.CardAmount;
import fr.univnantes.alma.commons.card.development.victoryPoint.VictoryPointCard;

/**
 * Class representing the victory point card: church
 */
@CardAmount(1)
public class ChurchCard extends VictoryPointCard {

    /**
     * Creates a new church card
     */
    public ChurchCard() {
        super("Church", "catan-web/catan-client/src/assets/special-card/Church.png");
    }

}