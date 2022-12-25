package fr.univnantes.alma.commons.construction;

import org.springframework.lang.NonNull;

import java.util.UUID;

public class ConstructionJSON {


    private final UUID uuid;
    private final String type;

    public ConstructionJSON(@NonNull UUID uuid, @NonNull String type) {
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