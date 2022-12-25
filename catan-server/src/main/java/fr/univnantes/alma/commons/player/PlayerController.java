package fr.univnantes.alma.commons.player;

import fr.univnantes.alma.commons.card.development.DevelopmentCardJSON;
import fr.univnantes.alma.core.notification.NotificationJSON;
import fr.univnantes.alma.commons.construction.ConstructionJSON;
import fr.univnantes.alma.commons.resource.ResourceJSON;
import fr.univnantes.alma.core.card.type.DevelopmentCard;
import fr.univnantes.alma.core.command.CommandManager;
import fr.univnantes.alma.core.player.Player;
import fr.univnantes.alma.core.construction.Construction;
import fr.univnantes.alma.core.player.PlayerManager;
import fr.univnantes.alma.core.ressource.Resource;
import org.springframework.lang.NonNull;

import java.util.*;

/**
 * Implementation of a player manager
 */
public class PlayerController implements PlayerManager {

    /**
     * Fields
     */
    private final Map<UUID, Player> players = new HashMap<>();
    private int actualPlayer = -1;

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull List<PlayerJSON> getPlayerInformation() {

        return players.values().stream()
                .map(player -> new PlayerJSON(player.getUUID())
                        .constructions(getPlayerConstructionsInformation(player))
                        .resources(getPlayerResourcesInformation(player))
                        .developmentCards(getPlayerDevelopmentCardInformation(player))
                        .victoryPoints(getVictoryPoint(player)))
                .toList();
    }

    /**
     * Gets the constructions information from the player
     *
     * @param player the player
     * @return the constructions information from the player
     */
    private @NonNull List<ConstructionJSON> getPlayerConstructionsInformation(@NonNull Player player) {
        return player.getConstructions().stream()
                .map(construction -> new ConstructionJSON(construction.getUUID(), construction.getClass().getName()))
                .toList();
    }

    /**
     * Gets the resources information from the player
     *
     * @param player the player
     * @return the resources information from the player
     */
    private @NonNull List<ResourceJSON> getPlayerResourcesInformation(@NonNull Player player) {
        return player.getResources().stream()
                .map(resource -> new ResourceJSON(resource.getName(), resource.getAmount()))
                .toList();
    }

    /**
     * Gets the development cards information from the player
     *
     * @param player the player
     * @return the development cards information from the player
     */
    private @NonNull List<DevelopmentCardJSON> getPlayerDevelopmentCardInformation(@NonNull Player player) {
        return player.getDevelopmentCard().stream()
                .map(developmentCard -> new DevelopmentCardJSON(developmentCard.getUUID(), developmentCard.getClass().getName()))
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull Player getPlayer(@NonNull PlayerJSON playerJSON) throws RuntimeException {
        return Optional.ofNullable(players.get(playerJSON.getUuid())).orElseThrow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasPlayer(@NonNull PlayerJSON playerJSON) {
        return Optional.ofNullable(players.get(playerJSON.getUuid())).isPresent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void createPlayer(@NonNull PlayerJSON playerJSON) {
        Player player = new PlayerImpl(playerJSON.getUuid());

        players.put(player.getUUID(), player);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deletePlayer(@NonNull Player player) {
        players.remove(player.getUUID());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canPlay(@NonNull Player player) {
        return actualPlayer != -1 && getActualPlayer().equals(player);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void nextPlayer() {
        actualPlayer = (actualPlayer + 1) % players.size();
    }

    /**
     * Gets the actual player
     *
     * @return the actual player
     */
    private @NonNull Player getActualPlayer() {
        return players.values().stream().toList().get(actualPlayer);
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
    public void addConstruction(@NonNull Player player, @NonNull Construction construction) {
        player.getConstructions().add(construction);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeConstruction(@NonNull Player player, @NonNull Construction construction) {
        player.getConstructions().remove(construction);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasResource(@NonNull Player player, @NonNull Resource resource) {
        return player.getResources().stream()
                .anyMatch(playerResource -> playerResource.equals(resource) && playerResource.getAmount() >= resource.getAmount());
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
        player.getResources().stream()
                .filter(playerResource -> playerResource.equals(resource))
                .findFirst()
                .ifPresentOrElse(
                        playerResource -> playerResource.increaseAmount(resource.getAmount()),
                        () -> player.getResources().add(resource)
                );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addResources(@NonNull Player player, @NonNull List<Resource> resources) {
        resources.forEach(resource -> addResource(player, resource));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeResource(@NonNull Player player, @NonNull Resource resource) {
        player.getResources().stream()
                .filter(playerResource -> playerResource.equals(resource) && playerResource.getAmount() >= resource.getAmount())
                .findFirst()
                .ifPresent(playerResource -> playerResource.decreaseAmount(resource.getAmount()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeResources(@NonNull Player player, @NonNull List<Resource> resources) {
        resources.forEach(resource -> removeResource(player, resource));
    }

    public void pickAllResources(@NonNull Player player, @NonNull Resource resource) {
        players.values().stream()
                .filter(otherPlayer -> !otherPlayer.equals(player))
                .forEach(otherPlayer -> {
                    List<Resource> resources = otherPlayer.getResources().stream()
                            .filter(playerResource -> playerResource.equals(resource))
                            .toList();

                    addResources(player, resources);
                    removeResources(otherPlayer, resources);
                });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull DevelopmentCard getDevelopmentCard(@NonNull Player player) throws RuntimeException {
        return player.getDevelopmentCard().stream().findAny().orElseThrow();
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
    public boolean hasDevelopmentCard(@NonNull Player player) {
        return !player.getDevelopmentCard().isEmpty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addDevelopmentCard(@NonNull Player player, @NonNull DevelopmentCard developmentCard) {
        player.getDevelopmentCard().add(developmentCard);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeDevelopmentCard(@NonNull Player player, @NonNull DevelopmentCard developmentCard) {
        player.getDevelopmentCard().remove(developmentCard);
    }

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    public @NonNull NotificationJSON useDevelopmentCard(@NonNull Player player, @NonNull DevelopmentCard developmentCard, @NonNull CommandManager commandManager) {
        removeDevelopmentCard(player, developmentCard);
        return developmentCard.useEffect(commandManager, player);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getVictoryPoint(@NonNull Player player) {
        return player.getVictoryPoint();
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