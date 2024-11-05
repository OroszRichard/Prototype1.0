package org.example;

import java.io.*;
import java.util.*;

public class DatabaseHandler {
    private static final String FILE_PATH = "C:\\Asztal\\EGYETEM\\3. félév\\Programozási technológiák\\BEADANDÓ\\kínlódás\\OroszRichardprog tech 2.0 so-so\\game_state.txt";

    public DatabaseHandler() {
        createFile();
    }

    // Fájl létrehozása, ha nem létezik
    private void createFile() {
        try {
            File file = new File(FILE_PATH);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            System.out.println("Hiba történt a fájl létrehozásakor: " + e.getMessage());
        }
    }

    // Győzelem mentése
    public void saveWin(String playerName) {
        Map<String, Integer> highScores = readHighScores();

        highScores.put(playerName, highScores.getOrDefault(playerName, 0) + 1);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Map.Entry<String, Integer> entry : highScores.entrySet()) {
                writer.write(entry.getKey() + ":" + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Hiba történt a győzelem mentésekor: " + e.getMessage());
        }
    }

    // High score táblázat beolvasása a fájlból
    private Map<String, Integer> readHighScores() {
        Map<String, Integer> highScores = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
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

    // High score táblázat megjelenítése
    public void displayHighScores() {
        Map<String, Integer> highScores = readHighScores();
        System.out.println("High Score:");
        highScores.entrySet()
                .stream()
                .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
                .forEach(entry -> System.out.println(entry.getKey() + ": " + entry.getValue() + " győzelem"));
    }
}
