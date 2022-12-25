package fr.univnantes.alma.commons.notification;

import fr.univnantes.alma.core.notification.NotificationJSON;
import org.springframework.lang.NonNull;

import java.util.Collections;
import java.util.List;

public enum NotificationNoReplyJSON implements NotificationJSON {
    COMMAND_SUCCESS,
    COMMAND_NOT_FOUND,
    PLAYER_NOT_FOUND,
    PLAYER_HAS_NOT_CONSTRUCTION,
    PLAYER_CAN_NOT_BUY_DEVELOPMENT_CARD,
    PLAYER_HAS_NOT_DEVELOPMENT_CARD,
    PLAYER_HAS_NOT_RESOURCE,
    PLAYER_CAN_NOT_PLAY,
    CONSTRUCTABLE_AREA_NOT_FOUND,
    CONSTRUCTION_NOT_FOUND,
    CONSTRUCTION_NOT_CONSTRUCTABLE,
    DEVELOPMENT_CARD_NOT_FOUND,
    RESOURCE_NOT_FOUND,
    TERRITORY_NOT_FOUND,
    TRADE_NOT_FOUND,
    GAME_NOT_FOUND,
    IMPOSSIBLE_ACTION;


    @Override
    public boolean needReply() {
        return false;
    }

    @Override
    public @NonNull List<Object> getInformationReply() {
        return Collections.emptyList();
    }

}