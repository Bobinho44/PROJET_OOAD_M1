package fr.univnantes.alma.commons.player;

import fr.univnantes.alma.commons.card.CardJSONImpl;
import fr.univnantes.alma.commons.construction.ConstructionJSONImpl;
import fr.univnantes.alma.commons.resource.ResourceJSONImpl;
import fr.univnantes.alma.core.card.CardJSON;
import fr.univnantes.alma.core.command.CommandJSON;
import fr.univnantes.alma.core.exception.EmptyCardDeckException;
import fr.univnantes.alma.core.exception.UnregisteredPlayerException;
import fr.univnantes.alma.core.construction.ConstructionJSON;
import fr.univnantes.alma.core.player.PlayerJSON;
import fr.univnantes.alma.core.resource.ResourceJSON;
import fr.univnantes.alma.core.card.type.DevelopmentCard;
import fr.univnantes.alma.core.player.Player;
import fr.univnantes.alma.core.construction.Construction;
import fr.univnantes.alma.core.player.PlayerManager;
import fr.univnantes.alma.core.resource.Resource;
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
                .map(player -> new PlayerJSONImpl(player.getUUID())
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
        Objects.requireNonNull(player, "player cannot be null!");

        return player.getConstructions().stream()
                .map(construction -> (ConstructionJSON) new ConstructionJSONImpl(construction.getUUID(), construction.getClass().getName()))
                .toList();
    }

    /**
     * Gets the resources information from the player
     *
     * @param player the player
     * @return the resources information from the player
     */
    private @NonNull List<ResourceJSON> getPlayerResourcesInformation(@NonNull Player player) {
        Objects.requireNonNull(player, "player cannot be null!");

        return player.getResources().stream()
                .map(resource -> (ResourceJSON) new ResourceJSONImpl(resource.getName(), resource.getAmount()))
                .toList();
    }

    /**
     * Gets the development cards information from the player
     *
     * @param player the player
     * @return the development cards information from the player
     */
    private @NonNull List<CardJSON> getPlayerDevelopmentCardInformation(@NonNull Player player) {
        Objects.requireNonNull(player, "player cannot be null!");

        return player.getDevelopmentCard().stream()
                .map(developmentCard -> (CardJSON) new CardJSONImpl(developmentCard.getUUID(), developmentCard.getClass().getName()))
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull Player getPlayer(@NonNull PlayerJSON playerJSON) throws UnregisteredPlayerException {
        Objects.requireNonNull(playerJSON, "playerJSON cannot be null!");

        return Optional.ofNullable(players.get(playerJSON.getUUID())).orElseThrow(UnregisteredPlayerException::new);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasPlayer(@NonNull PlayerJSON playerJSON) {
        Objects.requireNonNull(playerJSON, "playerJSON cannot be null!");

        return Optional.ofNullable(players.get(playerJSON.getUUID())).isPresent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void createPlayer(@NonNull PlayerJSON playerJSON) {
        Objects.requireNonNull(playerJSON, "playerJSON cannot be null!");

        Player player = new PlayerImpl(playerJSON.getUUID());

        players.put(player.getUUID(), player);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deletePlayer(@NonNull Player player) {
        Objects.requireNonNull(player, "player cannot be null!");

        players.remove(player.getUUID());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canPlay(@NonNull Player player) {
        Objects.requireNonNull(player, "player cannot be null!");

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
        Objects.requireNonNull(player, "player cannot be null!");
        Objects.requireNonNull(construction, "construction cannot be null!");

        return player.getConstructions().stream()
                        .anyMatch(playerConstruction -> playerConstruction.equals(construction));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addConstruction(@NonNull Player player, @NonNull Construction construction) {
        Objects.requireNonNull(player, "player cannot be null!");
        Objects.requireNonNull(construction, "construction cannot be null!");

        player.getConstructions().add(construction);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeConstruction(@NonNull Player player, @NonNull Construction construction) {
        Objects.requireNonNull(player, "player cannot be null!");
        Objects.requireNonNull(construction, "construction cannot be null!");

        player.getConstructions().remove(construction);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasResource(@NonNull Player player, @NonNull Resource resource) {
        Objects.requireNonNull(player, "player cannot be null!");
        Objects.requireNonNull(resource, "resource cannot be null!");

        return player.getResources().stream()
                .anyMatch(playerResource -> playerResource.equals(resource) && playerResource.getAmount() >= resource.getAmount());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasResources(@NonNull Player player, @NonNull List<Resource> resources) {
        Objects.requireNonNull(player, "player cannot be null!");
        Objects.requireNonNull(resources, "resources cannot be null!");

        return resources.stream().allMatch(resource -> hasResource(player, resource));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addResource(@NonNull Player player, @NonNull Resource resource) {
        Objects.requireNonNull(player, "player cannot be null!");
        Objects.requireNonNull(resource, "resource cannot be null!");

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
        Objects.requireNonNull(player, "player cannot be null!");
        Objects.requireNonNull(resources, "resources cannot be null!");

        resources.forEach(resource -> addResource(player, resource));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeResource(@NonNull Player player, @NonNull Resource resource) {
        Objects.requireNonNull(player, "player cannot be null!");
        Objects.requireNonNull(resource, "resource cannot be null!");

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
        Objects.requireNonNull(player, "player cannot be null!");
        Objects.requireNonNull(resources, "resources cannot be null!");

        resources.forEach(resource -> removeResource(player, resource));
    }

    public void pickAllResources(@NonNull Player player, @NonNull Resource resource) {
        Objects.requireNonNull(player, "player cannot be null!");
        Objects.requireNonNull(resource, "resource cannot be null!");

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
    public @NonNull DevelopmentCard getDevelopmentCard(@NonNull Player player) throws EmptyCardDeckException {
        Objects.requireNonNull(player, "player cannot be null!");

        return player.getDevelopmentCard().stream().findAny().orElseThrow(EmptyCardDeckException::new);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasDevelopmentCard(@NonNull Player player, @NonNull DevelopmentCard developmentCard) {
        Objects.requireNonNull(player, "player cannot be null!");
        Objects.requireNonNull(developmentCard, "developmentCard cannot be null!");

        return player.getDevelopmentCard().stream()
                    .anyMatch(development -> development.equals(developmentCard));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasDevelopmentCard(@NonNull Player player) {
        Objects.requireNonNull(player, "player cannot be null!");

        return !player.getDevelopmentCard().isEmpty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addDevelopmentCard(@NonNull Player player, @NonNull DevelopmentCard developmentCard) {
        Objects.requireNonNull(player, "player cannot be null!");
        Objects.requireNonNull(developmentCard, "developmentCard cannot be null!");

        player.getDevelopmentCard().add(developmentCard);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeDevelopmentCard(@NonNull Player player, @NonNull DevelopmentCard developmentCard) {
        Objects.requireNonNull(player, "player cannot be null!");
        Objects.requireNonNull(developmentCard, "developmentCard cannot be null!");

        player.getDevelopmentCard().remove(developmentCard);
    }

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    public @NonNull CommandJSON useDevelopmentCard(@NonNull Player player, @NonNull DevelopmentCard developmentCard) {
        Objects.requireNonNull(player, "player cannot be null!");
        Objects.requireNonNull(developmentCard, "developmentCard cannot be null!");

        removeDevelopmentCard(player, developmentCard);

        return developmentCard.useEffect(player);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getVictoryPoint(@NonNull Player player) {
        Objects.requireNonNull(player, "player cannot be null!");

        return player.getVictoryPoint();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addVictoryPoints(@NonNull Player player, int amount) {
        Objects.requireNonNull(player, "player cannot be null!");

        player.addVictoryPoints(amount);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeVictoryPoints(@NonNull Player player, int amount) {
        Objects.requireNonNull(player, "player cannot be null!");

        player.removeVictoryPoints(amount);
    }

}