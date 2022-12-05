package fr.univnantes.alma.commons.card.special;

import fr.univnantes.alma.commons.annotation.CardAmount;
import fr.univnantes.alma.core.card.type.SpecialCard;

/**
 * Class representing the special card: most powerful army
 */
@CardAmount(1)
public class MostPowerfulArmyCard extends SpecialCard {

    /**
     * Creates a new most powerful army card
     */
    public MostPowerfulArmyCard() {
        super("Most powerful army", "picture");
    }

}