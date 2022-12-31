package fr.univnantes.alma.commons.territory;

import fr.univnantes.alma.commons.resource.ResourceJSON;
import fr.univnantes.alma.commons.token.Token;
import fr.univnantes.alma.commons.utils.collector.CollectorsUtils;
import fr.univnantes.alma.commons.utils.reflection.ReflectionUtils;
import fr.univnantes.alma.commons.utils.stream.IndexedStream;
import fr.univnantes.alma.commons.configuration.Configuration;
import fr.univnantes.alma.core.construction.constructableArea.IArea;
import fr.univnantes.alma.core.construction.type.Building;
import fr.univnantes.alma.core.construction.type.Path;
import fr.univnantes.alma.core.exception.UndefinedTerritoryResourceException;
import fr.univnantes.alma.core.exception.UnregisteredTerritoryException;
import fr.univnantes.alma.core.player.IPlayer;
import fr.univnantes.alma.core.resource.IResource;
import fr.univnantes.alma.core.territory.ITerritory;
import fr.univnantes.alma.core.territory.ITerritoryJSON;
import fr.univnantes.alma.core.territory.ITerritoryManager;
import org.springframework.lang.NonNull;

import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Implementation of a territory manager
 */
public class TerritoryManager implements ITerritoryManager {

    /**
     * Fields
     */
    private final Map<UUID, ITerritory> territories;

    /**
     * Creates a new territory manager
     */
    public TerritoryManager() {
        this.territories = createTerritories();

        affectTokens();
    }

    /**
     * Creates all territories
     *
     * @return all territories
     */
    private @NonNull Map<UUID, ITerritory> createTerritories() {
        return ReflectionUtils.getSubClassesOf(ITerritory.class, "fr.univnantes.alma.commons.territory.type").stream()
                .flatMap(territory -> IntStream.range(0, Configuration.getTerritoryAmount(territory.getSimpleName()))
                        .mapToObj(i -> ReflectionUtils.getInstancesOf(territory)))
                .collect(CollectorsUtils.toShuffledMap(ITerritory::getUUID, territory -> territory));
    }

    /**
     * Affects the tokens
     */
    private void affectTokens() {
        IndexedStream.from(territories.values().stream()
                        .filter(this::hasResource)
                        .toList())
                .forEach((territory, i) -> territory.setToken(Token.values()[i]));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull List<ITerritoryJSON> getTerritoriesInformation() {
        return territories.values().stream()
                .map(territory -> {
                    ITerritoryJSON territoryJSON = new TerritoryJSON(territory.getUUID(), territory.hasThief());

                    if (hasResource(territory)) {
                        return territoryJSON.resource(new ResourceJSON(getResource(territory).getName(), getResource(territory).getAmount()));
                    }

                    return territoryJSON;
                })
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull ITerritory getTerritory(@NonNull ITerritoryJSON territoryJSON) throws UnregisteredTerritoryException {
        Objects.requireNonNull(territoryJSON, "territoryJSON cannot be null!");

        return Optional.ofNullable(territories.get(territoryJSON.getUUID())).orElseThrow(UnregisteredTerritoryException::new);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasTerritory(@NonNull ITerritoryJSON territoryJSON) {
        Objects.requireNonNull(territoryJSON, "territoryJSON cannot be null!");

        return Optional.ofNullable(territories.get(territoryJSON.getUUID())).isPresent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasNeighbourBuilding(@NonNull ITerritory territory, @NonNull IArea<Building> buildingArea) {
        Objects.requireNonNull(territory, "territory cannot be null!");
        Objects.requireNonNull(buildingArea, "buildingArea cannot be null!");

        return territory.getNeighbourBuildings().contains(buildingArea);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addNeighbourBuilding(@NonNull ITerritory territory, @NonNull IArea<Building> buildingArea) {
        Objects.requireNonNull(territory, "territory cannot be null!");
        Objects.requireNonNull(buildingArea, "buildingArea cannot be null!");

        territory.getNeighbourBuildings().add(buildingArea);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeNeighbourBuilding(@NonNull ITerritory territory, @NonNull IArea<Building> buildingArea) {
        Objects.requireNonNull(territory, "territory cannot be null!");
        Objects.requireNonNull(buildingArea, "buildingArea cannot be null!");

        territory.getNeighbourBuildings().remove(buildingArea);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasNeighbourPath(@NonNull ITerritory territory, @NonNull IArea<Path> roadArea) {
        Objects.requireNonNull(territory, "territory cannot be null!");
        Objects.requireNonNull(roadArea, "roadArea cannot be null!");

        return territory.getNeighbourRoads().contains(roadArea);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addNeighbourPath(@NonNull ITerritory territory, @NonNull IArea<Path> roadArea) {
        Objects.requireNonNull(territory, "territory cannot be null!");
        Objects.requireNonNull(roadArea, "roadArea cannot be null!");

        territory.getNeighbourRoads().add(roadArea);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeNeighbourPath(@NonNull ITerritory territory, @NonNull IArea<Path> roadArea) {
        Objects.requireNonNull(territory, "territory cannot be null!");
        Objects.requireNonNull(roadArea, "roadArea cannot be null!");

        territory.getNeighbourRoads().remove(roadArea);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasResource(@NonNull ITerritory territory) {
        Objects.requireNonNull(territory, "territory cannot be null!");

        return territory.getResource().isPresent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull IResource getResource(@NonNull ITerritory territory) throws UndefinedTerritoryResourceException {
        Objects.requireNonNull(territory, "territory cannot be null!");

        return territory.getResource().orElseThrow(UndefinedTerritoryResourceException::new);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void moveThief(@NonNull ITerritory territory) {
        Objects.requireNonNull(territory, "territory cannot be null!");

        territories.values().stream().filter(ITerritory::hasThief).findFirst().ifPresent(from -> from.setThiefOccupation(false));
        territory.setThiefOccupation(true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull List<Map.Entry<IPlayer, IResource>> getLoot(int diceNumber) {
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
    private @NonNull Stream<Map.Entry<IPlayer, IResource>> extractLoots(@NonNull ITerritory territory) {
        Objects.requireNonNull(territory, "territory cannot be null!");

        return territory.getNeighbourBuildings().stream()
                .filter(area -> area.getConstruction().isPresent())
                .flatMap(area -> IntStream.range(0, area.getLootAmount())
                        .mapToObj(i -> Map.entry(area.getConstruction().get().getOwner(), getResource(territory))));
    }

}