package fr.univnantes.alma.commons.card.development.knight;

import fr.univnantes.alma.core.notification.NotificationJSON;
import fr.univnantes.alma.commons.notification.NotificationReplyJSON;
import fr.univnantes.alma.core.command.CommandManager;
import fr.univnantes.alma.core.card.type.DevelopmentCard;
import fr.univnantes.alma.core.player.Player;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Class representing knight cards
 */
public class KnightCard extends DevelopmentCard {

    /**
     * Creates a new knight card
     */
    public KnightCard() {
        super("Knight", "catan-web/catan-client/src/assets/special-card/Knight.png");
    }

    /**
     * Creates the knight card
     *
     * @param uuid the uuid
     */
    public KnightCard(@NonNull UUID uuid) {
        super(uuid, "Knight", "catan-web/catan-client/src/assets/special-card/Knight.png");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull NotificationJSON useEffect(@NonNull CommandManager commandManager, @NonNull Player player) {
        Objects.requireNonNull(commandManager, "commandManager cannot be null!");
        Objects.requireNonNull(player, "player cannot be null!");

        return new NotificationReplyJSON(List.of("moveThiefAndTakeCard"));
    }

}