package fr.univnantes.alma.core.construction;

import fr.univnantes.alma.commons.construction.ConstructionJSON;
import fr.univnantes.alma.commons.construction.constructableArea.ConstructableAreaJSON;
import fr.univnantes.alma.core.construction.constructableArea.ConstructableArea;
import fr.univnantes.alma.core.player.Player;
import fr.univnantes.alma.core.ressource.Resource;
import org.springframework.lang.NonNull;

import java.util.List;

/**
 * Interface representing a construction manager
 */
public interface ConstructionManager {

    /**
     * Gets the constructable areas information
     *
     * @return the constructable areas information
     */
    @NonNull List<ConstructableAreaJSON> getConstructableAreasInformation();

    /**
     * Gets the constructable area
     *
     * @param constructableAreaJSON the constructable area information
     * @return the constructable area
     * @throws RuntimeException if the constructable area does not exist
     */
    @NonNull <T extends Construction> ConstructableArea<T> getConstructableArea(@NonNull ConstructableAreaJSON constructableAreaJSON, @NonNull Class<T> type) throws RuntimeException;

    /**
     * Checks if the constructable area exist
     *
     * @param constructableAreaJSON the constructable area information
     * @return true if the constructable area exist, false otherwise
     */
    <T extends Construction> boolean hasConstructableArea(@NonNull ConstructableAreaJSON constructableAreaJSON, @NonNull Class<T> type);

    /**
     * Generates the construction
     *
     * @param constructionJSON the construction information
     * @return the construction
     */
    @NonNull <T extends Construction> T generateConstruction(@NonNull ConstructionJSON constructionJSON, @NonNull Class<T> type, @NonNull Player player);

    <T extends Construction> boolean hasConstruction(@NonNull ConstructionJSON constructionJSON, @NonNull Class<T> type, @NonNull Player player);
    /**
     * Gets the construction cost
     *
     * @param construction the construction
     * @return the construction cost
     */
    @NonNull List<Resource> getConstructionCost(@NonNull Construction construction);

    /**
     * Checks if the construction is constructable
     *
     * @param constructableArea the constructable area
     * @param construction the construction
     * @return true if the construction is constructable, false otherwise
     */
    <T extends Construction> boolean isConstructable(@NonNull ConstructableArea<T> constructableArea, @NonNull T construction);

    /**
     * Constructs the construction on the area
     *
     * @param constructableArea the constructable area
     * @param construction the construction
     */
    <T extends Construction> void construct(@NonNull ConstructableArea<T> constructableArea, @NonNull T construction);

    /**
     * Gets the minimum dock ratio of the player for the selected resource
     *
     * @param player the player
     * @param resource the resource
     * @return the minimum dock ratio of the player for the selected resource
     */
    int getPlayerDockRatio(@NonNull Player player, @NonNull Resource resource);

}