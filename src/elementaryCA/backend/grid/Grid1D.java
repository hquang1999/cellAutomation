package elementaryCA.backend.grid;

import elementaryCA.backend.cell.Cell;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

import static elementaryCA.backend.cell.Cell.ONE;
import static elementaryCA.backend.cell.Cell.ZERO;
/**
 * Author: Hieu Quang
 * Version: 2.0
 * Date: 18/11/20
 * This class does the evolution.
 */
public class Grid1D {
    // Conditions
    private char s111;
    private char s110;
    private char s101;
    private char s100;
    private char s011;
    private char s010;
    private char s001;
    private char s000;

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

        setBehaviours();
    }

    /**
     * This class sets the behaviors from the behavior string to
     * our assigned conditions.
      */
    private void setBehaviours () {
        s111 = behavior.charAt(0);
        s110 = behavior.charAt(1);
        s101 = behavior.charAt(2);
        s100 = behavior.charAt(3);
        s011 = behavior.charAt(4);
        s010 = behavior.charAt(5);
        s001 = behavior.charAt(6);
        s000 = behavior.charAt(7);
    }

    /**
     * This function does the transformation of the
     * conditions set by setBehaviours.It checks using
     * the indexes we gave and updates the list.
     * @param right index
     * @param left index
     * @param middle index
     * @param nextGen nextGen list
     */
    private void cellTransform(int right, int left
            , int middle, List<Cell> nextGen) {
        if (currentGen.get(middle) == ONE) { // MIDDLE
            // Condition 111
            if (currentGen.get(right) == ONE // RIGHT
                    && currentGen.get(left) == ONE) { // LEFT
                nextGen.add(Cell.fromChar(s111));
            }
            // Condition 011
            else if (currentGen.get(right) == ONE // RIGHT
                    && currentGen.get(left) == ZERO) { // LEFT
                nextGen.add(Cell.fromChar(s011));
            }
            // Condition 110
            else if (currentGen.get(right) == ZERO // RIGHT
                    && currentGen.get(left) == ONE) { // LEFT
                nextGen.add(Cell.fromChar(s110));
            }
            // Condition 010
            else if (currentGen.get(right) == ZERO // RIGHT
                    && currentGen.get(left) == ZERO) { // LEFT
                nextGen.add(Cell.fromChar(s010));
            }
        }
        // Middle Number 0
        else if (currentGen.get(middle) == ZERO) { // MIDDLE
            // Condition 101
            if (currentGen.get(right) == ONE // RIGHT
                    && currentGen.get(left) == ONE) { // LEFT
                nextGen.add(Cell.fromChar(s101));
            }
            // Condition 001
            else if (currentGen.get(right) == ONE //RIGHT
                    && currentGen.get(left) == ZERO) { //LEFT
                nextGen.add(Cell.fromChar(s001));
            }
            // Condition 100
            else if (currentGen.get(right) == ZERO // RIGHT
                    && currentGen.get(left) == ONE) { // LEFT
                nextGen.add(Cell.fromChar(s100));
            }
            // Condition 000
            else if (currentGen.get(right) == ZERO // RIGHT
                    && currentGen.get(left) == ZERO) { // LEFT
                nextGen.add(Cell.fromChar(s000));
            }
        }
    }

    /**
     * This function does the loop for the cell evolution.
     */
    private void evolve() {
        List<Cell> nextGen = new ArrayList<>();
        for (int i = 0; i < currentGen.size(); i++) {
            // Special Condition: The beginning has no
            // right so it wraps around to the end.
            if (i == 0) {
                cellTransform(i + 1,
                        currentGen.size() - 1, i, nextGen);
            }
            // Special Condition: The end has no left
            // so it wraps around to the beginning.
            else if (i == currentGen.size() - 1) {
                cellTransform(0, i - 1, i, nextGen);
            }
            // Normal condition.
            else {
                cellTransform(i + 1,i - 1, i, nextGen);
            }
        }
        // Inserting newGen to currentGen.
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

    /**
     * This function advances the state of the class to the next generation.
     * It then shows this new generation to the Java FX window.
     */
    public void nextGeneration() {
        evolve();
        show();
    }
}
