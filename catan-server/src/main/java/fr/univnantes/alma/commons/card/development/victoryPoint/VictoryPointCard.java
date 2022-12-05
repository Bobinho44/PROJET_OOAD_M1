package fr.univnantes.alma.commons.card.development.victoryPoint;

import fr.univnantes.alma.core.card.type.DevelopmentCard;
import fr.univnantes.alma.core.player.Player;
import org.springframework.lang.NonNull;

import java.util.Objects;

/**
 * Abstract Class representing victory point cards
 */
public abstract class VictoryPointCard extends DevelopmentCard {

    /**
     * Creates a new victory point card
     */
    public VictoryPointCard(@NonNull String name, String picture) {
        super(name, picture);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void useEffect(@NonNull Player player) {
        Objects.requireNonNull(player, "player cannot be null!");

        /*
        TODO:
            - give a victory point
         */
    }

}
