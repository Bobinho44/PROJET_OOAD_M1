package fr.univnantes.alma.commons.card.development.victoryPoint;

import fr.univnantes.alma.commons.annotation.DevelopmentCardCost;
import fr.univnantes.alma.commons.annotation.ResourceInformation;
import fr.univnantes.alma.commons.game.GameController;
import fr.univnantes.alma.core.card.type.DevelopmentCard;
import fr.univnantes.alma.core.player.Player;
import org.springframework.lang.NonNull;

import java.util.Objects;

/**
 * Abstract Class representing victory point cards
 */
@DevelopmentCardCost(resources = {
        @ResourceInformation(name = "Wheat", amount = 1),
        @ResourceInformation(name = "Wool", amount = 1),
        @ResourceInformation(name = "Ore", amount = 1)
})
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
    public void useEffect(@NonNull GameController gameController, @NonNull Player player) {
        Objects.requireNonNull(player, "player cannot be null!");

        player.addVictoryPoints(1);
    }

}
