package fr.univnantes.alma.commons.card.development.progress.cards;

import fr.univnantes.alma.commons.command.CommandJSONImpl;
import fr.univnantes.alma.core.command.CommandJSON;
import fr.univnantes.alma.core.player.Player;
import fr.univnantes.alma.commons.card.development.progress.ProgressCard;
import org.springframework.lang.NonNull;

import java.util.Collections;
import java.util.Objects;

/**
 * Implementation of a progress card: monopoly
 */
public class MonopolyCard extends ProgressCard {

    /**
     * Creates a new monopoly card
     */
    public MonopolyCard() {
        super("Monopoly", "catan-web/catan-client/src/assets/special-card/Monopoly.png");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull CommandJSON useEffect(@NonNull Player player) {
        Objects.requireNonNull(player, "player cannot be null!");

        return new CommandJSONImpl("askStealResourceFromAllPlayers", Collections.emptyList(), true);
    }

}