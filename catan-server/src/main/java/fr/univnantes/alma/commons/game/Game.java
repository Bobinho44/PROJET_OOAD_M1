package fr.univnantes.alma.commons.game;

import fr.univnantes.alma.commons.card.CardManager;
import fr.univnantes.alma.core.card.ICardJSON;
import fr.univnantes.alma.core.card.ICardManager;
import fr.univnantes.alma.core.command.ICommand;
import fr.univnantes.alma.core.command.ICommandJSON;
import fr.univnantes.alma.core.command.ICommandManager;
import fr.univnantes.alma.core.construction.IConstructionManager;
import fr.univnantes.alma.core.construction.type.Path;
import fr.univnantes.alma.core.dice.IDice;
import fr.univnantes.alma.core.game.IGameJSON;
import fr.univnantes.alma.core.notification.INotificationJSON;
import fr.univnantes.alma.commons.notification.NotificationReplyJSON;
import fr.univnantes.alma.core.player.IPlayerJSON;
import fr.univnantes.alma.commons.command.CommandManager;
import fr.univnantes.alma.commons.construction.ConstructionManager;
import fr.univnantes.alma.core.construction.IConstructionJSON;
import fr.univnantes.alma.core.construction.constructableArea.IAreaJSON;
import fr.univnantes.alma.commons.construction.type.building.city.City;
import fr.univnantes.alma.commons.construction.type.building.colony.Colony;
import fr.univnantes.alma.commons.construction.type.road.Road;
import fr.univnantes.alma.commons.dice.Dice;
import fr.univnantes.alma.commons.notification.NotificationNoReplyJSON;
import fr.univnantes.alma.commons.player.PlayerManager;
import fr.univnantes.alma.commons.resource.ResourceManager;
import fr.univnantes.alma.core.player.IPlayerManager;
import fr.univnantes.alma.core.resource.IResourceJSON;
import fr.univnantes.alma.commons.territory.TerritoryManager;
import fr.univnantes.alma.core.resource.IResourceManager;
import fr.univnantes.alma.core.territory.ITerritoryJSON;
import fr.univnantes.alma.commons.trade.TradeManager;
import fr.univnantes.alma.core.territory.ITerritoryManager;
import fr.univnantes.alma.core.trade.ITradeJSON;
import fr.univnantes.alma.core.card.type.DevelopmentCard;
import fr.univnantes.alma.core.construction.constructableArea.IArea;
import fr.univnantes.alma.core.construction.type.Building;
import fr.univnantes.alma.core.game.IGame;
import fr.univnantes.alma.core.player.IPlayer;
import fr.univnantes.alma.core.resource.IResource;
import fr.univnantes.alma.core.territory.ITerritory;
import fr.univnantes.alma.core.trade.ITrade;
import fr.univnantes.alma.core.trade.ITradeManager;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.*;
import java.util.stream.Stream;

/**
 * Implementation of a game
 */
public class Game implements IGame {

    /**
     * Fields
     */
    private final UUID uuid;
    private final ITerritoryManager territoryManager = new TerritoryManager();
    private final IConstructionManager constructionManager = new ConstructionManager(territoryManager);
    private final IPlayerManager playerManager = new PlayerManager();
    private final ICardManager cardManager = new CardManager();
    private final IResourceManager resourceManager = new ResourceManager();
    private final ITradeManager tradeManager = new TradeManager();
    private final ICommandManager commandManager = new CommandManager();
    private final IDice dice = new Dice();

    /**
     * Creates a new game
     */
    public Game(@NonNull UUID uuid) {
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
    public @NonNull IGameJSON getGameInformation() {
        return new GameJSON(uuid)
                .players(playerManager.getPlayerInformation())
                .resources(resourceManager.getResourcesInformation())
                .developmentCards(cardManager.getDevelopmentCardsInformation())
                .specialCards(cardManager.getSpecialCardsInformation())
                .territories(territoryManager.getTerritoriesInformation())
                .areas(constructionManager.getAreasInformation());
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
    public void addPlayer(@NonNull IPlayerJSON playerJSON) {
        Objects.requireNonNull(playerJSON, "playerJSON cannot be null!");

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
    public void removePlayer(@NonNull IPlayerJSON playerJSON) {
        Objects.requireNonNull(playerJSON, "playerJSON cannot be null!");

        if (!playerManager.hasPlayer(playerJSON)) {
            return;
        }

        IPlayer player = playerManager.getPlayer(playerJSON);

        playerManager.deletePlayer(player);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canPlay(@NonNull IPlayerJSON playerJSON) {
        Objects.requireNonNull(playerJSON, "playerJSON cannot be null!");

        if (!playerManager.hasPlayer(playerJSON)) {
            return false;
        }

        IPlayer player = playerManager.getPlayer(playerJSON);

        return playerManager.canPlay(player);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull INotificationJSON executeCommand(@NonNull String name, @NonNull List<Object> parameters) {
        Objects.requireNonNull(name, "name cannot be null!");
        Objects.requireNonNull(parameters, "parameters cannot be null!");

        //Checks if the command is valid
        if (!commandManager.hasCommand(name)) {
            return NotificationNoReplyJSON.COMMAND_NOT_FOUND;
        }

        //Gets data from the server
        ICommand command = commandManager.getCommand(name);

        return commandManager.execute(command, parameters);
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
            IPlayerJSON playerJSON = (IPlayerJSON) action.parameters().get(0);
            IAreaJSON areaJSON = (IAreaJSON) action.parameters().get(1);
            IConstructionJSON constructionJSON = (IConstructionJSON) action.parameters().get(2);

            //Checks if the player is valid
            if (!playerManager.hasPlayer(playerJSON)) {
                return NotificationNoReplyJSON.PLAYER_NOT_FOUND;
            }

            //Checks if the constructable area is valid
            if (!constructionManager.hasArea(areaJSON, Path.class)) {
                return NotificationNoReplyJSON.CONSTRUCTABLE_AREA_NOT_FOUND;
            }

            //Gets data from the server
            IPlayer player = playerManager.getPlayer(playerJSON);

            //Checks if the construction is valid
            if (!constructionManager.hasConstruction(constructionJSON, Road.class, player)) {
                return NotificationNoReplyJSON.CONSTRUCTION_NOT_FOUND;
            }

            //Gets data from the server
            IArea<Path> area = constructionManager.getArea(areaJSON, Path.class);
            Road road = constructionManager.generateConstruction(constructionJSON, Road.class, player);
            List<IResource> resources = constructionManager.getConstructionCost(road);

            //Checks if the player has construction
            if (!playerManager.hasConstruction(player, road)) {
                return NotificationNoReplyJSON.PLAYER_HAS_NOT_CONSTRUCTION;
            }

            //Checks if the player has resources
            if (!playerManager.hasResources(player, resources)) {
                return NotificationNoReplyJSON.PLAYER_HAS_NO_RESOURCE;
            }

            //Checks if the road is constructable
            if (!constructionManager.areaIsConstructable(area, road)) {
                return NotificationNoReplyJSON.CONSTRUCTION_NOT_CONSTRUCTABLE;
            }

            //Constructs the road and make the player pay
            constructionManager.constructOnArea(area, road);
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
            IPlayerJSON playerJSON = (IPlayerJSON) action.parameters().get(0);
            IAreaJSON areaJSON = (IAreaJSON) action.parameters().get(1);
            IConstructionJSON constructionJSON = (IConstructionJSON) action.parameters().get(2);

            //Checks if the player is valid
            if (!playerManager.hasPlayer(playerJSON)) {
                return NotificationNoReplyJSON.PLAYER_NOT_FOUND;
            }

            //Checks if the constructable area is valid
            if (!constructionManager.hasArea(areaJSON, Building.class)) {
                return NotificationNoReplyJSON.CONSTRUCTABLE_AREA_NOT_FOUND;
            }

            //Gets data from the server
            IPlayer player = playerManager.getPlayer(playerJSON);

            //Checks if the construction is valid
            if (!constructionManager.hasConstruction(constructionJSON, Colony.class, player)) {
                return NotificationNoReplyJSON.CONSTRUCTION_NOT_CONSTRUCTABLE;
            }

            //Gets data from the server
            IArea<Building> area = constructionManager.getArea(areaJSON, Building.class);
            Colony colony = constructionManager.generateConstruction(constructionJSON, Colony.class, player);
            List<IResource> resources = constructionManager.getConstructionCost(colony);

            //Checks if the player has construction
            if (!playerManager.hasConstruction(player, colony)) {
                return NotificationNoReplyJSON.PLAYER_HAS_NOT_CONSTRUCTION;
            }

            //Checks if the player has resources
            if (!playerManager.hasResources(player, resources)) {
                return NotificationNoReplyJSON.PLAYER_HAS_NO_RESOURCE;
            }

            //Checks if the colony is constructable
            if (!constructionManager.areaIsConstructable(area, colony)) {
                return NotificationNoReplyJSON.CONSTRUCTION_NOT_CONSTRUCTABLE;
            }

            //Constructs the colony and make the player pay
            constructionManager.constructOnArea(area, colony);
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
            IPlayerJSON playerJSON = (IPlayerJSON) action.parameters().get(0);
            IAreaJSON areaJSON = (IAreaJSON) action.parameters().get(1);
            IConstructionJSON constructionJSON = (IConstructionJSON) action.parameters().get(2);

            //Checks if the player is valid
            if (!playerManager.hasPlayer(playerJSON)) {
                return NotificationNoReplyJSON.PLAYER_NOT_FOUND;
            }

            //Checks if the constructable area is valid
            if (!constructionManager.hasArea(areaJSON, Building.class)) {
                return NotificationNoReplyJSON.CONSTRUCTABLE_AREA_NOT_FOUND;
            }

            //Gets data from the server
            IPlayer player = playerManager.getPlayer(playerJSON);

            //Checks if the construction is valid
            if (!constructionManager.hasConstruction(constructionJSON, City.class, player)) {
                return NotificationNoReplyJSON.CONSTRUCTION_NOT_CONSTRUCTABLE;
            }

            //Gets data from the server
            IArea<Building> area = constructionManager.getArea(areaJSON, Building.class);
            City city = constructionManager.generateConstruction(constructionJSON, City.class, player);
            List<IResource> resources = constructionManager.getConstructionCost(city);

            //Checks if the player has construction
            if (!playerManager.hasConstruction(player, city)) {
                return NotificationNoReplyJSON.PLAYER_HAS_NOT_CONSTRUCTION;
            }

            //Checks if the player has resources
            if (!playerManager.hasResources(player, resources)) {
                return NotificationNoReplyJSON.PLAYER_HAS_NO_RESOURCE;
            }

            //Checks if the city is constructable
            if (!constructionManager.areaIsConstructable(area, city)) {
                return NotificationNoReplyJSON.CONSTRUCTION_NOT_CONSTRUCTABLE;
            }

            //Constructs the city and make the player pay
            constructionManager.constructOnArea(area, city);
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
            IPlayerJSON playerJSON = (IPlayerJSON) action.parameters().get(0);

            //Checks if the player is valid
            if (!playerManager.hasPlayer(playerJSON)) {
                return NotificationNoReplyJSON.PLAYER_NOT_FOUND;
            }

            //Gets data from the server
            IPlayer player = playerManager.getPlayer(playerJSON);
            List<IResource> resources = cardManager.getDevelopmentCardCost();
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
     *              - a CardJSON
     */
    private void registerPlayDevelopmentCardCommand() {
        commandManager.addCommand("playDevelopmentCard", action -> {

            //Gets parameters
            IPlayerJSON playerJSON = (IPlayerJSON) action.parameters().get(0);
            ICardJSON cardJSON = (ICardJSON) action.parameters().get(1);

            //Checks if the player is valid
            if (!playerManager.hasPlayer(playerJSON)) {
                return NotificationNoReplyJSON.PLAYER_NOT_FOUND;
            }

            //Gets data from the server
            IPlayer player = playerManager.getPlayer(playerJSON);
            DevelopmentCard developmentCard = cardManager.generateDevelopmentCard(cardJSON);

            //Checks if the player has the development card
            if (playerManager.hasDevelopmentCard(player, developmentCard)) {
                return NotificationNoReplyJSON.PLAYER_HAS_NO_DEVELOPMENT_CARD;
            }

            //Uses the development card
            playerManager.removeDevelopmentCard(player, developmentCard);

            ICommandJSON commandJSON = playerManager.useDevelopmentCard(player, developmentCard);

            return executeCommand(commandJSON.name(), commandJSON.parameters());
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
            IPlayerJSON playerJSON = (IPlayerJSON) action.parameters().get(0);
            int amount = (int) action.parameters().get(1);

            //Checks if the player is valid
            if (playerManager.hasPlayer(playerJSON)) {
                return NotificationNoReplyJSON.PLAYER_NOT_FOUND;
            }

            //Gets data from the server
            IPlayer player = playerManager.getPlayer(playerJSON);

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
            IPlayerJSON playerJSON = (IPlayerJSON) action.parameters().get(0);

            return Stream.of(1, 3).map(i -> {
                IAreaJSON areaJSON = (IAreaJSON) action.parameters().get(i);
                IConstructionJSON constructionJSON = (IConstructionJSON) action.parameters().get(i + 1);

                //Checks if the player is valid
                if (!playerManager.hasPlayer(playerJSON)) {
                    return NotificationNoReplyJSON.PLAYER_NOT_FOUND;
                }

                //Checks if the constructable area is valid
                if (!constructionManager.hasArea(areaJSON, Path.class)) {
                    return NotificationNoReplyJSON.CONSTRUCTABLE_AREA_NOT_FOUND;
                }

                //Gets data from the server
                IPlayer player = playerManager.getPlayer(playerJSON);

                //Checks if the construction is valid
                if (!constructionManager.hasConstruction(constructionJSON, Road.class, player)) {
                    return NotificationNoReplyJSON.CONSTRUCTION_NOT_CONSTRUCTABLE;
                }

                //Gets data from the server
                IArea<Path> area = constructionManager.getArea(areaJSON, Path.class);
                Road road = constructionManager.generateConstruction(constructionJSON, Road.class, player);

                //Checks if the road is constructable
                if (!constructionManager.areaIsConstructable(area, road)) {
                    return NotificationNoReplyJSON.CONSTRUCTION_NOT_CONSTRUCTABLE;
                }

                //Constructs the road and make the player pay
                constructionManager.constructOnArea(area, road);
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
            IPlayerJSON playerJSON = (IPlayerJSON) action.parameters().get(0);

            return Stream.of(1, 2).map(i -> {
                IResourceJSON resourceJSON = (IResourceJSON) action.parameters().get(i);

                //Checks if the player is valid
                if (!playerManager.hasPlayer(playerJSON)) {
                    return NotificationNoReplyJSON.PLAYER_NOT_FOUND;
                }

                //Gets data from the server
                IPlayer player = playerManager.getPlayer(playerJSON);
                IResource resource = resourceManager.generateResource(resourceJSON);

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
            IPlayerJSON playerJSON = (IPlayerJSON) action.parameters().get(0);
            ITerritoryJSON territoryJSON = (ITerritoryJSON) action.parameters().get(1);
            IPlayerJSON victimJSON = (IPlayerJSON) action.parameters().get(2);

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
            IPlayer player = playerManager.getPlayer(playerJSON);
            ITerritory territory = territoryManager.getTerritory(territoryJSON);
            IPlayer victim = playerManager.getPlayer(victimJSON);

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
            IPlayerJSON playerJSON = (IPlayerJSON) action.parameters().get(0);
            IResourceJSON resourceJSON = (IResourceJSON) action.parameters().get(1);

            //Checks if the player is valid
            if (!playerManager.hasPlayer(playerJSON)) {
                return NotificationNoReplyJSON.PLAYER_NOT_FOUND;
            }

            //Gets data from the server
            IPlayer player = playerManager.getPlayer(playerJSON);
            IResource resource = resourceManager.generateResource(resourceJSON);

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
            IPlayerJSON playerJSON = (IPlayerJSON) action.parameters().get(0);
            IResourceJSON resourceJSON = (IResourceJSON) action.parameters().get(1);

            //Checks if the player is valid
            if (!playerManager.hasPlayer(playerJSON)) {
                return NotificationNoReplyJSON.PLAYER_NOT_FOUND;
            }

            //Gets data from the server
            IPlayer player = playerManager.getPlayer(playerJSON);
            IResource resource = resourceManager.generateResource(resourceJSON);

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
            ITradeJSON tradeJSON = (ITradeJSON) action.parameters().get(0);

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
            IPlayer sender = playerManager.getPlayer(tradeJSON.getSender());
            IPlayer receiver = playerManager.getPlayer(tradeJSON.getReceiver());
            List<IResource> offer = resourceManager.generateResources(tradeJSON.getOffer());
            List<IResource> request = resourceManager.generateResources(tradeJSON.getRequest());

            //Checks if the sender has resources of offer
            if (!playerManager.hasResources(sender, offer)) {
                return NotificationNoReplyJSON.PLAYER_HAS_NO_RESOURCE;
            }

            //Checks if the receiver has resources of request
            if (tradeJSON.hasReceiver() && !playerManager.hasResources(receiver, request)) {
                return NotificationNoReplyJSON.PLAYER_HAS_NO_RESOURCE;
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
            ITradeJSON tradeJSON = (ITradeJSON) action.parameters().get(0);

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
            ITrade trade = tradeManager.getTrade(tradeJSON);
            IPlayer sender = tradeManager.getSender(trade);
            IPlayer receiver = tradeManager.getReceiver(trade);
            List<IResource> offer = tradeManager.getOffer(trade);
            List<IResource> request = tradeManager.getRequest(trade);

            //Checks if the sender has resources of offer
            if (!playerManager.hasResources(sender, offer)) {
                return NotificationNoReplyJSON.PLAYER_HAS_NO_RESOURCE;
            }

            //Checks if the receiver has resources of request
            if (tradeManager.hasReceiver(trade) && !playerManager.hasResources(receiver, request)) {
                return NotificationNoReplyJSON.PLAYER_HAS_NO_RESOURCE;
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
            ITradeJSON tradeJSON = (ITradeJSON) action.parameters().get(0);

            //Checks if the trade is valid
            if (!tradeManager.hasTrade(tradeJSON)) {
                return NotificationNoReplyJSON.TRADE_NOT_FOUND;
            }

            //Gets data from the server
            ITrade trade = tradeManager.getTrade(tradeJSON);

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
        if (!(o instanceof Game game)) return false;
        return Objects.equals(uuid, game.uuid);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }

}