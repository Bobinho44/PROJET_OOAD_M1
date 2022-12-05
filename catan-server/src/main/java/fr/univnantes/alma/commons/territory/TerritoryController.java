package fr.univnantes.alma.commons.territory;

import fr.univnantes.alma.commons.annotation.TerritoryAmount;
import fr.univnantes.alma.commons.token.TokenImpl;
import fr.univnantes.alma.commons.utils.collector.CollectorsUtils;
import fr.univnantes.alma.commons.utils.reflection.ReflectionUtils;
import fr.univnantes.alma.commons.utils.stream.IndexedStream;
import fr.univnantes.alma.core.player.Player;
import fr.univnantes.alma.core.ressource.Resource;
import org.atlanmod.commons.tuple.Pair;
import org.springframework.lang.NonNull;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TerritoryController {

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

    public Optional<Territory> getTerritory(@NonNull UUID uuid) {
        return Optional.ofNullable(territories.get(uuid));
    }

    private Map<UUID, Territory> createTerritories() {
        return ReflectionUtils.getClassesOf(Territory.class, "fr.univnantes.alma.commons.territory.type").stream()
                .map(territory -> IntStream.range(0, territory.getAnnotation(TerritoryAmount.class).value())
                        .mapToObj(i -> Map.entry(UUID.randomUUID(), ReflectionUtils.getInstancesOf(territory)))
                        .toList())
                .flatMap(Collection::stream)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private void affectTokens() {
        IndexedStream.from(territories.values().stream()
                        .filter(territory -> territory.getResource().isPresent())
                        .collect(CollectorsUtils.toShuffledList()))
                .forEach((territory, i) -> territory.setToken(TokenImpl.values()[i]));
    }

    public Optional<Territory> getThiefTerritory() {
        return territories.values().stream().filter(Territory::hasThief).findFirst();
    }

    public void moveThief(@NonNull Territory territory) {
        getThiefTerritory().ifPresent(from -> from.setThiefOccupation(false));
        territory.setThiefOccupation(true);
    }

    public List<Pair<Player, Resource>> getLoot(int score) {
        return territories.values().stream()
                .filter(territory -> territory.getToken().getNumber() == score)
                .filter(territory -> territory.getResource().isPresent())
                .filter(territory -> !territory.hasThief())
                .map(territory -> territory.getBuildableTown().stream()
                        .filter(area -> area.getConstruction().isPresent())
                        .map(area -> IntStream.range(0, area.getLootAmount())
                                .mapToObj(i -> Pair.of(area.getConstruction().get().getOwner(), territory.getResource().get()))
                                .toList())
                        .flatMap(Collection::stream)
                        .toList())
                .flatMap(Collection::stream)
                //.sorted() //TODO Trié les joueurs ?
                .toList();
    }
}