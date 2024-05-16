package ru.oxymo.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("linear_symbols")
@JsonIgnoreProperties(ignoreUnknown = true)
public class LinearWinCombination extends WinCombination {
    @JsonProperty("covered_areas")
    private String[][] coveredAreasMatrix;

    public String[][] getCoveredAreasMatrix() {
        return coveredAreasMatrix;
    }

    public void setCoveredAreasMatrix(String[][] coveredAreasMatrix) {
        this.coveredAreasMatrix = coveredAreasMatrix;
    }
}