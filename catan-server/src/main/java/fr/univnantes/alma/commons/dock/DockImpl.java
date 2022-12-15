package fr.univnantes.alma.commons.dock;

import fr.univnantes.alma.core.dock.Dock;
import fr.univnantes.alma.core.ressource.Resource;
import org.springframework.lang.NonNull;

public class DockImpl implements Dock {

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
    public DockImpl(Resource resource, int ratio) {
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
    public Resource getResource() {
        return resource;
    }

}