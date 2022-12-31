package fr.univnantes.alma.commons.construction.dock;

import fr.univnantes.alma.core.construction.dock.IDock;
import fr.univnantes.alma.core.resource.IResource;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.Optional;

/**
 * Implementation of a dock
 */
public class Dock implements IDock {

    /**
     * Fields
     */
    private final int ratio;
    private final IResource resource;

    /**
     * Creates a new dock
     *
     * @param resource the resource
     * @param ratio the ratio
     */
    public Dock(@Nullable IResource resource, int ratio) {
        this.ratio = ratio;
        this.resource = resource;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getRatio() {
        return ratio;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull Optional<IResource> getResource() {
        return Optional.ofNullable(resource);
    }

}