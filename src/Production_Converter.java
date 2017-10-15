import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Production_Converter {
    //tconst	titleType	primaryTitle	originalTitle	isAdult	startYear	endYear	runtimeMinutes	genres
    public static void main(String[] args) {
        Stream<String> basicsLines = getFileStream(new File("/Users/zach/Documents/Databases/data/title.basics.tsv"));

        List<String> PRODUCTION_LINES = new ArrayList<>();
        List<String> GENRE_LINES = new ArrayList<>();

        assert basicsLines != null;

        basicsLines.forEach(line -> {
            String[] parts = line.split("\t");
            String tConst = parts[0];
            String titleType = parts[1];
            String primaryTitle = parts[2];
            String originalTitle = parts[3];
            String isAdult = parts[4];
            String startYear = parts[5];
            String endYear = parts[6];
            String runtime = parts[7];
            String[] genres = parts[8].split(",");

            PRODUCTION_LINES.add(tConst + "\t" + titleType + "\t" + primaryTitle + "\t" + originalTitle +
                    "\t" + isAdult + "\t" + startYear + "\t" + endYear + "\t" + runtime);

            for (String genre : genres) {
                if (!genre.equals("\\N")) {
                    GENRE_LINES.add(tConst + "\t" + genre);
                }
            }
        });

        writeFile("/Users/zach/Documents/Databases/IMDB_Database/Import_Data/production_data.tsv", PRODUCTION_LINES);
        writeFile("/Users/zach/Documents/Databases/IMDB_Database/Import_Data/genre_data.tsv", GENRE_LINES);
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
