package fr.univnantes.alma.core.ressource;

import fr.univnantes.alma.commons.resource.ResourceImpl;
import fr.univnantes.alma.commons.resource.ResourceJSON;
import fr.univnantes.alma.commons.utils.reflection.ReflectionUtils;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * Interface representing a resource manager
 */
public interface ResourceManager {

    /**
     * Gets the resources information
     *
     * @return the resources information
     */
    @NonNull List<ResourceJSON> getResourcesInformation();

    /**
     * Generates the resource
     *
     * @param resourceJSON the resource information
     * @return the resource
     */
    @NonNull Resource generateResource(@NonNull ResourceJSON resourceJSON);

    /**
     * Generates the resources
     *
     * @param resourcesJSON the resources information
     * @return the resources
     */
    @NonNull List<Resource> generateResources(@NonNull List<ResourceJSON> resourcesJSON);

    /**
     * Checks if there is the resource
     *
     * @param resource the resource
     * @return true if there is the resource, false otherwise
     */
    boolean hasResource(@NonNull Resource resource);

    /**
     * Checks if there is the resources
     *
     * @param resources the resources
     * @return true if there is the resources, false otherwise
     */
    boolean hasResources(@NonNull List<Resource> resources);

    /**
     * Adds the resource
     *
     * @param resource the resource
     */
    void addResource(@NonNull Resource resource);

    /**
     * Adds the resources
     *
     * @param resources the resources
     */
    void addResources(@NonNull List<Resource> resources);

    /**
     * Removes the resource
     *
     * @param resource the resource
     */
    void removeResource(@NonNull Resource resource);

    /**
     * Removes the resources
     *
     * @param resources the resources
     */
    void removeResources(@NonNull List<Resource> resources);

}