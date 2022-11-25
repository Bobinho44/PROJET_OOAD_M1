package fr.univnantes.alma.commons.board;

import fr.univnantes.alma.core.card.type.DevelopmentCard;
import fr.univnantes.alma.core.card.type.SpecialCard;
import fr.univnantes.alma.core.board.Board;
import fr.univnantes.alma.core.ressource.Resource;

public class BoardImpl implements Board {

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

    }

    @Override
    public void buildRoad(Road road, BuildableArea buildableArea) {

    }

    @Override
    public void buildColony(Colony colony, BuildableArea buildableArea) {

    }

    @Override
    public void buildCity(City city, BuildableArea buildableArea) {

    }

    @Override
    public void moveThief(Territory territory) {

    }

    @Override
    public Resource pickResource(Resource resource, int amount) {
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
