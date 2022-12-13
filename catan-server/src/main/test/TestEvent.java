import fr.univnantes.alma.commons.client.Client;
import fr.univnantes.alma.commons.game.GameController;
import fr.univnantes.alma.core.game.GameManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

public class TestEvent {

    static {
        ((ch.qos.logback.classic.Logger) LoggerFactory.getLogger("org.reflections")).setLevel(ch.qos.logback.classic.Level.OFF);
    }

    GameManager gameManager = new GameController();
    Client client1 = new Client();
    Client client2 = new Client();
    Client client3 = new Client();

    @Test
    public void a() {
        client1.requestJoinGame(gameManager);
        client2.requestJoinGame(gameManager);
        client3.requestJoinGame(gameManager);

        Assertions.assertEquals(client1.getGame(), client2.getGame());
        Assertions.assertNotEquals(client2.getGame(), client3.getGame());
    }

}
