package lab2.commands;

import lab2.Command;
import lab2.CommandException;
import lab2.Context;

public class Define implements Command {
    private String name;
    private double value;

    public Define(String ...args) throws CommandException {
        if (args.length != 2) throw new CommandException("Аргументов должно быть два");
        try {
            this.name = args[0];
            this.value = Double.parseDouble(args[1]);
        } catch (NumberFormatException  e) {
            throw new CommandException("Неверный формат аргументов");
        }
    }

    public void execute(Context context) throws CommandException {
        context.getVars().put(name, value);
    }
}
