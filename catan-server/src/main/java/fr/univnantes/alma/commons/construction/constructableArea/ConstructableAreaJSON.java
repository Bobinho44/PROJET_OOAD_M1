package fr.univnantes.alma.commons.construction.constructableArea;

import org.springframework.lang.NonNull;

import java.util.UUID;

public class ConstructableAreaJSON {

    private final UUID uuid;

    public ConstructableAreaJSON(@NonNull UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return uuid;
    }

}