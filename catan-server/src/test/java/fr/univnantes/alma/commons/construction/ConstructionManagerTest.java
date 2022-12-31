package fr.univnantes.alma.commons.construction;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import fr.univnantes.alma.commons.construction.area.Area;
import fr.univnantes.alma.commons.construction.dock.Dock;
import fr.univnantes.alma.commons.construction.type.building.city.City;
import fr.univnantes.alma.commons.construction.type.building.city.constructStrategy.CityConstructStrategy;
import fr.univnantes.alma.commons.construction.type.building.city.lootStrategy.CityLootStrategy;
import fr.univnantes.alma.commons.construction.type.building.colony.Colony;
import fr.univnantes.alma.commons.construction.type.building.colony.constructStrategy.ColonyConstructStrategy;
import fr.univnantes.alma.commons.construction.type.building.colony.lootStrategy.ColonyLootStrategy;
import fr.univnantes.alma.commons.construction.type.road.Road;
import fr.univnantes.alma.commons.construction.type.road.constructStrategy.RoadConstructStrategy;
import fr.univnantes.alma.commons.construction.type.road.lootStrategy.RoadLootStrategy;
import fr.univnantes.alma.commons.player.Player;
import fr.univnantes.alma.commons.resource.type.*;
import fr.univnantes.alma.commons.territory.TerritoryManager;
import fr.univnantes.alma.commons.utils.reflection.ReflectionUtils;
import fr.univnantes.alma.core.construction.IConstructionJSON;
import fr.univnantes.alma.core.construction.constructableArea.IArea;
import fr.univnantes.alma.core.construction.constructableArea.IAreaJSON;
import fr.univnantes.alma.core.construction.dock.IDock;
import fr.univnantes.alma.core.construction.type.Building;
import fr.univnantes.alma.core.construction.type.Path;
import fr.univnantes.alma.core.player.IPlayer;
import fr.univnantes.alma.core.resource.IResource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests of the construction manager
 */
class ConstructionManagerTest {

    static {
        ((Logger) LoggerFactory.getLogger("org.reflections")).setLevel(Level.OFF);
    }

    /**
     * Fields
     */
    private final TerritoryManager territoryManager = mock(TerritoryManager.class);
    private ConstructionManager constructionManager;
    private final IDock dock1 = new Dock(new WoodResource(), 2);
    private final IDock dock2 = new Dock(null, 3);
    private IAreaJSON areaJSON1;
    private final IConstructionJSON constructionJSON = new ConstructionJSON(UUID.randomUUID(), Colony.class.getName());
    private IArea<Building> area1;
    private IArea<Building> area2;
    private IArea<Path> area3;
    private final IPlayer player1 = new Player(UUID.randomUUID());
    private final Building construction1 = new Colony(player1);

    /**
     * Setup
     */
    @BeforeEach
    public void setup() {
        ReflectionUtils.changeObjectField(territoryManager, "territories", Collections.emptyMap());

        constructionManager = new ConstructionManager(territoryManager);

        constructionManager.createArea(ColonyConstructStrategy.COLONY_CONSTRUCT_STRATEGY, ColonyLootStrategy.COLONY_LOOT_STRATEGY, dock1);

        areaJSON1 = constructionManager.getAreasInformation().get(0);
        area1 = constructionManager.getArea(areaJSON1, Building.class);

        constructionManager.createArea(ColonyConstructStrategy.COLONY_CONSTRUCT_STRATEGY, ColonyLootStrategy.COLONY_LOOT_STRATEGY, dock2);
        IAreaJSON areaJSON2 = constructionManager.getAreasInformation().stream()
                .filter(areaInformation -> !areaInformation.getUUID().equals(areaJSON1.getUUID()))
                .findFirst()
                .orElseThrow();
        area2 = constructionManager.getArea(areaJSON2, Building.class);

        constructionManager.createArea(RoadConstructStrategy.ROAD_CONSTRUCT_STRATEGY, RoadLootStrategy.ROAD_LOOT_STRATEGY, null);
        IAreaJSON areaJSON3 = constructionManager.getAreasInformation().stream()
                .filter(areaInformation -> areaInformation.getType().equals(Path.class.getName()))
                .findFirst()
                .orElseThrow();
        area3 = constructionManager.getArea(areaJSON3, Path.class);

        constructionManager.constructOnArea(area1, construction1);
        constructionManager.addNeighbourBuildingToArea(area1, area2);
        constructionManager.addNeighbourPathToArea(area1, area3);
    }

    @Test
    public void hasAreaTest() {
        assertTrue(constructionManager.hasArea(areaJSON1, Building.class));
        assertFalse(constructionManager.hasArea(areaJSON1, Path.class));
    }

    @Test
    public void createAreaTest() {
        constructionManager.createArea(CityConstructStrategy.CITY_CONSTRUCT_STRATEGY, CityLootStrategy.CITY_LOOT_STRATEGY, null);

        assertEquals(4, constructionManager.getAreasInformation().size());
    }

    @Test
    public void deleteAreaTest() {
        constructionManager.deleteArea(area1);

        assertFalse(constructionManager.hasArea(areaJSON1, Building.class));
        assertEquals(2, constructionManager.getAreasInformation().size());
    }

    @Test
    public void isAreaOwnedByPlayerTest() {
        assertTrue(constructionManager.isAreaOwnedByPlayer(area1, player1));
        assertFalse(constructionManager.isAreaOwnedByPlayer(area1, new Player(UUID.randomUUID())));
    }

    @Test
    public void areaHasNeighbourBuildingTest() {
        assertTrue(constructionManager.areaHasNeighbourBuilding(area1));
        assertFalse(constructionManager.areaHasNeighbourBuilding(area3));
    }

    @Test
    public void buildingAreaIsAreaNeighbourTest() {
        assertTrue(constructionManager.buildingAreaIsAreaNeighbour(area1, area2));
        assertFalse(constructionManager.buildingAreaIsAreaNeighbour(area1, area1));
    }

    @Test
    public void addNeighbourBuildingToAreaTest() {
        IArea<Building> area4 = new Area<>(ColonyConstructStrategy.COLONY_CONSTRUCT_STRATEGY, ColonyLootStrategy.COLONY_LOOT_STRATEGY, dock1);

        constructionManager.addNeighbourBuildingToArea(area1, area4);

        assertTrue(constructionManager.buildingAreaIsAreaNeighbour(area1, area4));
    }

    @Test
    public void removeNeighbourBuildingFromAreaTest() {
        constructionManager.removeNeighbourBuildingFromArea(area1, area2);

        assertFalse(constructionManager.areaHasNeighbourBuilding(area1));
    }

    @Test
    public void areaHasNeighbourPathTest() {
        assertTrue(constructionManager.areaHasNeighbourPath(area1));
        assertFalse(constructionManager.areaHasNeighbourPath(area3));
    }

    @Test
    public void pathAreaIsAreaNeighbourTest() {
        IArea<Path> area4 = new Area<>(RoadConstructStrategy.ROAD_CONSTRUCT_STRATEGY, RoadLootStrategy.ROAD_LOOT_STRATEGY, null);

        assertTrue(constructionManager.pathAreaIsAreaNeighbour(area1, area3));
        assertFalse(constructionManager.pathAreaIsAreaNeighbour(area1, area4));
    }

    @Test
    public void addNeighbourPathToAreaTest() {
        IArea<Path> area4 = new Area<>(RoadConstructStrategy.ROAD_CONSTRUCT_STRATEGY, RoadLootStrategy.ROAD_LOOT_STRATEGY, null);

        constructionManager.addNeighbourPathToArea(area1, area4);

        assertTrue(constructionManager.pathAreaIsAreaNeighbour(area1, area4));
    }

    @Test
    public void removeNeighbourPathToAreaTest() {
        constructionManager.removeNeighbourPathToArea(area1, area3);

        assertFalse(constructionManager.areaHasNeighbourPath(area1));
    }

    @Test
    public void areaHasConstructionTest() {
        assertTrue(constructionManager.areaHasConstruction(area1));
        assertFalse(constructionManager.areaHasConstruction(area3));
    }

    @Test
    public void areaHasDockTest() {
        assertTrue(constructionManager.areaHasDock(area1));
        assertFalse(constructionManager.areaHasDock(area3));
    }

    @Test
    public void getDockResourceTest() {
        assertEquals(new WoodResource(), constructionManager.getDockResource(dock1));
    }

    @Test
    public void dockHasResourceTest() {
        assertTrue(constructionManager.dockHasResource(dock1));
        assertFalse(constructionManager.dockHasResource(dock2));
    }

    @Test
    public void getDockRatioTest() {
        assertEquals(2, constructionManager.getDockRatio(dock1));
        assertEquals(3, constructionManager.getDockRatio(dock2));
    }

    @Test
    public void isValidAreaDockResourceTest() {
        assertTrue(constructionManager.isValidDockResource(dock1, new WoodResource()));
        assertTrue(constructionManager.isValidDockResource(dock2, new OreResource()));
        assertFalse(constructionManager.isValidDockResource(dock1, new OreResource()));
    }

    @Test
    public void areaIsConstructableTest() {
        constructionManager.addNeighbourPathToArea(area2, area3);
        constructionManager.removeNeighbourBuildingFromArea(area1, area2);
        constructionManager.constructOnArea(area3, new Road(player1));

        assertTrue(constructionManager.areaIsConstructable(area2, new Colony(player1)));
        assertTrue(constructionManager.areaIsConstructable(area1, new City(player1)));
        assertFalse(constructionManager.areaIsConstructable(area1, construction1));
    }

    @Test
    public void constructOnAreaTest() {
        constructionManager.constructOnArea(area3, new Road(player1));

        assertTrue(constructionManager.areaHasConstruction(area3));
    }

    @Test
    public void hasConstructionTest() {
        assertTrue(constructionManager.hasConstruction(constructionJSON, Colony.class, player1));
        assertFalse(constructionManager.hasConstruction(constructionJSON, Road.class, player1));
    }

    @Test
    public void getConstructionCostTest() {
        IResource[] resources1 = {new WoodResource(), new ClayResource(), new WheatResource(), new WoolResource()};
        IResource[] resources2 = {new WheatResource().amount(2), new OreResource().amount(3)};
        IResource[] resources3 = {new WoodResource(), new ClayResource()};

        assertArrayEquals(resources1, constructionManager.getConstructionCost(construction1).toArray());
        assertArrayEquals(resources2, constructionManager.getConstructionCost(new City(player1)).toArray());
        assertArrayEquals(resources3, constructionManager.getConstructionCost(new Road(player1)).toArray());
    }

    @Test
    public void getPlayerDockRatioTest() {
        constructionManager.constructOnArea(area2, new Colony(player1));

        assertEquals(2, constructionManager.getPlayerDockRatio(player1, new WoodResource()));
        assertEquals(3, constructionManager.getPlayerDockRatio(player1, new OreResource()));
        assertEquals(4, constructionManager.getPlayerDockRatio(new Player(UUID.randomUUID()), new OreResource()));
    }
}