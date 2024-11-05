package org.example;

import java.util.Random;
import java.util.Scanner;

public class Game {
    private final Board board;
    private final Player human;
    private final Player computer;
    private final Random random;

    public Game(int rows, int cols) {
        this.board = new Board(rows, cols);
        this.human = new Player("Human", 'Y');
        this.computer = new Player("Computer", 'R');
        this.random = new Random();
    }

    public void start() {
        boolean gameWon = false;
        Player currentPlayer = human;
        Scanner scanner = new Scanner(System.in);

        while (!gameWon) {
            board.display();
            if (currentPlayer == human) {
                System.out.println(human.getName() + "'s turn (symbol: " + human.getSymbol() + ")");
                System.out.println("Choose a column (a-g): ");
                char column = scanner.nextLine().charAt(0);
                int col = column - 'a';
                if (!board.placeDisk(col, human.getSymbol())) {
                    System.out.println("Column is full. Choose another column.");
                    continue;
                }
            } else {
                System.out.println("Computer's turn (symbol: " + computer.getSymbol() + ")");
                int col;
                do {
                    col = random.nextInt(7);
                } while (board.isColumnFull(col));
                board.placeDisk(col, computer.getSymbol());
                System.out.println("Computer placed a disk in column " + (char) (col + 'a'));
            }

            gameWon = board.checkWin(currentPlayer.getSymbol());

            // Switch players
            currentPlayer = (currentPlayer == human) ? computer : human;
        }

        board.display();
        System.out.println((currentPlayer == human ? computer.getName() : human.getName()) + " wins!");
    }
}
