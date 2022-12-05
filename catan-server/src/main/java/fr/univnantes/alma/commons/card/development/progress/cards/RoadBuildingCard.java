package fr.univnantes.alma.commons.card.development.progress.cards;

import fr.univnantes.alma.commons.annotation.CardAmount;
import fr.univnantes.alma.core.player.Player;
import fr.univnantes.alma.commons.card.development.progress.ProgressCard;
import org.springframework.lang.NonNull;

import java.util.Objects;

/**
 * Class representing the progress card: road building
 */
@CardAmount(2)
public class RoadBuildingCard extends ProgressCard {

    /**
     * Creates a new road building card
     */
    public RoadBuildingCard() {
        super("Road building", "catan-web/catan-client/src/assets/special-card/RoadBuilding.png");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void useEffect(@NonNull Player player) {
        Objects.requireNonNull(player, "player cannot be null!");

        /*
        TODO:
            - place two free road
         */
    }

}
