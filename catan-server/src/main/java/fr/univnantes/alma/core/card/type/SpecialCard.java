package fr.univnantes.alma.core.card.type;

import fr.univnantes.alma.commons.game.GameController;
import fr.univnantes.alma.core.card.Card;
import fr.univnantes.alma.core.player.Player;
import org.springframework.lang.NonNull;

import javax.annotation.Nullable;
import java.util.Objects;

/**
 * Abstract Class representing special cards
 */
public abstract class SpecialCard implements Card {

    /**
     * Fields
     */
    private final String name;
    private final String picture;

    private @Nullable Player owner;

    /**
     * Creates a new special card
     *
     * @param name the name
     * @param picture the picture
     */
    public SpecialCard(@NonNull String name, String picture) {
        Objects.requireNonNull(name, "name cannot be null!");
        Objects.requireNonNull(name, "picture cannot be null!");

        this.name = name;
        this.picture = picture;
    }

    /**
     * Gets the special card name
     *
     * @return the special card name
     */
    public @NonNull String getName() {
        return name;
    }

    /**
     * Gets the card picture
     *
     * @return the picture
     */
    public @NonNull String getPicture() { return picture; }

    /**
     * {@inheritDoc}
     */
    @Override
    public void useEffect(@NonNull GameController gameController, @NonNull Player player) {}

    /**
     * {@inheritDoc}
     */
    @Override
    public void getEffect(@NonNull Player player) {
        Objects.requireNonNull(player, "player cannot be null!");
        player.addVictoryPoints(2);

        owner = player;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void looseEffect(@NonNull Player player) {
        Objects.requireNonNull(player, "player cannot be null!");

        player.addVictoryPoints(2);
        owner = null;
    }

    /**
     * @return the owner of the card, null if there is not yet
     */
    public Player getOwner(){
        return owner;
    }
}
