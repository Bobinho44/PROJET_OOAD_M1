package fr.univnantes.alma.commons.card.development.victoryPoint.cards;

import fr.univnantes.alma.commons.annotation.CardAmount;
import fr.univnantes.alma.commons.card.development.victoryPoint.VictoryPointCard;

/**
 * Class representing the victory point card: library
 */
@CardAmount(1)
public class LibraryCard extends VictoryPointCard {

    /**
     * Creates a new library card
     */
    public LibraryCard() {
        super("Library", "catan-web/catan-client/src/assets/special-card/Librairy.png");
    }

}