package lab2.commands;

import lab2.Command;
import lab2.CommandException;
import lab2.Context;

public class Push implements Command {
    private String name;

    @Override
    public void execute(Context context, String ...args) throws CommandException {
        if (args.length != 1) throw new CommandException("Команде должен передаваться 1 аргумент");
        this.name = args[0];
        Double value = context.getVars().remove(name);
        if (value == null) throw new CommandException("Такого токена не существует");
        context.getStack().push(value);
    }
}

