package langtonsLoop.backend.neighborhood;

import langtonsLoop.backend.cell.Cell;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;
/**
 * Author: Hieu Quang
 * Version: 2.0
 * Date: 1/12/20
 * This class does all the cell updating and looping.
 */
public class Neighborhood {

    // Grid pane
    private final GridPane gridPane;

    // Cell size
    private final double cellSize;

    // Current generation that we need to update.
    private List<List<Cell>> oldGens;

    // List of our rules.
    private final List<List<Cell>> rules;

    /***
     * Default Constructor
     * @param gridPane Pane
     * @param allGens Our current generation that we need to update.
     * @param rules Rule list.
     * @param cellSize Cell size.
     */
    public Neighborhood(GridPane gridPane
             , List<List<Cell>> allGens
             , List<List<Cell>> rules
             , double cellSize) {
        this.gridPane = gridPane;
        this.cellSize = cellSize;
        this.oldGens = allGens;
        this.rules = rules;
    }

    /**
     * This function checks the individual neighbor combinations
     * against our rule list to find out what the center cell will
     * become.
     * @param mid Our cell at the middle. This is the first cell
     *            combination.
     * @param NESW List cells: NORTH, EAST, SOUTH, WEST.
     * @param ESWN List cells: EAST, SOUTH, WEST, NORTH.
     * @param SWNE List cells: SOUTH, WEST, NORTH, EAST.
     * @param WNES List cells: WEST, NORTH, EAST, SOUTH.
     * @param finalRow Our new row.
     */
    private void cellChecking (Cell mid,List<Cell> NESW,
            List<Cell> ESWN, List<Cell> SWNE, List<Cell> WNES,
                               List<Cell> finalRow) {

        for (int i = 0; i < rules.size(); i++) {
            // Temporary variable that we can use if we found a matching rule.
            List<Cell> foundRule = rules.get(i);
            // First, we check all rule lists to see if they have
            // the correct first cell (Check against our mid).
            if (rules.get(i).get(0) == mid) {
                // If we found a correct list, we add it's four cells
                // into a temporary list to compare.
                List<Cell> tempRules = new ArrayList<>();
                tempRules.add(rules.get(i).get(1));
                tempRules.add(rules.get(i).get(2));
                tempRules.add(rules.get(i).get(3));
                tempRules.add(rules.get(i).get(4));

                // Temporary length variable.
                int fRLength = foundRule.size() - 1;

                // Here, we check if the combinations matches,
                // I used .equals because order MATTERS.
                if (NESW.equals(tempRules)) {
                    // If the temp combination matches with our row,
                    // we get the rule combination's last cell and add
                    // it to our finalRow.
                    finalRow.add(foundRule.get(fRLength));
                    break;
                }
                else if (ESWN.equals(tempRules)) {
                    finalRow.add(foundRule.get(fRLength));
                    break;
                }
                else if (SWNE.equals(tempRules)) {
                    finalRow.add(foundRule.get(fRLength));
                    break;
                }
                else if (WNES.equals(tempRules)) {
                    finalRow.add(foundRule.get(fRLength));
                    break;
                }
            }
        }
    }

    /**
     * This function loops through the rows and check the neighbors.
     * We only need North, East, South, and West in a clockwise direction.
     * But since the neighbors are rotationally symmetrical, we need to
     * check multiple combinations.
     * @param top The top row.
     * @param bottom The bottom row.
     * @param middle The middle row (current row).
     * @param newGen Our main List<List<Cell>>.
     */
    private void rowEvolve (List<Cell> top
            , List<Cell> bottom, List <Cell> middle
            , List<List<Cell>> newGen) {
        // This is our new row that we update.
        List<Cell> finalRow = new ArrayList<>();

        for(int i = 0; i < middle.size(); i++) {
            // This is for normal checking.
            if ((i != 0) && (i != middle.size() - 1)) {
                // NORTH, EAST, SOUTH, WEST combination.
                List<Cell> NESW = new ArrayList<>();
                NESW.add(top.get(i));
                NESW.add(middle.get(i + 1));
                NESW.add(bottom.get(i));
                NESW.add(middle.get(i - 1));

                // EAST, SOUTH, WEST, NORTH combination.
                List<Cell> ESWN = new ArrayList<>();
                ESWN.add(middle.get(i + 1));
                ESWN.add(bottom.get(i));
                ESWN.add(middle.get(i - 1));
                ESWN.add(top.get(i));

                // SOUTH, WEST, NORTH, EAST combination.
                List<Cell> SWNE = new ArrayList<>();
                SWNE.add(bottom.get(i));
                SWNE.add(middle.get(i - 1));
                SWNE.add(top.get(i));
                SWNE.add(middle.get(i + 1));

                // WEST, NORTH, EAST, SOUTH combination.
                List<Cell> WNES = new ArrayList<>();
                WNES.add(middle.get(i - 1));
                WNES.add(top.get(i));
                WNES.add(middle.get(i + 1));
                WNES.add(bottom.get(i));

                // Now we use our list to compare with the rules.
                cellChecking(middle.get(i),
                        NESW,ESWN,SWNE,WNES,finalRow);
            }
            // Checking the very left index of the row. NO RIGHTS.
            else if (i == 0) {
                // NORTH, EAST, SOUTH, WEST combination.
                List<Cell> NESW = new ArrayList<>();
                NESW.add(top.get(i));
                NESW.add(middle.get(i + 1));
                NESW.add(bottom.get(i));
                // Since there is nothing to the left,
                // we wrap around and use the last value.
                NESW.add(middle.get(middle.size() - 1));

                // EAST, SOUTH, WEST, NORTH combination.
                List<Cell> ESWN = new ArrayList<>();
                ESWN.add(middle.get(i + 1));
                ESWN.add(bottom.get(i));
                // Since there is nothing to the left,
                // we wrap around and use the last value.
                ESWN.add(middle.get(middle.size() - 1));
                ESWN.add(top.get(i));

                // SOUTH, WEST, NORTH, EAST combination.
                List<Cell> SWNE = new ArrayList<>();
                SWNE.add(bottom.get(i));
                // Since there is nothing to the left,
                // we wrap around and use the last value.
                SWNE.add(middle.get(middle.size() - 1));
                SWNE.add(top.get(i));
                SWNE.add(middle.get(i + 1));

                // WEST, NORTH, EAST, SOUTH combination.
                List<Cell> WNES = new ArrayList<>();
                // Since there is nothing to the left,
                // we wrap around and use the last value.
                WNES.add(middle.get(middle.size() - 1));
                WNES.add(top.get(i));
                WNES.add(middle.get(i + 1));
                WNES.add(bottom.get(i));

                // Now we use our list to compare with the rules.
                cellChecking(middle.get(i),
                        NESW,ESWN,SWNE,WNES,finalRow);
            }
            // Checking the very right index of the row. NO LEFTS.
            else if (i == middle.size() - 1) {
                // NORTH, EAST, SOUTH, WEST combination.
                List<Cell> NESW = new ArrayList<>();
                NESW.add(top.get(i));
                // Since there is nothing to the right,
                // we wrap around and use the first value.
                NESW.add(middle.get(0));
                NESW.add(bottom.get(i));
                NESW.add(middle.get(i - 1));

                // EAST, SOUTH, WEST, NORTH combination.
                List<Cell> ESWN = new ArrayList<>();
                // Since there is nothing to the right,
                // we wrap around and use the first value.
                ESWN.add(middle.get(0));
                ESWN.add(bottom.get(i));
                ESWN.add(middle.get(i - 1));
                ESWN.add(top.get(i));

                // SOUTH, WEST, NORTH, EAST combination.
                List<Cell> SWNE = new ArrayList<>();
                SWNE.add(bottom.get(i));
                SWNE.add(middle.get(i - 1));
                SWNE.add(top.get(i));
                // Since there is nothing to the right,
                // we wrap around and use the first value.
                SWNE.add(middle.get(0));

                // WEST, NORTH, EAST, SOUTH combination.
                List<Cell> WNES = new ArrayList<>();
                WNES.add(middle.get(i - 1));
                WNES.add(top.get(i));
                // Since there is nothing to the right,
                // we wrap around and get the first value.
                WNES.add(middle.get(0));
                WNES.add(bottom.get(i));

                // Now we use our list to compare with the rules.
                cellChecking(middle.get(i),
                        NESW,ESWN,SWNE,WNES,finalRow);
            }
        }
        // Once for loop is done, we will have a completed row.
        // We then add it to our giant list of lists.
        newGen.add(finalRow);
    }

    /**
     * This is the main evolve function. It only loops row
     * by row through the List<List<Cell>>.
     */
    private void evolve() {
        List<List<Cell>> newGen = new ArrayList<>();

        for (int r = 0; r < oldGens.size(); r++) {
            // Using the top and bottom rows normally.
            if ((r > 0) && (r < oldGens.size() - 1)) {
                rowEvolve(oldGens.get(r - 1),
                        oldGens.get(r + 1), oldGens.get(r),
                        newGen);
            }
            // Since we are at the top row and there's nothing above,
            // we wrap around and use the last row as our top.
            else if(r == 0) {
                rowEvolve(oldGens.get(oldGens.size() - 1),
                        oldGens.get(r + 1), oldGens.get(r),
                        newGen);
            }
            // Since we are at the last row and there's nothing below,
            // we wrap around and use the first row as our bottom.
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
    public void show() {
        gridPane.getChildren().clear();
        int rowIndex = 0;
        int columnIndex = 0;
        for (List<Cell> curRow : oldGens) {
            for(Cell cell : curRow) {
                // Create a rectangle to represent the cell
                Rectangle rect = new Rectangle(cellSize,
                        cellSize, cell.getColor());
                gridPane.getChildren().remove(cell);
                // Add it to the JavaFX graph
                gridPane.add(rect, columnIndex, rowIndex);
                // Go to the next cell
                columnIndex++;
            }
            columnIndex = 0;
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