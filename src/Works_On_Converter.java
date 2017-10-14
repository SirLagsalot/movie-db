import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Works_On_Converter {

    public static void main(String[] args) {
        Stream<String> crew_lines = getFileStream(new File("/Users/zach/Documents/Databases/data/title.crew.tsv"));
        Stream<String> actors_lines = getFileStream(new File("/Users/zach/Documents/Databases/data/title.principals.tsv"));


        List<String> ACTS_IN_LINES = new ArrayList<>();
        List<String> WRITES_LINES = new ArrayList<>();
        List<String> DIRECTS_LINES = new ArrayList<>();

        assert crew_lines != null;
        assert actors_lines != null;

        crew_lines.forEach(line -> {
            String[] parts = line.split("\t");
            String tConst = parts[0];
            String[] directors = parts[1].split(",");
            String[] writers = parts[2].split(",");

            for (String director : directors) {
                if (!director.equals("\\N")) {
                    DIRECTS_LINES.add(director + "\t" + tConst);
                }
            }

            for (String writer : writers) {
                if (!writer.equals("\\N")) {
                    WRITES_LINES.add(writer + "\t" + tConst);
                }
            }
        });

        actors_lines.forEach(line -> {
            String[] parts = line.split("\t");
            String tConst = parts[0];
            String[] actors = parts[1].split(",");

            for (String actor : actors) {
                if (!actor.equals("\\N")) {
                    ACTS_IN_LINES.add(actor + " \t" + tConst);
                }
            }
        });

        writeFile("/Users/zach/Documents/Databases/IMDB_Database/Import_Data/acts_in_data.tsv", ACTS_IN_LINES);
        writeFile("/Users/zach/Documents/Databases/IMDB_Database/Import_Data/directs_data.tsv", DIRECTS_LINES);
        writeFile("/Users/zach/Documents/Databases/IMDB_Database/Import_Data/writes_data.tsv", WRITES_LINES);
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
