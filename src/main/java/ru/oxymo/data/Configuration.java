package ru.oxymo.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Configuration {
    @JsonProperty("columns")
    private int columns;
    @JsonProperty("rows")
    private int rows;
    @JsonProperty("symbols")
    private Map<String, Symbol> symbolMap;
    @JsonProperty("probabilities")
    private ProbabilityMap probabilityMap;
    @JsonProperty("win_combinations")
    private Map<String, WinCombination> winCombinationMap;

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public Map<String, Symbol> getSymbolMap() {
        return symbolMap;
    }

    public void setSymbolMap(Map<String, Symbol> symbolMap) {
        this.symbolMap = symbolMap;
    }

    public ProbabilityMap getProbabilityMap() {
        return probabilityMap;
    }

    public void setProbabilityMap(ProbabilityMap probabilityMap) {
        this.probabilityMap = probabilityMap;
    }

    public Map<String, WinCombination> getWinCombinationMap() {
        return winCombinationMap;
    }

    public void setWinCombinationMap(Map<String, WinCombination> winCombinationMap) {
        this.winCombinationMap = winCombinationMap;
    }
}