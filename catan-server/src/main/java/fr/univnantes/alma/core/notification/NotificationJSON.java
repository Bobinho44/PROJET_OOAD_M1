package fr.univnantes.alma.core.notification;

import org.springframework.lang.NonNull;

import java.util.List;

/**
 * Interface representing a notification
 */
public interface NotificationJSON {

    /**
     * Checks if the notification need a reply
     *
     * @return true if the notification need a reply, false otherwise
     */
    boolean needReply();

    /**
     * Gets the reply information
     *
     * @return the reply information
     */
    @NonNull List<Object> replyInformation();

}