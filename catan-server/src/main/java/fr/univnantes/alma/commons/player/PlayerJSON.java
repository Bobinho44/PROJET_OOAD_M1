package fr.univnantes.alma.commons.player;

import fr.univnantes.alma.core.construction.IConstructionJSON;
import fr.univnantes.alma.core.card.ICardJSON;
import fr.univnantes.alma.core.player.IPlayerJSON;
import fr.univnantes.alma.core.resource.IResourceJSON;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.*;

/**
 * Implementation of a player JSON information
 */
public class PlayerJSON implements IPlayerJSON {

    /**
     * Fields
     */
    private final UUID uuid;
    private List<IConstructionJSON> constructions = new ArrayList<>();
    private List<IResourceJSON> resources = new ArrayList<>();
    private List<ICardJSON> developmentCards = new ArrayList<>();
    private int victoryPoints;

    /**
     * Creates a new player json
     *
     * @param uuid the uuid
     */
    public PlayerJSON(@NonNull UUID uuid) {
        Objects.requireNonNull(uuid, "uuid cannot be null!");

        this.uuid = uuid;
    }

    /**
     * Gets the uuid
     *
     * @return the uuid
     */
    public @NonNull UUID getUUID() {
        return uuid;
    }

    /**
     * Gets the constructions
     *
     * @return the constructions
     */
    public @NonNull List<IConstructionJSON> getConstructions() {
        return constructions;
    }

    /**
     * Sets the constructions
     *
     * @param constructions the constructions
     * @return the player information
     */
    public @NonNull IPlayerJSON constructions(@NonNull List<IConstructionJSON> constructions) {
        Objects.requireNonNull(constructions, "constructions cannot be null!");

        this.constructions = constructions;

        return this;
    }

    /**
     * Gets the resources
     *
     * @return the resources
     */
    public @NonNull List<IResourceJSON> getResources() {
        return resources;
    }

    /**
     * Sets the resources
     *
     * @param resources the resources
     * @return the player information
     */
    public @NonNull IPlayerJSON resources(@NonNull List<IResourceJSON> resources) {
        Objects.requireNonNull(resources, "resources cannot be null!");

        this.resources = resources;

        return this;
    }

    /**
     * Gets the development cards
     *
     * @return the development cards
     */
    public @NonNull List<ICardJSON> getDevelopmentCards() {
        return developmentCards;
    }

    /**
     * Sets the development cards
     *
     * @param developmentCards the development cards
     * @return the player information
     */
    public @NonNull IPlayerJSON developmentCards(@NonNull List<ICardJSON> developmentCards) {
        Objects.requireNonNull(developmentCards, "developmentCards cannot be null!");

        this.developmentCards = developmentCards;

        return this;
    }

    /**
     * Gets the victory points
     *
     * @return the victory points
     */
    public int getVictoryPoints() {
        return victoryPoints;
    }

    /**
     * Sets the victory points
     *
     * @param victoryPoints the victory points
     * @return the player information
     */
    public @NonNull IPlayerJSON victoryPoints(int victoryPoints) {
        this.victoryPoints = victoryPoints;

        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(@Nullable Object o) {
        if (this == o) return true;
        if (!(o instanceof PlayerJSON playerJSON)) return false;
        return Objects.equals(uuid, playerJSON.uuid);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }

}