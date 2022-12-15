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
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Class representing the progress card: invention
 */
@CardAmount(2)
public class InventionCard extends ProgressCard {

    /**
     * Creates a new invention card
     */
    public InventionCard() {
        super("Invention", "catan-web/catan-client/src/assets/special-card/Monopoly.png");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void useEffect(@NonNull GameController gameController, @NonNull Player player) {
        Objects.requireNonNull(player, "player cannot be null!");
        List<Resource> list = List.of(gameController.pickResourceBank());
        Trade trade = new TradeImpl(player,null,new ArrayList<Resource>(),list);
        gameController.tradeWithBank(trade);

        list = List.of(gameController.pickResourceBank());
        trade = new TradeImpl(player,null,new ArrayList<Resource>(),list);
        gameController.tradeWithBank(trade);

    }

}