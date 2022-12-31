package fr.univnantes.alma.core.player;

import fr.univnantes.alma.core.construction.IConstructionJSON;
import fr.univnantes.alma.core.card.ICardJSON;
import fr.univnantes.alma.core.resource.IResourceJSON;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.UUID;

/**
 * Interface representing a player JSON information
 */
public interface IPlayerJSON {

    /**
     * Gets the uuid
     *
     * @return the uuid
     */
    @NonNull UUID getUUID();

    /**
     * Gets the constructions
     *
     * @return the constructions
     */
    @NonNull List<IConstructionJSON> getConstructions();

    /**
     * Sets the constructions
     *
     * @param constructions the constructions
     * @return the player information
     */
    @NonNull
    IPlayerJSON constructions(@NonNull List<IConstructionJSON> constructions);

    /**
     * Gets the resources
     *
     * @return the resources
     */
    @NonNull List<IResourceJSON> getResources();

    /**
     * Sets the resources
     *
     * @param resources the resources
     * @return the player information
     */
    @NonNull
    IPlayerJSON resources(@NonNull List<IResourceJSON> resources);

    /**
     * Gets the development cards
     *
     * @return the development cards
     */
    @NonNull List<ICardJSON> getDevelopmentCards();

    /**
     * Sets the development cards
     *
     * @param developmentCards the development cards
     * @return the player information
     */
    @NonNull
    IPlayerJSON developmentCards(@NonNull List<ICardJSON> developmentCards);

    /**
     * Gets the victory points
     *
     * @return the victory points
     */
    int getVictoryPoints();

    /**
     * Sets the victory points
     *
     * @param victoryPoints the victory points
     * @return the player information
     */
    @NonNull
    IPlayerJSON victoryPoints(int victoryPoints);

}