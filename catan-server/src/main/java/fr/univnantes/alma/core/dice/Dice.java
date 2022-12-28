package fr.univnantes.alma.core.dice;

/**
 * Interface representing a dice
 */
public interface Dice {

    /**
     * Gets the dice score
     * @return the dice score
     */
    int getScore();

    /**
     * Rolls the dice
     */
    void roll();

}