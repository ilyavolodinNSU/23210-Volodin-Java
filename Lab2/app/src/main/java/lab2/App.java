package lab2;

import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class App {
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(new File("C:/nsu/OOP/23210-Volodin-Java/Lab2/app/src/main/resources/input.txt"));
            Context context = new Context();
            CommandFactory factory = new CommandFactory();

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(" ");

                String commandName = parts[0];
                String[] commandArgs = Arrays.copyOfRange(parts, 1, parts.length);

                Command command = factory.createCommand(commandName, commandArgs);
                command.execute(context);
            }

            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
