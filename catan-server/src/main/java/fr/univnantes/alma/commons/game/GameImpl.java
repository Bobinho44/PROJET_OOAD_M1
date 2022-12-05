package fr.univnantes.alma.commons.game;

import fr.univnantes.alma.core.card.type.DevelopmentCard;
import fr.univnantes.alma.core.game.Game;
import fr.univnantes.alma.core.game.GameManager;
import org.springframework.lang.NonNull;

import java.util.UUID;

public class GameImpl implements Game {

    private final GameManager gameManager = new GameController();
    private int activePlayerNumber;

    @Override
    public void rollDice() {
        int score = gameManager.rollDice();

        if (score == 7) {
            /*
             * TODO:
             *  ask discard
             *  ask move thief
             *  ask steal card
             *
             */

        }

        else {
            gameManager.giveLoot();
        }
    }

    @Override
    public void moveThief(@NonNull UUID uuid) {
    }

    @Override
    public void stealCardFromPlayer(@NonNull UUID stealer, @NonNull UUID stolen) {

    }

    @Override
    public void discardHalfCards(@NonNull UUID player) {

    }

    @Override
    public void buildRoad(@NonNull UUID player, @NonNull UUID buildableRoadArea) {

    }

    @Override
    public void buildColony(@NonNull UUID player, @NonNull UUID buildableColonyArea) {

    }

    @Override
    public void buildCity(@NonNull UUID player, @NonNull UUID buildableCityArea) {

    }

    @Override
    public void buyDevelopmentCard(@NonNull UUID player) {

    }

    @Override
    public void playDevelopmentCard(@NonNull UUID player, DevelopmentCard developmentCard) {

    }

    @Override
    public void tradeWithPlayer(@NonNull UUID proposal) {

    }

    @Override
    public void tradeWithBank(@NonNull UUID proposal) {

    }

    @Override
    public void finishTheTour() {

    }

}
