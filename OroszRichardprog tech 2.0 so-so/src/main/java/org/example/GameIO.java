package org.example;

import java.io.*;
import java.util.*;

public class GameIO {
    private static final String BOARD_FILE_PATH = "C:\\Asztal\\EGYETEM\\3. félév\\Programozási technológiák\\BEADANDÓ\\kínlódás\\OroszRichardprog tech 2.0 so-so\\game_board.txt";
    private static final String HIGH_SCORES_FILE_PATH = "C:\\Asztal\\EGYETEM\\3. félév\\Programozási technológiák\\BEADANDÓ\\kínlódás\\OroszRichardprog tech 2.0 so-so\\high_scores.txt";

    // Játékállás betöltése fájlból, vagy üres pálya létrehozása
    public static char[][] loadGame(int rows, int cols) {
        char[][] board = new char[rows][cols];
        for (int i = 0; i < rows; i++) {
            Arrays.fill(board[i], '-');
        }

        File file = new File(BOARD_FILE_PATH);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                for (int i = 0; i < rows; i++) {
                    String line = reader.readLine();
                    if (line != null) {
                        for (int j = 0; j < Math.min(cols, line.length()); j++) {
                            board[i][j] = line.charAt(j);
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println("Hiba történt a játékállás betöltésekor: " + e.getMessage());
            }
        }
        return board;
    }

    // Játékállás mentése fájlba
    public static void saveGame(char[][] board) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(BOARD_FILE_PATH))) {
            for (char[] row : board) {
                writer.write(new String(row));
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Hiba történt a játékállás mentésekor: " + e.getMessage());
        }
    }

    // Játékos nevének bekérése (egyszerűsített)
    public static String askPlayerName() {
        System.out.print("Kérem, adja meg a játékos nevét: ");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    // Lépés bekérése a játékostól
    public static int getColumnInput() {
        System.out.print("Adja meg az oszlop számát (0-tól kezdve): ");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    // Játéktér kiíratása
    public static void printBoard(char[][] board) {
        for (char[] row : board) {
            System.out.println(new String(row));
        }
    }

    // High score mentése fájlba
    public static void saveWin(String playerName) {
        Map<String, Integer> highScores = readHighScores();

        highScores.put(playerName, highScores.getOrDefault(playerName, 0) + 1);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(HIGH_SCORES_FILE_PATH))) {
            for (Map.Entry<String, Integer> entry : highScores.entrySet()) {
                writer.write(entry.getKey() + ":" + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Hiba történt a high score mentésekor: " + e.getMessage());
        }
    }

    // High score beolvasása a fájlból
    public static Map<String, Integer> readHighScores() {
        Map<String, Integer> highScores = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(HIGH_SCORES_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    String name = parts[0];
                    int wins = Integer.parseInt(parts[1]);
                    highScores.put(name, wins);
                }
            }
        } catch (IOException e) {
            System.out.println("Hiba történt a high score beolvasásakor: " + e.getMessage());
        }
        return highScores;
    }

    // High score megjelenítése
    public static void displayHighScores() {
        Map<String, Integer> highScores = readHighScores();
        System.out.println("High Score:");
        highScores.entrySet()
                .stream()
                .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
                .forEach(entry -> System.out.println(entry.getKey() + ": " + entry.getValue() + " győzelem"));
    }
}
