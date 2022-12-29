package fr.univnantes.alma.commons.card;

import fr.univnantes.alma.core.card.CardJSON;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.Objects;
import java.util.UUID;

/**
 * Implementation of a card JSON information
 */
public class CardJSONImpl implements CardJSON {

    /**
     * Fields
     */
    private final UUID uuid;
    private final String type;

    /**
     * Creates a new card json
     *
     * @param uuid the uuid
     * @param type the type
     */
    public CardJSONImpl(@NonNull UUID uuid, @NonNull String type) {
        Objects.requireNonNull(uuid, "uuid cannot be null!");
        Objects.requireNonNull(type, "type cannot be null!");

        this.uuid = uuid;
        this.type = type;
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
    public @NonNull String getType() {
        return type;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(@Nullable Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CardJSONImpl cardJSON = (CardJSONImpl) o;
        return Objects.equals(uuid, cardJSON.uuid);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }

}