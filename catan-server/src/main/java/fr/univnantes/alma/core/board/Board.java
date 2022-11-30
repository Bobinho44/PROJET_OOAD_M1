package fr.univnantes.alma.core.board;

import fr.univnantes.alma.core.buildArea.BuildableArea;
import fr.univnantes.alma.core.card.type.DevelopmentCard;
import fr.univnantes.alma.core.card.type.SpecialCard;
import fr.univnantes.alma.core.ressource.Resource;
import org.springframework.lang.NonNull;

public interface Board {

    /**
     * Ressource
     * Développement
     *      Chevalier (
     *      Progrès (3)
     *      PV (5)
     * spéciales
     *      route (1)
     *      chevalier (1)
     */

    /**
     * Builds the board
     */
    void build();

    void createDevelopmentCardDeck();

    void createSpecialCardDeck();

    void createResourceDeck();

    void buildRoad(Road road, BuildableArea buildableArea);

    void buildColony(Colony colony, BuildableArea buildableArea);

    void buildCity(City city, BuildableArea buildableArea);

    void moveThief(Territory territory);

    Resource pickResource(Resource resource, int amount);

    DevelopmentCard pickDevelopmentCard();

    <S extends SpecialCard> S pickSpecialCard(@NonNull Class<S> type);

}