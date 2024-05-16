package ru.oxymo.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.oxymo.data.Result;
import ru.oxymo.data.Symbol;
import ru.oxymo.data.WinCombination;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CalculationUtilsTest {
    private Map<String, Symbol> symbolMap;
    private Map<String, WinCombination> winCombinationMap;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        String symbolsJSSONString = "{\"A\":{\"reward_multiplier\":50,\"type\":\"standard\"},\"B\":{\"reward_multiplier\":25,\"type\":\"standard\"},\"C\":{\"reward_multiplier\":10,\"type\":\"standard\"},\"D\":{\"reward_multiplier\":5,\"type\":\"standard\"},\"E\":{\"reward_multiplier\":3,\"type\":\"standard\"},\"F\":{\"reward_multiplier\":1.5,\"type\":\"standard\"},\"10x\":{\"reward_multiplier\":10,\"type\":\"bonus\",\"impact\":\"multiply_reward\"},\"5x\":{\"reward_multiplier\":5,\"type\":\"bonus\",\"impact\":\"multiply_reward\"},\"+1000\":{\"extra\":1000,\"type\":\"bonus\",\"impact\":\"extra_bonus\"},\"+500\":{\"extra\":500,\"type\":\"bonus\",\"impact\":\"extra_bonus\"},\"MISS\":{\"type\":\"bonus\",\"impact\":\"miss\"}}";
        symbolMap =
                JSONUtils.readValueFromString(symbolsJSSONString, new TypeReference<>() {
                });
        String winCombinationJSONString = "{\"same_symbol_3_times\":{\"reward_multiplier\":1,\"when\":\"same_symbols\",\"count\":3,\"group\":\"same_symbols\"},\"same_symbol_4_times\":{\"reward_multiplier\":1.5,\"when\":\"same_symbols\",\"count\":4,\"group\":\"same_symbols\"},\"same_symbol_5_times\":{\"reward_multiplier\":2,\"when\":\"same_symbols\",\"count\":5,\"group\":\"same_symbols\"},\"same_symbol_6_times\":{\"reward_multiplier\":3,\"when\":\"same_symbols\",\"count\":6,\"group\":\"same_symbols\"},\"same_symbol_7_times\":{\"reward_multiplier\":5,\"when\":\"same_symbols\",\"count\":7,\"group\":\"same_symbols\"},\"same_symbol_8_times\":{\"reward_multiplier\":10,\"when\":\"same_symbols\",\"count\":8,\"group\":\"same_symbols\"},\"same_symbol_9_times\":{\"reward_multiplier\":20,\"when\":\"same_symbols\",\"count\":9,\"group\":\"same_symbols\"},\"same_symbols_horizontally\":{\"reward_multiplier\":2,\"when\":\"linear_symbols\",\"group\":\"horizontally_linear_symbols\",\"covered_areas\":[[\"0:0\",\"0:1\",\"0:2\"],[\"1:0\",\"1:1\",\"1:2\"],[\"2:0\",\"2:1\",\"2:2\"]]},\"same_symbols_vertically\":{\"reward_multiplier\":2,\"when\":\"linear_symbols\",\"group\":\"vertically_linear_symbols\",\"covered_areas\":[[\"0:0\",\"1:0\",\"2:0\"],[\"0:1\",\"1:1\",\"2:1\"],[\"0:2\",\"1:2\",\"2:2\"]]},\"same_symbols_diagonally_left_to_right\":{\"reward_multiplier\":5,\"when\":\"linear_symbols\",\"group\":\"ltr_diagonally_linear_symbols\",\"covered_areas\":[[\"0:0\",\"1:1\",\"2:2\"]]},\"same_symbols_diagonally_right_to_left\":{\"reward_multiplier\":5,\"when\":\"linear_symbols\",\"group\":\"rtl_diagonally_linear_symbols\",\"covered_areas\":[[\"0:2\",\"1:1\",\"2:0\"]]}}";
        winCombinationMap =
                JSONUtils.readValueFromString(winCombinationJSONString, new TypeReference<>() {
                });
    }

    @Test
    @DisplayName("Testing reward for betting 100 for matrix: [[+500, F, C], [F, E, B], [F, F, F]]")
    void getCalculationResultTest1() {
        String[][] matrix = {{"+500", "F", "C"}, {"F", "E", "B"}, {"F", "F", "F"}};
        Result result =
                CalculationUtils.getCalculationResult(matrix, 100L, symbolMap, winCombinationMap);
        assertEquals(1100, result.getReward());
        assertEquals("+500", result.getAppliedBonusSymbol());
        assertEquals(List.of("same_symbol_5_times", "same_symbols_horizontally"),
                result.getWinningCombinationsMap().get("F"));
    }

    @Test
    @DisplayName("Testing reward for betting 500 for matrix: [[F, F, E], [10x, F, A], [B, C, B]]")
    void getCalculationResultTest2() {
        String[][] matrix = {{"F", "F", "E"}, {"10x", "F", "A"}, {"B", "C", "B"}};
        Result result =
                CalculationUtils.getCalculationResult(matrix, 500L, symbolMap, winCombinationMap);
        assertEquals(7500, result.getReward());
        assertEquals("10x", result.getAppliedBonusSymbol());
        assertEquals(List.of("same_symbol_3_times"),
                result.getWinningCombinationsMap().get("F"));
    }

    @Test
    @DisplayName("Testing reward for betting 100 for matrix: [[A, A, B], [A, +1000, B], [A, A, B]]")
    void getCalculationResultTest3() {
        String[][] matrix = {{"A", "A", "B"}, {"A", "+1000", "B"}, {"A", "A", "B"}};
        Result result =
                CalculationUtils.getCalculationResult(matrix, 100L, symbolMap, winCombinationMap);
        assertEquals(26000, result.getReward());
        assertEquals("+1000", result.getAppliedBonusSymbol());
        assertEquals(List.of("same_symbol_5_times", "same_symbols_vertically"),
                result.getWinningCombinationsMap().get("A"));
        assertEquals(List.of("same_symbol_3_times", "same_symbols_vertically"),
                result.getWinningCombinationsMap().get("B"));
    }

    @Test
    @DisplayName("Testing reward for betting 100 for matrix: [[F, E, B], [E, E, B], [5x, D, D]]")
    void getCalculationResultTest4() {
        String[][] matrix = {{"F", "E", "B"}, {"E", "E", "B"}, {"5x", "D", "D"}};
        Result result =
                CalculationUtils.getCalculationResult(matrix, 100L, symbolMap, winCombinationMap);
        assertEquals(1500, result.getReward());
        assertEquals("5x", result.getAppliedBonusSymbol());
        assertEquals(List.of("same_symbol_3_times"),
                result.getWinningCombinationsMap().get("E"));
    }

    @Test
    @DisplayName("Testing reward for betting 1000 for matrix: [[F, E, B], [A, E, C], [5x, D, D]]")
    void getCalculationResultTest5() {
        String[][] matrix = {{"F", "E", "B"}, {"A", "E", "C"}, {"5x", "D", "D"}};
        Result result =
                CalculationUtils.getCalculationResult(matrix, 1000L, symbolMap, winCombinationMap);
        assertEquals(0, result.getReward());
        assertNull(result.getAppliedBonusSymbol());
        assertTrue(result.getWinningCombinationsMap().isEmpty());
    }

    @Test
    @DisplayName("Testing reward for betting 100 for matrix: [[D, D, E], [F, E, E], [E, E, E]]")
    void getCalculationResultTest6() {
        String[][] matrix = {{"D", "D", "E"}, {"F", "E", "E"}, {"E", "E", "E"}};
        Result result =
                CalculationUtils.getCalculationResult(matrix, 100L, symbolMap, winCombinationMap);
        assertEquals(18000, result.getReward());
        assertNull(result.getAppliedBonusSymbol());
        assertEquals(List.of("same_symbol_6_times", "same_symbols_horizontally",
                        "same_symbols_vertically", "same_symbols_diagonally_right_to_left"),
                result.getWinningCombinationsMap().get("E"));
    }

    @Test
    @DisplayName("Testing incorrect input parameters")
    void throwExceptionOnGetCalculationResultTest() {
        String[][] matrix = {{"F", "E", "B"}, {"E", "E", "B"}, {"5x", "D", "D"}};
        assertThrows(IllegalArgumentException.class, () ->
                CalculationUtils.getCalculationResult(null, 100L, symbolMap, winCombinationMap));
        assertThrows(IllegalArgumentException.class, () ->
                CalculationUtils.getCalculationResult(matrix, -100L, symbolMap, winCombinationMap));
        assertThrows(IllegalArgumentException.class, () ->
                CalculationUtils.getCalculationResult(matrix, 100L, null, winCombinationMap));
        assertThrows(IllegalArgumentException.class, () ->
                CalculationUtils.getCalculationResult(matrix, 100L, symbolMap, null));
        assertThrows(IllegalArgumentException.class, () ->
                CalculationUtils.getCalculationResult(matrix, 100L, Collections.emptyMap(), Collections.emptyMap()));
    }
}