package fr.univnantes.alma.commons.player;

import fr.univnantes.alma.commons.card.development.DevelopmentCardJSON;
import fr.univnantes.alma.commons.construction.ConstructionJSON;
import fr.univnantes.alma.commons.resource.ResourceJSON;
import org.springframework.lang.NonNull;

import java.util.*;

public class PlayerJSON {

    private final UUID uuid;
    private List<ConstructionJSON> constructions = new ArrayList<>();
    private List<ResourceJSON> resources = new ArrayList<>();
    private List<DevelopmentCardJSON> developmentCards = new ArrayList<>();
    private int victoryPoints;

    public PlayerJSON(@NonNull UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return uuid;
    }

    public List<ConstructionJSON> getConstructions() {
        return constructions;
    }

    public PlayerJSON constructions(@NonNull List<ConstructionJSON> constructions) {
        this.constructions = constructions;

        return this;
    }

    public List<ResourceJSON> getResources() {
        return resources;
    }

    public PlayerJSON resources(@NonNull List<ResourceJSON> resources) {
        this.resources = resources;

        return this;
    }

    public List<DevelopmentCardJSON> getDevelopmentCards() {
        return developmentCards;
    }

    public PlayerJSON developmentCards(@NonNull List<DevelopmentCardJSON> developmentCards) {
        this.developmentCards = developmentCards;

        return this;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

    public PlayerJSON victoryPoints(@NonNull int victoryPoints) {
        this.victoryPoints = victoryPoints;

        return this;
    }

}