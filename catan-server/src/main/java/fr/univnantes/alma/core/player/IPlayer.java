package fr.univnantes.alma.core.player;

import fr.univnantes.alma.core.card.type.DevelopmentCard;
import fr.univnantes.alma.core.construction.IConstruction;
import fr.univnantes.alma.core.resource.IResource;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.UUID;

/**
 * Interface representing a player
 */
public interface IPlayer {

    /**
     * Gets uuid
     *
     * @return the uuid
     */
    @NonNull UUID getUUID();

    /**
     * Gets all constructions of the player
     *
     * @return all constructions of the player
     */
    @NonNull List<IConstruction> getConstructions();

    /**
     * Gets all resources of the player
     *
     * @return all resources of the player
     */
    @NonNull List<IResource> getResources();

    /**
     * Gets all development card of the player
     *
     * @return all development card of the player
     */
    @NonNull List<DevelopmentCard> getDevelopmentCard();

    /**
     * Gets the victory points
     *
     * @return the victory points
     */
    int getVictoryPoint();

    /**
     * Adds victory points to the player
     *
     * @param amount the amount
     */
    void addVictoryPoints(int amount);

    /**
     * Removes victory point from the player
     *
     * @param amount the amount
     */
    void removeVictoryPoints(int amount);

}
