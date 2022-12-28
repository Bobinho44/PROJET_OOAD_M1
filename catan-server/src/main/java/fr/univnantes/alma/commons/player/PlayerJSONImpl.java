package fr.univnantes.alma.commons.player;

import fr.univnantes.alma.core.construction.ConstructionJSON;
import fr.univnantes.alma.core.card.CardJSON;
import fr.univnantes.alma.core.player.PlayerJSON;
import fr.univnantes.alma.core.resource.ResourceJSON;
import org.springframework.lang.NonNull;

import java.util.*;

/**
 * Implementation of a player JSON information
 */
public class PlayerJSONImpl implements PlayerJSON {

    /**
     * Fields
     */
    private final UUID uuid;
    private List<ConstructionJSON> constructions = new ArrayList<>();
    private List<ResourceJSON> resources = new ArrayList<>();
    private List<CardJSON> developmentCards = new ArrayList<>();
    private int victoryPoints;

    /**
     * Creates a new player json
     *
     * @param uuid the uuid
     */
    public PlayerJSONImpl(@NonNull UUID uuid) {
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
    public @NonNull List<ConstructionJSON> getConstructions() {
        return constructions;
    }

    /**
     * Sets the constructions
     *
     * @param constructions the constructions
     * @return the player information
     */
    public @NonNull PlayerJSON constructions(@NonNull List<ConstructionJSON> constructions) {
        Objects.requireNonNull(constructions, "constructions cannot be null!");

        this.constructions = constructions;

        return this;
    }

    /**
     * Gets the resources
     *
     * @return the resources
     */
    public @NonNull List<ResourceJSON> getResources() {
        return resources;
    }

    /**
     * Sets the resources
     *
     * @param resources the resources
     * @return the player information
     */
    public @NonNull PlayerJSON resources(@NonNull List<ResourceJSON> resources) {
        Objects.requireNonNull(resources, "resources cannot be null!");

        this.resources = resources;

        return this;
    }

    /**
     * Gets the development cards
     *
     * @return the development cards
     */
    public @NonNull List<CardJSON> getDevelopmentCards() {
        return developmentCards;
    }

    /**
     * Sets the development cards
     *
     * @param developmentCards the development cards
     * @return the player information
     */
    public @NonNull PlayerJSON developmentCards(@NonNull List<CardJSON> developmentCards) {
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
    public @NonNull PlayerJSON victoryPoints(int victoryPoints) {
        this.victoryPoints = victoryPoints;

        return this;
    }

}