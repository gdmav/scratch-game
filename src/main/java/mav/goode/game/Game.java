package mav.goode.game;

import mav.goode.config.Config;
import mav.goode.config.StandardSymbolProbability;

import java.util.*;

public class Game {
    private final Config config;
    private final int bettingAmount;
    private final Random random;

    public Game(Config config, int bettingAmount) {
        this.config = config;
        this.bettingAmount = bettingAmount;
        this.random = new Random();
    }

    public Result play() {
        String[][] matrix = generateMatrix();
        Map<String, List<String>> winningCombinations = checkWinningCombinations(matrix);
        double reward = calculateReward(winningCombinations);
        String appliedBonusSymbol = applyBonusSymbols(matrix, reward);

        return new Result(matrix, reward, winningCombinations, appliedBonusSymbol);
    }

    private String[][] generateMatrix() {
        int rows = config.rows;
        int columns = config.columns;
        String[][] matrix = new String[rows][columns];

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                matrix[row][col] = getRandomSymbol(config.probabilities.standard_symbols, row, col);
            }
        }

        return matrix;
    }

    private String getRandomSymbol(StandardSymbolProbability[] probabilities, int row, int col) {
        // Implement random symbol generation based on probabilities
        StandardSymbolProbability probability = Arrays.stream(probabilities)
                .filter(p -> p.row == row && p.column == col)
                .findFirst()
                .orElse(probabilities[0]); // Default to the first probability if not found

        int totalWeight = probability.symbols.values().stream().mapToInt(Integer::intValue).sum();
        int randomValue = random.nextInt(totalWeight);

        for (Map.Entry<String, Integer> entry : probability.symbols.entrySet()) {
            randomValue -= entry.getValue();
            if (randomValue < 0) {
                return entry.getKey();
            }
        }

        return null; // Should never happen
    }

    private Map<String, List<String>> checkWinningCombinations(String[][] matrix) {
        Map<String, List<String>> winningCombinations = new HashMap<>();

        // Implement logic to check for winning combinations
        // Add entries to winningCombinations map

        return winningCombinations;
    }

    private double calculateReward(Map<String, List<String>> winningCombinations) {
        double reward = 0.0;

        for (Map.Entry<String, List<String>> entry : winningCombinations.entrySet()) {
            String symbol = entry.getKey();
            List<String> combinations = entry.getValue();

            double symbolRewardMultiplier = config.symbols.get(symbol).reward_multiplier;
            double totalMultiplier = combinations.stream()
                    .mapToDouble(combination -> config.win_combinations.get(combination).reward_multiplier)
                    .reduce(1.0, (a, b) -> a * b);

            reward += bettingAmount * symbolRewardMultiplier * totalMultiplier;
        }

        return reward;
    }

    private String applyBonusSymbols(String[][] matrix, double reward) {
        // Implement logic to apply bonus symbols
        // Update reward if a bonus symbol is applied

        return null; // Return the applied bonus symbol if any, otherwise return null
    }
}
