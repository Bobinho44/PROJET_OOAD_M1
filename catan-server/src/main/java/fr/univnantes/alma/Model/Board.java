package fr.univnantes.alma.Model;

import java.util.List;

public class Board {
    
    private final Dice dice = new Dice();
    private final List<Land> lands;

    public Board(List<Land> lands) {
        this.lands = lands;
    }

    
}
