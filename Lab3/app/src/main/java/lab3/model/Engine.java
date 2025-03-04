package lab3.model;

import java.util.Arrays;

public class Engine {
    private Model model;
    private Figure currentFigure;
    private int[][] finalFigurePosition;
    private FiguresBag bag;
    private EngineStatus status;

    public Engine() {
        bag = new FiguresBag("presets.json");
        restart();
    }

    public EngineStatus getStatus() {
        return this.status;
    }

    public Model build() {
        Model releaseModel = new Model();
        releaseModel.addScore(this.model.getScore());

        for (int i = 0; i < releaseModel.getField().getHeight(); i++) {
            System.arraycopy(model.getField().getRow(i), 0, releaseModel.getField().getRow(i), 0, releaseModel.getField().getWidth());
        }

        assembleField(releaseModel, this.currentFigure, this.finalFigurePosition);

        return releaseModel;
    }

    public void update() {
        if (this.status == EngineStatus.OVER) return;

        if (Arrays.deepEquals(this.currentFigure.getPosition(), this.finalFigurePosition)) {
            addFigure(this.model, this.currentFigure);

            if (filled(currentFigure)) {
                this.status = EngineStatus.OVER;
                System.out.println("Game over!");
            } else {
                calcCompletedLines();
                this.currentFigure = bag.getNextFigure();
                this.finalFigurePosition = calcFinalPosition(this.currentFigure);
            }
        } else {
            moveDown(this.currentFigure);
        }
    }

    public void abort() {
        this.status = EngineStatus.OVER;
        System.out.println("Game over!");
    }

    public void restart() {
        this.model = new Model();
        this.currentFigure = bag.getNextFigure();
        this.finalFigurePosition = calcFinalPosition(this.currentFigure);
        this.status = EngineStatus.RUN;
    }

    public void moveFigureLeft() {
        // if (this.status == EngineStatus.OVER) return;

        // try {
        //     shiftPosition(figure.getPosition(), -1, 0);
        //     this.finalFigurePosition = calcFinalPosition(this.currentFigure);
        // } catch (CollisionException e) {
        //     System.err.println("Коллизия");
        // }
    }

    public void moveFigureRight()  {
        // if (this.status == EngineStatus.OVER) return;

        // try {
        //     shiftPosition(this.currentFigure.getPosition(), 1, 0);
        //     this.finalFigurePosition = calcFinalPosition(this.currentFigure);
        // } catch (CollisionException e) {
        //     System.err.println("Коллизия");
        // }
    }

    public static void dropFigure() {
        // this.currentFigure.setPosition(this.finalFigurePosition);
    }

    public void rotate(boolean clockwise) {

    }
}
