package elementaryCA.backend.grid;

import elementaryCA.backend.cell.Cell;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

import static elementaryCA.backend.cell.Cell.ONE;
import static elementaryCA.backend.cell.Cell.ZERO;

public class Grid1D {
    // Used by JavaFX to display the visualization
    private final GridPane gridPane;
    // 8 bit string representing the behavior of the CA
    private final String behavior;
    // Width/height of cell
    private final double cellSize;

    // Current generation of cells
    private List<Cell> currentGen;
    // Current generation (row) being shown to the screen
    private int currentGenIndex;

    public Grid1D(GridPane gridPane,
                  String behavior,
                  List<Cell> currentGen,
                  double cellSize) {
        this.gridPane = gridPane;
        this.behavior = behavior;
        this.currentGen = currentGen;
        this.cellSize = cellSize;
        this.currentGenIndex = 0;
        // Show the initial generation to the screen
        show();
    }

    /**
     * TODO: Fill in the logic below
     * I would suggest starting off by hard coding one of the rules,
     * such as rule 30, then generalize from there.
     * This function evolves the current generation to the next generation
     * using the current rule set given by the behavior string.
     */
    private void evolve() {
        List<Cell> nextGen = new ArrayList<>();
        for (int i = 0; i < currentGen.size(); i++) {
            if (i == 0) {
                // Middle Number 1
                if (currentGen.get(i) == ONE) {
                    // Condition 111
                    if (currentGen.get(i + 1) == ONE // RIGHT
                            && currentGen.get(currentGen.size() - 1) == ONE) { // LEFT
                        nextGen.add(Cell.fromChar('0'));
                    }
                    // Condition 011
                    else if (currentGen.get(i + 1) == ONE
                            && currentGen.get(currentGen.size() - 1) == ZERO) {
                        nextGen.add(Cell.fromChar('1'));
                    }
                    // Condition 110
                    else if (currentGen.get(i + 1) == ZERO
                            && currentGen.get(currentGen.size() - 1) == ONE) {
                        nextGen.add(Cell.fromChar('0'));
                    }
                    // Condition 010
                    else if (currentGen.get(i + 1) == ZERO
                            && currentGen.get(currentGen.size() - 1) == ZERO) {
                        nextGen.add(Cell.fromChar('1'));
                    }
                }
                // Middle Number 0
                else if (currentGen.get(i) == ZERO) {
                    // Condition 101
                    if (currentGen.get(i + 1) == ONE
                            && currentGen.get(currentGen.size() - 1) == ONE) {
                        nextGen.add(Cell.fromChar('0'));
                    }
                    // Condition 001
                    else if (currentGen.get(i + 1) == ONE
                            && currentGen.get(currentGen.size() - 1) == ZERO) {
                        nextGen.add(Cell.fromChar('1'));
                    }
                    // Condition 100
                    else if (currentGen.get(i + 1) == ZERO
                            && currentGen.get(currentGen.size() - 1) == ONE) {
                        nextGen.add(Cell.fromChar('1'));
                    }
                    // Condition 000
                    else if (currentGen.get(i + 1) == ZERO
                            && currentGen.get(currentGen.size() - 1) == ZERO) {
                        nextGen.add(Cell.fromChar('0'));
                    }
                }
            }
            else if (i == currentGen.size() - 1) {
                // Middle Number 1
                if (currentGen.get(i) == ONE) {
                    // Condition 111
                    if (currentGen.get(0) == ONE
                            && currentGen.get(i - 1) == ONE) {
                        nextGen.add(Cell.fromChar('0'));
                    }
                    // Condition 011
                    else if (currentGen.get(i) == ONE
                            && currentGen.get(i - 1) == ZERO) {
                        nextGen.add(Cell.fromChar('1'));
                    }
                    // Condition 110
                    else if (currentGen.get(0) == ZERO
                            && currentGen.get(i - 1) == ONE) {
                        nextGen.add(Cell.fromChar('0'));
                    }
                    // Condition 010
                    else if (currentGen.get(0) == ZERO
                            && currentGen.get(i - 1) == ZERO) {
                        nextGen.add(Cell.fromChar('1'));
                    }
                }
                // Middle Number 0
                else if (currentGen.get(i) == ZERO) {
                    // Condition 101
                    if (currentGen.get(0) == ONE
                            && currentGen.get(i - 1) == ONE) {
                        nextGen.add(Cell.fromChar('0'));
                    }
                    // Condition 001
                    else if (currentGen.get(0) == ONE
                            && currentGen.get(i - 1) == ZERO) {
                        nextGen.add(Cell.fromChar('1'));
                    }
                    // Condition 100
                    else if (currentGen.get(0) == ZERO
                            && currentGen.get(i - 1) == ONE) {
                        nextGen.add(Cell.fromChar('1'));
                    }
                    // Condition 000
                    else if (currentGen.get(0) == ZERO
                            && currentGen.get(i - 1) == ZERO) {
                        nextGen.add(Cell.fromChar('0'));
                    }
                }
            }
            else {
                // Middle Number 1
                if (currentGen.get(i) == ONE) {
                    // Condition 111
                    if (currentGen.get(i + 1) == ONE // RIGHT
                            && currentGen.get(i - 1) == ONE) { // LEFT
                        nextGen.add(Cell.fromChar('0'));
                    }
                    // Condition 011
                    else if (currentGen.get(i + 1) == ONE
                            && currentGen.get(i - 1) == ZERO) {
                        nextGen.add(Cell.fromChar('1'));
                    }
                    // Condition 110
                    else if (currentGen.get(i + 1) == ZERO
                            && currentGen.get(i - 1) == ONE) {
                        nextGen.add(Cell.fromChar('0'));
                    }
                    // Condition 010
                    else if (currentGen.get(i + 1) == ZERO
                            && currentGen.get(i - 1) == ZERO) {
                        nextGen.add(Cell.fromChar('1'));
                    }
                }
                // Middle Number 0
                else if (currentGen.get(i) == ZERO) {
                    // Condition 101
                    if (currentGen.get(i + 1) == ONE
                            && currentGen.get(i - 1) == ONE) {
                        nextGen.add(Cell.fromChar('0'));
                    }
                    // Condition 001
                    else if (currentGen.get(i + 1) == ONE
                            && currentGen.get(i - 1) == ZERO) {
                        nextGen.add(Cell.fromChar('1'));
                    }
                    // Condition 100
                    else if (currentGen.get(i + 1) == ZERO
                            && currentGen.get(i - 1) == ONE) {
                        nextGen.add(Cell.fromChar('1'));
                    }
                    // Condition 000
                    else if (currentGen.get(i + 1) == ZERO
                            && currentGen.get(i - 1) == ZERO) {
                        nextGen.add(Cell.fromChar('0'));
                    }
                }
            }
        }

        // Inserting newGen to currentGen
        currentGen = nextGen;
    }

    /**
     * This function shows the current generation to the JavaFX window
     */
    private void show() {
        int colIndex = 0;
        // Create new rectangles to show for the current generation
        for (Cell cell : currentGen) {
            // Create a rectangle to represent the cell
            Rectangle rect = new Rectangle(cellSize, cellSize, cell.getColor());
            // Add it to the JavaFX graph
            gridPane.add(rect, colIndex,currentGenIndex);
            // Go to the next cell
            colIndex++;
        }
        // Row index
        currentGenIndex++;
    }

    /**
     * This function advances the state of the class to the next generation.
     * It then shows this new generation to the Java FX window.
     */
    public void nextGeneration() {
        evolve();
        show();
    }
}
