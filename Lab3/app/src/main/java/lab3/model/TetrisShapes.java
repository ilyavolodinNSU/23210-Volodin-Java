package lab3.model;

public class TetrisShapes {
    private java.util.Map<String, Shape> shapes;

    public java.util.Map<String, Shape> getShapes() {
        return this.shapes;
    }

    public Shape getShape(String name) {
        return this.shapes.get(name);
    }
    
}
