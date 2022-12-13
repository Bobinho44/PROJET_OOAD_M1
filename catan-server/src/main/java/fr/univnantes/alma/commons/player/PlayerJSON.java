package fr.univnantes.alma.commons.player;

import org.springframework.lang.NonNull;

import java.util.UUID;

public class PlayerJSON {

    private final UUID uuid;

    public PlayerJSON(@NonNull UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return uuid;
    }

}