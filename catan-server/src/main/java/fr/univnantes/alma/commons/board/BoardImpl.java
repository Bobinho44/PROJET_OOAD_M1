package fr.univnantes.alma.commons.board;

import fr.univnantes.alma.commons.town.City;
import fr.univnantes.alma.commons.town.Colony;
import fr.univnantes.alma.core.card.type.DevelopmentCard;
import fr.univnantes.alma.core.card.type.SpecialCard;
import fr.univnantes.alma.core.board.Board;
import fr.univnantes.alma.core.buildArea.BuildableArea;
import fr.univnantes.alma.core.ressource.Resource;
import fr.univnantes.alma.commons.road.Road;
import fr.univnantes.alma.commons.territory.Territory;


public class BoardImpl implements Board {

/**TODO après
        /
        private final int RESOURCE_NUMBER_PER_TYPE = 19;


        private final Stack<Resource> resources = new Stack<>();
        private final Stack<SpecialCard> specialCards = new Stack<>();

        @Override
        public void build() {

        }

        @Override
        public void createDevelopmentCardDeck() {

        }

        @Override
        public void createSpecialCardDeck() {
            IntStream.range(0, 1)
                    .forEach(i -> specialCards.addAll(ReflectionUtils.getInstancesOf(SpecialCard.class, "fr/univnantes/alma/commons/card/special")));

            Collections.shuffle(specialCards);
        }

        @Override
        public void createResourceDeck() {
            IntStream.range(0, RESOURCE_NUMBER_PER_TYPE)
                    .forEach(i -> resources.addAll(List.of(ResourceImpl.values())));

            Collections.shuffle(resources);
        }

        @Override
        public List<Resource> pickResource(Resource resource, int amount) {
            return IntStream.range(0, amount)
                    .mapToObj(i -> resources.remove(resources.search(resource)))
                    .toList();
        }

        @Override
        public List<Resource> pickResource(int amount) {
            return IntStream.range(0, amount)
                    .mapToObj(i -> resources.pop())
                    .toList();
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
*/
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
    public void buildRoad(Road road, BuildableArea buildableRoadArea) {

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
