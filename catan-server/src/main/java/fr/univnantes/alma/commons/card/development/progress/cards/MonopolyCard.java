package fr.univnantes.alma.commons.card.development.progress.cards;

import fr.univnantes.alma.commons.command.CommandJSON;
import fr.univnantes.alma.core.command.ICommandJSON;
import fr.univnantes.alma.core.player.IPlayer;
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
    public @NonNull ICommandJSON useEffect(@NonNull IPlayer player) {
        Objects.requireNonNull(player, "player cannot be null!");

        return new CommandJSON("askStealResourceFromAllPlayers", Collections.emptyList(), true);
    }

}