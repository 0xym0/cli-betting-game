package ru.oxymo.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.oxymo.data.ProbabilityMap;
import ru.oxymo.data.StandardSymbolProbability;
import ru.oxymo.data.SymbolProbability;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class MatrixUtilsTest {

    @Test
    @DisplayName("Throwing exception in case of invalid rows value")
    void throwExceptionOnGenerateMatrix() {
        assertThrows(IllegalArgumentException.class, () ->
                MatrixUtils.generateMatrix(Integer.MIN_VALUE, 3,
                        getProbabilityMap(Collections.emptyMap(), 3, "A")));
        assertThrows(IllegalArgumentException.class, () ->
                MatrixUtils.generateMatrix(0, 0,
                        getProbabilityMap(Collections.emptyMap(), 3, "A")));
        assertThrows(IllegalArgumentException.class, () ->
                MatrixUtils.generateMatrix(5, 5, new ProbabilityMap()));
        assertThrows(IllegalArgumentException.class, () ->
                MatrixUtils.generateMatrix(3, 3, null));
    }

    @Test
    @DisplayName("Correct generation of 4x4 F-matrix")
    void correct4x4GenerateMatrix() {
        final int size = 4;
        final String symbolString = "F";
        ProbabilityMap probabilityMap = getProbabilityMap(Collections.emptyMap(), size, symbolString);
        String[][] matrix = MatrixUtils.generateMatrix(size, size, probabilityMap);
        for (String[] row : matrix) {
            for (String item : row) {
                assertEquals(symbolString, item);
            }
        }
    }

    @Test
    @DisplayName("Correct generation of 3x3 D-matrix with bonus symbol")
    void correct3x3GenerateMatrixWithBonusSymbol() {
        final int size = 3;
        final String symbolString = "D";
        ProbabilityMap probabilityMap = getProbabilityMap(Map.of("10x", 1), size, symbolString);
        String[][] matrix = MatrixUtils.generateMatrix(size, size, probabilityMap);
        int counter = 0;
        for (String[] row : matrix) {
            for (String item : row) {
                if (symbolString.equals(item)) {
                    counter += 1;
                }
            }
        }
        assertTrue(counter >= size * size - 1);
    }

    @Test
    @DisplayName("Correct generation of 1x1 A-matrix")
    void correct1x1GenerateMatrix() {
        final int size = 1;
        final String symbolString = "A";
        ProbabilityMap probabilityMap = getProbabilityMap(Collections.emptyMap(), size, symbolString);
        String[][] matrix = MatrixUtils.generateMatrix(size, size, probabilityMap);
        assertEquals(symbolString, matrix[0][0]);
    }

    private static ProbabilityMap getProbabilityMap(Map<String, Integer> symbolProbabilityMap, int size, String symbolString) {
        ProbabilityMap probabilityMap = new ProbabilityMap();
        SymbolProbability bonusSymbolProbability = new SymbolProbability();
        bonusSymbolProbability.setSymbolProbabilityMap(symbolProbabilityMap);
        probabilityMap.setBonusSymbolProbability(bonusSymbolProbability);
        probabilityMap.setStandardSymbolProbabilityList(
                getStandardSymbolProbabilityList(size, symbolString)
        );
        return probabilityMap;
    }

    private static List<StandardSymbolProbability> getStandardSymbolProbabilityList(int size, String symbolString) {
        return IntStream.range(0, size * size)
                .boxed()
                .map(integer -> {
                    StandardSymbolProbability symbolProbability = new StandardSymbolProbability();
                    symbolProbability.setRow(integer / size);
                    symbolProbability.setColumn(integer % size);
                    symbolProbability.setSymbolProbabilityMap(Map.of(symbolString, 1));
                    return symbolProbability;
                })
                .collect(Collectors.toList());
    }
}