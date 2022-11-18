package fr.univnantes.alma.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Joueur {

    private final String name;
    private final List<Ressource> ressources = new ArrayList<>();
    private final List<DevelopmentCard> developmentCards = new ArrayList<>();
    private Optional<SpecialFiche> knight = Optional.empty();
    private Optional<SpecialFiche> roads = Optional.empty();


    public Joueur(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    
}
