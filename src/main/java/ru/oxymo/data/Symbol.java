package ru.oxymo.data;

import com.fasterxml.jackson.annotation.*;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = StandardSymbol.class, name = "standard"),
        @JsonSubTypes.Type(value = BonusSymbol.class, name = "bonus")
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class Symbol {
    @JsonProperty("type")
    private String type;
    @JsonProperty("reward_multiplier")
    private double rewardMultiplier;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getRewardMultiplier() {
        return rewardMultiplier;
    }

    public void setRewardMultiplier(double rewardMultiplier) {
        this.rewardMultiplier = rewardMultiplier;
    }
}