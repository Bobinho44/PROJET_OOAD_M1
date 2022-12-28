package fr.univnantes.alma.core.construction.dock;

import fr.univnantes.alma.core.resource.Resource;
import org.springframework.lang.NonNull;

import java.util.Optional;

/**
 * Interface representing a dock
 */
public interface Dock {

    /**
     * Gets the resource
     *
     * @return the optional resource
     */
    @NonNull Optional<Resource> getResource();

    /**
     * Gets the ratio
     *
     * @return the ratio
     */
    int getRatio();

}