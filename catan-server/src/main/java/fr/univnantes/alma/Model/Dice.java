package fr.univnantes.alma.Model;

public class Dice {

    public int rollDice() {
        return ((int) (Math.random() * 6 + 1) + (int) (Math.random() * 6 + 1));
    }

}
