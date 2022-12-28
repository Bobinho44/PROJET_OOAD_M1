package fr.univnantes.alma.core.player;

import fr.univnantes.alma.core.construction.ConstructionJSON;
import fr.univnantes.alma.core.card.CardJSON;
import fr.univnantes.alma.core.resource.ResourceJSON;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.UUID;

/**
 * Interface representing a player JSON information
 */
public interface PlayerJSON {

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
    @NonNull List<ConstructionJSON> getConstructions();

    /**
     * Sets the constructions
     *
     * @param constructions the constructions
     * @return the player information
     */
    @NonNull PlayerJSON constructions(@NonNull List<ConstructionJSON> constructions);

    /**
     * Gets the resources
     *
     * @return the resources
     */
    @NonNull List<ResourceJSON> getResources();

    /**
     * Sets the resources
     *
     * @param resources the resources
     * @return the player information
     */
    @NonNull PlayerJSON resources(@NonNull List<ResourceJSON> resources);

    /**
     * Gets the development cards
     *
     * @return the development cards
     */
    @NonNull List<CardJSON> getDevelopmentCards();

    /**
     * Sets the development cards
     *
     * @param developmentCards the development cards
     * @return the player information
     */
    @NonNull PlayerJSON developmentCards(@NonNull List<CardJSON> developmentCards);

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
    @NonNull PlayerJSON victoryPoints(int victoryPoints);

}