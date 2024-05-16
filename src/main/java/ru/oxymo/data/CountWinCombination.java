package ru.oxymo.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("same_symbols")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({"reward_multiplier", "when", "count", "group"})
public class CountWinCombination extends WinCombination {
    @JsonProperty("count")
    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}