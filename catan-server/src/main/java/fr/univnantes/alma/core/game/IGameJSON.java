package fr.univnantes.alma.core.game;

import fr.univnantes.alma.core.card.ICardJSON;
import fr.univnantes.alma.core.construction.constructableArea.IAreaJSON;
import fr.univnantes.alma.core.player.IPlayerJSON;
import fr.univnantes.alma.core.resource.IResourceJSON;
import fr.univnantes.alma.core.territory.ITerritoryJSON;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.UUID;

/**
 * Interface representing a game JSON information
 */
public interface IGameJSON {

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
    @NonNull List<IPlayerJSON> getPlayers();

    /**
     * Sets the players information
     *
     * @param players the players
     * @return the game information
     */
    @NonNull
    IGameJSON players(@NonNull List<IPlayerJSON> players);

    /**
     * Gets the resources information
     *
     * @return the resources information
     */
    @NonNull List<IResourceJSON> getResources();

    /**
     * Sets the resources information
     *
     * @param resources the resources
     * @return the game information
     */
    @NonNull
    IGameJSON resources(@NonNull List<IResourceJSON> resources);

    /**
     * Gets the development cards information
     *
     * @return the development cards information
     */
    @NonNull List<ICardJSON> getDevelopmentCards();

    /**
     * Sets the development cards information
     *
     * @param developmentCards the development cards
     * @return the game information
     */
    @NonNull
    IGameJSON developmentCards(@NonNull List<ICardJSON> developmentCards);

    /**
     * Gets the special cards information
     *
     * @return the special cards information
     */
    @NonNull List<ICardJSON> getSpecialCards();

    /**
     * Sets the special cards information
     *
     * @param specialCards the special cards
     * @return the game information
     */
    @NonNull
    IGameJSON specialCards(@NonNull List<ICardJSON> specialCards);

    /**
     * Gets the territories information
     *
     * @return the territories information
     */
    @NonNull List<ITerritoryJSON> getTerritories();

    /**
     * Sets the territories information
     *
     * @param territories the territories
     * @return the game information
     */
    @NonNull
    IGameJSON territories(@NonNull List<ITerritoryJSON> territories);

    /**
     * Gets the areas information
     *
     * @return the areas information
     */
    @NonNull List<IAreaJSON> getAreas();

    /**
     * Sets the areas information
     *
     * @param areas the areas
     * @return the game information
     */
    @NonNull
    IGameJSON areas(@NonNull List<IAreaJSON> areas);
}