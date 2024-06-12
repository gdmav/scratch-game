package mav.goode.game;

import com.fasterxml.jackson.databind.ObjectMapper;
import mav.goode.config.Config;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class ScratchGame {

    public static void main(String[] args) {
        if (args.length != 4 || !args[0].equals("--config") || !args[2].equals("--betting-amount")) {
            System.out.println("Usage: java -jar scratch-game.jar --config <config-file> --betting-amount <betting-amount>");
            return;
        }

        String configFilePath = args[1];
        int bettingAmount;

        try {
            bettingAmount = Integer.parseInt(args[3]);
        } catch (NumberFormatException e) {
            System.out.println("Invalid betting amount. Please enter a valid number.");
            return;
        }

        ObjectMapper objectMapper = new ObjectMapper();
        Config config;

        try {
            config = objectMapper.readValue(new File(configFilePath), Config.class);
        } catch (IOException e) {
            System.out.println("Error reading config file: " + e.getMessage());
            return;
        }

        Game game = new Game(config, bettingAmount);
        Result result = game.play();

        try {
            String resultJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
            System.out.println(resultJson);
        } catch (IOException e) {
            System.out.println("Error generating result JSON: " + e.getMessage());
        }
    }
}
