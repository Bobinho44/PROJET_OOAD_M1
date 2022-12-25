package fr.univnantes.alma.commons.player;

import fr.univnantes.alma.commons.card.development.knight.KnightCard;
import fr.univnantes.alma.commons.card.development.progress.cards.InventionCard;
import fr.univnantes.alma.commons.construction.type.building.colony.Colony;
import fr.univnantes.alma.commons.construction.type.road.RoadImpl;
import fr.univnantes.alma.commons.resource.type.ClayResource;
import fr.univnantes.alma.commons.resource.type.OreResource;
import fr.univnantes.alma.commons.resource.type.WheatResource;
import fr.univnantes.alma.core.card.type.DevelopmentCard;
import fr.univnantes.alma.core.player.Player;
import fr.univnantes.alma.core.ressource.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PlayerControllerTest {

    private final PlayerController playerController = new PlayerController();
    private final PlayerJSON playerJSON1 = new PlayerJSON(UUID.randomUUID());
    private final PlayerJSON playerJSON2 = new PlayerJSON(UUID.randomUUID());
    private Player player1;
    private final Colony colony = new Colony(player1);
    private final RoadImpl road = new RoadImpl(player1);
    private final Resource resource1 = new WheatResource().amount(3);
    private final Resource resource2 = new OreResource().amount(5);
    private final DevelopmentCard developmentCard1 = new InventionCard();
    private final DevelopmentCard developmentCard2 = new KnightCard();

    @BeforeEach
    public void setup() {
        playerController.createPlayer(playerJSON1);
        playerController.nextPlayer();
        player1 = playerController.getPlayer(playerJSON1);
        playerController.addConstruction(player1, road);
        playerController.addResources(player1, List.of(resource1, resource2));
        playerController.addDevelopmentCard(player1, developmentCard1);
    }

    @Test
    public void getPlayerTest() {
        assertEquals(playerJSON1.getUuid(), playerController.getPlayer(playerJSON1).getUUID());
    }

    @Test
    public void hasPlayerTest() {
        assertTrue(playerController.hasPlayer(playerJSON1));
        assertFalse(playerController.hasPlayer(playerJSON2));
    }

    @Test
    public void createPlayerTest() {
        playerController.createPlayer(playerJSON2);

        assertTrue(playerController.hasPlayer(playerJSON2));
    }

    @Test
    public void deletePlayerTest() {
        playerController.deletePlayer(player1);

        assertFalse(playerController.hasPlayer(playerJSON1));
    }

    @Test
    public void canPlayTest() {
        playerController.createPlayer(playerJSON2);

        assertNotEquals(playerController.canPlay(player1), playerController.canPlay(playerController.getPlayer(playerJSON2)));
    }

    @Test
    public void nextPlayerTest() {
        playerController.createPlayer(playerJSON2);

        playerController.nextPlayer();

        assertNotEquals(playerController.canPlay(player1), playerController.canPlay(playerController.getPlayer(playerJSON2)));
    }

    @Test
    public void hasConstructionTest() {
        assertTrue(playerController.hasConstruction(player1, road));
        assertFalse(playerController.hasConstruction(player1, colony));
    }

    @Test
    public void addConstructionTest() {
        playerController.addConstruction(player1, colony);

        assertTrue(playerController.hasConstruction(player1, road));
        assertTrue(playerController.hasConstruction(player1, colony));
    }

    @Test
    public void removeConstructionTest() {
        playerController.removeConstruction(player1, road);

        assertFalse(playerController.hasConstruction(player1, road));
    }

    @Test
    public void hasResourceTest() {
        assertTrue(playerController.hasResource(player1, resource1));
        assertFalse(playerController.hasResource(player1, new ClayResource()));
    }

    @Test
    public void hasResourcesTest() {
        assertTrue(playerController.hasResources(player1, List.of(resource1, resource2)));
        assertFalse(playerController.hasResources(player1, List.of(resource1, new ClayResource())));
    }

    @Test
    public void addResourceTest() {
        playerController.addResource(player1, new ClayResource().amount(5));

        assertTrue(playerController.hasResource(player1, new ClayResource().amount(2)));
        assertFalse(playerController.hasResource(player1, new ClayResource().amount(6)));
    }

    @Test
    public void addResourcesTest() {
        playerController.addResources(player1, List.of(new ClayResource().amount(5), new OreResource().amount(12)));

        assertTrue(playerController.hasResources(player1, List.of(new ClayResource().amount(5), resource1, new OreResource().amount(12))));
        assertFalse(playerController.hasResources(player1, List.of(new ClayResource().amount(6), new OreResource().amount(12))));
    }

    @Test
    public void removeResourceTest() {
        playerController.removeResource(player1, new WheatResource().amount(2));

        assertTrue(playerController.hasResource(player1, new WheatResource().amount(1)));
        assertFalse(playerController.hasResource(player1, new WheatResource().amount(2)));
    }

    @Test
    public void removeResourcesTest() {
        playerController.removeResources(player1, List.of(new WheatResource().amount(1), new OreResource().amount(4)));

        assertTrue(playerController.hasResources(player1, List.of(new WheatResource().amount(2), resource1, new OreResource().amount(1))));
        assertFalse(playerController.hasResources(player1, List.of(new WheatResource().amount(2), new OreResource().amount(2))));
    }

    @Test
    public void pickAllResourcesTest() {
        playerController.createPlayer(playerJSON2);
        Player player2 = playerController.getPlayer(playerJSON2);
        playerController.addResource(player2, new WheatResource().amount(11));

        playerController.pickAllResources(player1, new WheatResource());

        assertTrue(playerController.hasResource(player1, new WheatResource().amount(14)));
        assertFalse(playerController.hasResource(player2, new WheatResource().amount(11)));
    }

    @Test
    public void hasAnyDevelopmentCardTest() {
        playerController.createPlayer(playerJSON2);
        Player player2 = playerController.getPlayer(playerJSON2);

        assertTrue(playerController.hasDevelopmentCard(player1));
        assertFalse(playerController.hasDevelopmentCard(player2));
    }

    @Test
    public void hasDevelopmentCardTest() {
        assertTrue(playerController.hasDevelopmentCard(player1, developmentCard1));
        assertFalse(playerController.hasDevelopmentCard(player1, developmentCard2));
    }

    @Test
    public void addDevelopmentCardTest() {
        playerController.addDevelopmentCard(player1, developmentCard2);

        assertTrue(playerController.hasDevelopmentCard(player1, developmentCard1));
        assertTrue(playerController.hasDevelopmentCard(player1, developmentCard2));
    }

    @Test
    public void removeDevelopmentCardTest() {
        playerController.removeDevelopmentCard(player1, developmentCard1);

        assertFalse(playerController.hasDevelopmentCard(player1, developmentCard1));
    }

    @Test
    public void addVictoryPointsTest() {
        playerController.addVictoryPoints(player1, 12);

        assertEquals(12, playerController.getVictoryPoint(player1));
    }

    @Test
    public void removeVictoryPointsTest() {
        playerController.addVictoryPoints(player1, 12);

        playerController.removeVictoryPoints(player1, 7);

        assertEquals(5, playerController.getVictoryPoint(player1));
    }

}