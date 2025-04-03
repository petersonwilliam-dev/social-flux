package com.backend.util;

import com.backend.App;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {

    private static final Logger logger = LogManager.getLogger();

    public static Properties getProperties() {
        Properties props = new Properties();
        try (InputStream input = App.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) {
                logger.error("Arquivo application.properties não encontrado");
                System.exit(1);
            }
            props.load(input);
        } catch (IOException exception) {
            logger.error("Erro ao carregar o arquivo de propriedades /src/main/resources/application.properties", exception);
            System.exit(1);
        }
        return props;
    }
}
