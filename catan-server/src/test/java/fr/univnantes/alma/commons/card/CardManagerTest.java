package fr.univnantes.alma.commons.card;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import fr.univnantes.alma.commons.card.development.knight.KnightCard;
import fr.univnantes.alma.commons.card.special.LongestRoadCard;
import fr.univnantes.alma.commons.card.special.MostPowerfulArmyCard;
import fr.univnantes.alma.commons.player.Player;
import fr.univnantes.alma.commons.resource.type.OreResource;
import fr.univnantes.alma.commons.resource.type.WheatResource;
import fr.univnantes.alma.commons.resource.type.WoolResource;
import fr.univnantes.alma.core.card.ICardJSON;
import fr.univnantes.alma.core.card.type.DevelopmentCard;
import fr.univnantes.alma.core.card.type.SpecialCard;
import fr.univnantes.alma.core.player.IPlayer;
import fr.univnantes.alma.core.resource.IResource;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests of the card manager
 */
class CardManagerTest {

    static {
        ((Logger) LoggerFactory.getLogger("org.reflections")).setLevel(Level.OFF);
    }

    /**
     * Fields
     */
    private final CardManager cardManager = new CardManager();
    private final ICardJSON developmentCardJSON1 = cardManager.getDevelopmentCardsInformation().get(0);
    private final ICardJSON specialCardJSON = cardManager.getSpecialCardsInformation().get(0);
    private final SpecialCard specialCard = cardManager.getSpecialCard(specialCardJSON);
    private final IPlayer player = new Player(UUID.randomUUID());

    @Test
    public void generateDevelopmentCardTest() {
        DevelopmentCard developmentCard = cardManager.generateDevelopmentCard(developmentCardJSON1);

        assertEquals(developmentCardJSON1.getUUID(), developmentCard.getUUID());
        assertEquals(developmentCardJSON1.getType(), developmentCard.getClass().getName());
    }

    @Test
    public void hasDevelopmentCardTest() {
        assertTrue(cardManager.hasDevelopmentCard());
    }

    @Test
    public void getDevelopmentCardCostTest() {
        IResource[] resources = {new WheatResource(), new WoolResource(), new OreResource()};

        assertArrayEquals(resources, cardManager.getDevelopmentCardCost().toArray());
    }

    @Test
    public void addDevelopmentCardTest() {
        cardManager.getDevelopmentCardsInformation().stream()
                .map(cardManager::generateDevelopmentCard)
                .forEach(cardManager::removeDevelopmentCard);

        cardManager.addDevelopmentCard(new KnightCard());

        assertTrue(cardManager.hasDevelopmentCard());
    }

    @Test
    public void removeDevelopmentCardTest() {
        cardManager.getDevelopmentCardsInformation().stream()
                .map(cardManager::generateDevelopmentCard)
                .forEach(cardManager::removeDevelopmentCard);
        DevelopmentCard developmentCard = new KnightCard();
        cardManager.addDevelopmentCard(developmentCard);

        cardManager.removeDevelopmentCard(developmentCard);

        assertFalse(cardManager.hasDevelopmentCard());
    }

    @Test
    public void hasSpecialCardTest() {
        assertTrue(cardManager.hasSpecialCard(specialCardJSON));
        assertFalse(cardManager.hasSpecialCard(new CardJSON(UUID.randomUUID(), LongestRoadCard.class.getName())));
        assertFalse(cardManager.hasSpecialCard(new CardJSON(UUID.randomUUID(), MostPowerfulArmyCard.class.getName())));
    }

    @Test
    public void useSpecialCardTest() {
        cardManager.useSpecialCard(specialCard, player);

        assertTrue(cardManager.hasSpecialCardOwner(specialCard));
        assertEquals(player, cardManager.getSpecialCardOwner(specialCard));
    }
}