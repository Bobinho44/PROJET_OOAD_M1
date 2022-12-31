package fr.univnantes.alma.commons.player;

import fr.univnantes.alma.commons.card.CardJSON;
import fr.univnantes.alma.commons.construction.ConstructionJSON;
import fr.univnantes.alma.commons.resource.ResourceJSON;
import fr.univnantes.alma.core.card.ICardJSON;
import fr.univnantes.alma.core.command.ICommandJSON;
import fr.univnantes.alma.core.construction.IConstructionJSON;
import fr.univnantes.alma.core.exception.EmptyCardDeckException;
import fr.univnantes.alma.core.exception.UnregisteredPlayerException;
import fr.univnantes.alma.core.card.type.DevelopmentCard;
import fr.univnantes.alma.core.player.IPlayer;
import fr.univnantes.alma.core.construction.IConstruction;
import fr.univnantes.alma.core.player.IPlayerJSON;
import fr.univnantes.alma.core.player.IPlayerManager;
import fr.univnantes.alma.core.resource.IResource;
import fr.univnantes.alma.core.resource.IResourceJSON;
import org.springframework.lang.NonNull;

import java.util.*;

/**
 * Implementation of a player manager
 */
public class PlayerManager implements IPlayerManager {

    /**
     * Fields
     */
    private final Map<UUID, IPlayer> players = new HashMap<>();
    private int actualPlayer = -1;

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull List<IPlayerJSON> getPlayerInformation() {

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
    private @NonNull List<IConstructionJSON> getPlayerConstructionsInformation(@NonNull IPlayer player) {
        Objects.requireNonNull(player, "player cannot be null!");

        return player.getConstructions().stream()
                .map(construction -> (IConstructionJSON) new ConstructionJSON(construction.getUUID(), construction.getClass().getName()))
                .toList();
    }

    /**
     * Gets the resources information from the player
     *
     * @param player the player
     * @return the resources information from the player
     */
    private @NonNull List<IResourceJSON> getPlayerResourcesInformation(@NonNull IPlayer player) {
        Objects.requireNonNull(player, "player cannot be null!");

        return player.getResources().stream()
                .map(resource -> (IResourceJSON) new ResourceJSON(resource.getName(), resource.getAmount()))
                .toList();
    }

    /**
     * Gets the development cards information from the player
     *
     * @param player the player
     * @return the development cards information from the player
     */
    private @NonNull List<ICardJSON> getPlayerDevelopmentCardInformation(@NonNull IPlayer player) {
        Objects.requireNonNull(player, "player cannot be null!");

        return player.getDevelopmentCard().stream()
                .map(developmentCard -> (ICardJSON) new CardJSON(developmentCard.getUUID(), developmentCard.getClass().getName()))
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull IPlayer getPlayer(@NonNull IPlayerJSON playerJSON) throws UnregisteredPlayerException {
        Objects.requireNonNull(playerJSON, "playerJSON cannot be null!");

        return Optional.ofNullable(players.get(playerJSON.getUUID())).orElseThrow(UnregisteredPlayerException::new);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasPlayer(@NonNull IPlayerJSON playerJSON) {
        Objects.requireNonNull(playerJSON, "playerJSON cannot be null!");

        return Optional.ofNullable(players.get(playerJSON.getUUID())).isPresent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void createPlayer(@NonNull IPlayerJSON playerJSON) {
        Objects.requireNonNull(playerJSON, "playerJSON cannot be null!");

        IPlayer player = new Player(playerJSON.getUUID());

        players.put(player.getUUID(), player);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deletePlayer(@NonNull IPlayer player) {
        Objects.requireNonNull(player, "player cannot be null!");

        players.remove(player.getUUID());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canPlay(@NonNull IPlayer player) {
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
    private @NonNull IPlayer getActualPlayer() {
        return players.values().stream().toList().get(actualPlayer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasConstruction(@NonNull IPlayer player, @NonNull IConstruction construction) {
        Objects.requireNonNull(player, "player cannot be null!");
        Objects.requireNonNull(construction, "construction cannot be null!");

        return player.getConstructions().stream()
                        .anyMatch(playerConstruction -> playerConstruction.equals(construction));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addConstruction(@NonNull IPlayer player, @NonNull IConstruction construction) {
        Objects.requireNonNull(player, "player cannot be null!");
        Objects.requireNonNull(construction, "construction cannot be null!");

        player.getConstructions().add(construction);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeConstruction(@NonNull IPlayer player, @NonNull IConstruction construction) {
        Objects.requireNonNull(player, "player cannot be null!");
        Objects.requireNonNull(construction, "construction cannot be null!");

        player.getConstructions().remove(construction);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasResource(@NonNull IPlayer player, @NonNull IResource resource) {
        Objects.requireNonNull(player, "player cannot be null!");
        Objects.requireNonNull(resource, "resource cannot be null!");

        return player.getResources().stream()
                .anyMatch(playerResource -> playerResource.equals(resource) && playerResource.getAmount() >= resource.getAmount());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasResources(@NonNull IPlayer player, @NonNull List<IResource> resources) {
        Objects.requireNonNull(player, "player cannot be null!");
        Objects.requireNonNull(resources, "resources cannot be null!");

        return resources.stream().allMatch(resource -> hasResource(player, resource));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addResource(@NonNull IPlayer player, @NonNull IResource resource) {
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
    public void addResources(@NonNull IPlayer player, @NonNull List<IResource> resources) {
        Objects.requireNonNull(player, "player cannot be null!");
        Objects.requireNonNull(resources, "resources cannot be null!");

        resources.forEach(resource -> addResource(player, resource));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeResource(@NonNull IPlayer player, @NonNull IResource resource) {
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
    public void removeResources(@NonNull IPlayer player, @NonNull List<IResource> resources) {
        Objects.requireNonNull(player, "player cannot be null!");
        Objects.requireNonNull(resources, "resources cannot be null!");

        resources.forEach(resource -> removeResource(player, resource));
    }

    public void pickAllResources(@NonNull IPlayer player, @NonNull IResource resource) {
        Objects.requireNonNull(player, "player cannot be null!");
        Objects.requireNonNull(resource, "resource cannot be null!");

        players.values().stream()
                .filter(otherPlayer -> !otherPlayer.equals(player))
                .forEach(otherPlayer -> {
                    List<IResource> resources = otherPlayer.getResources().stream()
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
    public @NonNull DevelopmentCard getDevelopmentCard(@NonNull IPlayer player) throws EmptyCardDeckException {
        Objects.requireNonNull(player, "player cannot be null!");

        return player.getDevelopmentCard().stream().findAny().orElseThrow(EmptyCardDeckException::new);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasDevelopmentCard(@NonNull IPlayer player, @NonNull DevelopmentCard developmentCard) {
        Objects.requireNonNull(player, "player cannot be null!");
        Objects.requireNonNull(developmentCard, "developmentCard cannot be null!");

        return player.getDevelopmentCard().stream()
                    .anyMatch(development -> development.equals(developmentCard));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasDevelopmentCard(@NonNull IPlayer player) {
        Objects.requireNonNull(player, "player cannot be null!");

        return !player.getDevelopmentCard().isEmpty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addDevelopmentCard(@NonNull IPlayer player, @NonNull DevelopmentCard developmentCard) {
        Objects.requireNonNull(player, "player cannot be null!");
        Objects.requireNonNull(developmentCard, "developmentCard cannot be null!");

        player.getDevelopmentCard().add(developmentCard);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeDevelopmentCard(@NonNull IPlayer player, @NonNull DevelopmentCard developmentCard) {
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
    public @NonNull ICommandJSON useDevelopmentCard(@NonNull IPlayer player, @NonNull DevelopmentCard developmentCard) {
        Objects.requireNonNull(player, "player cannot be null!");
        Objects.requireNonNull(developmentCard, "developmentCard cannot be null!");

        removeDevelopmentCard(player, developmentCard);

        return developmentCard.useEffect(player);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getVictoryPoint(@NonNull IPlayer player) {
        Objects.requireNonNull(player, "player cannot be null!");

        return player.getVictoryPoint();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addVictoryPoints(@NonNull IPlayer player, int amount) {
        Objects.requireNonNull(player, "player cannot be null!");

        player.addVictoryPoints(amount);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeVictoryPoints(@NonNull IPlayer player, int amount) {
        Objects.requireNonNull(player, "player cannot be null!");

        player.removeVictoryPoints(amount);
    }

}