## Description ##

This is a betting game, that will generate a matrix (for example 3x3) from symbols (based on probabilities for each
individual cell) and based on winning combinations user either will win or lost.
User should place a bet with any amount which called *betting amount*.

There are two types of symbols: Standard symbols and Bonus symbols.

**Standard symbols**: identifies if user won or lost the game based on winning combinations (combination can be X times
repeated symbols or symbols that follow a specific pattern)

**Bonus symbols**: Bonus symbols are only effective when there are at least one winning combinations matches with the
generated matrix.

Standard and Bonus symbols could be set up through configuration file.

## Configuration ##

Using the configuration JSON you can specify:

* size of symbols matrix;
* standard and bonus symbols, how they affect the reward amount;
* set up symbol probabilities for specific matrix positions;
* set up specific win combinations of symbols.

Here is the example of `configuration.json` file for running the application:

```json
{
  "columns": 3,
  "rows": 3,
  "symbols": {
    "A": {
      "reward_multiplier": 50,
      "type": "standard"
    },
    "B": {
      "reward_multiplier": 25,
      "type": "standard"
    },
    "C": {
      "reward_multiplier": 10,
      "type": "standard"
    },
    "D": {
      "reward_multiplier": 5,
      "type": "standard"
    },
    "E": {
      "reward_multiplier": 3,
      "type": "standard"
    },
    "F": {
      "reward_multiplier": 1.5,
      "type": "standard"
    },
    "10x": {
      "reward_multiplier": 10,
      "type": "bonus",
      "impact": "multiply_reward"
    },
    "5x": {
      "reward_multiplier": 5,
      "type": "bonus",
      "impact": "multiply_reward"
    },
    "+1000": {
      "extra": 1000,
      "type": "bonus",
      "impact": "extra_bonus"
    },
    "+500": {
      "extra": 500,
      "type": "bonus",
      "impact": "extra_bonus"
    },
    "MISS": {
      "type": "bonus",
      "impact": "miss"
    }
  },
  "probabilities": {
    "standard_symbols": [
      {
        "column": 0,
        "row": 0,
        "symbols": {
          "A": 1,
          "B": 2,
          "C": 3,
          "D": 4,
          "E": 5,
          "F": 6
        }
      },
      {
        "column": 0,
        "row": 1,
        "symbols": {
          "A": 1,
          "B": 2,
          "C": 3,
          "D": 4,
          "E": 5,
          "F": 6
        }
      },
      {
        "column": 0,
        "row": 2,
        "symbols": {
          "A": 1,
          "B": 2,
          "C": 3,
          "D": 4,
          "E": 5,
          "F": 6
        }
      },
      {
        "column": 1,
        "row": 0,
        "symbols": {
          "A": 1,
          "B": 2,
          "C": 3,
          "D": 4,
          "E": 5,
          "F": 6
        }
      },
      {
        "column": 1,
        "row": 1,
        "symbols": {
          "A": 1,
          "B": 2,
          "C": 3,
          "D": 4,
          "E": 5,
          "F": 6
        }
      },
      {
        "column": 1,
        "row": 2,
        "symbols": {
          "A": 1,
          "B": 2,
          "C": 3,
          "D": 4,
          "E": 5,
          "F": 6
        }
      },
      {
        "column": 2,
        "row": 0,
        "symbols": {
          "A": 1,
          "B": 2,
          "C": 3,
          "D": 4,
          "E": 5,
          "F": 6
        }
      },
      {
        "column": 2,
        "row": 1,
        "symbols": {
          "A": 1,
          "B": 2,
          "C": 3,
          "D": 4,
          "E": 5,
          "F": 6
        }
      },
      {
        "column": 2,
        "row": 2,
        "symbols": {
          "A": 1,
          "B": 2,
          "C": 3,
          "D": 4,
          "E": 5,
          "F": 6
        }
      }
    ],
    "bonus_symbols": {
      "symbols": {
        "10x": 1,
        "5x": 2,
        "+1000": 3,
        "+500": 4,
        "MISS": 5
      }
    }
  },
  "win_combinations": {
    "same_symbol_3_times": {
      "reward_multiplier": 1,
      "when": "same_symbols",
      "count": 3,
      "group": "same_symbols"
    },
    "same_symbol_4_times": {
      "reward_multiplier": 1.5,
      "when": "same_symbols",
      "count": 4,
      "group": "same_symbols"
    },
    "same_symbol_5_times": {
      "reward_multiplier": 2,
      "when": "same_symbols",
      "count": 5,
      "group": "same_symbols"
    },
    "same_symbol_6_times": {
      "reward_multiplier": 3,
      "when": "same_symbols",
      "count": 6,
      "group": "same_symbols"
    },
    "same_symbol_7_times": {
      "reward_multiplier": 5,
      "when": "same_symbols",
      "count": 7,
      "group": "same_symbols"
    },
    "same_symbol_8_times": {
      "reward_multiplier": 10,
      "when": "same_symbols",
      "count": 8,
      "group": "same_symbols"
    },
    "same_symbol_9_times": {
      "reward_multiplier": 20,
      "when": "same_symbols",
      "count": 9,
      "group": "same_symbols"
    },
    "same_symbols_horizontally": {
      "reward_multiplier": 2,
      "when": "linear_symbols",
      "group": "horizontally_linear_symbols",
      "covered_areas": [
        [
          "0:0",
          "0:1",
          "0:2"
        ],
        [
          "1:0",
          "1:1",
          "1:2"
        ],
        [
          "2:0",
          "2:1",
          "2:2"
        ]
      ]
    },
    "same_symbols_vertically": {
      "reward_multiplier": 2,
      "when": "linear_symbols",
      "group": "vertically_linear_symbols",
      "covered_areas": [
        [
          "0:0",
          "1:0",
          "2:0"
        ],
        [
          "0:1",
          "1:1",
          "2:1"
        ],
        [
          "0:2",
          "1:2",
          "2:2"
        ]
      ]
    },
    "same_symbols_diagonally_left_to_right": {
      "reward_multiplier": 5,
      "when": "linear_symbols",
      "group": "ltr_diagonally_linear_symbols",
      "covered_areas": [
        [
          "0:0",
          "1:1",
          "2:2"
        ]
      ]
    },
    "same_symbols_diagonally_right_to_left": {
      "reward_multiplier": 5,
      "when": "linear_symbols",
      "group": "rtl_diagonally_linear_symbols",
      "covered_areas": [
        [
          "0:2",
          "1:1",
          "2:0"
        ]
      ]
    }
  }
}
```

## Running ##

For running the application just use the command in any command-line interface:

```bash
 java -jar cli-betting-game-1.0-jar-with-dependencies.jar --config configuration.json --betting-amount 100
```
