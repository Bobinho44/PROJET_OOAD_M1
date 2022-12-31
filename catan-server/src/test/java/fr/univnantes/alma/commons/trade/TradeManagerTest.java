package fr.univnantes.alma.commons.trade;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import fr.univnantes.alma.commons.player.Player;
import fr.univnantes.alma.commons.resource.type.OreResource;
import fr.univnantes.alma.commons.resource.type.WheatResource;
import fr.univnantes.alma.commons.resource.type.WoolResource;
import fr.univnantes.alma.core.resource.IResource;
import fr.univnantes.alma.core.trade.ITradeJSON;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import fr.univnantes.alma.core.player.IPlayer;
import fr.univnantes.alma.core.trade.ITrade;
import org.mockito.Mockito;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Tests of the trade manager
 */
public class TradeManagerTest {

    static {
        ((Logger) LoggerFactory.getLogger("org.reflections")).setLevel(Level.OFF);
    }

    /**
     * Fields
     */
    private final ITradeJSON tradeJSON = Mockito.mock(ITradeJSON.class);
    private final TradeManager tradeManager = new TradeManager();
    private final UUID uuid = UUID.randomUUID();
    private final IPlayer player1 = new Player(UUID.randomUUID());
    private final IPlayer player2 = new Player(UUID.randomUUID());
    private final List<IResource> offer = List.of(new OreResource(), new WheatResource());
    private final List<IResource> request = List.of(new WoolResource());
    private ITrade trade;

    /**
     * Setup
     */
    @BeforeEach
    public void setup() {
        Mockito.when(tradeJSON.getUUID()).thenReturn(uuid);

        tradeManager.createTrade(uuid, player1, player2, offer, request);
        trade = tradeManager.getTrade(tradeJSON);
    }

    @Test
    public void hasTradeTest() {
        Assertions.assertTrue(tradeManager.hasTrade(tradeJSON));
    }

    @Test
    public void deleteTradeTest() {
        tradeManager.deleteTrade(trade);

        Assertions.assertFalse(tradeManager.hasTrade(tradeJSON));
    }

    @Test
    public void hasReceiverTest() {
        UUID uuid2 = UUID.randomUUID();
        ITradeJSON tradeJSON2 = Mockito.mock(ITradeJSON.class);
        Mockito.when(tradeJSON2.getUUID()).thenReturn(uuid2);
        tradeManager.createTrade(uuid2, player1, null, Collections.emptyList(), Collections.emptyList());
        ITrade trade2 = tradeManager.getTrade(tradeJSON2);

        Assertions.assertTrue(tradeManager.hasReceiver(trade));
        Assertions.assertFalse(tradeManager.hasReceiver(trade2));
    }

    @Test
    public void getSenderTest() {
        Assertions.assertEquals(player1, tradeManager.getSender(trade));
    }

    @Test
    public void getReceiverTest() {
        UUID uuid2 = UUID.randomUUID();
        ITradeJSON tradeJSON2 = Mockito.mock(ITradeJSON.class);
        Mockito.when(tradeJSON2.getUUID()).thenReturn(uuid2);
        tradeManager.createTrade(uuid2, player1, null, Collections.emptyList(), Collections.emptyList());
        ITrade trade2 = tradeManager.getTrade(tradeJSON2);

        Assertions.assertEquals(player2, tradeManager.getReceiver(trade));
        Assertions.assertThrows(RuntimeException.class, () -> tradeManager.getReceiver(trade2));
    }

    @Test
    public void getOfferTest() {
        Assertions.assertEquals(List.of(new OreResource(), new WheatResource()), tradeManager.getOffer(trade));
    }

    @Test
    public void getRequestTest() {
        Assertions.assertEquals(List.of(new WoolResource()), tradeManager.getRequest(trade));
    }

}