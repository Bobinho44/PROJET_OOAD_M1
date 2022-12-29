package fr.univnantes.alma.commons.territory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import fr.univnantes.alma.commons.construction.constructableArea.AreaImpl;
import fr.univnantes.alma.commons.construction.type.building.colony.constructStrategy.ColonyConstructStrategy;
import fr.univnantes.alma.commons.construction.type.building.colony.lootStrategy.ColonyLootStrategy;
import fr.univnantes.alma.commons.construction.type.road.constructStrategy.RoadImplConstructStrategy;
import fr.univnantes.alma.commons.construction.type.road.lootStrategy.RoadImplLootStrategy;
import fr.univnantes.alma.commons.resource.ResourceController;
import fr.univnantes.alma.core.construction.constructableArea.Area;
import fr.univnantes.alma.core.construction.type.Building;
import fr.univnantes.alma.core.construction.type.Road;
import fr.univnantes.alma.core.resource.Resource;
import fr.univnantes.alma.core.territory.Territory;
import fr.univnantes.alma.core.territory.TerritoryJSON;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests of the territory controller
 */
class TerritoryControllerTest {

    static {
        ((Logger) LoggerFactory.getLogger("org.reflections")).setLevel(Level.OFF);
    }

    /**
     * Fields
     */
    private final TerritoryJSON territoryJSON = mock(TerritoryJSON.class);
    private final TerritoryController territoryController = new TerritoryController();
    private Territory territory;
    private Territory desert;
    private final Area<Building> buildingArea = new AreaImpl<>(ColonyConstructStrategy.COLONY_CONSTRUCT_STRATEGY, ColonyLootStrategy.COLONY_LOOT_STRATEGY, null);
    private final Area<Road> roadArea = new AreaImpl<>(RoadImplConstructStrategy.ROAD_CONSTRUCT_STRATEGY, RoadImplLootStrategy.ROAD_LOOT_STRATEGY, null);

    /**
     * Setup
     */
    @BeforeEach
    public void setup() {
        territory = territoryController.getTerritory(territoryController.getTerritoriesInformation().stream()
                .filter(TerritoryJSON::hasResource)
                .findFirst()
                .orElseThrow());

        desert = territoryController.getTerritory(territoryController.getTerritoriesInformation().stream()
                .filter(territoryInformation -> !territoryInformation.hasResource())
                .findFirst()
                .orElseThrow());

        when(territoryJSON.getUUID()).thenReturn(territory.getUUID());
    }

    @Test
    void hasTerritoryTest() {
        assertTrue(territoryController.hasTerritory(territoryJSON));
    }

    @Test
    void addNeighbourBuildingTest() {
        territoryController.addNeighbourBuilding(territory, buildingArea);

        assertTrue(territoryController.hasNeighbourBuilding(territory, buildingArea));
    }

    @Test
    void removeNeighbourBuildingTest() {
        territoryController.addNeighbourBuilding(territory, buildingArea);

        territoryController.removeNeighbourBuilding(territory, buildingArea);

        assertFalse(territoryController.hasNeighbourBuilding(territory, buildingArea));
    }

    @Test
    void addNeighbourRoadTest() {
        territoryController.addNeighbourRoad(territory, roadArea);

        assertTrue(territoryController.hasNeighbourRoad(territory, roadArea));
    }

    @Test
    void removeNeighbourRoadTest() {
        territoryController.addNeighbourRoad(territory, roadArea);

        territoryController.removeNeighbourRoad(territory, roadArea);

        assertFalse(territoryController.hasNeighbourRoad(territory, roadArea));
    }

    @Test
    void hasResourceTest() {
        assertTrue(territoryController.hasResource(territory));
        assertFalse(territoryController.hasResource(desert));
    }

    @Test
    void getResourceTest() {
        Resource resource = new ResourceController().generateResource(territoryController.getTerritoriesInformation().stream()
                .filter(information -> information.getUUID().equals(territory.getUUID()))
                .findFirst()
                .map(TerritoryJSON::getResource)
                .orElseThrow());

        assertEquals(resource, territoryController.getResource(territory));
        assertThrows(RuntimeException.class, () -> territoryController.getResource(desert));
    }

    @Test
    void moveThiefTest() {
        territoryController.moveThief(territory);
        Territory thief = territoryController.getTerritory(territoryController.getTerritoriesInformation().stream()
                .filter(TerritoryJSON::hasThief)
                .findFirst()
                .orElseThrow());

        assertEquals(territory, thief);
    }

}