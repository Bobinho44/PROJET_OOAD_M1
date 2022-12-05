package fr.univnantes.alma.core.ressource;

import org.springframework.lang.NonNull;

import java.util.List;

public interface ResourceManager {

    /**
     * Checks if a resource can be picked
     *
     * @return true if a resource can be picked, false otherwise
     */
    boolean canPickResource(Resource resource, int amount);

    /**
     * Removes a resource
     *
     * @param resource the resource
     * @param amount the amount
     */
    void removeResource(Resource resource, int amount);

    /**
     * adds a resource
     *
     * @param resource the resource
     * @param amount the amount
     */
    void addResource(Resource resource, int amount) ;

}