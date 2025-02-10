package lab2;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

// commands interface
interface Command {
    void execute(Context context);  
}

// concrete commands
class Pop implements Command {
    @Override
    public void execute(Context context) {
        context.getStack().pop();
    }
}

class Push implements Command {
    private final String name;

    Push(String ...args) {
        this.name = args[0];
    }

    @Override
    public void execute(Context context) {
        context.getStack().push(context.getVars().remove(name));
    }
}

class Plus implements Command {
    @Override
    public void execute(Context context) {
        double first = context.getStack().pop();
        double second = context.getStack().pop();
        context.getStack().push(first+second);
    }
}

class Minus implements Command {
    @Override
    public void execute(Context context) {
        double first = context.getStack().pop();
        double second = context.getStack().pop();
        context.getStack().push(first-second);
    }
}

class Mult implements Command {
    @Override
    public void execute(Context context) {
        double first = context.getStack().pop();
        double second = context.getStack().pop();
        context.getStack().push(first*second);
    }
}

class Div implements Command {
    @Override
    public void execute(Context context) {
        double first = context.getStack().pop();
        double second = context.getStack().pop();
        context.getStack().push(first/second);
    }
}

class Print implements Command {
    @Override
    public void execute(Context context) {
        System.out.println(context.getStack().peek());
    }
}

class Define implements Command {
    private final String name;
    private final double value;

    Define(String ...args) {
        this.name = args[0];
        this.value = Double.parseDouble(args[1]);
    }

    public void execute(Context context) {
        context.getVars().put(name, value);
    }
}

class Sqrt implements Command {
    @Override
    public void execute(Context context) {
        double value = context.getStack().pop();
        context.getStack().push(Math.sqrt(value));
    }
}

// command factory
public class CommandFactory {
    private final Map<String, String> commandMap = new HashMap<>();

    public CommandFactory() {
        loadConfig();
    }

    public Command createCommand(String name, String... args) {
        Command command = null;

        try {
            String className = commandMap.get(name.toUpperCase());
            if (className == null) {
                throw new IllegalArgumentException("Команда не найдена: " + name);
            }

            if (args.length > 0) {
                command = (Command) Class.forName(className).getDeclaredConstructor(String[].class).newInstance((Object) args);
            } else {
                command = (Command) Class.forName(className).getDeclaredConstructor().newInstance();
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Ошибка при создании команды: " + name, e);
        }

        return command;
    }

    private void loadConfig() {
        try (InputStream input = CommandFactory.class.getResourceAsStream("/commands.properties")) {
            if (input == null) {
                throw new IllegalStateException("Файл конфигурации commands.properties не найден");
            }
            
            Properties properties = new Properties();
            properties.load(input);

            for (String key : properties.stringPropertyNames()) {
                commandMap.put(key.toUpperCase(), properties.getProperty(key));
            }
        } catch (Exception e) {
            throw new RuntimeException("Ошибка загрузки конфигурации", e);
        }
    }
}

