package lab2.commands;

import lab2.Command;
import lab2.CommandException;
import lab2.Context;

public class Pop implements Command {
    @Override
    public void execute(Context context) throws CommandException {
        if (context.getStack().empty()) throw new CommandException("Стек пуст");
        context.getStack().pop();
    }
}
