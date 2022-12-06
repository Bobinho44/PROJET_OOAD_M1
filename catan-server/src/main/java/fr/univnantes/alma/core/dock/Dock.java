package fr.univnantes.alma.core.dock;

import fr.univnantes.alma.core.ressource.Resource;
import org.springframework.lang.NonNull;

public interface Dock {

    /**
     * Gets the resource
     *
     * @return the resource
     */
    @NonNull Resource getResource();

    /**
     * Gets the ratio
     *
     * @return the ratio
     */
    int getRatio();

}