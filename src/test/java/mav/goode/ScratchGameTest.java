package mav.goode;

import mav.goode.config.Config;
import mav.goode.config.ConfigLoader;
import mav.goode.game.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class ScratchGameTest {

    private Game game;
    private Config config;

    @BeforeEach
    public void setUp() {
        String configFilePath = "src/test/resources/test-config.json"; // Ensure this path is correct
        try {
            config = ConfigLoader.loadConfig(configFilePath);
            game = new Game(config, 100);
        } catch (IOException e) {
            e.printStackTrace();
            fail("Failed to load config file: " + e.getMessage());
        }
    }

    @Test
    public void testMatrixGeneration() {
        String[][] matrix = game.generateMatrix();

        assertNotNull(matrix);
        assertEquals(config.getRows(), matrix.length);
        assertEquals(config.getColumns(), matrix[0].length);


    }



    @Test
    public void testCheckSameSymbols() {
        String[][] matrix = {
                {"A", "A", "B"},
                {"A", "+1000", "B"},
                {"A", "A", "B"}
        };

        Map<String, List<String>> winningCombinations = new HashMap<>();
        game.checkSameSymbols(matrix, winningCombinations);

        assertTrue(winningCombinations.containsKey("A"));
        assertEquals(1, winningCombinations.get("A").size());
        assertTrue(winningCombinations.get("A").contains("same_symbols_5_times"));

        assertTrue(winningCombinations.containsKey("B"));
        assertEquals(1, winningCombinations.get("B").size());
        assertTrue(winningCombinations.get("B").contains("same_symbols_3_times"));
    }

    @Test
    public void testCheckLinearSymbols() {
        String[][] matrix = {
                {"A", "B", "C"},
                {"A", "+1000", "B"},
                {"A", "B", "B"}
        };

        Map<String, List<String>> winningCombinations = new HashMap<>();
        game.checkLinearSymbols(matrix, winningCombinations);

        assertTrue(winningCombinations.containsKey("A"));
        assertEquals(1, winningCombinations.get("A").size());
        assertTrue(winningCombinations.get("A").contains("same_symbols_horizontally"));

        assertTrue(winningCombinations.containsKey("B"));
        assertEquals(2, winningCombinations.get("B").size());
        assertTrue(winningCombinations.get("B").contains("same_symbols_vertically"));
        assertTrue(winningCombinations.get("B").contains("same_symbols_3_times"));
    }

    @Test
    public void testApplyBonusSymbols() {
        String[][] matrix = {
                {"A", "A", "A"},
                {"5x", "B", "C"},
                {"D", "E", "F"}
        };

        // Calculate initial reward without bonus
        Map<String, List<String>> winningCombinations = game.checkWinningCombinations(matrix);
        double initialReward = game.calculateReward(winningCombinations);

        // Apply bonus symbols
        String appliedBonusSymbol = game.applyBonusSymbols(matrix, initialReward);

        // Calculate expected reward after applying the bonus symbol
        double expectedReward = initialReward * config.getSymbols().get("5x").getRewardMultiplier();

        assertEquals(expectedReward, initialReward, 0.01);
        assertEquals("5x", appliedBonusSymbol);
    }

    @Test
    public void testLostGame() {
        String[][] matrix = {
                {"A", "B", "C"},
                {"E", "B", "5x"},
                {"F", "D", "C"}
        };

        Map<String, List<String>> winningCombinations = game.checkWinningCombinations(matrix);
        double reward = game.calculateReward(winningCombinations);
        String appliedBonusSymbol = game.applyBonusSymbols(matrix, reward);

        assertEquals(0.0, reward, 0.01);
        assertNull(appliedBonusSymbol);
    }
}
