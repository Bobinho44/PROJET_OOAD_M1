package fr.univnantes.alma.commons.card.development.progress.cards;

import fr.univnantes.alma.commons.command.CommandJSON;
import fr.univnantes.alma.commons.card.development.progress.ProgressCard;
import fr.univnantes.alma.core.command.ICommandJSON;
import fr.univnantes.alma.core.player.IPlayer;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Objects;

/**
 * Implementation of a progress card: road building
 */
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
    public @NonNull ICommandJSON useEffect(@NonNull IPlayer player) {
        Objects.requireNonNull(player, "player cannot be null!");

        return new CommandJSON("askPlaceTwoFreeRoad", List.of(player), true);
    }

}