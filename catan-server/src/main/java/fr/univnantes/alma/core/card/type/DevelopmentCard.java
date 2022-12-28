package fr.univnantes.alma.core.card.type;

import fr.univnantes.alma.core.card.Card;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.Objects;
import java.util.UUID;

/**
 * Abstract Class representing a development card
 */
public abstract class DevelopmentCard implements Card {

    /**
     * Fields
     */
    private final UUID uuid;
    private final String name;
    private final String picture;

    /**
     * Creates a new development card
     *
     * @param name the name
     * @param picture the picture
     */
    public DevelopmentCard(@NonNull String name, @NonNull String picture) {
        Objects.requireNonNull(name, "name cannot be null!");
        Objects.requireNonNull(picture, "picture cannot be null!");

        this.uuid = UUID.randomUUID();
        this.name = name;
        this.picture = picture;
    }

    /**
     * Creates a new development card
     *
     * @param uuid the uuid
     * @param name the name
     * @param picture the picture
     */
    public DevelopmentCard(@NonNull UUID uuid, @NonNull String name, @NonNull String picture) {
        Objects.requireNonNull(uuid, "uuid cannot be null!");
        Objects.requireNonNull(name, "name cannot be null!");
        Objects.requireNonNull(picture, "picture cannot be null!");

        this.uuid = uuid;
        this.name = name;
        this.picture = picture;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull UUID getUUID() {
        return uuid;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull String getName() {
        return name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull String getPicture() { return picture; }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(@Nullable Object o) {
        if (this == o) return true;
        if (!(o instanceof DevelopmentCard)) return false;

        return Objects.equals(uuid, ((DevelopmentCard) o).getUUID());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return uuid.hashCode();
    }

}