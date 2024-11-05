package org.example;

import java.util.Arrays;

public class Board {
    private final char[][] grid;
    private final int rows;
    private final int cols;

    public Board(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.grid = new char[rows][cols];
        for (char[] row : grid) {
            Arrays.fill(row, '.');  // '.' represents an empty cell
        }
    }

    public boolean isColumnFull(int col) {
        return grid[0][col] != '.';
    }

    public boolean placeDisk(int col, char symbol) {
        if (isColumnFull(col)) {
            return false;
        }
        for (int row = rows - 1; row >= 0; row--) {
            if (grid[row][col] == '.') {
                grid[row][col] = symbol;
                return true;
            }
        }
        return false;
    }

    public boolean checkWin(char symbol) {
        return checkHorizontal(symbol) || checkVertical(symbol) || checkDiagonal(symbol);
    }

    private boolean checkHorizontal(char symbol) {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col <= cols - 4; col++) {
                if (grid[row][col] == symbol && grid[row][col + 1] == symbol &&
                        grid[row][col + 2] == symbol && grid[row][col + 3] == symbol) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkVertical(char symbol) {
        for (int col = 0; col < cols; col++) {
            for (int row = 0; row <= rows - 4; row++) {
                if (grid[row][col] == symbol && grid[row + 1][col] == symbol &&
                        grid[row + 2][col] == symbol && grid[row + 3][col] == symbol) {
                    return true;
                }
            }
        }
        return false;
    }

    public char[][] getGrid() {
        return grid;
    }

    public int getCols() {
        return cols;
    }

    public int getRows() {
        return rows;
    }

    private boolean checkDiagonal(char symbol) {
        // Check for / diagonal
        for (int row = 3; row < rows; row++) {
            for (int col = 0; col <= cols - 4; col++) {
                if (grid[row][col] == symbol && grid[row - 1][col + 1] == symbol &&
                        grid[row - 2][col + 2] == symbol && grid[row - 3][col + 3] == symbol) {
                    return true;
                }
            }
        }
        // Check for \ diagonal
        for (int row = 0; row <= rows - 4; row++) {
            for (int col = 0; col <= cols - 4; col++) {
                if (grid[row][col] == symbol && grid[row + 1][col + 1] == symbol &&
                        grid[row + 2][col + 2] == symbol && grid[row + 3][col + 3] == symbol) {
                    return true;
                }
            }
        }
        return false;
    }

    public void display() {
        // Fejléc megjelenítése az ábécé alapján
        System.out.print("  ");
        for (int i = 0; i < cols; i++) {
            // Az ábécé betűit az oszlopokhoz igazítjuk
            System.out.print((char) ('A' + i) + " ");
        }
        System.out.println();

        // Tábla tartalmának megjelenítése
        for (int row = 0; row < rows; row++) {
            System.out.print((row + 1) + " ");
            for (int col = 0; col < cols; col++) {
                System.out.print(grid[row][col] + " ");
            }
            System.out.println();
        }
    }


}
