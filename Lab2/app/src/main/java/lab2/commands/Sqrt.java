package lab2.commands;

import lab2.Command;
import lab2.CommandException;
import lab2.Context;

public class Sqrt implements Command {
    @Override
    public void execute(Context context, String ...args) throws CommandException {
        if (args.length != 0) throw new CommandException("Команде не должны передаваться аргументы");
        if (context.getStack().empty()) throw new CommandException("Стек пуст");
        double value = context.getStack().pop();
        if (value < 0) throw new CommandException("Подкорневое значение отрицательно");
        context.getStack().push(Math.sqrt(value));
    }
}
