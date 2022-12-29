package fr.univnantes.alma.commons.territory;

import fr.univnantes.alma.commons.resource.ResourceJSONImpl;
import fr.univnantes.alma.commons.token.TokenImpl;
import fr.univnantes.alma.commons.utils.collector.CollectorsUtils;
import fr.univnantes.alma.commons.utils.reflection.ReflectionUtils;
import fr.univnantes.alma.commons.utils.stream.IndexedStream;
import fr.univnantes.alma.core.configuration.Configuration;
import fr.univnantes.alma.core.construction.constructableArea.Area;
import fr.univnantes.alma.core.construction.type.Building;
import fr.univnantes.alma.core.construction.type.Road;
import fr.univnantes.alma.core.exception.UndefinedTerritoryResourceException;
import fr.univnantes.alma.core.exception.UnregisteredTerritoryException;
import fr.univnantes.alma.core.player.Player;
import fr.univnantes.alma.core.resource.Resource;
import fr.univnantes.alma.core.territory.Territory;
import fr.univnantes.alma.core.territory.TerritoryJSON;
import fr.univnantes.alma.core.territory.TerritoryManager;
import org.springframework.lang.NonNull;

import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Implementation of a territory manager
 */
public class TerritoryController implements TerritoryManager {

    /**
     * Fields
     */
    private final Map<UUID, Territory> territories;

    /**
     * Creates a new territory manager
     */
    public TerritoryController() {
        this.territories = createTerritories();

        affectTokens();
    }

    /**
     * Creates all territories
     *
     * @return all territories
     */
    private @NonNull Map<UUID, Territory> createTerritories() {
        return ReflectionUtils.getSubClassesOf(Territory.class, "fr.univnantes.alma.commons.territory.type").stream()
                .flatMap(territory -> IntStream.range(0, Configuration.getTerritoryAmount(territory.getSimpleName()))
                        .mapToObj(i -> ReflectionUtils.getInstancesOf(territory)))
                .collect(CollectorsUtils.toShuffledMap(Territory::getUUID, territory -> territory));
    }

    /**
     * Affects the tokens
     */
    private void affectTokens() {
        IndexedStream.from(territories.values().stream()
                        .filter(this::hasResource)
                        .toList())
                .forEach((territory, i) -> territory.setToken(TokenImpl.values()[i]));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull List<TerritoryJSON> getTerritoriesInformation() {
        return territories.values().stream()
                .map(territory -> {
                    TerritoryJSON territoryJSON = new TerritoryJSONImpl(territory.getUUID(), territory.hasThief());

                    if (hasResource(territory)) {
                        territoryJSON.resource(new ResourceJSONImpl(getResource(territory).getName(), getResource(territory).getAmount()));
                    }

                    return territoryJSON;
                })
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull Territory getTerritory(@NonNull TerritoryJSON territoryJSON) throws UnregisteredTerritoryException {
        Objects.requireNonNull(territoryJSON, "territoryJSON cannot be null!");

        return Optional.ofNullable(territories.get(territoryJSON.getUUID())).orElseThrow(UnregisteredTerritoryException::new);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasTerritory(@NonNull TerritoryJSON territoryJSON) {
        Objects.requireNonNull(territoryJSON, "territoryJSON cannot be null!");

        return Optional.ofNullable(territories.get(territoryJSON.getUUID())).isPresent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasNeighbourBuilding(@NonNull Territory territory, @NonNull Area<Building> buildingArea) {
        Objects.requireNonNull(territory, "territory cannot be null!");
        Objects.requireNonNull(buildingArea, "buildingArea cannot be null!");

        return territory.getNeighbourBuildings().contains(buildingArea);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addNeighbourBuilding(@NonNull Territory territory, @NonNull Area<Building> buildingArea) {
        Objects.requireNonNull(territory, "territory cannot be null!");
        Objects.requireNonNull(buildingArea, "buildingArea cannot be null!");

        territory.getNeighbourBuildings().add(buildingArea);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeNeighbourBuilding(@NonNull Territory territory, @NonNull Area<Building> buildingArea) {
        Objects.requireNonNull(territory, "territory cannot be null!");
        Objects.requireNonNull(buildingArea, "buildingArea cannot be null!");

        territory.getNeighbourBuildings().remove(buildingArea);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasNeighbourRoad(@NonNull Territory territory, @NonNull Area<Road> roadArea) {
        Objects.requireNonNull(territory, "territory cannot be null!");
        Objects.requireNonNull(roadArea, "roadArea cannot be null!");

        return territory.getNeighbourRoads().contains(roadArea);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addNeighbourRoad(@NonNull Territory territory, @NonNull Area<Road> roadArea) {
        Objects.requireNonNull(territory, "territory cannot be null!");
        Objects.requireNonNull(roadArea, "roadArea cannot be null!");

        territory.getNeighbourRoads().add(roadArea);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeNeighbourRoad(@NonNull Territory territory, @NonNull Area<Road> roadArea) {
        Objects.requireNonNull(territory, "territory cannot be null!");
        Objects.requireNonNull(roadArea, "roadArea cannot be null!");

        territory.getNeighbourRoads().remove(roadArea);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasResource(@NonNull Territory territory) {
        Objects.requireNonNull(territory, "territory cannot be null!");

        return territory.getResource().isPresent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull Resource getResource(@NonNull Territory territory) throws UndefinedTerritoryResourceException {
        Objects.requireNonNull(territory, "territory cannot be null!");

        return territory.getResource().orElseThrow(UndefinedTerritoryResourceException::new);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void moveThief(@NonNull Territory territory) {
        Objects.requireNonNull(territory, "territory cannot be null!");

        territories.values().stream().filter(Territory::hasThief).findFirst().ifPresent(from -> from.setThiefOccupation(false));
        territory.setThiefOccupation(true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull List<Map.Entry<Player, Resource>> getLoot(int diceNumber) {
        return territories.values().stream()
                .filter(this::hasResource)
                .filter(territory -> territory.getToken().getNumber() == diceNumber)
                .filter(territory -> !territory.hasThief())
                .flatMap(this::extractLoots)
                .toList();
    }

    /**
     * Extracts loots from the territory
     *
     * @param territory the territory
     * @return the loots
     */
    private @NonNull Stream<Map.Entry<Player, Resource>> extractLoots(@NonNull Territory territory) {
        Objects.requireNonNull(territory, "territory cannot be null!");

        return territory.getNeighbourBuildings().stream()
                .filter(area -> area.getConstruction().isPresent())
                .flatMap(area -> IntStream.range(0, area.getLootAmount())
                        .mapToObj(i -> Map.entry(area.getConstruction().get().getOwner(), getResource(territory))));
    }

}