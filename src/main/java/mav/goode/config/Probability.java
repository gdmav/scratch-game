package mav.goode.config;

import lombok.Data;

import java.util.Map;
@Data
public class Probability {
    public StandardSymbolProbability[] standard_symbols;
    public BonusSymbols bonus_symbols;
}
