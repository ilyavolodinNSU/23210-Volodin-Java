package lab2;

import com.fasterxml.jackson.databind.ObjectMapper;

enum CommandName {
    POP,
    PUSH,
    PLUS,
    MINUS,
    MULT,
    DIV,
    PRINT,
    DEFINE
}

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

// command factory
public class CommandFactory {
    public Command createCommand(String name, String... args) {
        Command command = null;

        switch (name) {
            case "POP":
                command = new Pop();
                break;
            case "PUSH":
                command = new Push(args);
                break;
            case "PLUS":
                command = new Plus();
                break;
            case "MINUS":
                command = new Minus();
                break; 
            case "MULT":
                command = new Mult();
                break;
            case "DIV":
                command = new Div();
                break; 
            case "PRINT":
                command = new Print();
                break; 
            case "DEFINE":
                command = new Define(args);
                break;           
        }

        return command;
    }
}

