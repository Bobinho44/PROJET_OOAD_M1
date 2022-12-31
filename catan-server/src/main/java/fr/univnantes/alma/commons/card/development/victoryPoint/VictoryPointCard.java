package fr.univnantes.alma.commons.card.development.victoryPoint;

import fr.univnantes.alma.commons.command.CommandJSON;
import fr.univnantes.alma.core.card.type.DevelopmentCard;
import fr.univnantes.alma.core.command.ICommandJSON;
import fr.univnantes.alma.core.player.IPlayer;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Objects;

/**
 * Abstract Implementation of a victory point cards
 */
public abstract class VictoryPointCard extends DevelopmentCard {

    /**
     * Creates a new victory point card
     */
    public VictoryPointCard(@NonNull String name, @NonNull String picture) {
        super(name, picture);
    }

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    public @NonNull ICommandJSON useEffect(@NonNull IPlayer player) {
        Objects.requireNonNull(player, "player cannot be null!");

        return new CommandJSON("addVictoryPoint", List.of(player, 1), true);
    }

}