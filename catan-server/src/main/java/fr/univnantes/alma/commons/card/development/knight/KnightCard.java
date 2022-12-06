package fr.univnantes.alma.commons.card.development.knight;

import fr.univnantes.alma.commons.annotation.CardAmount;
import fr.univnantes.alma.commons.annotation.DevelopmentCardCost;
import fr.univnantes.alma.commons.annotation.ResourceInformation;
import fr.univnantes.alma.core.player.Player;
import fr.univnantes.alma.core.card.type.DevelopmentCard;
import org.springframework.lang.NonNull;

import java.util.Objects;

/**
 * Class representing knight cards
 */
@CardAmount(14)
@DevelopmentCardCost(resources = {
        @ResourceInformation(name = "Wheat", amount = 1),
        @ResourceInformation(name = "Wool", amount = 1),
        @ResourceInformation(name = "Ore", amount = 1)
})
public class KnightCard extends DevelopmentCard {

    /**
     * Creates a new knight card
     */
    public KnightCard() {
        super("Knight", "catan-web/catan-client/src/assets/special-card/Knight.png");
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