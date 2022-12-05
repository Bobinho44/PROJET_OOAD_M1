package fr.univnantes.alma.commons.game;

import fr.univnantes.alma.commons.card.CardController;
import fr.univnantes.alma.commons.construction.ConstructionController;
import fr.univnantes.alma.commons.construction.building.Building;
import fr.univnantes.alma.commons.dice.DiceImpl;
import fr.univnantes.alma.commons.player.PlayerManager;
import fr.univnantes.alma.commons.construction.road.Road;
import fr.univnantes.alma.commons.resource.ResourceController;
import fr.univnantes.alma.commons.territory.Territory;
import fr.univnantes.alma.commons.territory.TerritoryController;
import fr.univnantes.alma.commons.construction.building.type.city.City;
import fr.univnantes.alma.commons.construction.building.type.colony.Colony;
import fr.univnantes.alma.core.card.CardManager;
import fr.univnantes.alma.core.card.type.DevelopmentCard;
import fr.univnantes.alma.core.construction.ConstructionManager;
import fr.univnantes.alma.core.construction.constructableArea.ConstructableArea;
import fr.univnantes.alma.core.dice.Dice;
import fr.univnantes.alma.core.game.GameManager;
import fr.univnantes.alma.core.player.Player;
import fr.univnantes.alma.core.ressource.ResourceManager;
import org.springframework.lang.NonNull;

import java.util.*;

public class GameController implements GameManager {

    private final TerritoryController territoryManager = new TerritoryController();
    private final ConstructionManager constructionManager = new ConstructionController();
    private final PlayerManager playerManager = new PlayerManager();
    private final CardManager cardManager = new CardController();
    private final ResourceManager resourceManager = new ResourceController();

    /**
     * Fields
     */
    private final Dice dice = new DiceImpl();

    @Override
    public void build() {

    }

    @Override
    public int rollDice() {
        dice.roll();

        return dice.getScore();
    }

    @Override
    public void moveThief(@NonNull Territory territory) {
        territoryManager.moveThief(territory);
    }

    @Override
    public void buildRoad(@NonNull Player player, @NonNull ConstructableArea<Road> buildableRoadArea) {
        if (playerManager.canBuild(player, Road.class, constructionManager.getConstructionCost(Road.class))) {
            return;
        }

        if (!constructionManager.isConstructable(buildableRoadArea, new Road(player))) {
            return;
        }

        constructionManager.construct(buildableRoadArea, new Road(player));
        playerManager.removeResource(player, constructionManager.getConstructionCost(Road.class));
        playerManager.removeConstruction(player, Road.class);
    }

    @Override
    public void buildColony(@NonNull Player player, @NonNull ConstructableArea<Building> buildableBuildingArea) {
        if (playerManager.canBuild(player, Colony.class, constructionManager.getConstructionCost(Colony.class))) {
            return;
        }

        if (!constructionManager.isConstructable(buildableBuildingArea, new Colony(player))) {
            return;
        }

        constructionManager.construct(buildableBuildingArea, new Colony(player));
        playerManager.removeResource(player, constructionManager.getConstructionCost(Colony.class));
        playerManager.removeConstruction(player, Colony.class);
    }

    @Override
    public void buildCity(@NonNull Player player, @NonNull ConstructableArea<Building> buildableBuildingArea) {
        if (playerManager.canBuild(player, City.class, constructionManager.getConstructionCost(City.class))) {
            return;
        }

        if (!constructionManager.isConstructable(buildableBuildingArea, new City(player))) {
            return;
        }

        constructionManager.construct(buildableBuildingArea, new City(player));
        playerManager.removeResource(player, constructionManager.getConstructionCost(Colony.class));
        playerManager.removeConstruction(player, City.class);
    }

    @Override
    public void buyDevelopmentCard(@NonNull Player player) {
        if (!playerManager.canBuyDevelopmentCard(player, cardManager.getDevelopmentCardCost())) {
            return;
        }

        if (cardManager.canPickDevelopmentCard()) {
            return;
        }

        playerManager.removeResource(player, cardManager.getDevelopmentCardCost());
        //playerManager.giveDevelopmentCard(player, cardManager.pickDevelopmentCard());
    }

    @Override
    public void playDevelopmentCard(@NonNull Player player, @NonNull DevelopmentCard developmentCard) {
        if (!playerManager.hasDevelopmentCard(player, developmentCard)) {
            return;
        }

        playerManager.pickDevelopmentCard(player, developmentCard).useEffect(player);
    }

    @Override
    public void tradeWithPlayer(UUID proposal) {

    }

    @Override
    public void tradeWithBank(UUID proposal) {

    }

    @Override
    public void giveLoot() {
        territoryManager.getLoot(dice.getScore()).forEach(loot -> {

            if (!resourceManager.canPickResource(loot.right, 1)) {
                return;
            }

            playerManager.giveResource(loot.left, loot.right);
            resourceManager.removeResource(loot.right, 1);
        });
    }

}