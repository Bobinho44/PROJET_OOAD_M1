package fr.univnantes.alma.commons.Dice;

import fr.univnantes.alma.core.dice.Dice;

import java.util.Random;

public class DiceImpl implements Dice {

    /**
     * {@inheritDoc}
     */
    @Override
    public int rollDice() {
        Random random = new Random();
        return random.nextInt(6) + random.nextInt(6) + 2;
    }

}