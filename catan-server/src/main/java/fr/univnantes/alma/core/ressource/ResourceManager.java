package fr.univnantes.alma.core.ressource;

import org.springframework.lang.NonNull;

import java.util.List;

public interface ResourceManager {

    /**
     * Checks if the list of resource is available
     *
     * @param resources the resources
     * @return true if the list of resource is available
     */
    public boolean hasResources(@NonNull List<Resource> resources);

    /**
     * Checks if a resource can be picked
     *
     * @return true if a resource can be picked, false otherwise
     */
    boolean canPickResource(@NonNull Resource resource, int amount);

    /**
     * Adds all resources
     *
     * @param resources all resources
     */
    void addResources(@NonNull List<Resource> resources);

    /**
     * adds a resource
     *
     * @param resource the resource
     * @param amount the amount
     */
    void addResource(@NonNull Resource resource, int amount);

    /**
     * Removes all resources
     *
     * @param resources all resources
     */
    void removeResources(@NonNull List<Resource> resources);

    /**
     * Removes a resource
     *
     * @param resource the resource
     * @param amount the amount
     */
    void removeResource(@NonNull Resource resource, int amount);

}