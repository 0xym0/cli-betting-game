package ru.oxymo.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("standard")
@JsonIgnoreProperties(ignoreUnknown = true)
public class StandardSymbol extends Symbol {
}