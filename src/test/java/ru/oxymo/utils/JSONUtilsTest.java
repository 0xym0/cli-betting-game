package ru.oxymo.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import ru.oxymo.data.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JSONUtilsTest {

    @Test
    void serializeAndDeserializeSymbolsTest() throws JsonProcessingException {
        checkJSONStringDeserializationAndSerialization(
                "{\"reward_multiplier\":1.5,\"type\":\"standard\"}", StandardSymbol.class);
        checkJSONStringDeserializationAndSerialization(
                "{\"reward_multiplier\":5,\"type\":\"bonus\",\"impact\":\"multiply_reward\"}", BonusSymbol.class);
        checkJSONStringDeserializationAndSerialization(
                "{\"extra\":500,\"type\":\"bonus\",\"impact\":\"extra_bonus\"}", BonusSymbol.class);
        checkJSONStringDeserializationAndSerialization(
                "{\"type\":\"bonus\",\"impact\":\"miss\"}", BonusSymbol.class);
    }

    @Test
    void serializeAndDeserializeWinCombinationsTest() throws JsonProcessingException {
        checkJSONStringDeserializationAndSerialization(
                "{\"reward_multiplier\":1,\"when\":\"same_symbols\",\"count\":3,\"group\":\"same_symbols\"}",
                CountWinCombination.class);
        checkJSONStringDeserializationAndSerialization(
                "{\"reward_multiplier\":5,\"when\":\"linear_symbols\",\"group\":\"ltr_diagonally_linear_symbols\",\"covered_areas\":[[\"0:0\",\"1:1\",\"2:2\"]]}",
                LinearWinCombination.class);
    }

    @Test
    void writeValueToFormattedString() throws JsonProcessingException {
        String expectedString1 = "{\r\n" +
                "  \"matrix\" : [ [ \"+500\", \"E\", \"F\" ], [ \"B\", \"F\", \"F\" ], [ \"F\", \"B\", \"B\" ] ],\r\n" +
                "  \"reward\" : 4125,\r\n" +
                "  \"applied_winning_combinations\" : {\r\n" +
                "    \"B\" : [ \"same_symbol_3_times\" ],\r\n" +
                "    \"F\" : [ \"same_symbol_4_times\", \"same_symbols_diagonally_right_to_left\" ]\r\n" +
                "  },\r\n" +
                "  \"applied_bonus_symbol\" : \"+500\"\r\n" +
                "}";
        Map<String, List<String>> winningCombinationsMap = new LinkedHashMap<>();
        winningCombinationsMap.put("B", List.of("same_symbol_3_times"));
        winningCombinationsMap.put("F", List.of("same_symbol_4_times", "same_symbols_diagonally_right_to_left"));
        Result result1 = new Result(
                new String[][]{{"+500", "E", "F"}, {"B", "F", "F"}, {"F", "B", "B"}},
                4125.0,
                winningCombinationsMap,
                "+500");
        String formattedOutput1 = JSONUtils.writeValueToFormattedString(result1);
        assertEquals(expectedString1, formattedOutput1);

        String expectedString2 = "{\r\n" +
                "  \"matrix\" : [ [ \"A\", \"A\", \"B\" ], [ \"C\", \"C\", \"B\" ], [ \"D\", \"F\", \"E\" ] ],\r\n" +
                "  \"reward\" : 0\r\n" +
                "}";
        Result result2 = new Result(
                new String[][]{{"A", "A", "B"}, {"C", "C", "B"}, {"D", "F", "E"}},
                0.0,
                null, null);
        String formattedOutput2 = JSONUtils.writeValueToFormattedString(result2);
        assertEquals(expectedString2, formattedOutput2);
    }

    private static <T> void checkJSONStringDeserializationAndSerialization(String originalJSONString, Class<T> classType) throws JsonProcessingException {
        T object = JSONUtils.readValueFromString(originalJSONString, classType);
        String symbolString = JSONUtils.writeValueToString(object);
        assertEquals(originalJSONString, symbolString);
    }
}