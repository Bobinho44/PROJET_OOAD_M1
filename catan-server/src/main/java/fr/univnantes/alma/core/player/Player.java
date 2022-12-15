package fr.univnantes.alma.core.player;

import fr.univnantes.alma.core.card.type.DevelopmentCard;
import fr.univnantes.alma.core.construction.Construction;
import fr.univnantes.alma.core.ressource.Resource;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

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
     * Remove and return the resource of a type given
     *
     * @param resource the type of resource
     * @return all the resource of this type
     */
    Resource removeAllResource(@NonNull Resource resource);

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

    /**
     * Gets the victory points
     *
     * @return the victory points
     */
    int getsVictoryPoint();

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

    /**
     * Change the rule of trades, should be used only when colony are build on dock
     *
     * @param resource the type of resource, null if it's all resources
     * @param ratio the ratio of trade
     */
    void changeRuleTradeWithBank(@Nullable Resource resource, int ratio);

}
