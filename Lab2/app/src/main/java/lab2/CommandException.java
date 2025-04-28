package lab2;

public class CommandException extends Exception {
    public CommandException(String message) {
        super(message);
    }

    public CommandException() {
        super("Произошла ошибка команды");
    }
}
