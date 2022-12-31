package fr.univnantes.alma.commons.notification;

import fr.univnantes.alma.core.notification.INotificationJSON;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Objects;

/**
 * Implementation of a reply notification
 */
public record NotificationReplyJSON(List<Object> replyInformation) implements INotificationJSON {

    /**
     * Creates a new notification reply json
     *
     * @param replyInformation the reply information
     */
    public NotificationReplyJSON(@NonNull List<Object> replyInformation) {
        Objects.requireNonNull(replyInformation, "replyInformation cannot be null!");

        this.replyInformation = replyInformation;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean needReply() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull List<Object> replyInformation() {
        return replyInformation;
    }


}