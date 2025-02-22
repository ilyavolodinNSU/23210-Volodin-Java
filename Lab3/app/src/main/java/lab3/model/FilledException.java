package lab3.model;

public class FilledException extends Exception {
    public FilledException(String message) {
        super(message);
    }

    public FilledException() {
        super("Поле заполнено");
    }
}
