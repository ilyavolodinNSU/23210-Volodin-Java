package lab2.commands;

import lab2.Command;
import lab2.CommandException;
import lab2.Context;

public class Print implements Command {
    @Override
    public void execute(Context context) throws CommandException {
        if (context.getStack().empty()) {
            System.out.println("Стек пуст");
        } else {
            System.out.println(context.getStack().peek());
        }
        
    }
}
