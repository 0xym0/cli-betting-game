package ru.oxymo.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("bonus")
@JsonIgnoreProperties(ignoreUnknown = true)
public class BonusSymbol extends Symbol {
    @JsonProperty("extra")
    private int extra;
    @JsonProperty("impact")
    private String impact;

    public int getExtra() {
        return extra;
    }

    public void setExtra(int extra) {
        this.extra = extra;
    }

    public String getImpact() {
        return impact;
    }

    public void setImpact(String impact) {
        this.impact = impact;
    }
}