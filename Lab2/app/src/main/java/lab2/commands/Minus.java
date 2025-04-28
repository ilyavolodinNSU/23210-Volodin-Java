package lab2.commands;

import lab2.Command;
import lab2.CommandException;
import lab2.Context;

public class Minus implements Command {
    @Override
    public void execute(Context context, String ...args) throws CommandException {
        if (args.length != 0) throw new CommandException("Команде не должны передаваться аргументы");
        if (context.getStack().size() < 2) throw new CommandException("В стеке менее 2 элементов");
        double first = context.getStack().pop();
        double second = context.getStack().pop();
        context.getStack().push(first-second);
    }
}
