package fr.univnantes.alma.commons.territory;

import fr.univnantes.alma.commons.construction.constructableArea.ConstructableAreaImpl;
import fr.univnantes.alma.commons.construction.type.building.colony.constructStrategy.ColonyConstructStrategy;
import fr.univnantes.alma.commons.construction.type.building.colony.lootStrategy.ColonyLootStrategy;
import fr.univnantes.alma.commons.construction.type.road.constructStrategy.RoadConstructStrategy;
import fr.univnantes.alma.commons.construction.type.road.lootStrategy.RoadLootStrategy;
import fr.univnantes.alma.commons.resource.ResourceController;
import fr.univnantes.alma.core.construction.constructableArea.ConstructableArea;
import fr.univnantes.alma.core.construction.type.Building;
import fr.univnantes.alma.core.construction.type.Road;
import fr.univnantes.alma.core.ressource.Resource;
import fr.univnantes.alma.core.territory.Territory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class TerritoryControllerTest {

    private final TerritoryJSON territoryJSON = mock(TerritoryJSON.class);
    private final TerritoryController territoryController = new TerritoryController();
    private Territory territory;
    private Territory desert;
    private final ConstructableArea<Building> buildingArea = new ConstructableAreaImpl<>(ColonyConstructStrategy.COLONY_CONSTRUCT_STRATEGY, ColonyLootStrategy.COLONY_LOOT_STRATEGY, null);
    private final ConstructableArea<Road> roadArea = new ConstructableAreaImpl<>(RoadConstructStrategy.ROAD_CONSTRUCT_STRATEGY, RoadLootStrategy.ROAD_LOOT_STRATEGY, null);

    @BeforeEach
    public void setup() {
        territory = territoryController.getTerritories().stream()
                .filter(territory1 -> territory1.getResource().isPresent())
                .findFirst()
                .orElseThrow();

        desert = territoryController.getTerritories().stream()
                .filter(territory1 -> territory1.getResource().isEmpty())
                .findFirst()
                .orElseThrow();

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
        Territory thief = territoryController.getTerritories().stream()
                .filter(Territory::hasThief)
                .findFirst()
                .orElseThrow();

        assertEquals(territory, thief);
    }

}