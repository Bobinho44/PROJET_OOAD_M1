package fr.univnantes.alma.core.ressource;

import org.springframework.lang.NonNull;

public interface Resource {

    @NonNull String getName();


    Resource amount(int amount);

    int getAmount();

    void increaseAmount(int amount);

    void decreaseAmount(int amount);

    boolean isSimilar(Resource resource);
}