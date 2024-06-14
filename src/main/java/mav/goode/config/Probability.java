package mav.goode.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Probability {
    @JsonProperty("standard_symbols")
    public StandardSymbolProbability[] standardSymbols;
    @JsonProperty("bonus_symbols")
    public BonusSymbols bonusSymbols;
}
