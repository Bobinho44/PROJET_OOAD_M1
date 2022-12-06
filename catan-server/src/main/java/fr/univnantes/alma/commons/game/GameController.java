package fr.univnantes.alma.commons.game;

import fr.univnantes.alma.commons.card.CardController;
import fr.univnantes.alma.commons.card.development.progress.ProgressCard;
import fr.univnantes.alma.commons.construction.ConstructionController;
import fr.univnantes.alma.commons.construction.type.road.RoadImpl;
import fr.univnantes.alma.core.construction.type.Building;
import fr.univnantes.alma.commons.dice.DiceImpl;
import fr.univnantes.alma.commons.player.PlayerController;
import fr.univnantes.alma.core.construction.type.Road;
import fr.univnantes.alma.commons.resource.ResourceController;
import fr.univnantes.alma.commons.territory.TerritoryImpl;
import fr.univnantes.alma.commons.territory.TerritoryController;
import fr.univnantes.alma.commons.construction.type.building.city.City;
import fr.univnantes.alma.commons.construction.type.building.colony.Colony;
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
    private final PlayerController playerManager = new PlayerController();
    private final CardManager cardManager = new CardController();
    private final ResourceManager resourceManager = new ResourceController();

    /**
     * Fields
     */
    private final Dice dice = new DiceImpl();

    /**
     * {@inheritDoc}
     */
    @Override
    public void build() {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int rollDice() {
        dice.roll();

        return dice.getScore();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void moveThief(@NonNull TerritoryImpl territory) {
        territoryManager.moveThief(territory);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void buildRoad(@NonNull Player player, @NonNull ConstructableArea<Road> buildableRoadArea) {
        Road road = constructionManager.getConstruction(player, RoadImpl.class);

        if (playerManager.canConstruct(player, road, constructionManager.getConstructionCost(RoadImpl.class))) {
            return;
        }

        if (!constructionManager.isConstructable(buildableRoadArea, new RoadImpl(player))) {
            return;
        }

        constructionManager.construct(buildableRoadArea, road);
        playerManager.removeResources(player, constructionManager.getConstructionCost(RoadImpl.class));
        playerManager.removeConstruction(player, road);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void buildColony(@NonNull Player player, @NonNull ConstructableArea<Building> buildableBuildingArea) {
        Colony colony = constructionManager.getConstruction(player, Colony.class);

        if (playerManager.canConstruct(player, colony, constructionManager.getConstructionCost(Colony.class))) {
            return;
        }

        if (!constructionManager.isConstructable(buildableBuildingArea, new Colony(player))) {
            return;
        }

        constructionManager.construct(buildableBuildingArea, colony);
        playerManager.removeResources(player, constructionManager.getConstructionCost(Colony.class));
        playerManager.removeConstruction(player, colony);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void buildCity(@NonNull Player player, @NonNull ConstructableArea<Building> buildableBuildingArea) {
        City city = constructionManager.getConstruction(player, City.class);

        if (playerManager.canConstruct(player, city, constructionManager.getConstructionCost(City.class))) {
            return;
        }

        if (!constructionManager.isConstructable(buildableBuildingArea, new City(player))) {
            return;
        }

        constructionManager.construct(buildableBuildingArea, city);
        playerManager.removeResources(player, constructionManager.getConstructionCost(Colony.class));
        playerManager.removeConstruction(player, city);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void buyDevelopmentCard(@NonNull Player player) {
        if (!playerManager.canBuyDevelopmentCard(player, cardManager.getDevelopmentCardCost(ProgressCard.class))) {
            return;
        }

        if (cardManager.canPickDevelopmentCard(ProgressCard.class)) {
            return;
        }

        playerManager.removeResources(player, cardManager.getDevelopmentCardCost(ProgressCard.class));
        playerManager.addDevelopmentCard(player, cardManager.pickDevelopmentCard(ProgressCard.class));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void playDevelopmentCard(@NonNull Player player, @NonNull DevelopmentCard developmentCard) {
        if (!playerManager.hasDevelopmentCard(player, developmentCard)) {
            return;
        }

        playerManager.removeDevelopmentCard(player, developmentCard);
        developmentCard.useEffect(player);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void tradeWithPlayer(UUID proposal) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void tradeWithBank(UUID proposal) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void giveLoot() {
        territoryManager.getLoot(dice.getScore()).forEach(loot -> {

            if (!resourceManager.canPickResource(loot.getValue(), 1)) {
                return;
            }

            playerManager.addResource(loot.getKey(), loot.getValue());
            resourceManager.removeResource(loot.getValue(), 1);
        });
    }

}