package fr.univnantes.alma.commons.token;

import fr.univnantes.alma.core.token.Token;

public enum TokenImpl implements Token {
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

    private final int number;

    TokenImpl(int number) {
        this.number = number;
    }

    @Override
    public int getNumber() {
        return number;
    }

}