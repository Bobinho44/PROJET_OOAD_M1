package fr.univnantes.alma.websocket;

import fr.univnantes.alma.Player;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;

public class PlayerController {

    @MessageMapping("/player")
    @SendTo("/topic/player")
    public Player greeting(String message) {
        return new Player();
    }
}
