package fr.univnantes.alma.commons.construction;

import fr.univnantes.alma.commons.construction.area.Area;
import fr.univnantes.alma.commons.construction.area.AreaJSON;
import fr.univnantes.alma.core.construction.constructStrategy.IConstructStrategy;
import fr.univnantes.alma.commons.resource.Resource;
import fr.univnantes.alma.commons.utils.reflection.ReflectionUtils;
import fr.univnantes.alma.commons.configuration.Configuration;
import fr.univnantes.alma.core.construction.IConstruction;
import fr.univnantes.alma.core.construction.IConstructionJSON;
import fr.univnantes.alma.core.construction.IConstructionManager;
import fr.univnantes.alma.core.construction.constructableArea.IArea;
import fr.univnantes.alma.core.construction.constructableArea.IAreaJSON;
import fr.univnantes.alma.core.construction.lootStrategy.ILootStrategy;
import fr.univnantes.alma.core.construction.type.Building;
import fr.univnantes.alma.core.construction.type.Path;
import fr.univnantes.alma.core.construction.dock.IDock;
import fr.univnantes.alma.core.exception.*;
import fr.univnantes.alma.core.player.IPlayer;
import fr.univnantes.alma.core.resource.IResource;
import fr.univnantes.alma.core.territory.ITerritory;
import fr.univnantes.alma.core.territory.ITerritoryManager;
import org.springframework.core.GenericTypeResolver;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.*;

/**
 * Implementation of a construction manager
 */
public class ConstructionManager implements IConstructionManager {

    /**
     * Fields
     */
    private final Map<UUID, IArea<? extends IConstruction>> areas = new HashMap<>();

    /**
     * Creates a new construction manager
     */
    public ConstructionManager(@NonNull ITerritoryManager territoryManager) {
        Map<UUID, ITerritory> territories = (Map<UUID, ITerritory>) ReflectionUtils.getObjectField(territoryManager, "territories");
        //TODO createConstructableArea
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull List<IAreaJSON> getAreasInformation() {
        return areas.values().stream()
                .map(area -> {
                    Class<?> type = GenericTypeResolver.resolveTypeArgument(area.getConstructStrategy().getClass(), IConstructStrategy.class);
                    return (IAreaJSON) new AreaJSON(area.getUUID(), Objects.requireNonNull(type).getName());
                })
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public @NonNull <T extends IConstruction> IArea<T> getArea(@NonNull IAreaJSON areaJSON, @NonNull Class<T> type) throws UnregisteredAreaException {
        Objects.requireNonNull(areaJSON, "areaJSON cannot be null!");
        Objects.requireNonNull(type, "type cannot be null!");

        return (IArea<T>) Optional.ofNullable(areas.get(areaJSON.getUUID())).orElseThrow(UnregisteredAreaException::new);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends IConstruction> boolean hasArea(@NonNull IAreaJSON areaJSON, @NonNull Class<T> type) {
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
    public <T extends IConstruction> void createArea(@NonNull IConstructStrategy<T> constructStrategy, @NonNull ILootStrategy<T> lootStrategy, @Nullable IDock dock) {
        Objects.requireNonNull(constructStrategy, "constructStrategy cannot be null!");
        Objects.requireNonNull(lootStrategy, "lootStrategy cannot be null!");

        IArea<T> area = new Area<>(constructStrategy, lootStrategy, dock);

        areas.put(area.getUUID(), area);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends IConstruction> void deleteArea(@NonNull IArea<T> area) {
        Objects.requireNonNull(area, "area cannot be null!");

        areas.remove(area.getUUID());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends IConstruction> boolean isAreaOwnedByPlayer(@NonNull IArea<T> area, @NonNull IPlayer player) {
        Objects.requireNonNull(area, "area cannot be null!");
        Objects.requireNonNull(player, "player cannot be null!");

        return getAreaConstruction(area).getOwner().equals(player);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends IConstruction> boolean areaHasNeighbourBuilding(@NonNull IArea<T> area) {
        Objects.requireNonNull(area, "area cannot be null!");

        return !area.getNeighbourBuildings().isEmpty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends IConstruction> boolean buildingAreaIsAreaNeighbour(@NonNull IArea<T> area, @NonNull IArea<Building> buildingArea) {
        Objects.requireNonNull(area, "area cannot be null!");
        Objects.requireNonNull(buildingArea, "buildingArea cannot be null!");

        return area.getNeighbourBuildings().contains(buildingArea);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends IConstruction> void addNeighbourBuildingToArea(@NonNull IArea<T> area, @NonNull IArea<Building> buildingArea) {
        Objects.requireNonNull(area, "area cannot be null!");
        Objects.requireNonNull(buildingArea, "buildingArea cannot be null!");

        area.getNeighbourBuildings().add(buildingArea);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends IConstruction> void removeNeighbourBuildingFromArea(@NonNull IArea<T> area, @NonNull IArea<Building> buildingArea) {
        Objects.requireNonNull(area, "area cannot be null!");
        Objects.requireNonNull(buildingArea, "buildingArea cannot be null!");

        area.getNeighbourBuildings().remove(buildingArea);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends IConstruction> boolean areaHasNeighbourPath(@NonNull IArea<T> area) {
        Objects.requireNonNull(area, "area cannot be null!");

        return !area.getNeighbourPaths().isEmpty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends IConstruction> boolean pathAreaIsAreaNeighbour(@NonNull IArea<T> area, @NonNull IArea<Path> pathArea) {
        Objects.requireNonNull(area, "area cannot be null!");
        Objects.requireNonNull(pathArea, "pathArea cannot be null!");

        return area.getNeighbourPaths().contains(pathArea);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends IConstruction> void addNeighbourPathToArea(@NonNull IArea<T> area, @NonNull IArea<Path> pathArea) {
        Objects.requireNonNull(area, "area cannot be null!");
        Objects.requireNonNull(pathArea, "pathArea cannot be null!");

        area.getNeighbourPaths().add(pathArea);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends IConstruction> void removeNeighbourPathToArea(@NonNull IArea<T> area, @NonNull IArea<Path> pathArea) {
        Objects.requireNonNull(area, "area cannot be null!");
        Objects.requireNonNull(pathArea, "pathArea cannot be null!");

        area.getNeighbourPaths().remove(pathArea);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull <T extends IConstruction> IConstruction getAreaConstruction(@NonNull IArea<T> area) throws UndefinedAreaConstructionException {
        Objects.requireNonNull(area, "area cannot be null!");

        return area.getConstruction().orElseThrow(UndefinedAreaConstructionException::new);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends IConstruction> boolean areaHasConstruction(@NonNull IArea<T> area) {
        Objects.requireNonNull(area, "area cannot be null!");

        return area.getConstruction().isPresent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull <T extends IConstruction> IDock getAreaDock(@NonNull IArea<T> area) throws UndefinedAreaDockException {
        Objects.requireNonNull(area, "area cannot be null!");

        return area.getDock().orElseThrow(UndefinedAreaDockException::new);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends IConstruction> boolean areaHasDock(@NonNull IArea<T> area) {
        Objects.requireNonNull(area, "area cannot be null!");

        return area.getDock().isPresent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull IResource getDockResource(@NonNull IDock dock) throws UndefinedDockResourceException {
        Objects.requireNonNull(dock, "dock cannot be null!");

        return dock.getResource().orElseThrow(UndefinedDockResourceException::new);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean dockHasResource(@NonNull IDock dock) {
        Objects.requireNonNull(dock, "dock cannot be null!");

        return dock.getResource().isPresent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getDockRatio(@NonNull IDock dock) {
        Objects.requireNonNull(dock, "dock cannot be null!");

        return dock.getRatio();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValidDockResource(@NonNull IDock dock, @NonNull IResource resource) {
        Objects.requireNonNull(dock, "dock cannot be null!");
        Objects.requireNonNull(resource, "resource cannot be null!");

        return dock.getResource().map(dockResource -> dockResource.equals(resource)).orElse(true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends IConstruction> boolean areaIsConstructable(@NonNull IArea<T> area, @NonNull T construction) {
        Objects.requireNonNull(area, "area cannot be null!");
        Objects.requireNonNull(construction, "construction cannot be null!");

        return area.getConstructStrategy().isConstructable(area, construction);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends IConstruction> void constructOnArea(@NonNull IArea<T> area, @NonNull T construction) {
        Objects.requireNonNull(area, "area cannot be null!");
        Objects.requireNonNull(construction, "construction cannot be null!");

        area.getConstructStrategy().construct(area, construction);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public @NonNull <T extends IConstruction> T generateConstruction(@NonNull IConstructionJSON constructionJSON, @NonNull Class<T> type, @NonNull IPlayer player) throws InvalidInformationException {
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
    public <T extends IConstruction> boolean hasConstruction(@NonNull IConstructionJSON constructionJSON, @NonNull Class<T> type, @NonNull IPlayer player) {
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
    public @NonNull List<IResource> getConstructionCost(@NonNull IConstruction construction) {
        Objects.requireNonNull(construction, "construction cannot be null!");

        return Configuration.getConstructionCost(construction.getClass().getSimpleName()).stream()
                .map(resource -> (IResource) new Resource(resource.name()) {}.amount(resource.amount()))
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getPlayerDockRatio(@NonNull IPlayer player, @NonNull IResource resource) {
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