package fr.univnantes.alma.core.ressource;

import org.springframework.lang.NonNull;

public interface Resource {

    /**
     * Gets the name
     *
     * @return the name
     */
    @NonNull String getName();

    /**
     * Sets the amount
     *
     * @param amount the amount
     * @return the resource
     */
    @NonNull Resource amount(int amount);

    /**
     * Gets the amount
     *
     * @return the amount
     */
    int getAmount();

    /**
     * Increases the resource amount
     *
     * @param amount the increases amount
     */
    void increaseAmount(int amount);

    /**
     * Decreases the resource amount
     *
     * @param amount the decrease amount
     */
    void decreaseAmount(int amount);

    /**
     * Checks if the resources are similar (same name)
     *
     * @param resource the other resource
     * @return true if the resources are similar, false otherwise
     */
    boolean isSimilar(@NonNull Resource resource);

    /**
     * Create a resource with the same type, and return it
     *
     * @return a new instance of resource with the same type
     */
    Resource newResource();
}