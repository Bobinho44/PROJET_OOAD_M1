package fr.univnantes.alma.commons.construction.dock;

import fr.univnantes.alma.core.construction.dock.Dock;
import fr.univnantes.alma.core.resource.Resource;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.Optional;

/**
 * Implementation of a dock
 */
public final class DockImpl implements Dock {

    /**
     * Fields
     */
    private final int ratio;
    private final Resource resource;

    /**
     * Creates a new dock
     *
     * @param resource the resource
     * @param ratio the ratio
     */
    public DockImpl(@Nullable Resource resource, int ratio) {
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
    public @NonNull Optional<Resource> getResource() {
        return Optional.ofNullable(resource);
    }

}