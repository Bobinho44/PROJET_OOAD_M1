package fr.univnantes.alma.commons.card.development.progress.cards;

import fr.univnantes.alma.Player;
import fr.univnantes.alma.commons.card.development.progress.ProgressCard;
import org.springframework.lang.NonNull;

import java.util.Objects;

/**
 * Class representing the progress card: monopoly
 */
public class MonopolyCard extends ProgressCard {

    /**
     * Creates a new monopoly card
     */
    public MonopolyCard() {
        super("Monopoly");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void useEffect(@NonNull Player player) {
        Objects.requireNonNull(player, "player cannot be null!");

        /*
        TODO:
            - take all resources of a chosen type from all players
         */
    }

}