package fr.univnantes.alma.commons.game;

import org.springframework.lang.NonNull;

import java.util.UUID;

public class GameJSON {

    private final UUID uuid;

    public GameJSON(@NonNull UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return uuid;
    }

}