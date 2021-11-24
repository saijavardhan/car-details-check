package utilities;

import com.opencsv.bean.CsvToBeanBuilder;
import model.CarDetails;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class FileUtils {

    public Set<String> getRegistrationNumbersFromAllInputFiles(String inputFilesPath, String fileNameStartsWith) throws IOException {
        List<File> filesToIterate = getFilesStartingWithName(inputFilesPath, fileNameStartsWith);
        Set<String> regNumbers = new HashSet<String>();
        for (File file : filesToIterate) {
            byte[] fileBytes = Files.readAllBytes(file.getAbsoluteFile().toPath());
            String fileContent = new String(fileBytes);
            String regSevenChars = "(^[A-Z]{2}[0-9]{2}[A-Z]{3}$)";
            String regFourChars = "(^[A-Z]{2}[0-9]{2}$)";
            String regThreeChars = "(^[A-Z]{3}$)";
            List<String> splittedChars = Arrays.asList(fileContent.split("\\W+"));
            for (int i = 0; i < splittedChars.size(); i++) {
                if (Pattern.matches(regSevenChars, splittedChars.get(i))) {
                    regNumbers.add(splittedChars.get(i));
                }
                if (Pattern.matches(regFourChars, splittedChars.get(i))) {
                    if (i + 1 <= splittedChars.size() && Pattern.matches(regThreeChars, splittedChars.get(i + 1))) {
                        regNumbers.add(splittedChars.get(i) + splittedChars.get(i + 1));
                    }
                }
            }
        }
        return regNumbers;
    }

    public static List<CarDetails> convertCSVToCarDetailsObjects(final String filePath, String fileName) throws IOException, ClassNotFoundException {
        File file = getFilesStartingWithName( filePath,fileName).get(0);
        List<CarDetails> expectedCarDetails = new CsvToBeanBuilder(new FileReader(file.getAbsolutePath()))
                .withType(CarDetails.class)
                .build()
                .parse();
        return expectedCarDetails;
    }

    private static List<File> getFilesStartingWithName(String filepath, String fileNameStartsWith) throws IOException {
        return Files.list(Paths.get(filepath))
                .filter(Files::isRegularFile)
                .map(Path::toFile)
                .filter(file -> file.getName().startsWith(fileNameStartsWith))
                .collect(Collectors.toList());
    }
}
