package lab2.commands;

import lab2.Command;
import lab2.CommandException;
import lab2.Context;

public class Push implements Command {
    private final String name;

    public Push(String ...args) throws CommandException {
        if (args.length != 1) throw new CommandException("Аргумент должен быть один");
        this.name = args[0];
    }

    @Override
    public void execute(Context context) throws CommandException {
        Double value = context.getVars().remove(name);
        if (value == null) throw new CommandException("Такого токена не существует");
        context.getStack().push(value);
    }
}

