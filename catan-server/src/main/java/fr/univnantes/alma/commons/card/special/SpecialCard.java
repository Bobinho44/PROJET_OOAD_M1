package fr.univnantes.alma.commons.card.special;

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

    /**
     * Creates a new special card
     *
     * @param name the name
     */
    public SpecialCard(@NonNull String name) {
        Objects.requireNonNull(name, "name cannot be null!");

        this.name = name;
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
