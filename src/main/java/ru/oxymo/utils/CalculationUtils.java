package ru.oxymo.utils;

import ru.oxymo.data.*;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CalculationUtils {
    public static Result getCalculationResult(String[][] matrix,
                                              long bettingAmount,
                                              Map<String, Symbol> symbolMap,
                                              Map<String, WinCombination> winCombinationMap) {
        checkInputParameters(matrix, bettingAmount, symbolMap, winCombinationMap);
        String bonusSymbolCode = null;
        Map<String, Integer> symbolCountingMap = new HashMap<>();
        for (String[] row : matrix) {
            for (String symbolString : row) {
                Symbol symbol = symbolMap.get(symbolString);
                if (symbol == null) {
                    throw new IllegalArgumentException(
                            "Not found symbol data by symbol code = " + symbolString + " in symbol map: " + symbolMap);
                }
                if (symbol.getClass().equals(StandardSymbol.class)) {
                    symbolCountingMap.merge(symbolString, 1, Integer::sum);
                } else if (symbol.getClass().equals(BonusSymbol.class)) {
                    if (bonusSymbolCode == null) {
                        bonusSymbolCode = symbolString;
                    } else {
                        throw new IllegalArgumentException(
                                "Only one bonus symbol possible in the matrix: " + Arrays.deepToString(matrix));
                    }
                }
            }
        }
        Map<String, List<String>> appliedSymbolWinCombinations = new HashMap<>();
        winCombinationMap.forEach((winCombinationCode, winCombination) -> {
            if (winCombination != null && winCombination.getClass().equals(CountWinCombination.class)) {
                int count = ((CountWinCombination) winCombination).getCount();
                symbolCountingMap.forEach((symbolString, symbolCount) -> {
                    if (count == symbolCount) {
                        appliedSymbolWinCombinations.computeIfAbsent(symbolString, key -> new ArrayList<>())
                                .add(winCombinationCode);
                    }
                });
            } else if (winCombination != null && winCombination.getClass().equals(LinearWinCombination.class)) {
                String[][] coveredAreasMatrix = ((LinearWinCombination) winCombination).getCoveredAreasMatrix();
                checkAndSaveLinearWinCombination(
                        matrix, winCombinationCode, coveredAreasMatrix, appliedSymbolWinCombinations);
            }
        });

        double reward = calculateReward(
                bettingAmount, symbolMap, winCombinationMap, appliedSymbolWinCombinations);
        reward = applyBonusToRewardIfExists(reward, bonusSymbolCode, symbolMap);
        return new Result(matrix, reward, appliedSymbolWinCombinations,
                reward > 0 ? bonusSymbolCode : null);
    }

    private static void checkInputParameters(String[][] matrix,
                                             long bettingAmount,
                                             Map<String, Symbol> symbolMap,
                                             Map<String, WinCombination> winCombinationMap) {
        if (matrix == null || matrix.length == 0) {
            throw new IllegalArgumentException("Empty matrix passed to getCalculationResult method");
        }
        if (bettingAmount <= 0) {
            throw new IllegalArgumentException("Invalid betting amount passedto getCalculationResult method");
        }
        if (symbolMap == null || symbolMap.isEmpty()) {
            throw new IllegalArgumentException("Empty symbol map passed to getCalculationResult method");
        }
        if (winCombinationMap == null || winCombinationMap.isEmpty()) {
            throw new IllegalArgumentException("Empty win combination map passed to getCalculationResult method");
        }
    }

    private static void checkAndSaveLinearWinCombination(String[][] matrix,
                                                         String winCombinationCode,
                                                         String[][] coveredAreasMatrix,
                                                         Map<String, List<String>> appliedSymbolWinCombinations) {
        for (String[] coveredArea : coveredAreasMatrix) {
            Arrays.stream(coveredArea)
                    .map(cellCode -> {
                        String[] stringPair = cellCode.split(":", 2);
                        int row = Integer.parseInt(stringPair[0]);
                        int column = Integer.parseInt(stringPair[1]);
                        return matrix[row][column];
                    })
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                    .entrySet()
                    .stream()
                    .filter(symbolCountingEntry -> symbolCountingEntry.getValue() == coveredArea.length)
                    .map(Map.Entry::getKey)
                    .findFirst()
                    .ifPresent(symbolString ->
                            appliedSymbolWinCombinations.computeIfAbsent(symbolString, key -> new ArrayList<>())
                                    .add(winCombinationCode));
        }
    }

    private static double calculateReward(long bettingAmount,
                                          Map<String, Symbol> symbolMap,
                                          Map<String, WinCombination> winCombinationMap,
                                          Map<String, List<String>> appliedSymbolWinCombinations) {
        double reward = 0;
        for (Map.Entry<String, List<String>> entry : appliedSymbolWinCombinations.entrySet()) {
            double currentReward = bettingAmount * symbolMap.get(entry.getKey()).getRewardMultiplier();
            for (String winCombinationCode : entry.getValue()) {
                currentReward *= winCombinationMap.get(winCombinationCode).getRewardMultiplier();
            }
            reward += currentReward;
        }
        return reward;
    }

    private static double applyBonusToRewardIfExists(double reward,
                                                     String bonusSymbolCode,
                                                     Map<String, Symbol> symbolMap) {
        if (bonusSymbolCode != null && reward > 0) {
            BonusSymbol bonusSymbol = (BonusSymbol) symbolMap.get(bonusSymbolCode);
            switch (bonusSymbol.getImpact()) {
                case "multiply_reward": {
                    reward *= bonusSymbol.getRewardMultiplier();
                    break;
                }
                case "extra_bonus": {
                    reward += bonusSymbol.getExtra();
                    break;
                }
            }
        }
        return reward;
    }
}