package fr.univnantes.alma.commons.card.special.cards;

import fr.univnantes.alma.core.card.type.SpecialCard;

/**
 * Class representing the special card: most powerful army
 */
public class MostPowerfulArmyCard extends SpecialCard {

    /**
     * Creates a new most powerful army card
     */
    public MostPowerfulArmyCard() {
        super("Most powerful army", "NaN (image pas encore créée)");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getSourcePicture() {
        return this.getFileLocation();
    }
}