package fr.univnantes.alma.commons.trade;

import fr.univnantes.alma.core.player.Player;
import fr.univnantes.alma.core.trade.Trade;
import fr.univnantes.alma.core.trade.TradeManager;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TradeController implements TradeManager {

    /**
     * Fields
     */
    private final List<Trade> trades = new ArrayList<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull Optional<Trade> getTrade(@NonNull Player sender, @NonNull Player receiver) {
        return trades.stream()
                .filter(trade -> trade.getSender().equals(sender) && trade.getReceiver().equals(receiver))
                .findFirst();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasTrade(@NonNull Player sender, @NonNull Player receiver) {
        return getTrade(sender, receiver).isPresent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addTrade(@NonNull Trade trade) {
        trades.add(trade);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeTrade(@NonNull Trade trade) {
        trades.remove(trade);
    }

}