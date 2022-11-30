package fr.univnantes.alma.core.board;

import fr.univnantes.alma.commons.road.BuildableRoadArea;
import fr.univnantes.alma.commons.road.Road;
import fr.univnantes.alma.commons.territory.Territory;
import fr.univnantes.alma.commons.town.City;
import fr.univnantes.alma.commons.town.Colony;
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

    void buildRoad(Road road, BuildableRoadArea buildableRoadArea);

    void buildColony(Colony colony, BuildableArea buildableArea);

    void buildCity(City city, BuildableArea buildableArea);

    void moveThief(Territory fromTerritory,Territory toTerritory);

    Resource pickResource(Resource resource, int amount);

    DevelopmentCard pickDevelopmentCard();

    <S extends SpecialCard> S pickSpecialCard(@NonNull Class<S> type);

}