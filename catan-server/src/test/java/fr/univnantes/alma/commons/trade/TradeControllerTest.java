package fr.univnantes.alma.commons.trade;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import fr.univnantes.alma.commons.player.PlayerImpl;
import fr.univnantes.alma.commons.resource.type.OreResource;
import fr.univnantes.alma.commons.resource.type.WheatResource;
import fr.univnantes.alma.commons.resource.type.WoolResource;
import fr.univnantes.alma.core.resource.Resource;
import fr.univnantes.alma.core.trade.TradeJSON;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import fr.univnantes.alma.core.player.Player;
import fr.univnantes.alma.core.trade.Trade;
import org.mockito.Mockito;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Tests of the trade controller
 */
public class TradeControllerTest {

    static {
        ((Logger) LoggerFactory.getLogger("org.reflections")).setLevel(Level.OFF);
    }

    /**
     * Fields
     */
    private final TradeJSON tradeJSON = Mockito.mock(TradeJSON.class);
    private final TradeController tradeController = new TradeController();
    private final UUID uuid = UUID.randomUUID();
    private final Player player1 = new PlayerImpl(UUID.randomUUID());
    private final Player player2 = new PlayerImpl(UUID.randomUUID());
    private final List<Resource> offer = List.of(new OreResource(), new WheatResource());
    private final List<Resource> request = List.of(new WoolResource());
    private Trade trade;

    /**
     * Setup
     */
    @BeforeEach
    public void setup() {
        Mockito.when(tradeJSON.getUUID()).thenReturn(uuid);

        tradeController.createTrade(uuid, player1, player2, offer, request);
        trade = tradeController.getTrade(tradeJSON);
    }

    @Test
    public void hasTradeTest() {
        Assertions.assertTrue(tradeController.hasTrade(tradeJSON));
    }

    @Test
    public void deleteTradeTest() {
        tradeController.deleteTrade(trade);

        Assertions.assertFalse(tradeController.hasTrade(tradeJSON));
    }

    @Test
    public void hasReceiverTest() {
        UUID uuid2 = UUID.randomUUID();
        TradeJSON tradeJSON2 = Mockito.mock(TradeJSON.class);
        Mockito.when(tradeJSON2.getUUID()).thenReturn(uuid2);
        tradeController.createTrade(uuid2, player1, null, Collections.emptyList(), Collections.emptyList());
        Trade trade2 = tradeController.getTrade(tradeJSON2);

        Assertions.assertTrue(tradeController.hasReceiver(trade));
        Assertions.assertFalse(tradeController.hasReceiver(trade2));
    }

    @Test
    public void getSenderTest() {
        Assertions.assertEquals(player1, tradeController.getSender(trade));
    }

    @Test
    public void getReceiverTest() {
        UUID uuid2 = UUID.randomUUID();
        TradeJSON tradeJSON2 = Mockito.mock(TradeJSON.class);
        Mockito.when(tradeJSON2.getUUID()).thenReturn(uuid2);
        tradeController.createTrade(uuid2, player1, null, Collections.emptyList(), Collections.emptyList());
        Trade trade2 = tradeController.getTrade(tradeJSON2);

        Assertions.assertEquals(player2, tradeController.getReceiver(trade));
        Assertions.assertThrows(RuntimeException.class, () -> tradeController.getReceiver(trade2));
    }

    @Test
    public void getOfferTest() {
        Assertions.assertEquals(List.of(new OreResource(), new WheatResource()), tradeController.getOffer(trade));
    }

    @Test
    public void getRequestTest() {
        Assertions.assertEquals(List.of(new WoolResource()), tradeController.getRequest(trade));
    }

}