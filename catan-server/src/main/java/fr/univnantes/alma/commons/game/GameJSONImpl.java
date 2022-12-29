package fr.univnantes.alma.commons.game;

import fr.univnantes.alma.core.card.CardJSON;
import fr.univnantes.alma.core.game.GameJSON;
import fr.univnantes.alma.core.player.PlayerJSON;
import fr.univnantes.alma.core.construction.constructableArea.AreaJSON;
import fr.univnantes.alma.core.resource.ResourceJSON;
import fr.univnantes.alma.core.territory.TerritoryJSON;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Implementation of a game JSON information
 */
public class GameJSONImpl implements GameJSON {

    /**
     * Fields
     */
    private final UUID uuid;
    private List<ResourceJSON> resources;
    private List<CardJSON> developmentCards;
    private List<CardJSON> specialCards;
    private List<TerritoryJSON> territories;
    private List<PlayerJSON> players;
    private List<AreaJSON> areas;

    /**
     * Creates a new game json
     *
     * @param uuid the uuid
     */
    public GameJSONImpl(@NonNull UUID uuid) {
        Objects.requireNonNull(uuid, "uuid cannot be null!");

        this.uuid = uuid;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull UUID getUUID() {
        return uuid;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull List<PlayerJSON> getPlayers() {
        return players;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull GameJSON players(@NonNull List<PlayerJSON> players) {
        Objects.requireNonNull(players, "players cannot be null!");

        this.players = players;

        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull List<ResourceJSON> getResources() {
        return resources;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull GameJSON resources(@NonNull List<ResourceJSON> resources) {
        Objects.requireNonNull(resources, "resources cannot be null!");

        this.resources = resources;

        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull List<CardJSON> getDevelopmentCards() {
        return developmentCards;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull GameJSON developmentCards(@NonNull List<CardJSON> developmentCards) {
        Objects.requireNonNull(developmentCards, "developmentCards cannot be null!");

        this.developmentCards = developmentCards;

        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull List<CardJSON> getSpecialCards() {
        return specialCards;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull GameJSON specialCards(@NonNull List<CardJSON> specialCards) {
        Objects.requireNonNull(specialCards, "specialCards cannot be null!");

        this.specialCards = specialCards;

        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull List<TerritoryJSON> getTerritories() {
        return territories;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull GameJSON territories(@NonNull List<TerritoryJSON> territories) {
        Objects.requireNonNull(territories, "territories cannot be null!");

        this.territories = territories;

        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull List<AreaJSON> getAreas() {
        return areas;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull GameJSON areas(@NonNull List<AreaJSON> areas) {
        Objects.requireNonNull(areas, "areas cannot be null!");

        this.areas = areas;

        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(@Nullable Object o) {
        if (this == o) return true;
        if (!(o instanceof GameJSONImpl gameJSON)) return false;
        return Objects.equals(uuid, gameJSON.uuid);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }

}