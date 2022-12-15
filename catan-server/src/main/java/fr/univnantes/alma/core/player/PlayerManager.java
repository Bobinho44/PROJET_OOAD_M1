package fr.univnantes.alma.core.player;

import fr.univnantes.alma.core.card.type.DevelopmentCard;
import fr.univnantes.alma.core.construction.Construction;
import fr.univnantes.alma.core.ressource.Resource;
import org.springframework.lang.NonNull;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PlayerManager {

    /**
     * Gets a player
     *
     * @param uuid the player uuid
     * @return the optional player
     */
    @NonNull Optional<Player> getPlayer(@NonNull UUID uuid);

    /**
     * Adds a player
     *
     * @param player the player
     */
    void addPlayer(@NonNull Player player);

    /**
     * Removes a player
     *
     * @param player the player
     */
    void removePlayer(@NonNull Player player);

    /**
     * Checks if the player has the construction
     *
     * @param player the player
     * @param construction the construction
     * @return true if the player has the construction, false otherwise
     */
    boolean hasConstruction(@NonNull Player player, @NonNull Construction construction);

    /**
     * Checks if the player can construct the construction
     *
     * @param player the player
     * @param construction the construction
     * @param resources the needed resources
     * @return true if the player can construct the construction, false otherwise
     */
    boolean canConstruct(@NonNull Player player, @NonNull Construction construction, @NonNull List<Resource> resources);

    /**
     * Adds a construction to the player
     *
     * @param player the player
     * @param construction the construction
     */
    void addConstruction(@NonNull Player player, @NonNull Construction construction);

    /**
     * Removes a construction from the player
     *
     * @param player the player
     * @param construction the construction
     */
    void removeConstruction(@NonNull Player player, @NonNull Construction construction);

    /**
     * Checks if the player has the resource
     *
     * @param player the player
     * @param resource the resource
     * @return true if the player has the resource, false otherwise
     */
    boolean hasResource(@NonNull Player player, @NonNull Resource resource);

    /**
     * Checks if the player has all resources
     *
     * @param player the player
     * @param resources all resources
     * @return true if the player has all resources, false otherwise
     */
    boolean hasResources(@NonNull Player player, @NonNull List<Resource> resources);

    /**
     * Adds the resource to the player
     *
     * @param player the player
     * @param resource the resource
     */
    void addResource(@NonNull Player player, @NonNull Resource resource);

    /**
     * Adds the list of resources from the player
     *
     * @param player the player
     * @param resources the list of resources
     */
    void addResources(@NonNull Player player, @NonNull List<Resource> resources);

    /**
     * Removes the resource from the player
     *
     * @param player the player
     * @param resource the resource
     */
    void removeResource(@NonNull Player player, @NonNull Resource resource);

    /**
     * Removes the list of resource from the player
     *
     * @param player the player
     * @param resources the list of resource
     */
    void removeResources(@NonNull Player player, @NonNull List<Resource> resources);

    /**
     * Checks if the player has the development card
     *
     * @param player the player
     * @param developmentCard the development card
     * @return true if the player has the development card, false otherwise
     */


    boolean hasDevelopmentCard(@NonNull Player player, @NonNull DevelopmentCard developmentCard);

    /**
     * Checks if the player can buy a development card
     *
     * @param player the player
     * @param resources the needed resources
     * @return true if the player can buy a development card, false otherwise
     */
    boolean canBuyDevelopmentCard(@NonNull Player player, @NonNull List<Resource> resources);

    /**
     * Adds a development card to the player
     *
     * @param player the player
     * @param developmentCard the development card
     */
    void addDevelopmentCard(@NonNull Player player, @NonNull DevelopmentCard developmentCard);

    /**
     * Removes the development card
     *
     * @param player the player
     * @param developmentCard the development card
     */
    void removeDevelopmentCard(@NonNull Player player, @NonNull DevelopmentCard developmentCard);

    /**
     * Gets the victory points
     *
     * @param player the player
     * @return the victory points
     */
    int getsVictoryPoint(@NonNull Player player);

    /**
     * Adds victory points to the player
     * 
     * @param player the player
     * @param amount the amount
     */
    void addVictoryPoints(@NonNull Player player, int amount);

    /**
     * Removes victory points from the player
     *
     * @param player the player
     * @param amount the amount
     */
    void removeVictoryPoints(@NonNull Player player, int amount);

    /**
     * Get all the players
     *
     * @return all the players
     */
    Collection<Player> getAllPlayers();

}