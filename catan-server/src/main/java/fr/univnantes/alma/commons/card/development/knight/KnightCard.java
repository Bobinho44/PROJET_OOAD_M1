package fr.univnantes.alma.commons.card.development.knight;

import fr.univnantes.alma.commons.command.CommandJSONImpl;
import fr.univnantes.alma.core.command.CommandJSON;
import fr.univnantes.alma.core.card.type.DevelopmentCard;
import fr.univnantes.alma.core.player.Player;
import org.springframework.lang.NonNull;

import java.util.Collections;
import java.util.Objects;

/**
 * Implementation of a card: knight
 */
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
    public @NonNull CommandJSON useEffect(@NonNull Player player) {
        Objects.requireNonNull(player, "player cannot be null!");

        return new CommandJSONImpl("askMoveThiefAndTakeCard", Collections.emptyList(), true);
    }

}