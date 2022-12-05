package fr.univnantes.alma.commons.card.development.progress.cards;

import fr.univnantes.alma.commons.annotation.CardAmount;
import fr.univnantes.alma.core.player.Player;
import fr.univnantes.alma.commons.card.development.progress.ProgressCard;
import org.springframework.lang.NonNull;

import java.util.Objects;

/**
 * Class representing the progress card: invention
 */
@CardAmount(2)
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
    public void useEffect(@NonNull Player player) {
        Objects.requireNonNull(player, "player cannot be null!");

        /*
        TODO:
            - take 2 resources from the bank
         */
    }

}