package fr.univnantes.alma.commons.territory;

import org.springframework.lang.NonNull;

import java.util.UUID;

public class TerritoryJSON {

    private final UUID uuid;

    public TerritoryJSON(@NonNull UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return uuid;
    }

}