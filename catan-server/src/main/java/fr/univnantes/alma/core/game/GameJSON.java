package fr.univnantes.alma.core.game;

import fr.univnantes.alma.core.card.CardJSON;
import fr.univnantes.alma.core.construction.constructableArea.AreaJSON;
import fr.univnantes.alma.core.player.PlayerJSON;
import fr.univnantes.alma.core.resource.ResourceJSON;
import fr.univnantes.alma.core.territory.TerritoryJSON;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.UUID;

/**
 * Interface representing a game JSON information
 */
public interface GameJSON {

    /**
     * Gets the uuid
     *
     * @return the uuid
     */
    @NonNull UUID getUUID();

    /**
     * Gets the players information
     *
     * @return the players information
     */
    @NonNull List<PlayerJSON> getPlayers();

    /**
     * Sets the players information
     *
     * @param players the players
     * @return the game information
     */
    @NonNull GameJSON players(@NonNull List<PlayerJSON> players);

    /**
     * Gets the resources information
     *
     * @return the resources information
     */
    @NonNull List<ResourceJSON> getResources();

    /**
     * Sets the resources information
     *
     * @param resources the resources
     * @return the game information
     */
    @NonNull GameJSON resources(@NonNull List<ResourceJSON> resources);

    /**
     * Gets the development cards information
     *
     * @return the development cards information
     */
    @NonNull List<CardJSON> getDevelopmentCards();

    /**
     * Sets the development cards information
     *
     * @param developmentCards the development cards
     * @return the game information
     */
    @NonNull GameJSON developmentCards(@NonNull List<CardJSON> developmentCards);

    /**
     * Gets the special cards information
     *
     * @return the special cards information
     */
    @NonNull List<CardJSON> getSpecialCards();

    /**
     * Sets the special cards information
     *
     * @param specialCards the special cards
     * @return the game information
     */
    @NonNull GameJSON specialCards(@NonNull List<CardJSON> specialCards);

    /**
     * Gets the territories information
     *
     * @return the territories information
     */
    @NonNull List<TerritoryJSON> getTerritories();

    /**
     * Sets the territories information
     *
     * @param territories the territories
     * @return the game information
     */
    @NonNull GameJSON territories(@NonNull List<TerritoryJSON> territories);

    /**
     * Gets the areas information
     *
     * @return the areas information
     */
    @NonNull List<AreaJSON> getAreas();

    /**
     * Sets the areas information
     *
     * @param areas the areas
     * @return the game information
     */
    @NonNull GameJSON areas(@NonNull List<AreaJSON> areas);
}