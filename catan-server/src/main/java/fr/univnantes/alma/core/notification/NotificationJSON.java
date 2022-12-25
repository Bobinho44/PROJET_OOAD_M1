package fr.univnantes.alma.core.notification;

import org.springframework.lang.NonNull;

import java.util.List;

public interface NotificationJSON {

    boolean needReply();

    @NonNull List<Object> getInformationReply();

}
