package ru.oxymo.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProbabilityMap {
    @JsonProperty("standard_symbols")
    private List<StandardSymbolProbability> standardSymbolProbabilityList;
    @JsonProperty("bonus_symbols")
    private SymbolProbability bonusSymbolProbability;

    public List<StandardSymbolProbability> getStandardSymbolProbabilityList() {
        return standardSymbolProbabilityList;
    }

    public void setStandardSymbolProbabilityList(List<StandardSymbolProbability> standardSymbolProbabilityList) {
        this.standardSymbolProbabilityList = standardSymbolProbabilityList;
    }

    public SymbolProbability getBonusSymbolProbability() {
        return bonusSymbolProbability;
    }

    public void setBonusSymbolProbability(SymbolProbability bonusSymbolProbability) {
        this.bonusSymbolProbability = bonusSymbolProbability;
    }
}