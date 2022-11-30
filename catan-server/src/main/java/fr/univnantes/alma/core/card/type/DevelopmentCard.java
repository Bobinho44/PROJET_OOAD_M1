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
     * Location
     */
    private final String fileLocation;

    /**
     * Creates a new special card
     *
     * @param name the name
     * @param fileLocation the URL location of the picture
     */
    public DevelopmentCard(@NonNull String name, String fileLocation) {
        Objects.requireNonNull(name, "name cannot be null!");
        Objects.requireNonNull(fileLocation, "No URL file input!");

        this.name = name;
        this.fileLocation = fileLocation;
    }

    /**
     * Gets the development card name
     *
     * @return the development card name
     */
    public @NonNull String getName() {
        return name;
    }

    /** To get the localization file of the picture.
     * @return the URL of the picture file.
     */
    public @NonNull String getFileLocation() { return this.fileLocation; }

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