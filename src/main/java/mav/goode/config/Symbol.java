package mav.goode.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Symbol {
    @JsonProperty("reward_multiplier")
    public double rewardMultiplier;
    public String type;
    public Integer extra;
    public String impact;


}
