package gameOfLife.backend.neighborhood;

import gameOfLife.backend.cell.Cell;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

import static gameOfLife.backend.cell.Cell.ONE;
import static gameOfLife.backend.cell.Cell.ZERO;
/**
 * Author: Hieu Quang
 * Version: 1.0
 * Date: 20/11/20
 * This class does all the cell looping and manipulation.
 */
public class Neighborhood {

    private final GridPane gridPane;

    private final double cellSize;

    private List<List<Cell>> oldGens;

    private int row;

    public Neighborhood(GridPane gridPane
             , List<List<Cell>> allGens, double cellSize
             , int rows) {
        this.gridPane = gridPane;
        this.cellSize = cellSize;
        this.oldGens = allGens;
        this.row = rows;
    }

    /**
     * This function determines whether the cell lives or dies.
     * @param neighbors Amount of neighbors.
     * @param temp List we add to.
     * @param cell Current cell in the position.
     */
    private void addNeighbors(int neighbors
            , List<Cell> temp, Cell cell) {
        /*
            If neighbors are less than 2 or more than 3, cell dies.
         */
        if ((neighbors < 2) || (neighbors > 3)){
            temp.add(Cell.fromChar('0'));
        }

        /*
            If the cell is alive, and the neighbors are 2 or 3,
            cell lives.
         */
        if ((cell == ONE) && ((neighbors == 2) || neighbors == 3)){
            temp.add(Cell.fromChar('1'));
        }
        /*
            If the cell is dead but it has 3 neighbors, it
            becomes alive.
         */
        if ((cell == ZERO) && (neighbors == 3)) {
            temp.add(Cell.fromChar('1'));
        }
        // Otherwise, if there are only 2, it dies.
        else if ((cell == ZERO) &&
                (neighbors == 2)) {
            temp.add(Cell.fromChar('0'));
        }
    }

    /**
     * This function does the looping through the rows. It's the
     * algorithm to find the neighbors. Since we are given the
     * top and bottom rows from evolve(), we don't have to
     * do any weird top and bottom wraps.
     * @param top The top row.
     * @param bottom The bottom row.
     * @param middle The middle row (current row).
     * @param newGen Our main List<List<Cell>>.
     */
    private void rowEvolve (List<Cell> top
            , List<Cell> bottom, List <Cell> middle
            , List<List<Cell>> newGen) {
        // Our total neighbors.
        int neighbors;
        // Temp list that we update.
        List<Cell> temp = new ArrayList<>();
        // Looping through one line, going right
        for(int i = 0; i < middle.size(); i++) {
            // This is for normal checking.
            if ((i != 0) && (i != middle.size() - 1)) {
                // Neighbors must be zero when we start.
                neighbors = 0;

                if (top.get(i - 1) == ONE) {
                    neighbors++;
                }
                if (top.get(i) == ONE) {
                    neighbors++;
                }
                if (top.get(i + 1) == ONE) {
                    neighbors++;
                }

                if (bottom.get(i - 1) == ONE) {
                    neighbors++;
                }
                if (bottom.get(i) == ONE) {
                    neighbors++;
                }
                if (bottom.get(i + 1) == ONE) {
                    neighbors++;
                }

                // Checking the left cell.
                if (middle.get(i - 1) == ONE) {
                    neighbors++;
                }
                // Checking the right cell.
                if (middle.get(i + 1) == ONE) {
                    neighbors++;
                }
                addNeighbors(neighbors,temp,middle.get(i));
            }
            // Checking the very left index of the row.
            else if (i == 0) {
                // Neighbors must be zero when we start.
                neighbors = 0;

                // SPECIAL CONDITION, we check the very right cell.
                if (top.get(middle.size() - 1) == ONE) {
                    neighbors++;
                }
                if (top.get(i) == ONE) {
                    neighbors++;
                }
                if (top.get(i + 1) == ONE) {
                    neighbors++;
                }

                // SPECIAL CONDITION, we check the very right cell.
                if (bottom.get(middle.size() - 1) == ONE) {
                    neighbors++;
                }
                if (bottom.get(i) == ONE) {
                    neighbors++;
                }
                if (bottom.get(i + 1) == ONE) {
                    neighbors++;
                }

                // Checking the right cell.
                if (middle.get(i + 1) == ONE) {
                    neighbors++;
                }
                // Can't check left, check right most.
                if (middle.get(middle.size() - 1) == ONE) {
                    neighbors++;
                }

                addNeighbors(neighbors,temp,middle.get(i));
            }
            // Checking the very right index of the row.
            else if (i == middle.size() - 1) {
                // Neighbors must be zero when we start.
                neighbors = 0;

                if (top.get(i - 1) == ONE) {
                    neighbors++;
                }
                if (top.get(i) == ONE) {
                    neighbors++;
                }
                // Instead, we check the very left cell.
                if (top.get(0) == ONE) {
                    neighbors++;
                }

                if (bottom.get(i - 1) == ONE) {
                    neighbors++;
                }
                if (bottom.get(i) == ONE) {
                    neighbors++;
                }
                if (bottom.get(0) == ONE) {
                    neighbors++;
                }

                // Checking the left cell.
                if (middle.get(i - 1) == ONE) {
                    neighbors++;
                }
                // Can't check right, check left most.
                if (middle.get(0) == ONE) {
                    neighbors++;
                }
                // Calls functions to check if the cell lives or dies.
                addNeighbors(neighbors,temp,middle.get(i));
            }
        }
        newGen.add(temp);
    }

    /**
     * This is the main evolve function. It only loops row
     * by row through the List<List<Cell>>.
     */
    private void evolve() {
        List<List<Cell>> newGen = new ArrayList<>();
        for (int r = 0; r < row; r++) {
            // Using the top and bottom rows normally.
            if ((r != 0) && (r != oldGens.size() - 1)) {
                rowEvolve(oldGens.get(r - 1),
                        oldGens.get(r + 1), oldGens.get(r),
                        newGen);
            }
            /*
                If row is at the top, we have to use the very bottom
                row as our top (last index) with a normal row
                for the bottom.
             */
            else if(r == 0) {
                rowEvolve(oldGens.get(oldGens.size() - 1),
                        oldGens.get(r + 1), oldGens.get(r),
                        newGen);
            }
            /*
                If row is at the bottom, we have to use the very
                top row as our bottom (index 0) with a
                a normal row for the top.
             */
            else if(r == oldGens.size() - 1) {
                rowEvolve(oldGens.get(r - 1),
                        oldGens.get(0), oldGens.get(r),
                        newGen);
            }
        }
        // Replace the older list with the new one.
        oldGens = newGen;
    }

    /**
     * JavaFX show. Joe gave this to us.
     */
    private void show() {
        gridPane.getChildren().clear();
        int colIndex = 0;
        int rowIndex = 0;
        // Create new rectangles to show for the current generation
        for (List<Cell> curRow : oldGens) {
            for (Cell cell : curRow) {
                // Create a rectangle to represent the cell
                Rectangle rect = new Rectangle(cellSize,
                        cellSize, cell.getColor());
                gridPane.getChildren().remove(cell);
                // Add it to the JavaFX graph
                gridPane.add(rect, colIndex, rowIndex);
                // Go to the next cell
                colIndex++;
            }
            colIndex = 0;
            rowIndex++;
        }
    }

    /**
     * This function calls everything together.
     */
    public void nextGeneration() {
        show();
        evolve();
    }
}
