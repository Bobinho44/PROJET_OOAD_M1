package fr.univnantes.alma.commons.territory;

import fr.univnantes.alma.commons.resource.ResourceJSON;
import fr.univnantes.alma.commons.token.TokenImpl;
import fr.univnantes.alma.commons.utils.collector.CollectorsUtils;
import fr.univnantes.alma.commons.utils.reflection.ReflectionUtils;
import fr.univnantes.alma.commons.utils.stream.IndexedStream;
import fr.univnantes.alma.core.configuration.Configuration;
import fr.univnantes.alma.core.construction.constructableArea.ConstructableArea;
import fr.univnantes.alma.core.construction.type.Building;
import fr.univnantes.alma.core.construction.type.Road;
import fr.univnantes.alma.core.player.Player;
import fr.univnantes.alma.core.ressource.Resource;
import fr.univnantes.alma.core.territory.Territory;
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
        return ReflectionUtils.getClassesOf(Territory.class, "fr.univnantes.alma.commons.territory.type").stream()
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
                    TerritoryJSON territoryJSON = new TerritoryJSON(territory.getUUID(), territory.hasThief());

                    if (hasResource(territory)) {
                        territoryJSON.resource(new ResourceJSON(getResource(territory).getName(), getResource(territory).getAmount()));
                    }

                    return territoryJSON;
                })
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull List<Territory> getTerritories() {
        return territories.values().stream().toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull Territory getTerritory(@NonNull TerritoryJSON territoryJSON) throws RuntimeException {
        return Optional.ofNullable(territories.get(territoryJSON.getUUID())).orElseThrow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasTerritory(@NonNull TerritoryJSON territoryJSON) {
        return Optional.ofNullable(territories.get(territoryJSON.getUUID())).isPresent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasNeighbourBuilding(@NonNull Territory territory, @NonNull ConstructableArea<Building> constructableArea) {
        return territory.getNeighbourBuildings().contains(constructableArea);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addNeighbourBuilding(@NonNull Territory territory, @NonNull ConstructableArea<Building> building) {
        territory.getNeighbourBuildings().add(building);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeNeighbourBuilding(@NonNull Territory territory, @NonNull ConstructableArea<Building> building) {
        territory.getNeighbourBuildings().remove(building);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasNeighbourRoad(@NonNull Territory territory, @NonNull ConstructableArea<Road> constructableArea) {
        return territory.getNeighbourRoads().contains(constructableArea);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addNeighbourRoad(@NonNull Territory territory, @NonNull ConstructableArea<Road> road) {
        territory.getNeighbourRoads().add(road);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeNeighbourRoad(@NonNull Territory territory, @NonNull ConstructableArea<Road> road) {
        territory.getNeighbourRoads().remove(road);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasResource(@NonNull Territory territory) {
        return territory.getResource().isPresent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull Resource getResource(@NonNull Territory territory) {
        return territory.getResource().orElseThrow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void moveThief(@NonNull Territory territory) {
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
                .flatMap(this::extractLoot)
                .toList();
    }

    private Stream<Map.Entry<Player, Resource>> extractLoot(@NonNull Territory territory) {
        return territory.getNeighbourBuildings().stream()
                .filter(area -> area.getConstruction().isPresent())
                .flatMap(area -> IntStream.range(0, area.getLootAmount())
                        .mapToObj(i -> Map.entry(area.getConstruction().get().getOwner(), getResource(territory))));
    }

}