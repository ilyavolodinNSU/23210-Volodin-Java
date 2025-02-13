package lab2;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CommandFactory {
    private final Map<String, String> commandMap = new HashMap<>();
    private static final Logger logger = LogManager.getLogger(CommandFactory.class);

    public CommandFactory(String configPath) throws FileNotFoundException {
        try {
            loadConfig(configPath);
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException(e.getMessage());
        } catch (IOException e) {
            logger.error("Ошибка загрузки конфигурации {}", e.getMessage());
            System.err.println("Ошибка загрузки конфигурации" + e.getMessage());
        }
    }

    public Command createCommand(String name, String... args) throws IllegalArgumentException {
        Command command = null;

        try {
            String className = commandMap.get(name.toUpperCase());
            
            if (className == null) throw new IllegalArgumentException("Команда не найдена: " + name);

            if (args.length > 0) {
                command = (Command) Class.forName(className).getDeclaredConstructor(String[].class).newInstance((Object) args);
            } else {
                command = (Command) Class.forName(className).getDeclaredConstructor().newInstance();
            }

            //logger.info("Создана команда: {}", name);
        } catch (Exception e) {
            throw new IllegalArgumentException("Ошибка при создании команды: " + name, e);
        }

        return command;
    }

    private void loadConfig(String configPath) throws FileNotFoundException, IOException {
        InputStream input = CommandFactory.class.getResourceAsStream(configPath);
        if (input == null) throw new FileNotFoundException("Файл конфигурации commands.properties не найден");
        
        Properties properties = new Properties();
        properties.load(input);

        for (String key : properties.stringPropertyNames()) {
            commandMap.put(key.toUpperCase(), properties.getProperty(key));
        }

        logger.info("Стандартный конфиг загружен. Количество команд: {}", properties.size());
    }
}

