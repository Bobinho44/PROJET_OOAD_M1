package fr.univnantes.alma.commons.card;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import fr.univnantes.alma.commons.card.development.knight.KnightCard;
import fr.univnantes.alma.commons.card.special.LongestRoadCard;
import fr.univnantes.alma.commons.card.special.MostPowerfulArmyCard;
import fr.univnantes.alma.commons.player.PlayerImpl;
import fr.univnantes.alma.commons.resource.type.OreResource;
import fr.univnantes.alma.commons.resource.type.WheatResource;
import fr.univnantes.alma.commons.resource.type.WoolResource;
import fr.univnantes.alma.core.card.CardJSON;
import fr.univnantes.alma.core.card.type.DevelopmentCard;
import fr.univnantes.alma.core.card.type.SpecialCard;
import fr.univnantes.alma.core.player.Player;
import fr.univnantes.alma.core.resource.Resource;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests of the card controller
 */
class CardControllerTest {

    static {
        ((Logger) LoggerFactory.getLogger("org.reflections")).setLevel(Level.OFF);
    }

    /**
     * Fields
     */
    private final CardController cardController = new CardController();
    private final CardJSON developmentCardJSON1 = cardController.getDevelopmentCardsInformation().get(0);
    private final CardJSON specialCardJSON = cardController.getSpecialCardsInformation().get(0);
    private final SpecialCard specialCard = cardController.getSpecialCard(specialCardJSON);
    private final Player player = new PlayerImpl(UUID.randomUUID());

    @Test
    public void generateDevelopmentCardTest() {
        DevelopmentCard developmentCard = cardController.generateDevelopmentCard(developmentCardJSON1);

        assertEquals(developmentCardJSON1.getUUID(), developmentCard.getUUID());
        assertEquals(developmentCardJSON1.getType(), developmentCard.getClass().getName());
    }

    @Test
    public void hasDevelopmentCardTest() {
        assertTrue(cardController.hasDevelopmentCard());
    }

    @Test
    public void getDevelopmentCardCostTest() {
        Resource[] resources = {new WheatResource(), new WoolResource(), new OreResource()};

        assertArrayEquals(resources, cardController.getDevelopmentCardCost().toArray());
    }

    @Test
    public void addDevelopmentCardTest() {
        cardController.getDevelopmentCardsInformation().stream()
                .map(cardController::generateDevelopmentCard)
                .forEach(cardController::removeDevelopmentCard);

        cardController.addDevelopmentCard(new KnightCard());

        assertTrue(cardController.hasDevelopmentCard());
    }

    @Test
    public void removeDevelopmentCardTest() {
        cardController.getDevelopmentCardsInformation().stream()
                .map(cardController::generateDevelopmentCard)
                .forEach(cardController::removeDevelopmentCard);
        DevelopmentCard developmentCard = new KnightCard();
        cardController.addDevelopmentCard(developmentCard);

        cardController.removeDevelopmentCard(developmentCard);

        assertFalse(cardController.hasDevelopmentCard());
    }

    @Test
    public void hasSpecialCardTest() {
        assertTrue(cardController.hasSpecialCard(specialCardJSON));
        assertFalse(cardController.hasSpecialCard(new CardJSONImpl(UUID.randomUUID(), LongestRoadCard.class.getName())));
        assertFalse(cardController.hasSpecialCard(new CardJSONImpl(UUID.randomUUID(), MostPowerfulArmyCard.class.getName())));
    }

    @Test
    public void useSpecialCardTest() {
        cardController.useSpecialCard(specialCard, player);

        assertTrue(cardController.hasSpecialCardOwner(specialCard));
        assertEquals(player, cardController.getSpecialCardOwner(specialCard));
    }
}