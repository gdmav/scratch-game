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
//
//    private Game game;
//    private Config config;
//
//    @BeforeEach
//    public void setUp() {
//        String configFilePath = "src/test/resources/test-config.json"; // Ensure this path is correct
//        try {
//            config = ConfigLoader.loadConfig(configFilePath);
//            game = new Game(config, 100);
//        } catch (IOException e) {
//            e.printStackTrace();
//            fail("Failed to load config file: " + e.getMessage());
//        }
//    }
//    @Test
//    public void testWinningCombination() {
//        String[][] matrix = {
//                {"A", "A", "B"},
//                {"A", "+1000", "B"},
//                {"A", "A", "B"}
//        };
//
//        // Simulate the game play with a predefined matrix
//        Map<String, List<String>> winningCombinations = game.checkWinningCombinations(matrix);
//        double reward = game.calculateReward(winningCombinations);
//        String appliedBonusSymbol = game.applyBonusSymbols(matrix, reward);
//
//        // Expected winning combinations based on provided matrix and config
//        Map<String, List<String>> expectedWinningCombinations = new HashMap<>();
//        expectedWinningCombinations.put("A", Arrays.asList("same_symbol_5_times", "same_symbols_vertically"));
//        expectedWinningCombinations.put("B", Arrays.asList("same_symbol_3_times", "same_symbols_vertically"));
//
//        // Validate the results
//        assertEquals(6600, reward);
//        assertEquals("+1000", appliedBonusSymbol);
//        assertEquals(expectedWinningCombinations, winningCombinations);
//    }
//
//    @Test
//    public void testLosingCombination() {
//        String[][] matrix = {
//                {"A", "B", "C"},
//                {"D", "E", "F"},
//                {"G", "H", "I"}
//        };
//
//        // Simulate the game play with a predefined matrix
//        Map<String, List<String>> winningCombinations = game.checkWinningCombinations(matrix);
//        double reward = game.calculateReward(winningCombinations);
//        String appliedBonusSymbol = game.applyBonusSymbols(matrix, reward);
//
//        // Validate the results
//        assertTrue(winningCombinations.isEmpty());
//        assertEquals(0, reward);
//        assertNull(appliedBonusSymbol);
//    }

}
