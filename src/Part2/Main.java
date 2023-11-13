package Part2;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {

            // 1. Lecture à partir d'un fichier

            String inputFile = "files/input.txt";
            String[] lines = readFromFile(inputFile);

            // 2. Manipulation du contenu dans le fichier

            int[] wordCounts = countWords(lines);

            // 3. Exportation des résultats dans un fichier

            String outputFile = "files/output.txt";
            exportResults(outputFile, wordCounts);

            System.out.println("Export terminé");

        } catch (FileNotFoundException e) {
            System.err.println("Fichier non trouvé");
        }
    }

    // 1. Lecture à partir d'un fichier

    private static String[] readFromFile(String fileName) throws FileNotFoundException {
        try (Scanner scanner = new Scanner(new File(fileName))) {
            int lineCount = 0;
            while (scanner.hasNextLine()) {
                lineCount++;
                scanner.nextLine();
            }

            String[] lines = new String[lineCount];
            scanner.close();

            scanner.useDelimiter("\n");
            scanner.reset();

            for (int i = 0; i < lineCount; i++) {
                if (scanner.hasNext()) {
                    lines[i] = scanner.next().trim();
                }
            }

            return lines;
        }
    }

    // 2. Manipulation du contenu dans le fichier

    private static int[] countWords(String[] lines) {
        int[] wordCounts = new int[lines.length];

        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            String[] words = line.split("\\s+"); // Split by whitespace
            wordCounts[i] = words.length;
        }

        return wordCounts;
    }

    // 3. Exportation des résultats dans un fichier

    private static void exportResults(String fileName, int[] wordCounts) throws FileNotFoundException {
        try (PrintWriter writer = new PrintWriter(new File(fileName))) {
            for (int count : wordCounts) {
                writer.println("Nombre de mots : " + count);
            }
        }
    }
}
