package fr.univnantes.alma.commons.game;

import fr.univnantes.alma.core.card.ICardJSON;
import fr.univnantes.alma.core.game.IGameJSON;
import fr.univnantes.alma.core.player.IPlayerJSON;
import fr.univnantes.alma.core.construction.constructableArea.IAreaJSON;
import fr.univnantes.alma.core.resource.IResourceJSON;
import fr.univnantes.alma.core.territory.ITerritoryJSON;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Implementation of a game JSON information
 */
public class GameJSON implements IGameJSON {

    /**
     * Fields
     */
    private final UUID uuid;
    private List<IResourceJSON> resources;
    private List<ICardJSON> developmentCards;
    private List<ICardJSON> specialCards;
    private List<ITerritoryJSON> territories;
    private List<IPlayerJSON> players;
    private List<IAreaJSON> areas;

    /**
     * Creates a new game json
     *
     * @param uuid the uuid
     */
    public GameJSON(@NonNull UUID uuid) {
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
    public @NonNull List<IPlayerJSON> getPlayers() {
        return players;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull IGameJSON players(@NonNull List<IPlayerJSON> players) {
        Objects.requireNonNull(players, "players cannot be null!");

        this.players = players;

        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull List<IResourceJSON> getResources() {
        return resources;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull IGameJSON resources(@NonNull List<IResourceJSON> resources) {
        Objects.requireNonNull(resources, "resources cannot be null!");

        this.resources = resources;

        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull List<ICardJSON> getDevelopmentCards() {
        return developmentCards;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull IGameJSON developmentCards(@NonNull List<ICardJSON> developmentCards) {
        Objects.requireNonNull(developmentCards, "developmentCards cannot be null!");

        this.developmentCards = developmentCards;

        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull List<ICardJSON> getSpecialCards() {
        return specialCards;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull IGameJSON specialCards(@NonNull List<ICardJSON> specialCards) {
        Objects.requireNonNull(specialCards, "specialCards cannot be null!");

        this.specialCards = specialCards;

        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull List<ITerritoryJSON> getTerritories() {
        return territories;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull IGameJSON territories(@NonNull List<ITerritoryJSON> territories) {
        Objects.requireNonNull(territories, "territories cannot be null!");

        this.territories = territories;

        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull List<IAreaJSON> getAreas() {
        return areas;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull IGameJSON areas(@NonNull List<IAreaJSON> areas) {
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
        if (!(o instanceof GameJSON gameJSON)) return false;
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