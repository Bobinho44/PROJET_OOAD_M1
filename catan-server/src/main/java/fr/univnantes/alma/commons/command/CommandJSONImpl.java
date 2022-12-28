package fr.univnantes.alma.commons.command;

import fr.univnantes.alma.core.command.CommandJSON;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Objects;

/**
 * Implementation of a command JSON information
 */
public class CommandJSONImpl implements CommandJSON {

    /**
     * Fields
     */
    private final String name;
    private final List<Object> parameters;
    private final boolean needActive;

    /**
     * Creates a new command json
     *
     * @param name the name
     * @param parameters the parameters
     * @param needActive if the sender must be the active player
     */
    public CommandJSONImpl(@NonNull String name, @NonNull List<Object> parameters, boolean needActive) {
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
    public @NonNull String getName() {
        return name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull List<Object> getParameters() {
        return parameters;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isNeedActive() {
        return needActive;
    }

}