package fr.univnantes.alma.commons.card.development.progress.cards;

import fr.univnantes.alma.commons.command.CommandJSON;
import fr.univnantes.alma.commons.card.development.progress.ProgressCard;
import fr.univnantes.alma.core.command.ICommandJSON;
import fr.univnantes.alma.core.player.IPlayer;
import org.springframework.lang.NonNull;

import java.util.Collections;
import java.util.Objects;

/**
 * Implementation of a progress card: invention
 */
public class InventionCard extends ProgressCard {

    /**
     * Creates a new invention card
     */
    public InventionCard() {
        super("Invention", "catan-web/catan-client/src/assets/special-card/Monopoly.png");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull ICommandJSON useEffect(@NonNull IPlayer player) {
        Objects.requireNonNull(player, "player cannot be null!");

        return new CommandJSON("askTakeTwoResources", Collections.emptyList(), true);
    }

}