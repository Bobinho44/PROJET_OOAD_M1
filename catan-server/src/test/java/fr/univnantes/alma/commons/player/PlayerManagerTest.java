package fr.univnantes.alma.commons.player;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import fr.univnantes.alma.commons.card.development.knight.KnightCard;
import fr.univnantes.alma.commons.card.development.progress.cards.InventionCard;
import fr.univnantes.alma.commons.construction.type.building.colony.Colony;
import fr.univnantes.alma.commons.construction.type.road.Road;
import fr.univnantes.alma.commons.resource.type.ClayResource;
import fr.univnantes.alma.commons.resource.type.OreResource;
import fr.univnantes.alma.commons.resource.type.WheatResource;
import fr.univnantes.alma.core.card.type.DevelopmentCard;
import fr.univnantes.alma.core.player.IPlayer;
import fr.univnantes.alma.core.player.IPlayerJSON;
import fr.univnantes.alma.core.resource.IResource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests of the player manager
 */
class PlayerManagerTest {

    static {
        ((Logger) LoggerFactory.getLogger("org.reflections")).setLevel(Level.OFF);
    }

    /**
     * Fields
     */
    private final PlayerManager playerManager = new PlayerManager();
    private final IPlayerJSON playerJSON1 = new PlayerJSON(UUID.randomUUID());
    private final IPlayerJSON playerJSON2 = new PlayerJSON(UUID.randomUUID());
    private IPlayer player1;
    private Colony colony;
    private Road road;
    private final IResource resource1 = new WheatResource().amount(3);
    private final IResource resource2 = new OreResource().amount(5);
    private final DevelopmentCard developmentCard1 = new InventionCard();
    private final DevelopmentCard developmentCard2 = new KnightCard();

    /**
     * Setup
     */
    @BeforeEach
    public void setup() {
        playerManager.createPlayer(playerJSON1);
        playerManager.nextPlayer();
        player1 = playerManager.getPlayer(playerJSON1);

        colony = new Colony(player1);
        road = new Road(player1);

        playerManager.addConstruction(player1, road);
        playerManager.addResources(player1, List.of(resource1, resource2));
        playerManager.addDevelopmentCard(player1, developmentCard1);
    }

    @Test
    public void getPlayerTest() {
        assertEquals(playerJSON1.getUUID(), playerManager.getPlayer(playerJSON1).getUUID());
    }

    @Test
    public void hasPlayerTest() {
        assertTrue(playerManager.hasPlayer(playerJSON1));
        assertFalse(playerManager.hasPlayer(playerJSON2));
    }

    @Test
    public void createPlayerTest() {
        playerManager.createPlayer(playerJSON2);

        assertTrue(playerManager.hasPlayer(playerJSON2));
    }

    @Test
    public void deletePlayerTest() {
        playerManager.deletePlayer(player1);

        assertFalse(playerManager.hasPlayer(playerJSON1));
    }

    @Test
    public void canPlayTest() {
        playerManager.createPlayer(playerJSON2);

        assertNotEquals(playerManager.canPlay(player1), playerManager.canPlay(playerManager.getPlayer(playerJSON2)));
    }

    @Test
    public void nextPlayerTest() {
        playerManager.createPlayer(playerJSON2);

        playerManager.nextPlayer();

        assertNotEquals(playerManager.canPlay(player1), playerManager.canPlay(playerManager.getPlayer(playerJSON2)));
    }

    @Test
    public void hasConstructionTest() {
        assertTrue(playerManager.hasConstruction(player1, road));
        assertFalse(playerManager.hasConstruction(player1, colony));
    }

    @Test
    public void addConstructionTest() {
        playerManager.addConstruction(player1, colony);

        assertTrue(playerManager.hasConstruction(player1, road));
        assertTrue(playerManager.hasConstruction(player1, colony));
    }

    @Test
    public void removeConstructionTest() {
        playerManager.removeConstruction(player1, road);

        assertFalse(playerManager.hasConstruction(player1, road));
    }

    @Test
    public void hasResourceTest() {
        assertTrue(playerManager.hasResource(player1, resource1));
        assertFalse(playerManager.hasResource(player1, new ClayResource()));
    }

    @Test
    public void hasResourcesTest() {
        assertTrue(playerManager.hasResources(player1, List.of(resource1, resource2)));
        assertFalse(playerManager.hasResources(player1, List.of(resource1, new ClayResource())));
    }

    @Test
    public void addResourceTest() {
        playerManager.addResource(player1, new ClayResource().amount(5));

        assertTrue(playerManager.hasResource(player1, new ClayResource().amount(2)));
        assertFalse(playerManager.hasResource(player1, new ClayResource().amount(6)));
    }

    @Test
    public void addResourcesTest() {
        playerManager.addResources(player1, List.of(new ClayResource().amount(5), new OreResource().amount(12)));

        assertTrue(playerManager.hasResources(player1, List.of(new ClayResource().amount(5), resource1, new OreResource().amount(12))));
        assertFalse(playerManager.hasResources(player1, List.of(new ClayResource().amount(6), new OreResource().amount(12))));
    }

    @Test
    public void removeResourceTest() {
        playerManager.removeResource(player1, new WheatResource().amount(2));

        assertTrue(playerManager.hasResource(player1, new WheatResource().amount(1)));
        assertFalse(playerManager.hasResource(player1, new WheatResource().amount(2)));
    }

    @Test
    public void removeResourcesTest() {
        playerManager.removeResources(player1, List.of(new WheatResource().amount(1), new OreResource().amount(4)));

        assertTrue(playerManager.hasResources(player1, List.of(new WheatResource().amount(2), resource1, new OreResource().amount(1))));
        assertFalse(playerManager.hasResources(player1, List.of(new WheatResource().amount(2), new OreResource().amount(2))));
    }

    @Test
    public void pickAllResourcesTest() {
        playerManager.createPlayer(playerJSON2);
        IPlayer player2 = playerManager.getPlayer(playerJSON2);
        playerManager.addResource(player2, new WheatResource().amount(11));

        playerManager.pickAllResources(player1, new WheatResource());

        assertTrue(playerManager.hasResource(player1, new WheatResource().amount(14)));
        assertFalse(playerManager.hasResource(player2, new WheatResource().amount(11)));
    }

    @Test
    public void hasAnyDevelopmentCardTest() {
        playerManager.createPlayer(playerJSON2);
        IPlayer player2 = playerManager.getPlayer(playerJSON2);

        assertTrue(playerManager.hasDevelopmentCard(player1));
        assertFalse(playerManager.hasDevelopmentCard(player2));
    }

    @Test
    public void hasDevelopmentCardTest() {
        assertTrue(playerManager.hasDevelopmentCard(player1, developmentCard1));
        assertFalse(playerManager.hasDevelopmentCard(player1, developmentCard2));
    }

    @Test
    public void addDevelopmentCardTest() {
        playerManager.addDevelopmentCard(player1, developmentCard2);

        assertTrue(playerManager.hasDevelopmentCard(player1, developmentCard1));
        assertTrue(playerManager.hasDevelopmentCard(player1, developmentCard2));
    }

    @Test
    public void removeDevelopmentCardTest() {
        playerManager.removeDevelopmentCard(player1, developmentCard1);

        assertFalse(playerManager.hasDevelopmentCard(player1, developmentCard1));
    }

    @Test
    public void addVictoryPointsTest() {
        playerManager.addVictoryPoints(player1, 12);

        assertEquals(12, playerManager.getVictoryPoint(player1));
    }

    @Test
    public void removeVictoryPointsTest() {
        playerManager.addVictoryPoints(player1, 12);

        playerManager.removeVictoryPoints(player1, 7);

        assertEquals(5, playerManager.getVictoryPoint(player1));
    }

}