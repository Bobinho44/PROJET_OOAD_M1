package fr.univnantes.alma.commons.card.development.knight;

import fr.univnantes.alma.commons.annotation.CardAmount;
import fr.univnantes.alma.commons.annotation.DevelopmentCardCost;
import fr.univnantes.alma.commons.annotation.ResourceInformation;
import fr.univnantes.alma.commons.game.GameController;
import fr.univnantes.alma.core.card.type.SpecialCard;
import fr.univnantes.alma.core.player.Player;
import fr.univnantes.alma.core.card.type.DevelopmentCard;
import org.springframework.lang.NonNull;

import java.util.Objects;

/**
 * Class representing knight cards
 */
@CardAmount(14)
@DevelopmentCardCost(resources = {
        @ResourceInformation(name = "Wheat", amount = 1),
        @ResourceInformation(name = "Wool", amount = 1),
        @ResourceInformation(name = "Ore", amount = 1)
})
public class KnightCard extends DevelopmentCard {

    /**
     * Creates a new knight card
     */
    public KnightCard() {
        super("Knight", "catan-web/catan-client/src/assets/special-card/Knight.png");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void useEffect(@NonNull GameController gameController, @NonNull Player player) {
        Objects.requireNonNull(player, "player cannot be null!");

        gameController.moveThief(gameController.pickTerritory());
        Player playerToSteal = gameController.pickOtherPlayer();
        player.addResource(playerToSteal.popRandomResource());
        player.addKnightInArmy(this);

        //test if the player has the biggest army, if so give him the special card
        if(player.sizeArmy() >= 3){
            for (SpecialCard specialCard : gameController.getSpecialsCards()) {
                if(specialCard.getName().equals("Most powerful army")){
                    if(specialCard.getOwner() == null){
                        specialCard.getEffect(playerToSteal);
                    }
                    if(specialCard.getOwner().sizeArmy() <= player.sizeArmy() && specialCard.getOwner() != player){
                        specialCard.looseEffect(specialCard.getOwner());
                        specialCard.getEffect(player);
                    }
                }
            }
        }
    }

}