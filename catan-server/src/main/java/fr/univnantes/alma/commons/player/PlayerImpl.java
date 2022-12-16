package fr.univnantes.alma.commons.player;

import fr.univnantes.alma.commons.resource.ResourceImpl;
import fr.univnantes.alma.commons.resource.type.*;
import fr.univnantes.alma.core.card.type.DevelopmentCard;
import fr.univnantes.alma.core.construction.Construction;
import fr.univnantes.alma.core.player.Player;
import fr.univnantes.alma.core.ressource.Resource;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.time.format.ResolverStyle;
import java.util.*;

public class PlayerImpl implements Player {

    /**
     * Fields
     */
    private final List<Construction> constructions = new ArrayList<>();
    private final List<Resource> resources = new ArrayList<>();
    private final List<DevelopmentCard> developmentCards = new ArrayList<>();
    private int victoryPoints;
    private Map<Resource,Integer> ruleTradeWithBank = new HashMap<>();
    private final Random random = new Random();

    /**
     * Create a new player
     */
    public PlayerImpl(){
        ruleTradeWithBank.put(new ClayResource(),4);
        ruleTradeWithBank.put(new OreResource(),4);
        ruleTradeWithBank.put(new WheatResource(),4);
        ruleTradeWithBank.put(new WoodResource(),4);
        ruleTradeWithBank.put(new WoolResource(),4);
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
        resources.stream()
                .filter(playerResource -> playerResource.isSimilar(resource) && playerResource.getAmount() >= resource.getAmount())
                        .forEach(playerResource -> {
                            playerResource.decreaseAmount(resource.getAmount());
                            if (playerResource.getAmount() == 0) {
                                resources.remove(resource);
                            }
                        });
    }

    /**
     * {@inheritDoc}
     */
    public Resource removeAllResource(@NonNull Resource resource){
        return resources.stream()
                .filter(playerResource -> playerResource.isSimilar(resource))
                .findFirst()
                .map(playerResource -> resources.remove(resources.indexOf(playerResource)))
                .orElse(null);
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void changeRuleTradeWithBank(@Nullable Resource resource, int ratio){
        if(resource == null) {
            ruleTradeWithBank.replaceAll((r, v) -> ratio);
        }
        else{
            ruleTradeWithBank.replace(resource,ratio);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Resource popRandomRessource(){
        int i = random.nextInt(resources.size());
        Resource stolenResource = resources.get(i).newResource();
        resources.remove(1);
        return stolenResource;
    }

}