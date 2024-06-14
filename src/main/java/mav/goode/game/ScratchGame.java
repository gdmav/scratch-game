package mav.goode.game;

import com.fasterxml.jackson.databind.ObjectMapper;
import mav.goode.config.Config;
import mav.goode.config.ConfigLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;

public class ScratchGame {
    private static final Logger logger = LoggerFactory.getLogger(ScratchGame.class);

    public static void main(String[] args) {
        if (args.length != 4 || !args[0].equals("--config") || !args[2].equals("--betting-amount")) {
            logger.error("Usage: java -jar scratch-game.jar --config <config-file> --betting-amount <betting-amount>");
            return;
        }

        String configFilePath = args[1];
        int bettingAmount;

        try {
            bettingAmount = Integer.parseInt(args[3]);
        } catch (NumberFormatException e) {
            logger.error("Invalid betting amount. Please enter a valid number.", e);
            return;
        }

        ObjectMapper objectMapper = new ObjectMapper();
        Config config;

        try {
            config = ConfigLoader.loadConfig(configFilePath);
        } catch (IOException e) {
            logger.error("Error reading config file: " + e.getMessage(), e);
            return;
        }

        Game game = new Game(config, bettingAmount);
        Result result = game.play();

        try {
            String resultJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
            logger.info("Game result: \n{}", resultJson);
        } catch (IOException e) {
            logger.error("Error generating result JSON: " + e.getMessage(), e);
        }
    }
}

