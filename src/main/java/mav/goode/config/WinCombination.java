package mav.goode.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class WinCombination {
    @JsonProperty("reward_multiplier")
    public double rewardMultiplier;
    public String when;
    public int count;
    public String group;
    @JsonProperty("covered_areas")
    public String[][] coveredAreas;



}
