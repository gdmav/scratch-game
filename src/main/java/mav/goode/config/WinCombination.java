package mav.goode.config;

import lombok.Data;

@Data
public class WinCombination {
    public double reward_multiplier;
    public String when;
    public int count;
    public String group;
    public String[][] covered_areas;



}
