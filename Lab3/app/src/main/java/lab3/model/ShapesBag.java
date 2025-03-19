package lab3.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class ShapesBag {
    private ArrayList<Shape> bag;
    private Random random;
    private TetrisShapes shapes;

    public ShapesBag(String presetsPath) {
        this.shapes = ShapeLoader.loadShapes(presetsPath);
        this.random = new Random();
        this.bag = new ArrayList<>();
        refillBag();
    }

    private void refillBag() {
        bag.clear();
        bag.addAll(shapes.getShapes().values());
        Collections.shuffle(bag, random);
    }

    public Shape getNextFigure() {
        if (bag.isEmpty()) {
            refillBag();
        }

        Shape copyFigure = bag.remove(bag.size()-1).clone();

        return copyFigure;
    }
}
