package fr.univnantes.alma.commons.card.development.progress.cards;

import fr.univnantes.alma.core.notification.NotificationJSON;
import fr.univnantes.alma.commons.notification.NotificationReplyJSON;
import fr.univnantes.alma.core.command.CommandManager;
import fr.univnantes.alma.commons.card.development.progress.ProgressCard;
import fr.univnantes.alma.core.player.Player;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Objects;

/**
 * Class representing the progress card: invention
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
    public @NonNull NotificationJSON useEffect(@NonNull CommandManager commandManager, @NonNull Player player) {
        Objects.requireNonNull(commandManager, "commandManager cannot be null!");
        Objects.requireNonNull(player, "player cannot be null!");

        return new NotificationReplyJSON(List.of("takeTwoResources"));
    }

}