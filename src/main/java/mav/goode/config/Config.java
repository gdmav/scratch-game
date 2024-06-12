package mav.goode.config;

import java.util.Map;
import java.util.List;

import java.util.Map;

public class Config {
    public int columns;
    public int rows;
    public Map<String, Symbol> symbols;
    public Probability probabilities;
    public Map<String, WinCombination> win_combinations;
}

public class Symbol {
    public double reward_multiplier;
    public String type;
    public Integer extra;
    public String impact;
}

public class Probability {
    public StandardSymbolProbability[] standard_symbols;
    public Map<String, Integer> bonus_symbols;
}

public class StandardSymbolProbability {
    public int column;
    public int row;
    public Map<String, Integer> symbols;
}

public class WinCombination {
    public double reward_multiplier;
    public String when;
    public int count;
    public String group;
    public String[][] covered_areas;
}
