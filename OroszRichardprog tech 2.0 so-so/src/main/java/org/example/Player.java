package org.example;

public class Player {
    private final String name;
    private final char symbol;  // 'Y' for yellow (human), 'R' for red (computer)

    public Player(String name, char symbol) {
        this.name = name;
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public char getSymbol() {
        return symbol;
    }
}
