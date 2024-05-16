package ru.oxymo.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SymbolProbability {
    @JsonProperty("symbols")
    private Map<String, Integer> symbolProbabilityMap;

    public Map<String, Integer> getSymbolProbabilityMap() {
        return symbolProbabilityMap;
    }

    public void setSymbolProbabilityMap(Map<String, Integer> symbolProbabilityMap) {
        this.symbolProbabilityMap = symbolProbabilityMap;
    }
}