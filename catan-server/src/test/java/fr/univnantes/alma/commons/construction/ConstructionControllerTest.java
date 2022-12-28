package fr.univnantes.alma.commons.construction;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import fr.univnantes.alma.commons.construction.constructableArea.AreaImpl;
import fr.univnantes.alma.commons.construction.dock.DockImpl;
import fr.univnantes.alma.commons.construction.type.building.city.City;
import fr.univnantes.alma.commons.construction.type.building.city.constructStrategy.CityConstructStrategy;
import fr.univnantes.alma.commons.construction.type.building.city.lootStrategy.CityLootStrategy;
import fr.univnantes.alma.commons.construction.type.building.colony.Colony;
import fr.univnantes.alma.commons.construction.type.building.colony.constructStrategy.ColonyConstructStrategy;
import fr.univnantes.alma.commons.construction.type.building.colony.lootStrategy.ColonyLootStrategy;
import fr.univnantes.alma.commons.construction.type.road.RoadImpl;
import fr.univnantes.alma.commons.construction.type.road.constructStrategy.RoadImplConstructStrategy;
import fr.univnantes.alma.commons.construction.type.road.lootStrategy.RoadImplLootStrategy;
import fr.univnantes.alma.commons.player.PlayerImpl;
import fr.univnantes.alma.commons.resource.type.*;
import fr.univnantes.alma.commons.territory.TerritoryController;
import fr.univnantes.alma.commons.utils.reflection.ReflectionUtils;
import fr.univnantes.alma.core.construction.ConstructionJSON;
import fr.univnantes.alma.core.construction.constructableArea.Area;
import fr.univnantes.alma.core.construction.constructableArea.AreaJSON;
import fr.univnantes.alma.core.construction.dock.Dock;
import fr.univnantes.alma.core.construction.type.Building;
import fr.univnantes.alma.core.construction.type.Road;
import fr.univnantes.alma.core.player.Player;
import fr.univnantes.alma.core.resource.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests of the construction controller
 */
class ConstructionControllerTest {

    static {
        ((Logger) LoggerFactory.getLogger("org.reflections")).setLevel(Level.OFF);
    }

    /**
     * Fields
     */
    private final TerritoryController territoryController = mock(TerritoryController.class);
    private ConstructionController constructionController;
    private final Dock dock1 = new DockImpl(new WoodResource(), 2);
    private final Dock dock2 = new DockImpl(null, 3);
    private AreaJSON areaJSON1;
    private final ConstructionJSON constructionJSON = new ConstructionJSONImpl(UUID.randomUUID(), Colony.class.getName());
    private Area<Building> area1;
    private Area<Building> area2;
    private Area<Road> area3;
    private final Player player1 = new PlayerImpl(UUID.randomUUID());
    private final Building construction1 = new Colony(player1);

    /**
     * Setup
     */
    @BeforeEach
    public void setup() {
        ReflectionUtils.changeObjectField(territoryController, "territories", Collections.emptyMap());

        constructionController = new ConstructionController(territoryController);

        constructionController.createArea(ColonyConstructStrategy.COLONY_CONSTRUCT_STRATEGY, ColonyLootStrategy.COLONY_LOOT_STRATEGY, dock1);

        areaJSON1 = constructionController.getAreasInformation().get(0);
        area1 = constructionController.getArea(areaJSON1, Building.class);

        constructionController.createArea(ColonyConstructStrategy.COLONY_CONSTRUCT_STRATEGY, ColonyLootStrategy.COLONY_LOOT_STRATEGY, dock2);
        AreaJSON areaJSON2 = constructionController.getAreasInformation().stream()
                .filter(areaInformation -> !areaInformation.getUUID().equals(areaJSON1.getUUID()))
                .findFirst()
                .orElseThrow();
        area2 = constructionController.getArea(areaJSON2, Building.class);

        constructionController.createArea(RoadImplConstructStrategy.ROAD_CONSTRUCT_STRATEGY, RoadImplLootStrategy.ROAD_LOOT_STRATEGY, null);
        AreaJSON areaJSON3 = constructionController.getAreasInformation().stream()
                .filter(areaInformation -> areaInformation.getType().contains("Road"))
                .findFirst()
                .orElseThrow();
        area3 = constructionController.getArea(areaJSON3, Road.class);

        constructionController.constructOnArea(area1, construction1);
        constructionController.addNeighbourBuildingToArea(area1, area2);
        constructionController.addNeighbourRoadToArea(area1, area3);
    }

    @Test
    public void hasAreaTest() {
        assertTrue(constructionController.hasArea(areaJSON1, Building.class));
        assertFalse(constructionController.hasArea(areaJSON1, Road.class));
    }

    @Test
    public void createAreaTest() {
        constructionController.createArea(CityConstructStrategy.CITY_CONSTRUCT_STRATEGY, CityLootStrategy.CITY_LOOT_STRATEGY, null);

        assertEquals(4, constructionController.getAreasInformation().size());
    }

    @Test
    public void deleteAreaTest() {
        constructionController.deleteArea(area1);

        assertFalse(constructionController.hasArea(areaJSON1, Building.class));
        assertEquals(2, constructionController.getAreasInformation().size());
    }

    @Test
    public void isAreaOwnedByPlayerTest() {
        assertTrue(constructionController.isAreaOwnedByPlayer(area1, player1));
        assertFalse(constructionController.isAreaOwnedByPlayer(area1, new PlayerImpl(UUID.randomUUID())));
    }

    @Test
    public void areaHasNeighbourBuildingTest() {
        assertTrue(constructionController.areaHasNeighbourBuilding(area1));
        assertFalse(constructionController.areaHasNeighbourBuilding(area3));
    }

    @Test
    public void buildingAreaIsAreaNeighbourTest() {
        assertTrue(constructionController.buildingAreaIsAreaNeighbour(area1, area2));
        assertFalse(constructionController.buildingAreaIsAreaNeighbour(area1, area1));
    }

    @Test
    public void addNeighbourBuildingToAreaTest() {
        Area<Building> area4 = new AreaImpl<>(ColonyConstructStrategy.COLONY_CONSTRUCT_STRATEGY, ColonyLootStrategy.COLONY_LOOT_STRATEGY, dock1);

        constructionController.addNeighbourBuildingToArea(area1, area4);

        assertTrue(constructionController.buildingAreaIsAreaNeighbour(area1, area4));
    }

    @Test
    public void removeNeighbourBuildingFromAreaTest() {
        constructionController.removeNeighbourBuildingFromArea(area1, area2);

        assertFalse(constructionController.areaHasNeighbourBuilding(area1));
    }

    @Test
    public void areaHasNeighbourRoadTest() {
        assertTrue(constructionController.areaHasNeighbourRoad(area1));
        assertFalse(constructionController.areaHasNeighbourRoad(area3));
    }

    @Test
    public void roadAreaIsAreaNeighbourTest() {
        Area<Road> area4 = new AreaImpl<>(RoadImplConstructStrategy.ROAD_CONSTRUCT_STRATEGY, RoadImplLootStrategy.ROAD_LOOT_STRATEGY, null);

        assertTrue(constructionController.roadAreaIsAreaNeighbour(area1, area3));
        assertFalse(constructionController.roadAreaIsAreaNeighbour(area1, area4));
    }

    @Test
    public void addNeighbourRoadToAreaTest() {
        Area<Road> area4 = new AreaImpl<>(RoadImplConstructStrategy.ROAD_CONSTRUCT_STRATEGY, RoadImplLootStrategy.ROAD_LOOT_STRATEGY, null);

        constructionController.addNeighbourRoadToArea(area1, area4);

        assertTrue(constructionController.roadAreaIsAreaNeighbour(area1, area4));
    }

    @Test
    public void removeNeighbourRoadToAreaTest() {
        constructionController.removeNeighbourRoadToArea(area1, area3);

        assertFalse(constructionController.areaHasNeighbourRoad(area1));
    }

    @Test
    public void areaHasConstructionTest() {
        assertTrue(constructionController.areaHasConstruction(area1));
        assertFalse(constructionController.areaHasConstruction(area3));
    }

    @Test
    public void areaHasDockTest() {
        assertTrue(constructionController.areaHasDock(area1));
        assertFalse(constructionController.areaHasDock(area3));
    }

    @Test
    public void getDockResourceTest() {
        assertEquals(new WoodResource(), constructionController.getDockResource(dock1));
    }

    @Test
    public void dockHasResourceTest() {
        assertTrue(constructionController.dockHasResource(dock1));
        assertFalse(constructionController.dockHasResource(dock2));
    }

    @Test
    public void getDockRatioTest() {
        assertEquals(2, constructionController.getDockRatio(dock1));
        assertEquals(3, constructionController.getDockRatio(dock2));
    }

    @Test
    public void isValidAreaDockResourceTest() {
        assertTrue(constructionController.isValidDockResource(dock1, new WoodResource()));
        assertTrue(constructionController.isValidDockResource(dock2, new OreResource()));
        assertFalse(constructionController.isValidDockResource(dock1, new OreResource()));
    }

    @Test
    public void areaIsConstructableTest() {
        constructionController.addNeighbourRoadToArea(area2, area3);
        constructionController.removeNeighbourBuildingFromArea(area1, area2);
        constructionController.constructOnArea(area3, new RoadImpl(player1));

        assertTrue(constructionController.areaIsConstructable(area2, new Colony(player1)));
        assertTrue(constructionController.areaIsConstructable(area1, new City(player1)));
        assertFalse(constructionController.areaIsConstructable(area1, construction1));
    }

    @Test
    public void constructOnAreaTest() {
        constructionController.constructOnArea(area3, new RoadImpl(player1));

        assertTrue(constructionController.areaHasConstruction(area3));
    }

    @Test
    public void hasConstructionTest() {
        assertTrue(constructionController.hasConstruction(constructionJSON, Colony.class, player1));
        assertFalse(constructionController.hasConstruction(constructionJSON, RoadImpl.class, player1));
    }

    @Test
    public void getConstructionCostTest() {
        Resource[] resources1 = {new WoodResource(), new ClayResource(), new WheatResource(), new WoolResource()};
        Resource[] resources2 = {new WheatResource().amount(2), new OreResource().amount(3)};
        Resource[] resources3 = {new WoodResource(), new ClayResource()};

        assertArrayEquals(resources1, constructionController.getConstructionCost(construction1).toArray());
        assertArrayEquals(resources2, constructionController.getConstructionCost(new City(player1)).toArray());
        assertArrayEquals(resources3, constructionController.getConstructionCost(new RoadImpl(player1)).toArray());
    }

    @Test
    public void getPlayerDockRatioTest() {
        constructionController.constructOnArea(area2, new Colony(player1));

        assertEquals(2, constructionController.getPlayerDockRatio(player1, new WoodResource()));
        assertEquals(3, constructionController.getPlayerDockRatio(player1, new OreResource()));
        assertEquals(4, constructionController.getPlayerDockRatio(new PlayerImpl(UUID.randomUUID()), new OreResource()));
    }
}