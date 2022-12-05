package fr.univnantes.alma.core.card.type;

import fr.univnantes.alma.core.card.Card;
import fr.univnantes.alma.core.player.Player;
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
    private final String picture;

    /**
     * Creates a new special card
     *
     * @param name the name
     * @param picture the picture
     */
    public DevelopmentCard(@NonNull String name, @NonNull String picture) {
        Objects.requireNonNull(name, "name cannot be null!");
        Objects.requireNonNull(picture, "picture cannot be null!");

        this.name = name;
        this.picture = picture;
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
     * Gets the card picture
     *
     * @return the picture
     */
    public @NonNull String getPicture() { return picture; }

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