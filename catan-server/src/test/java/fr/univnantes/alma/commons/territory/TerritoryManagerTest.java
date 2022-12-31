package fr.univnantes.alma.commons.territory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import fr.univnantes.alma.commons.construction.area.Area;
import fr.univnantes.alma.commons.construction.type.building.colony.constructStrategy.ColonyConstructStrategy;
import fr.univnantes.alma.commons.construction.type.building.colony.lootStrategy.ColonyLootStrategy;
import fr.univnantes.alma.commons.construction.type.road.constructStrategy.RoadConstructStrategy;
import fr.univnantes.alma.commons.construction.type.road.lootStrategy.RoadLootStrategy;
import fr.univnantes.alma.commons.resource.ResourceManager;
import fr.univnantes.alma.core.construction.constructableArea.IArea;
import fr.univnantes.alma.core.construction.type.Building;
import fr.univnantes.alma.core.construction.type.Path;
import fr.univnantes.alma.core.resource.IResource;
import fr.univnantes.alma.core.territory.ITerritory;
import fr.univnantes.alma.core.territory.ITerritoryJSON;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests of the territory manager
 */
class TerritoryManagerTest {

    static {
        ((Logger) LoggerFactory.getLogger("org.reflections")).setLevel(Level.OFF);
    }

    /**
     * Fields
     */
    private final ITerritoryJSON territoryJSON = mock(ITerritoryJSON.class);
    private final TerritoryManager territoryManager = new TerritoryManager();
    private ITerritory territory;
    private ITerritory desert;
    private final IArea<Building> buildingArea = new Area<>(ColonyConstructStrategy.COLONY_CONSTRUCT_STRATEGY, ColonyLootStrategy.COLONY_LOOT_STRATEGY, null);
    private final IArea<Path> pathArea = new Area<>(RoadConstructStrategy.ROAD_CONSTRUCT_STRATEGY, RoadLootStrategy.ROAD_LOOT_STRATEGY, null);

    /**
     * Setup
     */
    @BeforeEach
    public void setup() {
        territory = territoryManager.getTerritory(territoryManager.getTerritoriesInformation().stream()
                .filter(ITerritoryJSON::hasResource)
                .findFirst()
                .orElseThrow());

        desert = territoryManager.getTerritory(territoryManager.getTerritoriesInformation().stream()
                .filter(territoryInformation -> !territoryInformation.hasResource())
                .findFirst()
                .orElseThrow());

        when(territoryJSON.getUUID()).thenReturn(territory.getUUID());
    }

    @Test
    void hasTerritoryTest() {
        assertTrue(territoryManager.hasTerritory(territoryJSON));
    }

    @Test
    void addNeighbourBuildingTest() {
        territoryManager.addNeighbourBuilding(territory, buildingArea);

        assertTrue(territoryManager.hasNeighbourBuilding(territory, buildingArea));
    }

    @Test
    void removeNeighbourBuildingTest() {
        territoryManager.addNeighbourBuilding(territory, buildingArea);

        territoryManager.removeNeighbourBuilding(territory, buildingArea);

        assertFalse(territoryManager.hasNeighbourBuilding(territory, buildingArea));
    }

    @Test
    void addNeighbourPathTest() {
        territoryManager.addNeighbourPath(territory, pathArea);

        assertTrue(territoryManager.hasNeighbourPath(territory, pathArea));
    }

    @Test
    void removeNeighbourPathTest() {
        territoryManager.addNeighbourPath(territory, pathArea);

        territoryManager.removeNeighbourPath(territory, pathArea);

        assertFalse(territoryManager.hasNeighbourPath(territory, pathArea));
    }

    @Test
    void hasResourceTest() {
        assertTrue(territoryManager.hasResource(territory));
        assertFalse(territoryManager.hasResource(desert));
    }

    @Test
    void getResourceTest() {
        IResource resource = new ResourceManager().generateResource(territoryManager.getTerritoriesInformation().stream()
                .filter(information -> information.getUUID().equals(territory.getUUID()))
                .findFirst()
                .map(ITerritoryJSON::getResource)
                .orElseThrow());

        assertEquals(resource, territoryManager.getResource(territory));
        assertThrows(RuntimeException.class, () -> territoryManager.getResource(desert));
    }

    @Test
    void moveThiefTest() {
        territoryManager.moveThief(territory);
        ITerritory thief = territoryManager.getTerritory(territoryManager.getTerritoriesInformation().stream()
                .filter(ITerritoryJSON::hasThief)
                .findFirst()
                .orElseThrow());

        assertEquals(territory, thief);
    }

}