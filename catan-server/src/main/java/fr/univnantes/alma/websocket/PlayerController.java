package fr.univnantes.alma.websocket;

import fr.univnantes.alma.commons.player.PlayerImpl;
import fr.univnantes.alma.core.player.Player;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;

public class PlayerController {

    @MessageMapping("/player")
    @SendTo("/topic/player")
    public Player greeting(String message) {
        return new PlayerImpl();
    }
}
