package mav.goode.config;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

    public class ConfigLoader {
        public static Config loadConfig(String configFilePath) throws IOException {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(new File(configFilePath), Config.class);
        }
    }


