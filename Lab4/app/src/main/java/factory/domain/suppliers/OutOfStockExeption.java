package factory.domain.suppliers;

public class OutOfStockExeption extends RuntimeException {
    public OutOfStockExeption(String message) {
        super(message);
    }
}
