package lab3.model;

public class СollisionException extends Exception {
    public СollisionException(String message) {
        super(message);
    }

    public СollisionException() {
        super("Возникла коллизия");
    }
}