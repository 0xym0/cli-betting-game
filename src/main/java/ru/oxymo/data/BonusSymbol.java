package ru.oxymo.data;

import com.fasterxml.jackson.annotation.*;

@JsonTypeName("bonus")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({"extra", "reward_multiplier", "type", "impact"})
public class BonusSymbol extends Symbol {
    @JsonProperty("extra")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Integer extra;
    @JsonProperty("impact")
    private String impact;

    public Integer getExtra() {
        return extra;
    }

    public void setExtra(Integer extra) {
        this.extra = extra;
    }

    public String getImpact() {
        return impact;
    }

    public void setImpact(String impact) {
        this.impact = impact;
    }
}