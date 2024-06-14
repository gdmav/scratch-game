package mav.goode.config;

import lombok.Data;

import java.util.Map;
@Data
public class StandardSymbolProbability {
    public int column;
    public int row;
    public Map<String, Integer> symbols;
}
