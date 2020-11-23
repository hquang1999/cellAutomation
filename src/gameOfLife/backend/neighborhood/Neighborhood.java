package gameOfLife.backend.neighborhood;

import gameOfLife.backend.cell.Cell;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

import static gameOfLife.backend.cell.Cell.ONE;

public class Neighborhood {

    private final GridPane gridPane;

    private final double cellSize;

    private List<List<Cell>> oldGens;

    private List<Cell> updateList;
    private int currentGenIndex;

    public Neighborhood(GridPane gridPane
             , List<List<Cell>> allGens, double cellSize) {
        this.gridPane = gridPane;
        this.cellSize = cellSize;
        this.oldGens = allGens;
        this.currentGenIndex = 0;

    }

    private void rowEvolve (List<Cell> top
            , List<Cell> bottom, List <Cell> middle
            , List<Cell> newGen) {
        int neighbors = 0;
        for(int i = 0; i < middle.size(); i++) {
            if (i == 0) {
                for (int t = i; t < i + 2; t++) {
                    if (top.get(t) == ONE) {
                        neighbors++;
                    }
                    if (bottom.get(t) == ONE) {
                        neighbors++;
                    }
                }
                if (middle.get(i + 1) == ONE) {
                    neighbors++;
                }
            }
            else if (i == middle.size() - 1) {
                for (int t = i; t > i - 2; t--) {
                    if (top.get(t) == ONE) {
                        neighbors++;
                    }
                    if (bottom.get(t) == ONE) {
                        neighbors++;
                    }
                }
                if (middle.get(i - 1) == ONE) {
                    neighbors++;
                }
            }
            else {
                for(int t = i - 1; t < i + 2; t++) {
                    if (top.get(t) == ONE) {
                        neighbors++;
                    }
                    if (bottom.get(t) == ONE) {
                        neighbors++;
                    }
                }
                if (middle.get(i - 1) == ONE) {
                    neighbors++;
                }
                if (middle.get(i + 1) == ONE) {
                    neighbors++;
                }
            }
            if ((neighbors < 2) || (neighbors > 3)){
                System.out.println("yes");
                newGen.add(Cell.fromChar('0'));
            }
            else {
                newGen.add(Cell.fromChar('1'));
            }
        }
    }

    private void evolve() {
        List<Cell> newGen = new ArrayList<>();
        for (int r = 0; r < oldGens.get(0).size(); r++) {
            if(r == 0) {
                rowEvolve(oldGens.get(oldGens.size() - 1),
                        oldGens.get(r + 1), oldGens.get(r), newGen);
            }
            else if(r == oldGens.size() - 1) {
                rowEvolve(oldGens.get(r - 1),
                        oldGens.get(0), oldGens.get(r), newGen);
            }
            else {
                rowEvolve(oldGens.get(r - 1),
                        oldGens.get(r + 1), oldGens.get(r), newGen);
            }
        }
        updateList = newGen;
    }

    private void show() {
        int colIndex = 0;
        // Create new rectangles to show for the current generation
        for (Cell cell : updateList) {
            // Create a rectangle to represent the cell
            Rectangle rect = new Rectangle(cellSize,
                    cellSize, cell.getColor());
            // Add it to the JavaFX graph
            gridPane.add(rect, colIndex,currentGenIndex);
            // Go to the next cell
            colIndex++;
        }
        // Row index
        currentGenIndex++;
    }
    public void nextGeneration() {
        evolve();
        show();
    }
}
