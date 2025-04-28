package lab3.model;

import java.util.Arrays;

import lab3.Data;

// работает с data

public class Engine {
    private Data data;
    private Shape currentShape;
    private int[][] finalShapePosition;
    private ShapesBag bag;
    private EngineStatus status;

    public Engine() {
        bag = new ShapesBag("presets.json");
        restart();
    }

    public EngineStatus getStatus() {
        return this.status;
    }

    private void updateFinalPosition() {
        this.finalShapePosition = GameLogic.calcFinalPosition(this.data.getField(), this.currentShape);
    }

    public Data build() {
        Data releaseModel = new Data();
        releaseModel.addScore(this.data.getScore());

        for (int i = 0; i < releaseModel.getField().getHeight(); i++) {
            System.arraycopy(data.getField().getRow(i), 0, releaseModel.getField().getRow(i), 0, releaseModel.getField().getWidth());
        }

        GameLogic.assembleField(releaseModel.getField(), this.currentShape, this.finalShapePosition);

        return releaseModel;
    }

    public void update() {
        if (this.status == EngineStatus.OVER) return;

        if (Arrays.deepEquals(this.currentShape.getPosition(), this.finalShapePosition)) {
            GameLogic.addFigure(this.data.getField(), this.currentShape);

            if (GameLogic.filled(currentShape)) {
                this.status = EngineStatus.OVER;
                System.out.println("Game over!");
            } else {
                int completedLines = GameLogic.calcCompletedLines(this.data.getField(), this.currentShape);
                this.data.addTotalLines(completedLines);
                this.data.setLevel(GameLogic.calcLevel(this.data.getTotalLines()));
                this.data.addScore(GameLogic.calcScore(this.data.getTotalLines(), this.data.getLevel()));
                this.currentShape = bag.getNextFigure();
                updateFinalPosition();
            }
        } else {
            GameLogic.moveDown(this.currentShape);
        }
    }

    public void abort() {
        this.status = EngineStatus.OVER;
        System.out.println("Game over!");
    }

    public void restart() {
        this.data = new Data();
        this.currentShape = bag.getNextFigure();
        updateFinalPosition();
        this.status = EngineStatus.RUN;
    }

    public void moveShapeLeft() {
        if (this.status == EngineStatus.OVER) return;

        GameLogic.moveLeft(this.data.getField(), this.currentShape);
        updateFinalPosition();
    }

    public void moveShapeRight()  {
        if (this.status == EngineStatus.OVER) return;

        GameLogic.moveRight(this.data.getField(), this.currentShape);
        updateFinalPosition();
    }

    public void dropShape() {
        this.currentShape.setPosition(this.finalShapePosition);
        update();
    }

    public void rotateShape(boolean clockwise) {
        GameLogic.rotate(this.data.getField(), currentShape, clockwise);
        updateFinalPosition();
    }
}
