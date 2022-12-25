package fr.univnantes.alma.commons.resource;

import org.springframework.lang.NonNull;

public class ResourceJSON {

    private final String name;
    private final int amount;

    public ResourceJSON(@NonNull String name, int amount) {
        this.name = name;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public int getAmount() {
        return amount;
    }

}