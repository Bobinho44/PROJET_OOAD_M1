package fr.univnantes.alma.commons.dice;

import fr.univnantes.alma.core.dice.IDice;

import java.util.Random;

/**
 * Implementation of a dice
 */
public class Dice implements IDice {

    /**
     * Fields
     */
    private int score;
    private final Random random = new Random();

    /**
     * {@inheritDoc}
     */
    @Override
    public int getScore() {
        return score;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void roll() {
        score = random.nextInt(6) + random.nextInt(6) + 2;
    }

}