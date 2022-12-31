package fr.univnantes.alma.core.player;

import fr.univnantes.alma.core.command.ICommandJSON;
import fr.univnantes.alma.core.exception.EmptyCardDeckException;
import fr.univnantes.alma.core.exception.UnregisteredPlayerException;
import fr.univnantes.alma.core.card.type.DevelopmentCard;
import fr.univnantes.alma.core.construction.IConstruction;
import fr.univnantes.alma.core.resource.IResource;
import org.springframework.lang.NonNull;

import java.util.List;

/**
 * Interface representing a player manager
 */
public interface IPlayerManager {

    /**
     * Gets the players information
     *
     * @return the players information
     */
    @NonNull List<IPlayerJSON> getPlayerInformation();

    /**
     * Gets the player
     *
     * @param playerJSON the player information
     * @return the player
     * @throws UnregisteredPlayerException if the player does not exist
     */
    @NonNull
    IPlayer getPlayer(@NonNull IPlayerJSON playerJSON) throws UnregisteredPlayerException;

    /**
     * Checks if the player exist
     *
     * @param playerJSON the player information
     * @return true if the player exist, false otherwise
     */
    boolean hasPlayer(@NonNull IPlayerJSON playerJSON);

    /**
     * Creates the player
     *
     * @param playerJSON the player information
     */
    void createPlayer(@NonNull IPlayerJSON playerJSON);

    /**
     * Deletes the player
     *
     * @param player the player
     */
    void deletePlayer(@NonNull IPlayer player);

    /**
     * Checks if the player can play
     *
     * @param player the player
     * @return true if the player can play, false otherwise
     */
    boolean canPlay(@NonNull IPlayer player);

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
    boolean hasConstruction(@NonNull IPlayer player, @NonNull IConstruction construction);

    /**
     * Adds the construction to the player
     *
     * @param player the player
     * @param construction the construction
     */
    void addConstruction(@NonNull IPlayer player, @NonNull IConstruction construction);

    /**
     * Removes the construction from the player
     *
     * @param player the player
     * @param construction the construction
     */
    void removeConstruction(@NonNull IPlayer player, @NonNull IConstruction construction);

    /**
     * Checks if the player has the resource
     *
     * @param player the player
     * @param resource the resource
     * @return true if the player has the resource, false otherwise
     */
    boolean hasResource(@NonNull IPlayer player, @NonNull IResource resource);

    /**
     * Checks if the player has the resources
     *
     * @param player the player
     * @param resources the resources
     * @return true if the player has the resources, false otherwise
     */
    boolean hasResources(@NonNull IPlayer player, @NonNull List<IResource> resources);

    /**
     * Adds the resource to the player
     *
     * @param player the player
     * @param resource the resource
     */
    void addResource(@NonNull IPlayer player, @NonNull IResource resource);

    /**
     * Adds the resources to the player
     *
     * @param player the player
     * @param resources the resources
     */
    void addResources(@NonNull IPlayer player, @NonNull List<IResource> resources);

    /**
     * Removes the resource from the player
     *
     * @param player the player
     * @param resource the resource
     */
    void removeResource(@NonNull IPlayer player, @NonNull IResource resource);

    /**
     * Removes the resources from the player
     *
     * @param player the player
     * @param resources the resources
     */
    void removeResources(@NonNull IPlayer player, @NonNull List<IResource> resources);

    /**
     * Makes player pick all resource of the selected type from other players
     *
     * @param player the player
     * @param resource the resource
     */
    void pickAllResources(@NonNull IPlayer player, @NonNull IResource resource);

    /**
     * Gets a development card from the player
     *
     * @param player the player
     * @return the development card
     * @throws EmptyCardDeckException if the player has not available development card
     */
    @NonNull DevelopmentCard getDevelopmentCard(@NonNull IPlayer player) throws EmptyCardDeckException;

    /**
     * Checks if the player has the development card
     *
     * @param player the player
     * @param developmentCard the development card
     * @return true if the player has the development card, false otherwise
     */
    boolean hasDevelopmentCard(@NonNull IPlayer player, @NonNull DevelopmentCard developmentCard);

    /**
     * Checks if the player has a development card
     *
     * @param player the player
     * @return true if the player has a development card, false otherwise
     */
    boolean hasDevelopmentCard(@NonNull IPlayer player);

    /**
     * Adds the development card to the player
     *
     * @param player the player
     * @param developmentCard the development card
     */
    void addDevelopmentCard(@NonNull IPlayer player, @NonNull DevelopmentCard developmentCard);

    /**
     * Removes the development card from the player
     *
     * @param player the player
     * @param developmentCard the development card
     */
    void removeDevelopmentCard(@NonNull IPlayer player, @NonNull DevelopmentCard developmentCard);

    /**
     * Uses the development card
     *
     * @param player          the player
     * @param developmentCard the development card
     * @return the result command information
     */
    @NonNull
    ICommandJSON useDevelopmentCard(@NonNull IPlayer player, @NonNull DevelopmentCard developmentCard);

    /**
     * Gets the victory points of the player
     *
     * @param player the player
     * @return the victory points of the player
     */
    int getVictoryPoint(@NonNull IPlayer player);

    /**
     * Adds victory points to the player
     *
     * @param player the player
     * @param amount the amount
     */
    void addVictoryPoints(@NonNull IPlayer player, int amount);

    /**
     * Removes victory points from the player
     *
     * @param player the player
     * @param amount the amount
     */
    void removeVictoryPoints(@NonNull IPlayer player, int amount);

}