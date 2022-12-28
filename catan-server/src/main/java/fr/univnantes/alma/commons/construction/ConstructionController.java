package fr.univnantes.alma.commons.construction;

import fr.univnantes.alma.commons.construction.constructableArea.AreaImpl;
import fr.univnantes.alma.commons.construction.constructableArea.AreaJSONImpl;
import fr.univnantes.alma.core.construction.constructStrategy.ConstructStrategy;
import fr.univnantes.alma.core.construction.constructableArea.AreaJSON;
import fr.univnantes.alma.commons.resource.ResourceImpl;
import fr.univnantes.alma.commons.utils.reflection.ReflectionUtils;
import fr.univnantes.alma.core.configuration.Configuration;
import fr.univnantes.alma.core.construction.Construction;
import fr.univnantes.alma.core.construction.ConstructionJSON;
import fr.univnantes.alma.core.construction.ConstructionManager;
import fr.univnantes.alma.core.construction.constructableArea.Area;
import fr.univnantes.alma.core.construction.lootStrategy.LootStrategy;
import fr.univnantes.alma.core.construction.type.Building;
import fr.univnantes.alma.core.construction.type.Road;
import fr.univnantes.alma.core.construction.dock.Dock;
import fr.univnantes.alma.core.exception.*;
import fr.univnantes.alma.core.player.Player;
import fr.univnantes.alma.core.resource.Resource;
import fr.univnantes.alma.core.territory.Territory;
import fr.univnantes.alma.core.territory.TerritoryManager;
import org.springframework.core.GenericTypeResolver;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.*;

/**
 * Implementation of a construction manager
 */
public class ConstructionController implements ConstructionManager {

    /**
     * Fields
     */
    private final Map<UUID, Area<? extends Construction>> areas = new HashMap<>();

    /**
     * Creates a new construction manager
     */
    public ConstructionController(@NonNull TerritoryManager territoryManager) {
        Map<UUID, Territory> territories = (Map<UUID, Territory>) ReflectionUtils.getObjectField(territoryManager, "territories");
        //TODO createConstructableArea
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull List<AreaJSON> getAreasInformation() {
        return areas.values().stream()
                .map(area -> {
                    Class<?> type = GenericTypeResolver.resolveTypeArgument(area.getConstructStrategy().getClass(), ConstructStrategy.class);
                    return (AreaJSON) new AreaJSONImpl(area.getUUID(), Objects.requireNonNull(type).getName());
                })
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public @NonNull <T extends Construction> Area<T> getArea(@NonNull AreaJSON areaJSON, @NonNull Class<T> type) throws UnregisteredAreaException {
        Objects.requireNonNull(areaJSON, "areaJSON cannot be null!");
        Objects.requireNonNull(type, "type cannot be null!");

        return (Area<T>) Optional.ofNullable(areas.get(areaJSON.getUUID())).orElseThrow(UnregisteredAreaException::new);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends Construction> boolean hasArea(@NonNull AreaJSON areaJSON, @NonNull Class<T> type) {
        Objects.requireNonNull(areaJSON, "areaJSON cannot be null!");
        Objects.requireNonNull(type, "type cannot be null!");

        //Checks if the area exist
        try {
            getArea(areaJSON, type);
            return Class.forName(areaJSON.getType()).equals(type);
        }

        //The area does not exist
        catch (ClassNotFoundException | UnregisteredAreaException e) {
            return false;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends Construction> void createArea(@NonNull ConstructStrategy<T> constructStrategy, @NonNull LootStrategy<T> lootStrategy, @Nullable Dock dock) {
        Objects.requireNonNull(constructStrategy, "constructStrategy cannot be null!");
        Objects.requireNonNull(lootStrategy, "lootStrategy cannot be null!");

        Area<T> area = new AreaImpl<>(constructStrategy, lootStrategy, dock);

        areas.put(area.getUUID(), area);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends Construction> void deleteArea(@NonNull Area<T> area) {
        Objects.requireNonNull(area, "area cannot be null!");

        areas.remove(area.getUUID());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends Construction> boolean isAreaOwnedByPlayer(@NonNull Area<T> area, @NonNull Player player) {
        Objects.requireNonNull(area, "area cannot be null!");
        Objects.requireNonNull(player, "player cannot be null!");

        return getAreaConstruction(area).getOwner().equals(player);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends Construction> boolean areaHasNeighbourBuilding(@NonNull Area<T> area) {
        Objects.requireNonNull(area, "area cannot be null!");

        return !area.getNeighbourBuildings().isEmpty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends Construction> boolean buildingAreaIsAreaNeighbour(@NonNull Area<T> area, @NonNull Area<Building> buildingArea) {
        Objects.requireNonNull(area, "area cannot be null!");
        Objects.requireNonNull(buildingArea, "buildingArea cannot be null!");

        return area.getNeighbourBuildings().contains(buildingArea);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends Construction> void addNeighbourBuildingToArea(@NonNull Area<T> area, @NonNull Area<Building> buildingArea) {
        Objects.requireNonNull(area, "area cannot be null!");
        Objects.requireNonNull(buildingArea, "buildingArea cannot be null!");

        area.getNeighbourBuildings().add(buildingArea);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends Construction> void removeNeighbourBuildingFromArea(@NonNull Area<T> area, @NonNull Area<Building> buildingArea) {
        Objects.requireNonNull(area, "area cannot be null!");
        Objects.requireNonNull(buildingArea, "buildingArea cannot be null!");

        area.getNeighbourBuildings().remove(buildingArea);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends Construction> boolean areaHasNeighbourRoad(@NonNull Area<T> area) {
        Objects.requireNonNull(area, "area cannot be null!");

        return !area.getNeighbourRoads().isEmpty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends Construction> boolean roadAreaIsAreaNeighbour(@NonNull Area<T> area, @NonNull Area<Road> roadArea) {
        Objects.requireNonNull(area, "area cannot be null!");
        Objects.requireNonNull(roadArea, "roadArea cannot be null!");

        return area.getNeighbourRoads().contains(roadArea);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends Construction> void addNeighbourRoadToArea(@NonNull Area<T> area, @NonNull Area<Road> roadArea) {
        Objects.requireNonNull(area, "area cannot be null!");
        Objects.requireNonNull(roadArea, "roadArea cannot be null!");

        area.getNeighbourRoads().add(roadArea);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends Construction> void removeNeighbourRoadToArea(@NonNull Area<T> area, @NonNull Area<Road> roadArea) {
        Objects.requireNonNull(area, "area cannot be null!");
        Objects.requireNonNull(roadArea, "roadArea cannot be null!");

        area.getNeighbourRoads().remove(roadArea);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull <T extends Construction> Construction getAreaConstruction(@NonNull Area<T> area) throws UndefinedAreaConstructionException {
        Objects.requireNonNull(area, "area cannot be null!");

        return area.getConstruction().orElseThrow(UndefinedAreaConstructionException::new);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends Construction> boolean areaHasConstruction(@NonNull Area<T> area) {
        Objects.requireNonNull(area, "area cannot be null!");

        return area.getConstruction().isPresent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull <T extends Construction> Dock getAreaDock(@NonNull Area<T> area) throws UndefinedAreaDockException {
        Objects.requireNonNull(area, "area cannot be null!");

        return area.getDock().orElseThrow(UndefinedAreaDockException::new);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends Construction> boolean areaHasDock(@NonNull Area<T> area) {
        Objects.requireNonNull(area, "area cannot be null!");

        return area.getDock().isPresent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull Resource getDockResource(@NonNull Dock dock) throws UndefinedDockResourceException {
        Objects.requireNonNull(dock, "dock cannot be null!");

        return dock.getResource().orElseThrow(UndefinedDockResourceException::new);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean dockHasResource(@NonNull Dock dock) {
        Objects.requireNonNull(dock, "dock cannot be null!");

        return dock.getResource().isPresent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getDockRatio(@NonNull Dock dock) {
        Objects.requireNonNull(dock, "dock cannot be null!");

        return dock.getRatio();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValidDockResource(@NonNull Dock dock, @NonNull Resource resource) {
        Objects.requireNonNull(dock, "dock cannot be null!");
        Objects.requireNonNull(resource, "resource cannot be null!");

        return dock.getResource().map(dockResource -> dockResource.equals(resource)).orElse(true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends Construction> boolean areaIsConstructable(@NonNull Area<T> area, @NonNull T construction) {
        Objects.requireNonNull(area, "area cannot be null!");
        Objects.requireNonNull(construction, "construction cannot be null!");

        return area.getConstructStrategy().isConstructable(area, construction);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends Construction> void constructOnArea(@NonNull Area<T> area, @NonNull T construction) {
        Objects.requireNonNull(area, "area cannot be null!");
        Objects.requireNonNull(construction, "construction cannot be null!");

        area.getConstructStrategy().construct(area, construction);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public @NonNull <T extends Construction> T generateConstruction(@NonNull ConstructionJSON constructionJSON, @NonNull Class<T> type, @NonNull Player player) throws InvalidInformationException {
        Objects.requireNonNull(constructionJSON, "constructionJSON cannot be null!");
        Objects.requireNonNull(type, "type cannot be null!");
        Objects.requireNonNull(player, "player cannot be null!");

        try {
            return (T) ReflectionUtils.getInstancesOf(Class.forName(constructionJSON.getType()), player);
        }

        catch (Exception e) {
            throw new InvalidInformationException();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends Construction> boolean hasConstruction(@NonNull ConstructionJSON constructionJSON, @NonNull Class<T> type, @NonNull Player player) {
        Objects.requireNonNull(constructionJSON, "constructionJSON cannot be null!");
        Objects.requireNonNull(type, "type cannot be null!");
        Objects.requireNonNull(player, "player cannot be null!");

        try {
            return generateConstruction(constructionJSON, type, player).getClass().equals(type);
        }

        catch (InvalidInformationException e) {
            return false;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull List<Resource> getConstructionCost(@NonNull Construction construction) {
        Objects.requireNonNull(construction, "construction cannot be null!");

        return Configuration.getConstructionCost(construction.getClass().getSimpleName()).stream()
                .map(resource -> (Resource) new ResourceImpl(resource.getName()) {}.amount(resource.getAmount()))
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getPlayerDockRatio(@NonNull Player player, @NonNull Resource resource) {
        Objects.requireNonNull(player, "player cannot be null!");
        Objects.requireNonNull(resource, "resource cannot be null!");

        return areas.values().stream()
                .filter(area -> areaHasConstruction(area) && isAreaOwnedByPlayer(area, player))
                .filter(area -> areaHasDock(area) && isValidDockResource(getAreaDock(area), resource))
                .map(area -> getDockRatio(getAreaDock(area)))
                .min(Integer::compare)
                .orElse(Configuration.getMaxDockRatio());
    }

}