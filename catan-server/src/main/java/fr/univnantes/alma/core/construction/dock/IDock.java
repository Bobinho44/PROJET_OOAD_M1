package fr.univnantes.alma.core.construction.dock;

import fr.univnantes.alma.core.resource.IResource;
import org.springframework.lang.NonNull;

import java.util.Optional;

/**
 * Interface representing a dock
 */
public interface IDock {

    /**
     * Gets the resource
     *
     * @return the optional resource
     */
    @NonNull Optional<IResource> getResource();

    /**
     * Gets the ratio
     *
     * @return the ratio
     */
    int getRatio();

}