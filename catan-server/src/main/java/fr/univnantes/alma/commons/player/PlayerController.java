package fr.univnantes.alma.commons.player;

import fr.univnantes.alma.core.card.type.DevelopmentCard;
import fr.univnantes.alma.core.player.Player;
import fr.univnantes.alma.core.construction.Construction;
import fr.univnantes.alma.core.player.PlayerManager;
import fr.univnantes.alma.core.ressource.Resource;
import org.springframework.lang.NonNull;

import java.util.*;

public class PlayerController implements PlayerManager {

    /**
     * Fields
     */
    private final Map<UUID, Player> players = new HashMap<>();

    /**
     * Creates a new player manager
     */
    public PlayerController() {
        //Initialize
        players.values().forEach(player -> {
            //ReflectionUtils.getInstancesOf(Building.class, "fr.univnantes.alma.commons.construction.building.type")
            //      .forEach(building -> IntStream.range(0, building.getAmount()).forEach(i -> player.addConstruction(building).add(progress)));

        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull Optional<Player> getPlayer(@NonNull UUID uuid) {
        return Optional.ofNullable(players.get(uuid));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addPlayer(@NonNull Player player) {
        players.put(UUID.randomUUID(), player);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removePlayer(@NonNull Player player) {
        players.entrySet().stream()
                .filter(registeredPlayer -> registeredPlayer.getValue().equals(player))
                .findFirst()
                .ifPresent(players::remove);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasConstruction(@NonNull Player player, @NonNull Construction construction) {
        return player.getConstructions().stream()
                .anyMatch(playerConstruction -> playerConstruction.equals(construction));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canConstruct(@NonNull Player player, @NonNull Construction construction, @NonNull List<Resource> resources) {
        return hasConstruction(player, construction) && hasResources(player, resources);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addConstruction(@NonNull Player player, @NonNull Construction construction) {
       player.addConstruction(construction);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeConstruction(@NonNull Player player, @NonNull Construction construction) {
        player.removeConstruction(construction);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasResource(@NonNull Player player, @NonNull Resource resource) {
        return player.getResources().stream()
                .anyMatch(playerResource -> playerResource.isSimilar(resource) && playerResource.getAmount() >= resource.getAmount());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasResources(@NonNull Player player, @NonNull List<Resource> resources) {
        return resources.stream().allMatch(resource -> hasResource(player, resource));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addResource(@NonNull Player player, @NonNull Resource resource) {
        player.addResource(resource);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addResources(@NonNull Player player, @NonNull List<Resource> resources) {
        resources.forEach(player::addResource);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeResource(@NonNull Player player, @NonNull Resource resource) {
        player.removeResource(resource);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeResources(@NonNull Player player, @NonNull List<Resource> resources) {
        resources.forEach(player::removeResource);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasDevelopmentCard(@NonNull Player player, @NonNull DevelopmentCard developmentCard) {
        return player.getDevelopmentCard().stream()
                .anyMatch(development -> development.equals(developmentCard));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canBuyDevelopmentCard(@NonNull Player player, @NonNull List<Resource> resources) {
        return resources.stream()
                .allMatch(resource -> player.getResources().stream()
                        .anyMatch(playerResource -> playerResource.isSimilar(resource)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addDevelopmentCard(@NonNull Player player, @NonNull DevelopmentCard developmentCard) {
        player.addDevelopmentCard(developmentCard);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeDevelopmentCard(@NonNull Player player, @NonNull DevelopmentCard developmentCard) {
        player.removeDevelopmentCard(developmentCard);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getsVictoryPoint(@NonNull Player player) {
        return player.getsVictoryPoint();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addVictoryPoints(@NonNull Player player, int amount) {
        player.addVictoryPoints(amount);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeVictoryPoints(@NonNull Player player, int amount) {
        player.removeVictoryPoints(amount);
    }

}