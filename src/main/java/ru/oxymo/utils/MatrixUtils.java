package ru.oxymo.utils;

import ru.oxymo.data.ProbabilityMap;
import ru.oxymo.data.StandardSymbolProbability;
import ru.oxymo.data.SymbolProbability;

import java.util.*;
import java.util.stream.Collectors;

public class MatrixUtils {
    private static final Random random = new Random();

    public static String[][] generateMatrix(int rows, int columns, ProbabilityMap probabilityMap) {
        checkInputParameters(rows, columns, probabilityMap);
        String[][] matrix = new String[rows][columns];

        Map<String, Map<String, Integer>> standardSymbolPositionProbabilityMap =
                getStandardSymbolPositionProbabilityMap(probabilityMap.getStandardSymbolProbabilityList());
        boolean isBonusSymbolAdded = false;
        Map<String, Integer> bonusSymbolProbabilityMap =
                probabilityMap.getBonusSymbolProbability().getSymbolProbabilityMap();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                Map<String, Integer> standardSymbolProbabilityMap =
                        standardSymbolPositionProbabilityMap.get(getPositionKey(i, j));
                String randomStandardSymbol = getRandomSymbol(standardSymbolProbabilityMap);
                if (isBonusSymbolAdded ||
                        bonusSymbolProbabilityMap == null || bonusSymbolProbabilityMap.isEmpty()) {
                    matrix[i][j] = randomStandardSymbol;
                } else {
                    String randomBonusSymbol = getRandomSymbol(bonusSymbolProbabilityMap);
                    double randomProbability = random.nextDouble();
                    double bonusSymbolProbability = 1.0 / (rows * columns);

//                    Since one bonus symbol possible in matrix
                    if (Double.compare(randomProbability, bonusSymbolProbability) <= 0) {
                        matrix[i][j] = randomBonusSymbol;
                        isBonusSymbolAdded = true;
                    } else {
                        matrix[i][j] = randomStandardSymbol;
                    }
                }
            }
        }
        return matrix;
    }

    private static void checkInputParameters(int rows, int columns, ProbabilityMap probabilityMap) {
        if (rows <= 0) {
            throw new IllegalArgumentException("Invalid rows count passed to generateMatrix method: " + rows);
        }
        if (columns <= 0) {
            throw new IllegalArgumentException("Invalid columns count passed to generateMatrix method: " + columns);
        }
        if (probabilityMap == null ||
                probabilityMap.getBonusSymbolProbability() == null ||
                probabilityMap.getStandardSymbolProbabilityList() == null) {
            throw new IllegalArgumentException("Empty symbol probability map passed to generateMatrix method: " + probabilityMap);
        }
    }

    private static LinkedHashMap<String, Map<String, Integer>> getStandardSymbolPositionProbabilityMap(
            List<StandardSymbolProbability> standardSymbolProbabilityList) {
        return standardSymbolProbabilityList
                .stream()
                .collect(Collectors.toMap(
                        symbolProbability -> getPositionKey(
                                symbolProbability.getRow(), symbolProbability.getColumn()),
                        SymbolProbability::getSymbolProbabilityMap,
                        (oldValue, newValue) -> oldValue,
                        LinkedHashMap::new));
    }

    private static String getRandomSymbol(Map<String, Integer> symbolProbabilityMap) {
        if (symbolProbabilityMap == null) {
            throw new IllegalArgumentException("Null symbol probability map passed to getRandomSymbol method");
        }
        int size = symbolProbabilityMap.size();
        int[] probabilityArray = new int[size];
        String[] symbolArray = new String[size];
        int k = 0;
        int standardProbabilitiesSum = 0;
        for (Map.Entry<String, Integer> entry : symbolProbabilityMap.entrySet()) {
            symbolArray[k] = entry.getKey();
            probabilityArray[k] = entry.getValue();
            standardProbabilitiesSum += entry.getValue();
            k++;
        }

        double[] cumulativeDistributionArray =
                getCumulativeDistributionArray(probabilityArray, standardProbabilitiesSum);

        double randomProbability = random.nextDouble();
        for (int i = 0; i < size; i++) {
            if (randomProbability > cumulativeDistributionArray[i]) continue;
            return symbolArray[i];
        }
        throw new NoSuchElementException("Empty symbol probability map passed");
    }

    private static double[] getCumulativeDistributionArray(int[] probabilityArray, int probabilitiesSum) {
        int size = probabilityArray.length;
        double[] cumulativeDistributionArray = new double[size];
        for (int i = 0; i < size; i++) {
            double previous = (i == 0) ?
                    0.0 : cumulativeDistributionArray[i - 1];
            if (i == size - 1) {
                cumulativeDistributionArray[i] = 1.0;
            } else {
                cumulativeDistributionArray[i] =
                        previous + probabilityArray[i] * 1.0 / probabilitiesSum;
            }
        }
        return cumulativeDistributionArray;
    }

    private static String getPositionKey(int i, int j) {
        return i + ":" + j;
    }
}