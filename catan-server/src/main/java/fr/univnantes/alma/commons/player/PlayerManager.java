package fr.univnantes.alma.commons.player;

import fr.univnantes.alma.core.card.type.DevelopmentCard;
import fr.univnantes.alma.core.player.Player;
import fr.univnantes.alma.core.construction.Construction;
import fr.univnantes.alma.core.ressource.Resource;
import org.springframework.lang.NonNull;

import java.util.*;

public class PlayerManager {

    private final Map<UUID, Player> players = new HashMap<>();

    public Optional<Player> getPlayer(@NonNull UUID uuid) {
        return Optional.ofNullable(players.get(uuid));
    }

    public <T extends Construction> Optional<T> getConstruction(@NonNull Player player, Class<T> type) {
        return player.getConstructions().stream()
                .filter(construction -> construction.getClass().equals(type))
                .map(construction -> (T) construction)
                .findFirst();
    }

    public boolean canBuyDevelopmentCard(@NonNull Player player, @NonNull List<Resource> resources) {
        return player.getResources().containsAll(resources);
    }

    public boolean hasDevelopmentCard(@NonNull Player player, @NonNull DevelopmentCard developmentCard) {
        return true;
    }

    public DevelopmentCard pickDevelopmentCard(@NonNull Player player, @NonNull DevelopmentCard developmentCard) {
        return null;
    }
    public <T extends Construction> boolean canBuild(@NonNull Player player, Class<T> type, List<Resource> resources) {
        return getConstruction(player, type).isPresent();
    }

    public void giveResource(@NonNull Player player, @NonNull Resource... resources) {
        Arrays.stream(resources).forEach(player::addResource);
    }

    public void giveResource(@NonNull Player player, @NonNull List<Resource> resources) {
        giveResource(player, resources.toArray(new Resource[0]));
    }

    public void removeResource(@NonNull Player player, @NonNull Resource... resources) {
        Arrays.stream(resources).forEach(player::removeResource);
    }

    public void removeResource(@NonNull Player player, @NonNull List<Resource> resources) {
        removeResource(player, resources.toArray(new Resource[0]));
    }

    public <T extends Construction> void removeConstruction(@NonNull Player player, Class<T> type) {
        getConstruction(player, type).ifPresent(construction -> player.getConstructions().remove(construction));
    }

    public void loadPlayers() {
        players.values().forEach(player -> {
            //ReflectionUtils.getInstancesOf(Building.class, "fr.univnantes.alma.commons.construction.building.type")
              //      .forEach(building -> IntStream.range(0, building.getAmount()).forEach(i -> player.addConstruction(building).add(progress)));

        });
    }


}