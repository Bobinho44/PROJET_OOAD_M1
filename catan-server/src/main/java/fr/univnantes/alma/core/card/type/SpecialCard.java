package fr.univnantes.alma.core.card.type;

import fr.univnantes.alma.Player;
import fr.univnantes.alma.core.card.Card;
import org.springframework.lang.NonNull;

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
    public void useEffect(@NonNull Player player) {}

    /**
     * {@inheritDoc}
     */
    @Override
    public void getEffect(@NonNull Player player) {
        Objects.requireNonNull(player, "player cannot be null!");

        /*
        TODO:
            - give 1 victory point
         */
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void looseEffect(@NonNull Player player) {
        Objects.requireNonNull(player, "player cannot be null!");

        /*
        TODO:
            - remove 1 victory point
         */
    }
}
