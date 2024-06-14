package mav.goode.config;

import lombok.Data;

import java.util.Map;

@Data
public class Config {
    public int columns;
    public int rows;
    public Map<String, Symbol> symbols;
    public Probability probabilities;
    public Map<String, WinCombination> win_combinations;

}

