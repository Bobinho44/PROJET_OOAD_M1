package fr.univnantes.alma.commons.command;

import fr.univnantes.alma.core.command.ICommandJSON;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Objects;

/**
 * Implementation of a command JSON information
 *
 * @param name Fields
 */
public record CommandJSON(String name, List<Object> parameters, boolean needActive) implements ICommandJSON {

    /**
     * Creates a new command json
     *
     * @param name       the name
     * @param parameters the parameters
     * @param needActive if the sender must be the active player
     */
    public CommandJSON(@NonNull String name, @NonNull List<Object> parameters, boolean needActive) {
        Objects.requireNonNull(name, "name cannot be null!");
        Objects.requireNonNull(parameters, "parameters cannot be null!");

        this.name = name;
        this.parameters = parameters;
        this.needActive = needActive;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull String name() {
        return name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull List<Object> parameters() {
        return parameters;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean needActive() {
        return needActive;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(@Nullable Object o) {
        if (this == o) return true;
        if (!(o instanceof CommandJSON commandJSON)) return false;
        return Objects.equals(name, commandJSON.name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

}