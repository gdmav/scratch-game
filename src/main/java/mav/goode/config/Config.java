package mav.goode.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;

@Data
public class Config {
    public int columns;
    public int rows;
    public Map<String, Symbol> symbols;
    public Probability probabilities;
    @JsonProperty("win_combinations")
    public Map<String, WinCombination> winCombinations;

}

