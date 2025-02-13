package lab2.commands;

import lab2.Command;
import lab2.CommandException;
import lab2.Context;

public class Sqrt implements Command {
    @Override
    public void execute(Context context) throws CommandException {
        if (context.getStack().empty()) throw new CommandException("Стек пуст");
        double value = context.getStack().pop();
        context.getStack().push(Math.sqrt(value));
    }
}
