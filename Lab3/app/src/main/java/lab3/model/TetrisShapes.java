package lab3.model;

public class TetrisShapes {
    private java.util.Map<String, Figure> shapes;

    public java.util.Map<String, Figure> getShapes() {
        return this.shapes;
    }

    public Figure getShape(String name) {
        return this.shapes.get(name);
    }
    
}
