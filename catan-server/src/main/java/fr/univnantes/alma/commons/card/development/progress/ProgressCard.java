package fr.univnantes.alma.commons.card.development.progress;

import fr.univnantes.alma.commons.annotation.DevelopmentCardCost;
import fr.univnantes.alma.commons.annotation.ResourceInformation;
import fr.univnantes.alma.core.card.type.DevelopmentCard;
import org.springframework.lang.NonNull;

/**
 * Abstract Class representing progress cards
 */
@DevelopmentCardCost(resources = {
        @ResourceInformation(name = "Wheat", amount = 1),
        @ResourceInformation(name = "Wool", amount = 1),
        @ResourceInformation(name = "Ore", amount = 1)
})
public abstract class ProgressCard extends DevelopmentCard {

    /**
     * Creates a new progress card
     */
    public ProgressCard(@NonNull String name, @NonNull String picture) {
        super(name, picture);
    }

}