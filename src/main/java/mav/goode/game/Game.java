package mav.goode.game;

import mav.goode.config.Config;
import mav.goode.config.StandardSymbolProbability;
import mav.goode.config.Symbol;
import mav.goode.config.WinCombination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class Game {
    private static final Logger logger = LoggerFactory.getLogger(Game.class);

    private final Config config;
    private final int bettingAmount;
    private final Random random;

    public Game(Config config, int bettingAmount) {
        this.config = config;
        this.bettingAmount = bettingAmount;
        this.random = new Random();
    }

    public Result play() {
        logger.info("Starting a new game with betting amount: {}", bettingAmount);
        String[][] matrix = generateMatrix();
        Map<String, List<String>> winningCombinations = checkWinningCombinations(matrix);
        double reward = calculateReward(winningCombinations);
        String appliedBonusSymbol = applyBonusSymbols(matrix, reward);

        return new Result(matrix, reward, winningCombinations, appliedBonusSymbol);
    }

    public String[][] generateMatrix() {
        int rows = config.getRows();
        int columns = config.getColumns();
        String[][] matrix = new String[rows][columns];

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                matrix[row][col] = getRandomSymbol(row, col);
                logger.debug("Generated symbol for cell [{}][{}]: {}", row, col, matrix[row][col]);
            }
        }

        return matrix;
    }

    private String getRandomSymbol(int row, int col) {
        double bonusProbability = 0.1; // Adjust this value based on the desired probability of a bonus symbol
        double randomValue = random.nextDouble();

        if (randomValue < bonusProbability) {
            return getRandomBonusSymbol();
        } else {
            return getRandomStandardSymbol(row, col);
        }
    }

    private String getRandomStandardSymbol(int row, int col) {
        StandardSymbolProbability probability = Arrays.stream(config.getProbabilities().getStandardSymbols())
                .filter(p -> p.getRow() == row && p.getColumn() == col)
                .findFirst()
                .orElse(config.getProbabilities().getStandardSymbols()[0]); // Default to the first probability if not found

        int totalWeight = probability.getSymbols().values().stream().mapToInt(Integer::intValue).sum();
        int randomValue = random.nextInt(totalWeight);

        for (Map.Entry<String, Integer> entry : probability.getSymbols().entrySet()) {
            randomValue -= entry.getValue();
            if (randomValue < 0) {
                return entry.getKey();
            }
        }

        return null; // Should never happen
    }

    private String getRandomBonusSymbol() {
        Map<String, Integer> bonusSymbols = config.getProbabilities().getBonusSymbols().getSymbols();
        int totalWeight = bonusSymbols.values().stream().mapToInt(Integer::intValue).sum();
        int randomValue = random.nextInt(totalWeight);

        for (Map.Entry<String, Integer> entry : bonusSymbols.entrySet()) {
            randomValue -= entry.getValue();
            if (randomValue < 0) {
                return entry.getKey();
            }
        }

        return null; // Should never happen
    }

    public Map<String, List<String>> checkWinningCombinations(String[][] matrix) {
        Map<String, List<String>> winningCombinations = new HashMap<>();

        checkSameSymbols(matrix, winningCombinations);
        checkLinearSymbols(matrix, winningCombinations);

        return winningCombinations;
    }

    public void checkSameSymbols(String[][] matrix, Map<String, List<String>> winningCombinations) {
        Map<String, Integer> symbolCount = new HashMap<>();

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                String symbol = matrix[i][j];
                symbolCount.put(symbol, symbolCount.getOrDefault(symbol, 0) + 1);
            }
        }

        for (Map.Entry<String, Integer> entry : symbolCount.entrySet()) {
            String symbol = entry.getKey();
            int count = entry.getValue();

            for (Map.Entry<String, WinCombination> winEntry : config.getWinCombinations().entrySet()) {
                String winCombName = winEntry.getKey();
                WinCombination winComb = winEntry.getValue();

                if ("same_symbols".equals(winComb.getWhen()) && winComb.getCount() == count) {
                    winningCombinations.computeIfAbsent(symbol, k -> new ArrayList<>()).add(winCombName);
                }
            }
        }
    }

    public void checkLinearSymbols(String[][] matrix, Map<String, List<String>> winningCombinations) {
        for (Map.Entry<String, WinCombination> entry : config.getWinCombinations().entrySet()) {
            WinCombination winComb = entry.getValue();
            if ("linear_symbols".equals(winComb.getWhen())) {
                for (String[] area : winComb.getCoveredAreas()) {
                    if (isWinningArea(matrix, area)) {
                        String symbol = matrix[Integer.parseInt(area[0])][Integer.parseInt(area[1])];
                        winningCombinations.computeIfAbsent(symbol, k -> new ArrayList<>()).add(entry.getKey());
                    }
                }
            }
        }
    }

    private boolean isWinningArea(String[][] matrix, String[] area) {
        String firstSymbol = matrix[Integer.parseInt(area[0])][Integer.parseInt(area[1])];

        for (String cell : area) {
            int row = Integer.parseInt(String.valueOf(cell.charAt(0)));
            int col = Integer.parseInt(String.valueOf(cell.charAt(2)));
            if (!matrix[row][col].equals(firstSymbol)) {
                return false;
            }
        }

        return true;
    }

    public double calculateReward(Map<String, List<String>> winningCombinations) {
        double reward = 0.0;

        for (Map.Entry<String, List<String>> entry : winningCombinations.entrySet()) {
            String symbol = entry.getKey();
            List<String> combinations = entry.getValue();

            double symbolRewardMultiplier = config.getSymbols().get(symbol).getRewardMultiplier();
            double totalMultiplier = combinations.stream()
                    .mapToDouble(combination -> config.getWinCombinations().get(combination).getRewardMultiplier())
                    .reduce(1.0, (a, b) -> a * b);

            reward += bettingAmount * symbolRewardMultiplier * totalMultiplier;
            logger.debug("Calculated reward for symbol {}: {}", symbol, reward);
        }

        return reward;
    }

    public String applyBonusSymbols(String[][] matrix, double reward) {
        String appliedBonusSymbol = null;

        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[row].length; col++) {
                String symbol = matrix[row][col];
                Symbol symbolConfig = config.getSymbols().get(symbol);
                if ("bonus".equals(symbolConfig.getType())) {
                    appliedBonusSymbol = symbol;
                    reward = applyBonusEffect(symbol, reward);
                }
            }
        }

        return appliedBonusSymbol;
    }

    private double applyBonusEffect(String bonusSymbol, double reward) {
        Symbol symbolConfig = config.getSymbols().get(bonusSymbol);

        switch (symbolConfig.getImpact()) {
            case "multiply_reward":
                reward *= symbolConfig.getExtra();
                break;

        }

        return reward;
    }
}
