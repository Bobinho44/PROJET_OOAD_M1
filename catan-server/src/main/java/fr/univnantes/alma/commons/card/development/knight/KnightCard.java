package fr.univnantes.alma.commons.card.development.knight;

import fr.univnantes.alma.Player;
import fr.univnantes.alma.core.card.type.DevelopmentCard;
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
        super("Knight", "catan-web/catan-client/src/assets/special-card/Knight.png");
    }

    @Override
    public String getSourcePicture() {
        return this.getFileLocation();
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