package lab2.commands;

import lab2.Command;
import lab2.CommandException;
import lab2.Context;

public class Plus implements Command {
    @Override
    public void execute(Context context) throws CommandException {
        if (context.getStack().size() < 2) throw new CommandException("В стеке менее 2 элементов");
        double first = context.getStack().pop();
        double second = context.getStack().pop();
        context.getStack().push(first+second);
    }
}
