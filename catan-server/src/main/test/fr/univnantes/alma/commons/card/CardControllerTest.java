package fr.univnantes.alma.commons.card;

import fr.univnantes.alma.commons.card.development.DevelopmentCardJSON;
import fr.univnantes.alma.commons.card.development.knight.KnightCard;
import fr.univnantes.alma.commons.card.special.LongestRoadCard;
import fr.univnantes.alma.commons.card.special.SpecialCardJSON;
import fr.univnantes.alma.commons.resource.type.OreResource;
import fr.univnantes.alma.commons.resource.type.WheatResource;
import fr.univnantes.alma.commons.resource.type.WoolResource;
import fr.univnantes.alma.core.card.type.DevelopmentCard;
import fr.univnantes.alma.core.ressource.Resource;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CardControllerTest {

    private final CardController cardController = new CardController();
    private final DevelopmentCardJSON developmentCardJSON1 = cardController.getDevelopmentCardsInformation().get(0);
    private final SpecialCardJSON specialCardJSON = cardController.getSpecialCardsInformation().get(0);

    @Test
    public void generateDevelopmentCard() {
        DevelopmentCard developmentCard = cardController.generateDevelopmentCard(developmentCardJSON1);

        assertEquals(developmentCardJSON1.getUUID(), developmentCard.getUUID());
        assertEquals(developmentCardJSON1.getType(), developmentCard.getClass().getName());
    }

    @Test
    public void hasDevelopmentCard() {
        assertTrue(cardController.hasDevelopmentCard());
    }

    @Test
    public void getDevelopmentCardCost() {
        Resource[] resources = {new WheatResource(), new WoolResource(), new OreResource()};

        assertArrayEquals(resources, cardController.getDevelopmentCardCost().toArray());
    }

    @Test
    public void addDevelopmentCard() {
        cardController.getDevelopmentCardsInformation().stream()
                .map(cardController::generateDevelopmentCard)
                .forEach(cardController::removeDevelopmentCard);

        cardController.addDevelopmentCard(new KnightCard());

        assertTrue(cardController.hasDevelopmentCard());
    }

    @Test
    public void removeDevelopmentCard() {
        cardController.getDevelopmentCardsInformation().stream()
                .map(cardController::generateDevelopmentCard)
                .forEach(cardController::removeDevelopmentCard);
        DevelopmentCard developmentCard = new KnightCard();
        cardController.addDevelopmentCard(developmentCard);

        cardController.removeDevelopmentCard(developmentCard);

        assertFalse(cardController.hasDevelopmentCard());
    }

    @Test
    public void hasSpecialCard() {
        assertTrue(cardController.hasSpecialCard(specialCardJSON));
        assertFalse(cardController.hasSpecialCard(new SpecialCardJSON(UUID.randomUUID(), LongestRoadCard.class.getName())));
    }

}