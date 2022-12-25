package fr.univnantes.alma.commons.game;

import fr.univnantes.alma.commons.card.CardController;
import fr.univnantes.alma.commons.card.development.DevelopmentCardJSON;
import fr.univnantes.alma.core.notification.NotificationJSON;
import fr.univnantes.alma.commons.notification.NotificationReplyJSON;
import fr.univnantes.alma.commons.player.PlayerJSON;
import fr.univnantes.alma.commons.command.CommandController;
import fr.univnantes.alma.commons.construction.ConstructionController;
import fr.univnantes.alma.commons.construction.ConstructionJSON;
import fr.univnantes.alma.commons.construction.constructableArea.ConstructableAreaJSON;
import fr.univnantes.alma.commons.construction.type.building.city.City;
import fr.univnantes.alma.commons.construction.type.building.colony.Colony;
import fr.univnantes.alma.commons.construction.type.road.RoadImpl;
import fr.univnantes.alma.commons.dice.DiceImpl;
import fr.univnantes.alma.commons.notification.NotificationNoReplyJSON;
import fr.univnantes.alma.commons.player.PlayerController;
import fr.univnantes.alma.commons.resource.ResourceController;
import fr.univnantes.alma.commons.resource.ResourceJSON;
import fr.univnantes.alma.commons.territory.TerritoryController;
import fr.univnantes.alma.commons.territory.TerritoryJSON;
import fr.univnantes.alma.commons.trade.TradeController;
import fr.univnantes.alma.commons.trade.TradeJSON;
import fr.univnantes.alma.core.card.CardManager;
import fr.univnantes.alma.core.card.type.DevelopmentCard;
import fr.univnantes.alma.core.command.CommandManager;
import fr.univnantes.alma.core.construction.ConstructionManager;
import fr.univnantes.alma.core.construction.constructableArea.ConstructableArea;
import fr.univnantes.alma.core.construction.type.Building;
import fr.univnantes.alma.core.construction.type.Road;
import fr.univnantes.alma.core.dice.Dice;
import fr.univnantes.alma.core.game.Game;
import fr.univnantes.alma.core.player.Player;
import fr.univnantes.alma.core.player.PlayerManager;
import fr.univnantes.alma.core.ressource.Resource;
import fr.univnantes.alma.core.ressource.ResourceManager;
import fr.univnantes.alma.core.territory.Territory;
import fr.univnantes.alma.core.territory.TerritoryManager;
import fr.univnantes.alma.core.trade.Trade;
import fr.univnantes.alma.core.trade.TradeManager;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.*;
import java.util.stream.Stream;

public class GameImpl implements Game {

    private final UUID uuid;
    private final TerritoryManager territoryManager = new TerritoryController();
    private final ConstructionManager constructionManager = new ConstructionController(territoryManager.getTerritories());
    private final PlayerManager playerManager = new PlayerController();
    private final CardManager cardManager = new CardController();
    private final ResourceManager resourceManager = new ResourceController();
    private final TradeManager tradeManager = new TradeController();
    private final CommandManager commandManager = new CommandController();
    private final Dice dice = new DiceImpl();

    /**
     * Creates a new game
     */
    public GameImpl(@NonNull UUID uuid) {
        this.uuid = uuid;

        registerNextPlayerCommand();
        registerGiveLootCommand();
        registerRollDiceCommand();
        registerFinishTourCommand();
        registerBuildRoadCommand();
        registerBuildColonyCommand();
        registerBuildCityCommand();
        registerBuyDevelopmentCardCommand();
        registerPlayDevelopmentCardCommand();
        registerUpdateVictoryPointCommand();
        registerPlaceTwoFreeRoadCommand();
        registerTakeTwoResourcesCommand();
        registerMoveThiefAndTakeCardCommand();
        registerStealResourceFromAllPlayersCommand();
        registerGiveResourcesCommand();
        registerProposeTradeCommand();
        registerAcceptTradeCommand();
        registerRefuseTradeCommand();
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
    public @NonNull GameJSON getGameInformation() {
        return new GameJSON(uuid)
                .players(playerManager.getPlayerInformation())
                .resources(resourceManager.getResourcesInformation())
                .developmentCards(cardManager.getDevelopmentCardsInformation())
                .territories(territoryManager.getTerritoriesInformation())
                .constructableAreas(constructionManager.getConstructableAreasInformation());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isFull() {
        return playerManager.getPlayerInformation().size() >= 2;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addPlayer(@NonNull PlayerJSON playerJSON) {
        if (playerManager.hasPlayer(playerJSON)) {
            return;
        }

        playerManager.createPlayer(playerJSON);

        if (isFull()) {
            executeCommand("nextPlayer", Collections.emptyList());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removePlayer(@NonNull PlayerJSON playerJSON) {
        if (!playerManager.hasPlayer(playerJSON)) {
            return;
        }

        Player player = playerManager.getPlayer(playerJSON);

        playerManager.deletePlayer(player);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canPlay(@NonNull PlayerJSON playerJSON) {
        if (!playerManager.hasPlayer(playerJSON)) {
            return false;
        }

        Player player = playerManager.getPlayer(playerJSON);

        return playerManager.canPlay(player);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull NotificationJSON executeCommand(@NonNull String name, @NonNull List<Object> parameters) {
        return commandManager.execute(name, parameters);
    }

    /**
     * Registers next player command
     */
    private void registerNextPlayerCommand() {
        commandManager.addCommand("nextPlayer", action -> {
            playerManager.nextPlayer();

            return NotificationNoReplyJSON.COMMAND_SUCCESS;
        });
    }

    /**
     * Registers give loot command
     */
    private void registerGiveLootCommand() {
        commandManager.addCommand("giveLoot", action -> {
            territoryManager.getLoot(dice.getScore()).forEach(loot -> {

                if (resourceManager.hasResource(loot.getValue())) {
                    playerManager.addResource(loot.getKey(), loot.getValue());
                    resourceManager.removeResource(loot.getValue());
                }
            });

            return NotificationNoReplyJSON.COMMAND_SUCCESS;
        });
    }

    /**
     * Registers roll dice command
     */
    private void registerRollDiceCommand() {
        commandManager.addCommand("rollDice", action -> {
            dice.roll();
            int score = dice.getScore();

            if (score == 7) {
                return new NotificationReplyJSON(Collections.singletonList("discardMoveThiefAndSteal"));
            }

            else {
                executeCommand("giveLoot", Collections.emptyList());
            }

            return NotificationNoReplyJSON.COMMAND_SUCCESS;
        });
    }

    /**
     * Registers finish tour command
     */
    private void registerFinishTourCommand() {
        commandManager.addCommand("finishTour", action -> {
            //TODO check victory point

            //Finishes the tour and choose the next player
            playerManager.nextPlayer();

            return NotificationNoReplyJSON.COMMAND_SUCCESS;
        });
    }

    /**
     * Registers build road command
     * <p>
     * Parameters : - a PlayerJSON
     *              - a ConstructableAreaJSON (type = road)
     *              - a ConstructionJSON (type = roadImpl)
     */
    private void registerBuildRoadCommand() {
        commandManager.addCommand("buildRoad", action -> {

            //Gets parameters
            PlayerJSON playerJSON = (PlayerJSON) action.getParameters().get(0);
            ConstructableAreaJSON constructableAreaJSON = (ConstructableAreaJSON) action.getParameters().get(1);
            ConstructionJSON constructionJSON = (ConstructionJSON) action.getParameters().get(2);

            //Checks if the player is valid
            if (!playerManager.hasPlayer(playerJSON)) {
                return NotificationNoReplyJSON.PLAYER_NOT_FOUND;
            }

            //Checks if the constructable area is valid
            if (!constructionManager.hasConstructableArea(constructableAreaJSON, Road.class)) {
                return NotificationNoReplyJSON.CONSTRUCTABLE_AREA_NOT_FOUND;
            }

            //Gets data from the server
            Player player = playerManager.getPlayer(playerJSON);

            //Checks if the construction is valid
            if (!constructionManager.hasConstruction(constructionJSON, RoadImpl.class, player)) {
                return NotificationNoReplyJSON.CONSTRUCTION_NOT_FOUND;
            }

            //Gets data from the server
            ConstructableArea<Road> constructableArea = constructionManager.getConstructableArea(constructableAreaJSON, Road.class);
            RoadImpl road = constructionManager.generateConstruction(constructionJSON, RoadImpl.class, player);
            List<Resource> resources = constructionManager.getConstructionCost(road);

            //Checks if the player has construction
            if (!playerManager.hasConstruction(player, road)) {
                return NotificationNoReplyJSON.PLAYER_HAS_NOT_CONSTRUCTION;
            }

            //Checks if the player has resources
            if (!playerManager.hasResources(player, resources)) {
                return NotificationNoReplyJSON.PLAYER_HAS_NOT_RESOURCE;
            }

            //Checks if the road is constructable
            if (!constructionManager.isConstructable(constructableArea, road)) {
                return NotificationNoReplyJSON.CONSTRUCTION_NOT_CONSTRUCTABLE;
            }

            //Constructs the road and make the player pay
            constructionManager.construct(constructableArea, road);
            playerManager.removeResources(player, resources);
            playerManager.removeConstruction(player, road);

            return NotificationNoReplyJSON.COMMAND_SUCCESS;
        });
    }

    /**
     * Registers build road command
     * <p>
     * Parameters : - a PlayerJSON
     *              - a ConstructableAreaJSON (type = building)
     *              - a ConstructionJSON (type = colony)
     */
    private void registerBuildColonyCommand() {
        commandManager.addCommand("buildColony", action -> {

            //Gets parameters
            PlayerJSON playerJSON = (PlayerJSON) action.getParameters().get(0);
            ConstructableAreaJSON constructableAreaJSON = (ConstructableAreaJSON) action.getParameters().get(1);
            ConstructionJSON constructionJSON = (ConstructionJSON) action.getParameters().get(2);

            //Checks if the player is valid
            if (!playerManager.hasPlayer(playerJSON)) {
                return NotificationNoReplyJSON.PLAYER_NOT_FOUND;
            }

            //Checks if the constructable area is valid
            if (!constructionManager.hasConstructableArea(constructableAreaJSON, Road.class)) {
                return NotificationNoReplyJSON.CONSTRUCTABLE_AREA_NOT_FOUND;
            }

            //Gets data from the server
            Player player = playerManager.getPlayer(playerJSON);

            //Checks if the construction is valid
            if (!constructionManager.hasConstruction(constructionJSON, Colony.class, player)) {
                return NotificationNoReplyJSON.CONSTRUCTION_NOT_CONSTRUCTABLE;
            }

            //Gets data from the server
            ConstructableArea<Building> constructableArea = constructionManager.getConstructableArea(constructableAreaJSON, Building.class);
            Colony colony = constructionManager.generateConstruction(constructionJSON, Colony.class, player);
            List<Resource> resources = constructionManager.getConstructionCost(colony);

            //Checks if the player has construction
            if (!playerManager.hasConstruction(player, colony)) {
                return NotificationNoReplyJSON.PLAYER_HAS_NOT_CONSTRUCTION;
            }

            //Checks if the player has resources
            if (!playerManager.hasResources(player, resources)) {
                return NotificationNoReplyJSON.PLAYER_HAS_NOT_RESOURCE;
            }

            //Checks if the colony is constructable
            if (!constructionManager.isConstructable(constructableArea, colony)) {
                return NotificationNoReplyJSON.CONSTRUCTION_NOT_CONSTRUCTABLE;
            }

            //Constructs the colony and make the player pay
            constructionManager.construct(constructableArea, colony);
            playerManager.removeResources(player, resources);
            playerManager.removeConstruction(player, colony);

            return NotificationNoReplyJSON.COMMAND_SUCCESS;
        });
    }

    /**
     * Registers build road command
     * <p>
     * Parameters : - a PlayerJSON
     *              - a ConstructableAreaJSON (type = building)
     *              - a ConstructionJSON (type = city)
     */
    private void registerBuildCityCommand() {
        commandManager.addCommand("buildCity", action -> {

            //Gets parameters
            PlayerJSON playerJSON = (PlayerJSON) action.getParameters().get(0);
            ConstructableAreaJSON constructableAreaJSON = (ConstructableAreaJSON) action.getParameters().get(1);
            ConstructionJSON constructionJSON = (ConstructionJSON) action.getParameters().get(2);

            //Checks if the player is valid
            if (!playerManager.hasPlayer(playerJSON)) {
                return NotificationNoReplyJSON.PLAYER_NOT_FOUND;
            }

            //Checks if the constructable area is valid
            if (!constructionManager.hasConstructableArea(constructableAreaJSON, Road.class)) {
                return NotificationNoReplyJSON.CONSTRUCTABLE_AREA_NOT_FOUND;
            }

            //Gets data from the server
            Player player = playerManager.getPlayer(playerJSON);

            //Checks if the construction is valid
            if (!constructionManager.hasConstruction(constructionJSON, City.class, player)) {
                return NotificationNoReplyJSON.CONSTRUCTION_NOT_CONSTRUCTABLE;
            }

            //Gets data from the server
            ConstructableArea<Building> constructableArea = constructionManager.getConstructableArea(constructableAreaJSON, Building.class);
            City city = constructionManager.generateConstruction(constructionJSON, City.class, player);
            List<Resource> resources = constructionManager.getConstructionCost(city);

            //Checks if the player has construction
            if (!playerManager.hasConstruction(player, city)) {
                return NotificationNoReplyJSON.PLAYER_HAS_NOT_CONSTRUCTION;
            }

            //Checks if the player has resources
            if (!playerManager.hasResources(player, resources)) {
                return NotificationNoReplyJSON.PLAYER_HAS_NOT_RESOURCE;
            }

            //Checks if the city is constructable
            if (!constructionManager.isConstructable(constructableArea, city)) {
                return NotificationNoReplyJSON.CONSTRUCTION_NOT_CONSTRUCTABLE;
            }

            //Constructs the city and make the player pay
            constructionManager.construct(constructableArea, city);
            playerManager.removeResources(player, resources);
            playerManager.removeConstruction(player, city);

            return NotificationNoReplyJSON.COMMAND_SUCCESS;
        });
    }

    /**
     * Registers build road command
     * <p>
     * Parameters : - a PlayerJSON
     */
    private void registerBuyDevelopmentCardCommand() {
        commandManager.addCommand("buyDevelopmentCard", action -> {

            //Gets parameters
            PlayerJSON playerJSON = (PlayerJSON) action.getParameters().get(0);

            //Checks if the player is valid
            if (!playerManager.hasPlayer(playerJSON)) {
                return NotificationNoReplyJSON.PLAYER_NOT_FOUND;
            }

            //Gets data from the server
            Player player = playerManager.getPlayer(playerJSON);
            List<Resource> resources = cardManager.getDevelopmentCardCost();
            DevelopmentCard developmentCard = cardManager.getDevelopmentCard();

            //Checks if the player has resources
            if (!playerManager.hasResources(player, resources)) {
                return NotificationNoReplyJSON.PLAYER_CAN_NOT_BUY_DEVELOPMENT_CARD;
            }

            //Checks if there is an available development card
            if (cardManager.hasDevelopmentCard()) {
                return NotificationNoReplyJSON.DEVELOPMENT_CARD_NOT_FOUND;
            }

            //Picks the development card and make the player pay
            cardManager.removeDevelopmentCard(developmentCard);
            playerManager.addDevelopmentCard(player, developmentCard);
            playerManager.removeResources(player, resources);

            return NotificationNoReplyJSON.COMMAND_SUCCESS;
        });
    }

    /**
     * Registers build road command
     * <p>
     * Parameters : - a PlayerJSON
     *              - a DevelopmentCardJSON
     */
    private void registerPlayDevelopmentCardCommand() {
        commandManager.addCommand("playDevelopmentCard", action -> {

            //Gets parameters
            PlayerJSON playerJSON = (PlayerJSON) action.getParameters().get(0);
            DevelopmentCardJSON developmentCardJSON = (DevelopmentCardJSON) action.getParameters().get(1);

            //Checks if the player is valid
            if (!playerManager.hasPlayer(playerJSON)) {
                return NotificationNoReplyJSON.PLAYER_NOT_FOUND;
            }

            //Gets data from the server
            Player player = playerManager.getPlayer(playerJSON);
            DevelopmentCard developmentCard = cardManager.generateDevelopmentCard(developmentCardJSON);

            //Checks if the player has the development card
            if (playerManager.hasDevelopmentCard(player, developmentCard)) {
                return NotificationNoReplyJSON.PLAYER_HAS_NOT_DEVELOPMENT_CARD;
            }

            //Uses the development card
            playerManager.removeDevelopmentCard(player, developmentCard);
            return playerManager.useDevelopmentCard(player, developmentCard, commandManager);
        });
    }

    /**
     * Registers update victory point command
     * <p>
     * Parameters : - a PlayerJSON
     *              - an int
     */
    private void registerUpdateVictoryPointCommand() {
        commandManager.addCommand("updateVictoryPoint", action -> {

            //Gets parameters
            PlayerJSON playerJSON = (PlayerJSON) action.getParameters().get(0);
            int amount = (int) action.getParameters().get(1);

            //Checks if the player is valid
            if (playerManager.hasPlayer(playerJSON)) {
                return NotificationNoReplyJSON.PLAYER_NOT_FOUND;
            }

            //Gets data from the server
            Player player = playerManager.getPlayer(playerJSON);

            //Updates victory point of the player
            playerManager.addVictoryPoints(player, amount);

            return NotificationNoReplyJSON.COMMAND_SUCCESS;
        });
    }

    /**
     * Registers place two free road command
     * <p>
     * Parameters : - a PlayerJSON
     *              - a ConstructableAreaJSON (type = road)
     *              - a ConstructionJSON (type = roadImpl)
     *              - a ConstructableAreaJSON (type = road)
     *              - a ConstructionJSON (type = roadImpl)
     */
    private void registerPlaceTwoFreeRoadCommand() {
        commandManager.addCommand("placeTwoFreeRoad", action -> {

            //Gets parameters
            PlayerJSON playerJSON = (PlayerJSON) action.getParameters().get(0);

            return Stream.of(1, 3).map(i -> {
                ConstructableAreaJSON constructableAreaJSON = (ConstructableAreaJSON) action.getParameters().get(i);
                ConstructionJSON constructionJSON = (ConstructionJSON) action.getParameters().get(i + 1);

                //Checks if the player is valid
                if (!playerManager.hasPlayer(playerJSON)) {
                    return NotificationNoReplyJSON.PLAYER_NOT_FOUND;
                }

                //Checks if the constructable area is valid
                if (!constructionManager.hasConstructableArea(constructableAreaJSON, Road.class)) {
                    return NotificationNoReplyJSON.CONSTRUCTABLE_AREA_NOT_FOUND;
                }

                //Gets data from the server
                Player player = playerManager.getPlayer(playerJSON);

                //Checks if the construction is valid
                if (!constructionManager.hasConstruction(constructionJSON, RoadImpl.class, player)) {
                    return NotificationNoReplyJSON.CONSTRUCTION_NOT_CONSTRUCTABLE;
                }

                //Gets data from the server
                ConstructableArea<Road> constructableArea = constructionManager.getConstructableArea(constructableAreaJSON, Road.class);
                RoadImpl road = constructionManager.generateConstruction(constructionJSON, RoadImpl.class, player);

                //Checks if the road is constructable
                if (!constructionManager.isConstructable(constructableArea, road)) {
                    return NotificationNoReplyJSON.CONSTRUCTION_NOT_CONSTRUCTABLE;
                }

                //Constructs the road and make the player pay
                constructionManager.construct(constructableArea, road);
                playerManager.removeConstruction(player, road);

                return NotificationNoReplyJSON.COMMAND_SUCCESS;
            })
                    .filter(notificationNoReplyJSON -> notificationNoReplyJSON != NotificationNoReplyJSON.COMMAND_SUCCESS)
                    .findFirst()
                    .orElse(NotificationNoReplyJSON.COMMAND_SUCCESS);
        });
    }

    /**
     * Registers build road command
     * <p>
     * Parameters : - a PlayerJSON
     *              - a ResourceJSON (amount = 1)
     *              - a ResourceJSON (amount = 1)
     */
    private void registerTakeTwoResourcesCommand() {
        commandManager.addCommand("takeTwoResources", action -> {

            //Gets parameters
            PlayerJSON playerJSON = (PlayerJSON) action.getParameters().get(0);

            return Stream.of(1, 2).map(i -> {
                ResourceJSON resourceJSON = (ResourceJSON) action.getParameters().get(i);

                //Checks if the player is valid
                if (!playerManager.hasPlayer(playerJSON)) {
                    return NotificationNoReplyJSON.PLAYER_NOT_FOUND;
                }

                //Gets data from the server
                Player player = playerManager.getPlayer(playerJSON);
                Resource resource = resourceManager.generateResource(resourceJSON);

                //Checks if there is enough available resource
                if (!resourceManager.hasResource(resource)) {
                    return NotificationNoReplyJSON.RESOURCE_NOT_FOUND;
                }

                //Picks the resource to the player
                resourceManager.removeResource(resource);
                playerManager.addResource(player, resource);

                return NotificationNoReplyJSON.COMMAND_SUCCESS;
            })
                    .filter(notificationNoReplyJSON -> notificationNoReplyJSON != NotificationNoReplyJSON.COMMAND_SUCCESS)
                    .findFirst()
                    .orElse(NotificationNoReplyJSON.COMMAND_SUCCESS);
        });
    }

    /**
     * Registers move thief and take card command
     * <p>
     * Parameters : - a PlayerJSON
     *              - a TerritoryJSON (new thief territory)
     *              - a PlayerJSON (victim)
     */
    private void registerMoveThiefAndTakeCardCommand() {
        commandManager.addCommand("moveThiefAndTakeCard", action -> {

            //Gets parameters
            PlayerJSON playerJSON = (PlayerJSON) action.getParameters().get(0);
            TerritoryJSON territoryJSON = (TerritoryJSON) action.getParameters().get(1);
            PlayerJSON victimJSON = (PlayerJSON) action.getParameters().get(2);

            //Checks if the player is valid
            if (!playerManager.hasPlayer(playerJSON)) {
                return NotificationNoReplyJSON.PLAYER_NOT_FOUND;
            }

            //Checks if the territory is valid
            if (!territoryManager.hasTerritory(territoryJSON)) {
                return NotificationNoReplyJSON.TERRITORY_NOT_FOUND;
            }

            //Checks if the victim is valid
            if (!playerManager.hasPlayer(victimJSON)) {
                return NotificationNoReplyJSON.PLAYER_NOT_FOUND;
            }

            //Gets data from the server
            Player player = playerManager.getPlayer(playerJSON);
            Territory territory = territoryManager.getTerritory(territoryJSON);
            Player victim = playerManager.getPlayer(victimJSON);

            //Checks if the victim has available development card
            if (!playerManager.hasDevelopmentCard(victim)) {
                return NotificationNoReplyJSON.PLAYER_NOT_FOUND;
            }

            //Gets data from the server
            DevelopmentCard developmentCard = playerManager.getDevelopmentCard(victim);

            //Moves the thief and make player take a card from the victim
            territoryManager.moveThief(territory);
            playerManager.addDevelopmentCard(player, developmentCard);
            playerManager.removeDevelopmentCard(victim, developmentCard);

            return NotificationNoReplyJSON.COMMAND_SUCCESS;
        });
    }

    /**
     * Registers steal resource from all players command
     * <p>
     * Parameters : - a PlayerJSON
     *              - a ResourceJSON (amount = 1)
     */
    private void registerStealResourceFromAllPlayersCommand() {
        commandManager.addCommand("stealResourceFromAllPlayers", action -> {

            //Gets parameters
            PlayerJSON playerJSON = (PlayerJSON) action.getParameters().get(0);
            ResourceJSON resourceJSON = (ResourceJSON) action.getParameters().get(1);

            //Checks if the player is valid
            if (!playerManager.hasPlayer(playerJSON)) {
                return NotificationNoReplyJSON.PLAYER_NOT_FOUND;
            }

            //Gets data from the server
            Player player = playerManager.getPlayer(playerJSON);
            Resource resource = resourceManager.generateResource(resourceJSON);

            //Makes player pick all resources of the selected type from the other players
            playerManager.pickAllResources(player, resource);

            return NotificationNoReplyJSON.COMMAND_SUCCESS;
        });
    }

    /**
     * Registers give resources command
     * <p>
     * Parameters : - a PlayerJSON
     *              - a ResourceJSON
     */
    private void registerGiveResourcesCommand() {
        commandManager.addCommand("giveResources", action -> {

            //Gets parameters
            PlayerJSON playerJSON = (PlayerJSON) action.getParameters().get(0);
            ResourceJSON resourceJSON = (ResourceJSON) action.getParameters().get(1);

            //Checks if the player is valid
            if (!playerManager.hasPlayer(playerJSON)) {
                return NotificationNoReplyJSON.PLAYER_NOT_FOUND;
            }

            //Gets data from the server
            Player player = playerManager.getPlayer(playerJSON);
            Resource resource = resourceManager.generateResource(resourceJSON);

            //Give resource to the player
            playerManager.addResource(player, resource);

            return NotificationNoReplyJSON.COMMAND_SUCCESS;
        });
    }

    /**
     * Registers propose trade command
     * Parameters : - a TradeJSON
     */
    private void registerProposeTradeCommand() {
        commandManager.addCommand("proposeTrade", action -> {

            //Gets parameters
            TradeJSON tradeJSON = (TradeJSON) action.getParameters().get(0);

            //Checks if the sender is valid
            if (!playerManager.hasPlayer(tradeJSON.getSender())) {
                return NotificationNoReplyJSON.PLAYER_NOT_FOUND;
            }

            //Checks if the receiver is valid
            if (!playerManager.hasPlayer(tradeJSON.getReceiver())) {
                return NotificationNoReplyJSON.PLAYER_NOT_FOUND;
            }

            //Gets data from the server
            UUID uuid = tradeJSON.getUUID();
            Player sender = playerManager.getPlayer(tradeJSON.getSender());
            Player receiver = playerManager.getPlayer(tradeJSON.getReceiver());
            List<Resource> offer = resourceManager.generateResources(tradeJSON.getOffer());
            List<Resource> request = resourceManager.generateResources(tradeJSON.getRequest());

            //Checks if the sender has resources of offer
            if (!playerManager.hasResources(sender, offer)) {
                return NotificationNoReplyJSON.PLAYER_HAS_NOT_RESOURCE;
            }

            //Checks if the receiver has resources of request
            if (tradeJSON.hasReceiver() && !playerManager.hasResources(receiver, request)) {
                return NotificationNoReplyJSON.PLAYER_HAS_NOT_RESOURCE;
            }

            //Checks if the bank has resources of request
            if (!tradeJSON.hasReceiver() && resourceManager.hasResources(request)) {
                return NotificationNoReplyJSON.RESOURCE_NOT_FOUND;
            }

            //Creates a new trade
            tradeManager.createTrade(uuid, sender, receiver, offer, request);

            return new NotificationReplyJSON(List.of(receiver, "RespondTrade", tradeJSON));
        });
    }

    /**
     * Registers accept trade command
     * Parameters : - a TradeJSON
     */
    private void registerAcceptTradeCommand() {
        commandManager.addCommand("acceptTrade", action -> {

            //Gets parameters
            TradeJSON tradeJSON = (TradeJSON) action.getParameters().get(0);

            //Checks if the trade is valid
            if (!tradeManager.hasTrade(tradeJSON)) {
                return NotificationNoReplyJSON.TRADE_NOT_FOUND;
            }

            //Checks if the sender is valid
            if (!playerManager.hasPlayer(tradeJSON.getSender())) {
                return NotificationNoReplyJSON.PLAYER_NOT_FOUND;
            }

            //Checks if the receiver is valid
            if (!playerManager.hasPlayer(tradeJSON.getReceiver())) {
                return NotificationNoReplyJSON.PLAYER_NOT_FOUND;
            }

            //Gets data from the server
            Trade trade = tradeManager.getTrade(tradeJSON);
            Player sender = tradeManager.getSender(trade);
            Player receiver = tradeManager.getReceiver(trade);
            List<Resource> offer = tradeManager.getOffer(trade);
            List<Resource> request = tradeManager.getRequest(trade);

            //Checks if the sender has resources of offer
            if (!playerManager.hasResources(sender, offer)) {
                return NotificationNoReplyJSON.PLAYER_HAS_NOT_RESOURCE;
            }

            //Checks if the receiver has resources of request
            if (tradeManager.hasReceiver(trade) && !playerManager.hasResources(receiver, request)) {
                return NotificationNoReplyJSON.PLAYER_HAS_NOT_RESOURCE;
            }

            //Checks if the bank has resources of request
            if (!tradeManager.hasReceiver(trade) && !resourceManager.hasResources(request)) {
                return NotificationNoReplyJSON.RESOURCE_NOT_FOUND;
            }

            //Exchanges resources for the sender
            playerManager.removeResources(sender, offer);
            playerManager.addResources(sender, request);

            //Exchanges resources for the receiver
            if (tradeManager.hasReceiver(trade)) {
                playerManager.removeResources(receiver, request);
                playerManager.addResources(receiver, offer);
            }

            //Exchanges resources for the bank
            else {
                resourceManager.removeResources(request);
                resourceManager.addResources(offer);
            }

            return NotificationNoReplyJSON.COMMAND_SUCCESS;
        });
    }

    /**
     * Registers refuse trade command
     * Parameters : - a TradeJSON
     */
    private void registerRefuseTradeCommand() {
        commandManager.addCommand("refuseTrade", action -> {

            //Gets parameters
            TradeJSON tradeJSON = (TradeJSON) action.getParameters().get(0);

            //Checks if the trade is valid
            if (!tradeManager.hasTrade(tradeJSON)) {
                return NotificationNoReplyJSON.TRADE_NOT_FOUND;
            }

            //Gets data from the server
            Trade trade = tradeManager.getTrade(tradeJSON);

            //Deletes the trade
            tradeManager.deleteTrade(trade);

            return NotificationNoReplyJSON.COMMAND_SUCCESS;
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(@Nullable Object o) {
        if (this == o) return true;
        if (!(o instanceof GameImpl)) return false;

        return Objects.equals(uuid, ((GameImpl) o).getUUID());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return uuid.hashCode();
    }

}