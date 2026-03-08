package pro1;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class Main {public static void main(String[] args) {
    String currentDir = System.getProperty("user.dir");
    Path inputDir = Paths.get(currentDir, "input");
    Path outputDir = Paths.get(currentDir, "output");

    try {
        if (!Files.exists(outputDir)) {
            Files.createDirectories(outputDir);
        }

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(inputDir, "*.csv")) {
            for (Path file : stream) {
                processFile(file, outputDir);
            }
        }
        System.out.println("Zpracování bylo úspěšně dokončeno.");

    } catch (IOException e) {
        System.err.println("Chyba: " + e.getMessage());
    }
}

    private static void processFile(Path inputFile, Path outputDir) throws IOException {
        List<String> lines = Files.readAllLines(inputFile);
        List<String> outputLines = new ArrayList<>();

        for (String line : lines) {
            if (line.trim().isEmpty()) continue;

            // Rozdělení podle středníku rovnítka a dvojtečky
            String[] parts = line.split("\\s*[;=:]\\s*");

            if (parts.length != 2) {
                outputLines.add(line);
                continue;
            }

            String name = parts[0].trim();
            String expression = parts[1].trim();

            // využití třídy fraction
            Fraction fraction = Fraction.parse(expression);

            outputLines.add(name + "," + fraction.toString());
        }

        Path outputFile = outputDir.resolve(inputFile.getFileName());
        Files.write(outputFile, outputLines);
        System.out.println("Převedeno: " + inputFile.getFileName());
    }
}