package ru.oxymo.utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtils {
    public static String readFileContents(String pathToFileString) throws IOException {
        Path configurationPath = Paths.get(pathToFileString);
        return String.join("",
                Files.readAllLines(configurationPath, StandardCharsets.UTF_8));
    }
}