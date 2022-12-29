package fr.univnantes.alma.commons.construction.constructableArea;

import fr.univnantes.alma.core.construction.constructableArea.AreaJSON;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.Objects;
import java.util.UUID;

/**
 * Implementation of a constructable area JSON information
 */
public class AreaJSONImpl implements AreaJSON {
    /**
     * Fields
     */
    private final UUID uuid;
    private final String type;

    /**
     * Creates a new construction json
     *
     * @param uuid the uuid
     * @param type the type
     */
    public AreaJSONImpl(@NonNull UUID uuid, @NonNull String type) {
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
        if (!(o instanceof AreaJSONImpl areaJSON)) return false;
        return Objects.equals(uuid, areaJSON.uuid);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }

}