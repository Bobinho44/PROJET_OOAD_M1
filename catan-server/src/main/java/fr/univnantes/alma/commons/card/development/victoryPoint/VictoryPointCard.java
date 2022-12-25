package fr.univnantes.alma.commons.card.development.victoryPoint;

import fr.univnantes.alma.core.notification.NotificationJSON;
import fr.univnantes.alma.core.card.type.DevelopmentCard;
import fr.univnantes.alma.core.command.CommandManager;
import fr.univnantes.alma.core.player.Player;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Objects;

/**
 * Abstract Class representing victory point cards
 */
public abstract class VictoryPointCard extends DevelopmentCard {

    /**
     * Creates a new victory point card
     */
    public VictoryPointCard(@NonNull String name, String picture) {
        super(name, picture);
    }

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    public @NonNull NotificationJSON useEffect(@NonNull CommandManager commandManager, @NonNull Player player) {
        Objects.requireNonNull(commandManager, "commandManager cannot be null!");
        Objects.requireNonNull(player, "player cannot be null!");

        return commandManager.execute("addVictoryPoint", List.of(player, 1));
    }

}
