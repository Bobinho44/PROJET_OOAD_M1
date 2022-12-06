package fr.univnantes.alma.commons.territory;

import fr.univnantes.alma.commons.annotation.TerritoryAmount;
import fr.univnantes.alma.commons.token.TokenImpl;
import fr.univnantes.alma.commons.utils.collector.CollectorsUtils;
import fr.univnantes.alma.commons.utils.reflection.ReflectionUtils;
import fr.univnantes.alma.commons.utils.stream.IndexedStream;
import fr.univnantes.alma.core.player.Player;
import fr.univnantes.alma.core.ressource.Resource;
import fr.univnantes.alma.core.territory.Territory;
import fr.univnantes.alma.core.territory.TerritoryManager;
import org.springframework.lang.NonNull;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
                .map(territory -> IntStream.range(0, territory.getAnnotation(TerritoryAmount.class).value())
                        .mapToObj(i -> Map.entry(UUID.randomUUID(), ReflectionUtils.getInstancesOf(territory)))
                        .toList())
                .flatMap(Collection::stream)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        //TODO add constructableArea
    }

    /**
     * Affects the tokens
     */
    private void affectTokens() {
        IndexedStream.from(territories.values().stream()
                        .filter(Territory::hasResource)
                        .collect(CollectorsUtils.toShuffledList()))
                .forEach((territory, i) -> territory.setToken(TokenImpl.values()[i]));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull Optional<Territory> getTerritory(@NonNull UUID uuid) {
        return Optional.ofNullable(territories.get(uuid));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull Optional<Territory> getThiefTerritory() {
        return territories.values().stream().filter(Territory::hasThief).findFirst();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void moveThief(@NonNull Territory territory) {
        getThiefTerritory().ifPresent(from -> from.setThiefOccupation(false));
        territory.setThiefOccupation(true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull List<Map.Entry<Player, Resource>> getLoot(int number) {
        return territories.values().stream()
                .filter(territory -> territory.getToken().getNumber() == number)
                .filter(Territory::hasResource)
                .filter(territory -> !territory.hasThief())
                .map(territory -> territory.getNeighbourBuildings().stream()
                        .filter(area -> area.getConstruction().isPresent())
                        .map(area -> IntStream.range(0, area.getLootAmount())
                                .mapToObj(i -> Map.entry(area.getConstruction().get().getOwner(), territory.getResource().get()))
                                .toList())
                        .flatMap(Collection::stream)
                        .toList())
                .flatMap(Collection::stream)
                //.sorted() //TODO Trié les joueurs ?
                .toList();
    }

}