package lab3.model;

public class CollisionException extends Exception {
    public CollisionException(String message) {
        super(message);
    }

    public CollisionException() {
        super("Возникла коллизия");
    }
}