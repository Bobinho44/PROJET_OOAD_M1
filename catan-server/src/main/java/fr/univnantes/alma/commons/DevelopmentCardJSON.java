package fr.univnantes.alma.commons;

import org.springframework.lang.NonNull;

import java.util.UUID;

public class DevelopmentCardJSON {

    private final UUID uuid;

    public DevelopmentCardJSON(@NonNull UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return uuid;
    }

}