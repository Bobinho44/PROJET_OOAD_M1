package fr.univnantes.alma.commons.card.special;

import org.springframework.lang.NonNull;

import java.util.UUID;

public class SpecialCardJSON {

    private final UUID uuid;
    private final String type;

    public SpecialCardJSON(@NonNull UUID uuid, @NonNull String type) {
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
