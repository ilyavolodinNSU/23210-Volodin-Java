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
        if (args.length > 1) {
            System.err.println("Слишком много аругментов!\nИспользуйте следующий формат для необязательных аргументов: <input file> <command config>");
            System.exit(1);
        }

        logger.info("Программа запущена");

        try {
            Scanner scanner;
            if (args.length > 0) {
                scanner = new Scanner(new File(args[0]));
                logger.info("Загружен входной файл");
            } else {
                scanner = new Scanner(System.in);
                logger.info("Считывание команд из консоли...");
            }

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
                    Command command = factory.createCommand(commandName);
                    command.execute(context, commandArgs);
                    logger.info("Выполнение команды {} с аргументами {}", commandName, commandArgs.length != 0 ? Arrays.toString(commandArgs) : "");
                } catch (IllegalArgumentException e) {
                    logger.error("Ошибка фабрики: {}", e.getMessage());
                    System.err.println(e.getMessage());
                } catch (CommandException e) {
                    logger.error("Ошибка выполнения команды {}: {}", commandName, e.getMessage());
                    System.err.println("Ошибка выполнения команды " + commandName + ": " + e.getMessage());
                }
            }

            scanner.close();
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
