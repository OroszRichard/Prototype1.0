package org.example;

public class Main {
    public static void main(String[] args) {
        // Játék inicializálása
        int rows = 6; // sorok száma
        int cols = 7; // oszlopok száma

        // Játék betöltése szövegfájlból vagy üres pályával indítás
        char[][] board = GameIO.loadGame(rows, cols);

        // Adatbázis handler inicializálása
        DatabaseHandler dbHandler = new DatabaseHandler();

        // Játékos nevének bekérése
        String playerName = GameIO.askPlayerName();

        // Játéktér kiíratása
        System.out.println("Játék kezdete:");
        GameIO.printBoard(board);

        // Játék logika
        boolean gameRunning = true;
        while (gameRunning) {
            // Játékos lépésének bekérése
            int column = GameIO.getColumnInput();

            // Ellenőrzés, hogy a lépés alkalmazható-e
            if (isValidMove(board, column)) {
                applyMove(board, column, 'Y'); // 'Y' a sárga korong a játékos számára

                // Játéktér kiírása és állás mentése
                GameIO.printBoard(board);
                GameIO.saveGame(board);

                // Győzelem ellenőrzése
                if (checkWin(board, 'Y')) {
                    System.out.println("Gratulálunk, " + playerName + ", nyertél!");
                    dbHandler.saveWin(playerName);
                    gameRunning = false;
                }

                // Gépi játékos random lépése
                int aiMove = generateRandomMove(board);
                applyMove(board, aiMove, 'R'); // 'R' a piros korong a gép számára

                // Ellenőrzés a gépi játékos nyert-e
                if (checkWin(board, 'R')) {
                    System.out.println("Sajnáljuk, a gép nyert.");
                    gameRunning = false;
                }
            } else {
                System.out.println("Érvénytelen lépés, próbáld újra!");
            }
        }

        // High score táblázat kiíratása
        System.out.println("\nHigh Score:");
        dbHandler.displayHighScores();
    }

    // Egy segédmetódus a lépés érvényességének ellenőrzésére
    private static boolean isValidMove(char[][] board, int column) {
        return column >= 0 && column < board[0].length && board[0][column] == '-';
    }

    // Segédmetódus a lépés alkalmazására
    private static void applyMove(char[][] board, int column, char symbol) {
        for (int row = board.length - 1; row >= 0; row--) {
            if (board[row][column] == '-') {
                board[row][column] = symbol;
                break;
            }
        }
    }

    // Győzelem ellenőrzése négy azonos korong esetén
    private static boolean checkWin(char[][] board, char symbol) {
        int rows = board.length;
        int cols = board[0].length;

        // Vízszintes ellenőrzés
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col <= cols - 4; col++) {
                if (board[row][col] == symbol && board[row][col + 1] == symbol &&
                        board[row][col + 2] == symbol && board[row][col + 3] == symbol) {
                    return true;
                }
            }
        }

        // Függőleges ellenőrzés
        for (int col = 0; col < cols; col++) {
            for (int row = 0; row <= rows - 4; row++) {
                if (board[row][col] == symbol && board[row + 1][col] == symbol &&
                        board[row + 2][col] == symbol && board[row + 3][col] == symbol) {
                    return true;
                }
            }
        }

        // Átlós ellenőrzés (bal felső - jobb alsó)
        for (int row = 0; row <= rows - 4; row++) {
            for (int col = 0; col <= cols - 4; col++) {
                if (board[row][col] == symbol && board[row + 1][col + 1] == symbol &&
                        board[row + 2][col + 2] == symbol && board[row + 3][col + 3] == symbol) {
                    return true;
                }
            }
        }

        // Átlós ellenőrzés (jobb felső - bal alsó)
        for (int row = 0; row <= rows - 4; row++) {
            for (int col = 3; col < cols; col++) {
                if (board[row][col] == symbol && board[row + 1][col - 1] == symbol &&
                        board[row + 2][col - 2] == symbol && board[row + 3][col - 3] == symbol) {
                    return true;
                }
            }
        }

        return false; // Nincs győzelem
    }

    // Random lépés generálása a gép számára
    private static int generateRandomMove(char[][] board) {
        int column;
        do {
            column = (int) (Math.random() * board[0].length);
        } while (!isValidMove(board, column));
        return column;
    }
}
