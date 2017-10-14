import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Person_Converter {

    public static void main(String[] args) {
        Stream<String> lines = getFileStream(new File("/Users/zach/Documents/Databases/data/name.basics.tsv"));

        List<String> PERSON_LINES = new ArrayList<>();
        List<String> KNOWN_FOR_LINES = new ArrayList<>();
        List<String> PROFESSIONS_LINES = new ArrayList<>();

        assert lines != null;
        lines.forEach(line -> {
            String[] parts = line.split("\t");
            String nConst = parts[0];
            String primaryName = parts[1];
            String birthYear = parts[2];
            String deathYear = parts[3];
            List<String> professions = Arrays.asList(parts[4].split(","));
            List<String> knownFor = Arrays.asList(parts[5].split(","));

            PERSON_LINES.add(nConst + "\t" + primaryName + "\t" + birthYear + "\t" + deathYear);

            for (String prof : professions) {
                PROFESSIONS_LINES.add(nConst + "\t" + prof);
            }

            for (String knownF : knownFor) {
                KNOWN_FOR_LINES.add(nConst + "\t" + knownF);
            }
        });

        writeFile("/Users/zach/Documents/Databases/IMDB_Database/Import_Data/person_data.tsv", PERSON_LINES);
        writeFile("/Users/zach/Documents/Databases/IMDB_Database/Import_Data/know_for_data.tsv", KNOWN_FOR_LINES);
        writeFile("/Users/zach/Documents/Databases/IMDB_Database/Import_Data/primary_prof_data.tsv", PROFESSIONS_LINES);
    }

    private static Stream<String> getFileStream(File file) {
        try {
            return new BufferedReader(new FileReader(file)).lines();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private static void writeFile(String outputPath, List<String> lines) {
        try (FileWriter fw = new FileWriter(outputPath)) {
            lines.forEach(line -> {
                try {
                    fw.write(line);
                    fw.write("\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            fw.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
