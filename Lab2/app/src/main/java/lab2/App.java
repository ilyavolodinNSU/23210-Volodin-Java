package lab2;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class App {
    private static final Logger logger = LogManager.getLogger(App.class);

    public static void main(String[] args) {
        logger.info("Программа запущена");
        try (Scanner scanner = new Scanner(new File("C:/nsu/OOP/23210-Volodin-Java/Lab2/app/src/main/resources/input.txt"))) {
            logger.info("Загружен входной файл");
            Context context = new Context();
            CommandFactory factory = new CommandFactory("/commands.properties");
            logger.info("Фабрика и контекст созданы");

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.startsWith("#")  || line.isEmpty()) continue;
                String[] parts = line.split(" ");

                String commandName = parts[0];
                String[] commandArgs = Arrays.copyOfRange(parts, 1, parts.length);

                try {
                    Command command = factory.createCommand(commandName, commandArgs);
                    command.execute(context);
                    logger.info("Выполнение команды {} с аргументами {}", commandName, commandArgs.length != 0 ? Arrays.toString(commandArgs) : "");
                } catch (IllegalArgumentException e) {
                    logger.error("Ошибка фабрики: {}", e.getMessage());
                    System.err.println(e.getMessage());
                } catch (CommandException e) {
                    logger.error("Ошибка выполнения команды {}: {}", commandName, e.getMessage());
                    System.err.println("Ошибка выполнения команды " + commandName + ": " + e.getMessage());
                }
            }
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage());
            System.err.println(e.getMessage());
        } catch (Exception e) {
            logger.error("Неизвестная ошибка: {}", e.getMessage());
            System.err.println("Неизвестная ошибка: " + e.getMessage());
        } finally {
            logger.info("Завершение работы программы\n");
        }
    }
}
