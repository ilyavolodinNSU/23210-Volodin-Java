package lab3.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class FiguresBag {
    private ArrayList<Figure> bag;
    private Random random;
    private TetrisShapes shapes;

    public FiguresBag(String presetsPath) {
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

    public Figure getNextFigure() {
        if (bag.isEmpty()) {
            refillBag();
        }

        Figure copyFigure = bag.remove(bag.size()-1).clone();

        return copyFigure;
    }
}
