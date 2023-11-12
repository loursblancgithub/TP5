package Part1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.*;
import java.util.ArrayList;
import java.util.List;
public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("files/dpt2018.csv"));
        StringBuilder stringBuilder = new StringBuilder();
        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }
        while (scanner.hasNextLine()) {
            stringBuilder.append(scanner.nextLine()).append("\n");
        }
        scanner.close();
        String data = stringBuilder.toString();
        String[] lines = data.split("\n");

        //Première et deuxième question

        int nbinc = 0;
        String batch = Arrays.toString(lines);
        Pattern pattern = Pattern.compile("XXXX");
        Matcher matcher = pattern.matcher(batch);
        for (int i = 0; i<lines.length;i++){
            if (matcher.find()){
                nbinc++;
            }
        }
        int XXXX=nbinc;
        int nbinc2 = 0;
        Pattern pattern2 = Pattern.compile("XX");
        Matcher matcher2 = pattern2.matcher(batch);
        for (int i = 0; i<lines.length;i++){
            if (matcher2.find()){
                nbinc2++;
            }
        }
        int XX = nbinc2;
        String corrected = batch;
        batch = batch.replaceAll("XXXX","-1");
        String corrected2 = batch;
        batch = batch.replaceAll("XX","-1");
        System.out.println("Le fichier comporte " + (XXXX+XX) +" champs incomplets.");

        //Troisième question

        int nbfem = 0;
        List<String> uniqueFemaleNames = new ArrayList<>();
        Pattern patternfem = Pattern.compile("2;([^;]+);");
        Matcher matcherfem = patternfem.matcher(batch);
        while (matcherfem.find()) {
            String femaleName = matcherfem.group(1);
            if (!uniqueFemaleNames.contains(femaleName)) {
                uniqueFemaleNames.add(femaleName);
                nbfem++;
            }
        }
        int fem = nbfem;
        int nbmasc = 0;
        List<String> uniqueOccurrences = new ArrayList<>();
        Pattern patternmasc = Pattern.compile("1;");
        Matcher matchermasc = patternmasc.matcher(batch);
        while (matchermasc.find()) {
            String occurrence = matchermasc.group();
            if (!uniqueOccurrences.contains(occurrence)) {
                uniqueOccurrences.add(occurrence);
                nbmasc++;
            }
        }
        int masc = nbmasc;
        System.out.println("Le fichier comporte " + fem + " prénoms féminins différents et " + masc + " noms masculins différents.");

        //Quatrième question

        int nbtcjhpg = 0;
        String[] patterns = {"THIBAUT", "CLAUDE", "JÉRÔME", "HERVÉ", "PATRICK", "GUILLAUME"};
        for (String patternString : patterns) {
            Pattern pattern1 = Pattern.compile("1;" + patternString);
            Matcher matcher1 = pattern1.matcher(batch);

            while (matcher1.find()) {
                nbtcjhpg++;
            }
        }
        int nameshommes = nbtcjhpg;
        System.out.println(nameshommes + " hommes ont été nommés Thibaut, Claude, Jérôme, Hervé, Patrick ou Guillaume sur la durée du recensement. ");

        //Cinquième question

        Pattern pattern2016 = Pattern.compile("2016");
        Matcher matcher2016 = pattern2016.matcher(batch);
        int nb75 = 0;
        Pattern pattern75 = Pattern.compile("\\b75\\b");
        Matcher matcher75 = pattern75.matcher(batch);
        for (int i = 0; i<lines.length;i++){
            if (matcher2016.find()){
                if (matcher75.find()){
                    nb75++;
                }
            }
        }
        int sevenfive = nb75;
        System.out.println("Il y a eu " + sevenfive + " naissances à Paris en 2016.");

        //Sixième question

        int[] yearsjean = new int[lines.length];
        int[] jeanOccurrences = new int[lines.length];
        int arrayIndexjean = 0;

        for (String line : lines) {
            int year = extractYear(line);
            int departmentCount = extractDepartmentCount(line);

            if (year > 0 && departmentCount > 0) {
                Pattern patternJean = Pattern.compile("1;JEAN;");
                Matcher matcherJean = patternJean.matcher(line);

                while (matcherJean.find()) {
                    yearsjean[arrayIndexjean] = year;
                    jeanOccurrences[arrayIndexjean] += departmentCount;
                    arrayIndexjean++;
                }
            }
        }
        int totalOccurrencesjean = 0;
        for (int i = 0; i < arrayIndexjean; i++) {
            totalOccurrencesjean += jeanOccurrences[i];
        }
        int averagejean = arrayIndexjean > 0 ? totalOccurrencesjean / arrayIndexjean : 0;
        System.out.println("En moyenne, le prénom Jean a été donné " + averagejean + " fois chaque année.");

        //Septième question

        int[] years = new int[lines.length];
        int[] marieOccurrences = new int[lines.length];
        int arrayIndex = 0;

        for (String line : lines) {
            int year = extractYear(line);
            int departmentCount = extractDepartmentCount(line);

            if (year > 0 && departmentCount > 0) {
                Pattern patternMarie = Pattern.compile("2;MARIE;");
                Matcher matcherMarie = patternMarie.matcher(line);

                while (matcherMarie.find()) {
                    years[arrayIndex] = year;
                    marieOccurrences[arrayIndex] += departmentCount;
                    arrayIndex++;
                }
            }
        }
        int totalOccurrences = 0;
        for (int i = 0; i < arrayIndex; i++) {
            totalOccurrences += marieOccurrences[i];
        }
        int averagemarie = arrayIndex > 0 ? totalOccurrences / arrayIndex : 0;
        System.out.println("En moyenne, le prénom Marie a été donné " + averagemarie + " fois chaque année.");

        //Huitième question

        String[] camilleLines = getCamilleLines(lines);
        int boysCount = 0;
        int girlsCount = 0;

        for (String line : camilleLines) {
            int gender = extractGender(line);
            if (gender == 1) {
                boysCount++;
            } else if (gender == 2) {
                girlsCount++;
            }
            int year = extractYear(line);
            if (girlsCount > boysCount) {
                System.out.println("L'année " + year + " compte plus de filles prénommées Camille que de garçons.");
                break;
            }
        }
    }


    private static int extractYear(String line) {
        String[] columns = line.split(";");
        if (columns.length > 2) {
            try {
                return Integer.parseInt(columns[2]);
            } catch (NumberFormatException ignored) {
            }
        }
        return -1;
    }

    private static int extractDepartmentCount(String line) {
        String[] columns = line.split(";");
        if (columns.length > 4) {
            try {
                return Integer.parseInt(columns[4]);
            } catch (NumberFormatException ignored) {
            }
        }
        return -1;
    }
    private static int extractGender(String line) {
        String[] columns = line.split(";");
        if (columns.length > 3) {
            try {
                return Integer.parseInt(columns[3]);
            } catch (NumberFormatException ignored) {
            }
        }
        return 0;
    }
    private static String[] getCamilleLines(String[] allLines) {
        return Arrays.stream(allLines)
                .filter(line -> line.contains("CAMILLE"))
                .toArray(String[]::new);
    }
}