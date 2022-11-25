package fr.univnantes.alma.commons.card.development.knight;

import fr.univnantes.alma.Player;
import fr.univnantes.alma.commons.card.development.DevelopmentCard;
import org.springframework.lang.NonNull;

import java.util.Objects;

/**
 * Class representing knight cards
 */
public class KnightCard extends DevelopmentCard {

    /**
     * Creates a new knight card
     */
    public KnightCard() {
        super("Knight");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void useEffect(@NonNull Player player) {
        Objects.requireNonNull(player, "player cannot be null!");

        /*
        TODO:
            - move thief
            - take a card from another player
         */
    }

}