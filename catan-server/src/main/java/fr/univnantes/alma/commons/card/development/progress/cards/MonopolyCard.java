package fr.univnantes.alma.commons.card.development.progress.cards;

import fr.univnantes.alma.commons.annotation.CardAmount;
import fr.univnantes.alma.commons.game.GameController;
import fr.univnantes.alma.commons.trade.TradeImpl;
import fr.univnantes.alma.core.player.Player;
import fr.univnantes.alma.commons.card.development.progress.ProgressCard;
import fr.univnantes.alma.core.ressource.Resource;
import fr.univnantes.alma.core.trade.Trade;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Class representing the progress card: monopoly
 */
@CardAmount(2)
public class MonopolyCard extends ProgressCard {

    /**
     * Creates a new monopoly card
     */
    public MonopolyCard() {
        super("Monopoly", "catan-web/catan-client/src/assets/special-card/Monopoly.png");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void useEffect(@NonNull GameController gameController, @NonNull Player player) {
        Objects.requireNonNull(player, "player cannot be null!");
        Resource resource = gameController.pickResource();
        Resource addResource = gameController.takeResourcesAllPlayer(player,resource);
        if(addResource == null)
            return;
        for (Resource r: player.getResources()) {
            if(r.isSimilar(addResource)){
                r.increaseAmount(addResource.getAmount());
                return;
            }
        }
        player.addResource(addResource);
    }

}