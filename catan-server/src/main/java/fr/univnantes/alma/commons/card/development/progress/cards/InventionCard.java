package fr.univnantes.alma.commons.card.development.progress.cards;

import fr.univnantes.alma.Player;
import fr.univnantes.alma.commons.card.development.progress.ProgressCard;
import org.springframework.lang.NonNull;

import java.util.Objects;

/**
 * Class representing the progress card: invention
 */
public class InventionCard extends ProgressCard {

    /**
     * Creates a new invention card
     */
    public InventionCard() {
        super("Invention");
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