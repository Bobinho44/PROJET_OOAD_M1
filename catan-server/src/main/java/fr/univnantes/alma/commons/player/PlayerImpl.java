package fr.univnantes.alma.commons.player;

import fr.univnantes.alma.core.card.type.DevelopmentCard;
import fr.univnantes.alma.core.construction.Construction;
import fr.univnantes.alma.core.player.Player;
import fr.univnantes.alma.core.resource.Resource;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Implementation of a player
 */
public class PlayerImpl implements Player {

    /**
     * Fields
     */
    private final UUID uuid;
    private final List<Construction> constructions = new ArrayList<>();
    private final List<Resource> resources = new ArrayList<>();
    private final List<DevelopmentCard> developmentCards = new ArrayList<>();
    private int victoryPoints;

    /**
     * Creates a new player
     *
     * @param uuid the uuid
     */
    public PlayerImpl(@NonNull UUID uuid) {
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
    public @NonNull List<Construction> getConstructions() {
        return constructions;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull List<Resource> getResources() {
        return resources;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull List<DevelopmentCard> getDevelopmentCard() {
        return developmentCards;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getVictoryPoint() {
        return victoryPoints;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addVictoryPoints(int amount) {
        victoryPoints += amount;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeVictoryPoints(int amount) {
        victoryPoints -= amount;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(@Nullable Object o) {
        if (this == o) return true;
        if (!(o instanceof PlayerImpl player)) return false;
        return Objects.equals(uuid, player.uuid);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }

}