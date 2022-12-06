package fr.univnantes.alma.commons.player;

import fr.univnantes.alma.core.card.type.DevelopmentCard;
import fr.univnantes.alma.core.construction.Construction;
import fr.univnantes.alma.core.player.Player;
import fr.univnantes.alma.core.ressource.Resource;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;

public class PlayerImpl implements Player {

    /**
     * Fields
     */
    private final List<Construction> constructions = new ArrayList<>();
    private final List<Resource> resources = new ArrayList<>();
    private final List<DevelopmentCard> developmentCards = new ArrayList<>();
    private int victoryPoints;

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
    public void addConstruction(@NonNull Construction construction) {
        constructions.add(construction);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeConstruction(@NonNull Construction construction) {
        constructions.remove(construction);
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
    public void addResource(@NonNull Resource resource) {
        resources.add(resource);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeResource(@NonNull Resource resource) {
        resources.remove(resource);
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
    public void addDevelopmentCard(@NonNull DevelopmentCard developmentCard) {
        developmentCards.add(developmentCard);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeDevelopmentCard(@NonNull DevelopmentCard developmentCard) {
        developmentCards.remove(developmentCard);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getsVictoryPoint() {
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

}