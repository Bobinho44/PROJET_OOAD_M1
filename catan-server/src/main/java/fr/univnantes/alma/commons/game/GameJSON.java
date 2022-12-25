package fr.univnantes.alma.commons.game;

import fr.univnantes.alma.commons.card.development.DevelopmentCardJSON;
import fr.univnantes.alma.commons.card.special.SpecialCardJSON;
import fr.univnantes.alma.commons.player.PlayerJSON;
import fr.univnantes.alma.commons.construction.constructableArea.ConstructableAreaJSON;
import fr.univnantes.alma.commons.resource.ResourceJSON;
import fr.univnantes.alma.commons.territory.TerritoryJSON;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class GameJSON {

    private final UUID uuid;
    private List<ResourceJSON> resources;
    private List<DevelopmentCardJSON> developmentCards;
    private List<SpecialCardJSON> specialCards;
    private List<TerritoryJSON> territories;
    private List<PlayerJSON> players;
    private List<ConstructableAreaJSON> constructableAreas;

    public GameJSON(@NonNull UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUUID() {
        return uuid;
    }

    public List<PlayerJSON> getPlayers() {
        return players;
    }

    public GameJSON players(@NonNull List<PlayerJSON> players) {
        this.players = players;

        return this;
    }

    public List<ResourceJSON> getResources() {
        return resources;
    }

    public GameJSON resources(@NonNull List<ResourceJSON> resources) {
        this.resources = resources;

        return this;
    }

    public List<DevelopmentCardJSON> getDevelopmentCards() {
        return developmentCards;
    }

    public GameJSON developmentCards(@NonNull List<DevelopmentCardJSON> developmentCards) {
        this.developmentCards = developmentCards;

        return this;
    }

    public List<SpecialCardJSON> getSpecialCards() {
        return specialCards;
    }

    public GameJSON SpecialCards(@NonNull List<SpecialCardJSON> specialCards) {
        this.specialCards = specialCards;

        return this;
    }

    public List<TerritoryJSON> getTerritories() {
        return territories;
    }

    public GameJSON territories(@NonNull List<TerritoryJSON> territories) {
        this.territories = territories;

        return this;
    }

    public List<ConstructableAreaJSON> getConstructableAreas() {
        return constructableAreas;
    }

    public GameJSON constructableAreas(@NonNull List<ConstructableAreaJSON> constructableAreas) {
        this.constructableAreas = constructableAreas;

        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GameJSON)) return false;

        return Objects.equals(uuid, ((GameJSON) o).getUUID());
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }

}