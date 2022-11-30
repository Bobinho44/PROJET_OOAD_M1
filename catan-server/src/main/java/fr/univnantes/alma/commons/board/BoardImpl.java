package fr.univnantes.alma.commons.board;

import fr.univnantes.alma.Player;
import fr.univnantes.alma.commons.road.BuildableRoadArea;
import fr.univnantes.alma.commons.town.City;
import fr.univnantes.alma.commons.town.Colony;
import fr.univnantes.alma.core.card.type.DevelopmentCard;
import fr.univnantes.alma.core.card.type.SpecialCard;
import fr.univnantes.alma.core.board.Board;
import fr.univnantes.alma.core.buildArea.BuildableArea;
import fr.univnantes.alma.core.ressource.Resource;
import fr.univnantes.alma.commons.road.Road;
import fr.univnantes.alma.commons.territory.Territory;

import java.util.HashMap;
import java.util.Map;


public class BoardImpl implements Board {

    private final Integer INITRESOURCESCARD = 19;

    Player currentPlayer;

    Map<Resource,Integer> resourceDeck;

    @Override
    public void build() {

    }

    @Override
    public void createDevelopmentCardDeck() {

    }

    @Override
    public void createSpecialCardDeck() {

    }

    @Override
    public void createResourceDeck() {
        resourceDeck = new HashMap<>();
        /*TODO
        resourceDeck.put(ressourceType,INITRESOURCESCARD);
        */
    }

    @Override
    public void buildRoad(Road road, BuildableRoadArea buildableRoadArea) {
        buildableRoadArea.placeRoad(road,currentPlayer);
    }

    @Override
    public void buildColony(Colony colony, BuildableArea buildableArea) {

    }

    @Override
    public void buildCity(City city, BuildableArea buildableArea) {

    }

    @Override
    public void moveThief(Territory fromTerritory,Territory toTerritory) {
        fromTerritory.setThiefOccupation(false);
        toTerritory.setThiefOccupation(true);
    }

    @Override
    public Resource pickResource(Resource resource, int amount) {
        Integer i = resourceDeck.get(resource);
        if(i >= amount){
            resourceDeck.put(resource,i - amount);
            //TODO return (amount) cartes de resources
            return null;
        }
        resourceDeck.put(resource,0);
        //TODO return (i) cartes de ressources
        return null;
    }

    @Override
    public DevelopmentCard pickDevelopmentCard() {
        return null;
    }

    @Override
    public <S extends SpecialCard> S pickSpecialCard(Class<S> type) {
        return null;
    }
}
