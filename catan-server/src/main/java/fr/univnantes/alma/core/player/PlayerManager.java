package fr.univnantes.alma.core.player;

import fr.univnantes.alma.core.notification.NotificationJSON;
import fr.univnantes.alma.commons.player.PlayerJSON;
import fr.univnantes.alma.core.card.type.DevelopmentCard;
import fr.univnantes.alma.core.command.CommandManager;
import fr.univnantes.alma.core.construction.Construction;
import fr.univnantes.alma.core.ressource.Resource;
import org.springframework.lang.NonNull;

import java.util.List;

/**
 * Interface representing a player manager
 */
public interface PlayerManager {

    /**
     * Gets the players information
     *
     * @return the players information
     */
    @NonNull List<PlayerJSON> getPlayerInformation();

    /**
     * Gets the player
     *
     * @param playerJSON the player information
     * @return the player
     * @throws RuntimeException if the player does not exist
     */
    @NonNull Player getPlayer(@NonNull PlayerJSON playerJSON) throws RuntimeException;

    /**
     * Checks if the player exist
     *
     * @param playerJSON the player information
     * @return true if the player exist, false otherwise
     */
    boolean hasPlayer(@NonNull PlayerJSON playerJSON);

    /**
     * Creates the player
     *
     * @param playerJSON the player information
     */
    void createPlayer(@NonNull PlayerJSON playerJSON);

    /**
     * Deletes the player
     *
     * @param player the player
     */
    void deletePlayer(@NonNull Player player);

    /**
     * Checks if the player can play
     *
     * @param player the player
     * @return true if the player can play, false otherwise
     */
    boolean canPlay(@NonNull Player player);

    /**
     * Changes the actual player to the next player
     */
    void nextPlayer();

    /**
     * Checks if the player has the construction
     *
     * @param player the player
     * @param construction the construction
     * @return true if the player can construct the construction
     */
    boolean hasConstruction(@NonNull Player player, @NonNull Construction construction);

    /**
     * Adds the construction to the player
     *
     * @param player the player
     * @param construction the construction
     */
    void addConstruction(@NonNull Player player, @NonNull Construction construction);

    /**
     * Removes the construction from the player
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
     * Checks if the player has the resources
     *
     * @param player the player
     * @param resources the resources
     * @return true if the player has the resources, false otherwise
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
     * Adds the resources to the player
     *
     * @param player the player
     * @param resources the resources
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
     * Removes the resources from the player
     *
     * @param player the player
     * @param resources the resources
     */
    void removeResources(@NonNull Player player, @NonNull List<Resource> resources);

    /**
     * Makes player pick all resource of the selected type from other players
     *
     * @param player the player
     * @param resource the resource
     */
    void pickAllResources(@NonNull Player player, @NonNull Resource resource);

    /**
     * Gets a development card from the player
     *
     * @param player the player
     * @return the development card
     * @throws RuntimeException if the player has not available development card
     */
    @NonNull DevelopmentCard getDevelopmentCard(@NonNull Player player) throws RuntimeException;

    /**
     * Checks if the player has the development card
     *
     * @param player the player
     * @param developmentCard the development card
     * @return true if the player has the development card, false otherwise
     */
    boolean hasDevelopmentCard(@NonNull Player player, @NonNull DevelopmentCard developmentCard);

    /**
     * Checks if the player has a development card
     *
     * @param player the player
     * @return true if the player has a development card, false otherwise
     */
    boolean hasDevelopmentCard(@NonNull Player player);

    /**
     * Adds the development card to the player
     *
     * @param player the player
     * @param developmentCard the development card
     */
    void addDevelopmentCard(@NonNull Player player, @NonNull DevelopmentCard developmentCard);

    /**
     * Removes the development card from the player
     *
     * @param player the player
     * @param developmentCard the development card
     */
    void removeDevelopmentCard(@NonNull Player player, @NonNull DevelopmentCard developmentCard);

    /**
     * Uses the development card
     *
     * @param player          the player
     * @param developmentCard the development card
     * @param commandManager  the command manager
     * @return
     */
    @NonNull NotificationJSON useDevelopmentCard(@NonNull Player player, @NonNull DevelopmentCard developmentCard, @NonNull CommandManager commandManager);

    /**
     * Gets the victory points of the player
     *
     * @param player the player
     * @return the victory points of the player
     */
    int getVictoryPoint(@NonNull Player player);

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
}