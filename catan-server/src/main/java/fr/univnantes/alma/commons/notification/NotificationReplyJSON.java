package fr.univnantes.alma.commons.notification;

import fr.univnantes.alma.core.notification.NotificationJSON;
import org.springframework.lang.NonNull;

import java.util.List;

public class NotificationReplyJSON implements NotificationJSON {

    private final boolean needReply;
    private final List<Object> informationReply;

    public NotificationReplyJSON(@NonNull List<Object> informationReply) {
        this.needReply = true;
        this.informationReply = informationReply;
    }

    @Override
    public boolean needReply() {
        return needReply;
    }

    @Override
    public @NonNull List<Object> getInformationReply() {
        return informationReply;
    }

}