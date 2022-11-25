package fr.univnantes.alma.commons.card.development.progress;

import fr.univnantes.alma.commons.card.development.DevelopmentCard;
import org.springframework.lang.NonNull;

/**
 * Abstract Class representing progress cards
 */
public abstract class ProgressCard extends DevelopmentCard {

    /**
     * Creates a new progress card
     */
    public ProgressCard(@NonNull String name) {
        super(name);
    }

}