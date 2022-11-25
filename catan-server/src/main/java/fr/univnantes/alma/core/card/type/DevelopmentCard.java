package fr.univnantes.alma.core.card.type;

import fr.univnantes.alma.Player;
import fr.univnantes.alma.core.card.Card;
import org.springframework.lang.NonNull;

import java.util.Objects;

/**
 * Abstract Class representing development cards
 */
public abstract class DevelopmentCard implements Card {

    /**
     * Fields
     */
    private final String name;

    /**
     * Creates a new special card
     *
     * @param name the name
     */
    public DevelopmentCard(@NonNull String name) {
        Objects.requireNonNull(name, "name cannot be null!");

        this.name = name;
    }

    /**
     * Gets the development card name
     *
     * @return the development card name
     */
    public @NonNull String getName() {
        return name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void getEffect(@NonNull Player player) {}

    /**
     * {@inheritDoc}
     */
    @Override
    public void looseEffect(@NonNull Player player) {}

}