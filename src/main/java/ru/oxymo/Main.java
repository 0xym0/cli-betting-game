package ru.oxymo;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.fasterxml.jackson.core.JsonProcessingException;
import ru.oxymo.data.Configuration;
import ru.oxymo.data.Result;
import ru.oxymo.utils.CalculationUtils;
import ru.oxymo.utils.JSONUtils;
import ru.oxymo.utils.MatrixUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    @Parameter(names = {"--config", "-c"})
    String configurationFileString;
    @Parameter(names = {"--betting-amount", "-b"})
    long bettingAmount;

    private Configuration configuration;

    public static void main(String[] args) {
        Main main = new Main();
        JCommander.newBuilder()
                .addObject(main)
                .build()
                .parse(args);

        try {
            main.loadConfiguration();
            main.generateResult();
        } catch (IOException | RuntimeException e) {
            System.out.println("Error caught - " + e.getClass().getSimpleName() + ": " + e.getMessage());
        }
    }

    private void generateResult() throws JsonProcessingException {
        String[][] matrix = MatrixUtils.generateMatrix(configuration.getRows(),
                configuration.getColumns(), configuration.getProbabilityMap());
        Result result = CalculationUtils.getCalculationResult(
                matrix,
                bettingAmount,
                configuration.getSymbolMap(),
                configuration.getWinCombinationMap());
        System.out.println(JSONUtils.writeValueToFormattedString(result));
    }

    private void loadConfiguration() throws IOException {
        Path configurationPath = Paths.get(configurationFileString);
        String contentString = String.join("",
                Files.readAllLines(configurationPath, StandardCharsets.UTF_8));
        configuration = JSONUtils.readValueFromString(contentString, Configuration.class);
    }
}