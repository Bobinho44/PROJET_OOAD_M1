package fr.univnantes.alma.commons.construction.constructableArea;

import org.springframework.lang.NonNull;

import java.util.UUID;

public class ConstructableAreaJSON {

    private final UUID uuid;
    private final String type;

    public ConstructableAreaJSON(@NonNull UUID uuid, @NonNull String type) {
        this.uuid = uuid;
        this.type = type;
    }

    public UUID getUUID() {
        return uuid;
    }

    public String getType() {
        return type;
    }
}