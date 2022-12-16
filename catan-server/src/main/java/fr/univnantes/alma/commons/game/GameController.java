package fr.univnantes.alma.commons.game;

import fr.univnantes.alma.commons.card.CardController;
import fr.univnantes.alma.commons.card.development.progress.ProgressCard;
import fr.univnantes.alma.commons.construction.ConstructionController;
import fr.univnantes.alma.commons.construction.type.road.RoadImpl;
import fr.univnantes.alma.commons.resource.ResourceImpl;
import fr.univnantes.alma.commons.trade.TradeController;
import fr.univnantes.alma.core.construction.type.Building;
import fr.univnantes.alma.commons.dice.DiceImpl;
import fr.univnantes.alma.commons.player.PlayerController;
import fr.univnantes.alma.core.construction.type.Road;
import fr.univnantes.alma.commons.resource.ResourceController;
import fr.univnantes.alma.core.ressource.Resource;
import fr.univnantes.alma.core.territory.Territory;
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
import fr.univnantes.alma.core.player.PlayerManager;
import fr.univnantes.alma.core.ressource.ResourceManager;
import fr.univnantes.alma.core.territory.TerritoryManager;
import fr.univnantes.alma.core.trade.Trade;
import fr.univnantes.alma.core.trade.TradeManager;
import jdk.jshell.spi.ExecutionControl;
import org.springframework.lang.NonNull;

import javax.annotation.Nullable;

public class GameController implements GameManager {

    private final TerritoryManager territoryManager = new TerritoryController();
    private final ConstructionManager constructionManager = new ConstructionController(territoryManager.getTerritories());
    private final PlayerManager playerManager = new PlayerController();
    private final CardManager cardManager = new CardController();
    private final ResourceManager resourceManager = new ResourceController();
    private final TradeManager tradeManager = new TradeController();

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
    public void moveThief(@NonNull Territory territory) {
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
        if(buildableBuildingArea.hasDock())
            player.changeRuleTradeWithBank(buildableBuildingArea.getDock().get().getResource(), buildableBuildingArea.getDock().get().getRatio());
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
        developmentCard.useEffect(this,player);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void tradeWithPlayer(@NonNull Trade trade) {
        tradeManager.addTrade(trade);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void tradeWithBank(@NonNull Trade trade) {
        if (!playerManager.hasResources(trade.getSender(), trade.getOffer())) {
            return;
        }

        if (!resourceManager.hasResources(trade.getRequest())) {
            return;
        }

        resourceManager.addResources(trade.getOffer());
        resourceManager.removeResources(trade.getRequest());
        playerManager.addResources(trade.getSender(), trade.getRequest());
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

    /**
     * {@inheritDoc}
     */
    @Override
    public @Nullable Resource takeResourcesAllPlayer(@NonNull Player player, @NonNull Resource resource){
        Resource resourceGiven = resource.newResource();
        resourceGiven.amount(0);
        Resource resourceTmp;
        for (Player p: playerManager.getAllPlayers()) {
            if(p != player) {
                resourceTmp = p.removeAllResource(resource);
                if (resourceTmp != null) {
                    resource.increaseAmount(resourceTmp.getAmount());
                }
            }
        }
        if(resourceGiven.getAmount() != 0)
            return resourceGiven;
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Player pickOtherPlayer(){
        //TODO: renvoie un autre joueur choisi par le joueur
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Resource pickResource(){
        //TODO: renvoie une ressource choisi par le joueur
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Resource pickResourceBank(){
        //TODO: renvoie une ressource qui est dans la bank choisi par le joueur
        return null;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Territory pickTerritory(){
        //TODO: renvoie un territoire choisi par le joueur
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public ConstructableArea<Road> pickConstructableRoadArea(){
        //TODO: renvoie un contructableArea de route
        return null;
    }

}