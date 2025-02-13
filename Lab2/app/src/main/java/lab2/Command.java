package lab2;

public interface Command {
    void execute(Context context) throws CommandException;  
}
