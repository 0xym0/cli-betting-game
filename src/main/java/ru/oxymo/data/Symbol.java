package ru.oxymo.data;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ru.oxymo.utils.DoubleJSONSerializer;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type",
        include = JsonTypeInfo.As.EXISTING_PROPERTY, visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = StandardSymbol.class, name = "standard"),
        @JsonSubTypes.Type(value = BonusSymbol.class, name = "bonus")
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class Symbol {
    @JsonProperty("reward_multiplier")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = DoubleJSONSerializer.class)
    private Double rewardMultiplier;
    @JsonProperty("type")
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getRewardMultiplier() {
        return rewardMultiplier;
    }

    public void setRewardMultiplier(Double rewardMultiplier) {
        this.rewardMultiplier = rewardMultiplier;
    }
}