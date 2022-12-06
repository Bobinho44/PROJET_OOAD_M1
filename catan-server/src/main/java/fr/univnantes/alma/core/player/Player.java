package fr.univnantes.alma.core.player;

import fr.univnantes.alma.core.card.type.DevelopmentCard;
import fr.univnantes.alma.core.construction.Construction;
import fr.univnantes.alma.core.ressource.Resource;
import org.springframework.lang.NonNull;

import java.util.List;

public interface Player {

    /**
     * Gets all constructions of the player
     *
     * @return all constructions of the player
     */
    @NonNull List<Construction> getConstructions();

    /**
     * Adds a construction to the player
     *
     * @param construction the construction
     */
    void addConstruction(@NonNull Construction construction);

    /**
     * Removes a construction from the player
     *
     * @param construction the construction
     */
    void removeConstruction(@NonNull Construction construction);

    /**
     * Gets all resources of the player
     *
     * @return all resources of the player
     */
    @NonNull List<Resource> getResources();

    /**
     * Adds a resource to the player
     *
     * @param resource the resource
     */
    void addResource(@NonNull Resource resource);

    /**
     * Removes a resource from the player
     *
     * @param resource the resource
     */
    void removeResource(@NonNull Resource resource);

    /**
     * Gets all development card of the player
     *
     * @return all development card of the player
     */
    @NonNull List<DevelopmentCard> getDevelopmentCard();

    /**
     * Adds a development card to the player
     *
     * @param developmentCard the development card
     */
    void addDevelopmentCard(@NonNull DevelopmentCard developmentCard);

    /**
     * Removes a development card from the player
     *
     * @param developmentCard the development card
     */
    void removeDevelopmentCard(@NonNull DevelopmentCard developmentCard);

}
