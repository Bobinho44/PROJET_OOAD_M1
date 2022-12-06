package fr.univnantes.alma.core.trade;

import fr.univnantes.alma.core.player.Player;
import org.springframework.lang.NonNull;

import java.util.Optional;

public interface TradeManager {

    /**
     * Gets the trade
     *
     * @param sender the sender
     * @param receiver the receiver
     * @return the optional trade
     */
    @NonNull Optional<Trade> getTrade(@NonNull Player sender, @NonNull Player receiver);

    /**
     * Checks if the trade between the sender and the receiver exist
     *
     * @param sender the sender
     * @param receiver the receiver
     * @return true if the trade between the sender and the receiver exist, false otherwise
     */
    boolean hasTrade(@NonNull Player sender, @NonNull Player receiver);

    /**
     * Adds the trade
     *
     * @param trade the trade
     */
    void addTrade(@NonNull Trade trade);

    /**
     * Removes the trade
     *
     * @param trade the trade
     */
    void removeTrade(@NonNull Trade trade);

}