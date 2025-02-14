package lab2;

public interface Command {
    void execute(Context context, String ...args) throws CommandException;  
}
