package fr.univnantes.alma.websocket;

import fr.univnantes.alma.core.player.Player;

import java.util.Date;

public interface WebSocketMessage {

    Player getPlayer();
    Date getDate();
}
