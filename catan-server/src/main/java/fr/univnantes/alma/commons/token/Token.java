package fr.univnantes.alma.commons.token;

import fr.univnantes.alma.core.token.IToken;

/**
 * Implementation of a token
 */
public enum Token implements IToken {
    A(5),
    B(2),
    C(6),
    D(3),
    E(8),
    F(10),
    G(9),
    H(12),
    I(11),
    J(4),
    K(8),
    L(10),
    M(9),
    N(4),
    O(5),
    P(6),
    Q(3),
    R(11);

    /**
     * Fields
     */
    private final int number;

    /**
     * Creates a new token
     *
     * @param number the number
     */
    Token(int number) {
        this.number = number;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getNumber() {
        return number;
    }

}