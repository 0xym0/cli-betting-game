package ru.oxymo.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ru.oxymo.utils.DoubleJSONSerializer;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "when",
        include = JsonTypeInfo.As.EXISTING_PROPERTY, visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = CountWinCombination.class, name = "same_symbols"),
        @JsonSubTypes.Type(value = LinearWinCombination.class, name = "linear_symbols")
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class WinCombination {
    @JsonProperty("reward_multiplier")
    @JsonSerialize(using = DoubleJSONSerializer.class)
    private double rewardMultiplier;
    @JsonProperty("when")
    private String when;
    @JsonProperty("group")
    private String group;

    public double getRewardMultiplier() {
        return rewardMultiplier;
    }

    public void setRewardMultiplier(double rewardMultiplier) {
        this.rewardMultiplier = rewardMultiplier;
    }

    public String getWhen() {
        return when;
    }

    public void setWhen(String when) {
        this.when = when;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}