package ru.oxymo.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Result {
    @JsonProperty("matrix")
    private String[][] matrix;
    @JsonProperty("reward")
    private double reward;
    @JsonProperty("applied_winning_combinations")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Map<String, List<String>> winningCombinationsMap;
    @JsonProperty("applied_bonus_symbol")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String appliedBonusSymbol;

    public Result(String[][] matrix, double reward, Map<String, List<String>> winningCombinationsMap, String appliedBonusSymbol) {
        this.matrix = matrix;
        this.reward = reward;
        this.winningCombinationsMap = winningCombinationsMap;
        this.appliedBonusSymbol = appliedBonusSymbol;
    }

    public String[][] getMatrix() {
        return matrix;
    }

    public double getReward() {
        return reward;
    }

    public Map<String, List<String>> getWinningCombinationsMap() {
        return winningCombinationsMap;
    }

    public String getAppliedBonusSymbol() {
        return appliedBonusSymbol;
    }

    @Override
    public String toString() {
        return Result.class.getSimpleName() + '{' +
                "matrix=" + Arrays.toString(matrix) +
                ", reward=" + reward +
                ", winningCombinationsMap=" + winningCombinationsMap +
                ", appliedBonusSymbol='" + appliedBonusSymbol + '\'' +
                '}';
    }
}