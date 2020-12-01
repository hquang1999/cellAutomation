package langtonsLoop.backend.neighborhood;

import langtonsLoop.backend.cell.Cell;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static langtonsLoop.backend.cell.Cell.ONE;
import static langtonsLoop.backend.cell.Cell.ZERO;

public class Neighborhood {

    private final GridPane gridPane;

    private final double cellSize;

    private List<List<Cell>> oldGens;

    private final List<List<Cell>> rules;

    private final List<Cell> zeroes;

    private int row;

    public Neighborhood(GridPane gridPane
             , List<List<Cell>> allGens
             , List<List<Cell>> rules
             , List<Cell> zeroes
             , double cellSize
             , int rows) {
        this.gridPane = gridPane;
        this.cellSize = cellSize;
        this.oldGens = allGens;
        this.rules = rules;
        this.zeroes = zeroes;
        this.row = rows;

    }

    private void cellChecking (Cell mid,List<Cell> NESW,
            List<Cell> ESWN, List<Cell> SWNE, List<Cell> WNES,
                               List<Cell> finalRow) {

        for (int i = 0; i < rules.size(); i++) {
            if (rules.get(i).get(0) == mid) {
                List<Cell> tempRules = new ArrayList<>();
                tempRules.add(rules.get(i).get(1));
                tempRules.add(rules.get(i).get(2));
                tempRules.add(rules.get(i).get(3));
                tempRules.add(rules.get(i).get(4));

                if (NESW.equals(tempRules)) {
                    finalRow.add(rules.get(i).get(5));
                    break;
                }
                else if (ESWN.equals(tempRules)) {
                    finalRow.add(rules.get(i).get(5));
                    break;
                }
                else if (SWNE.equals(tempRules)) {
                    finalRow.add(rules.get(i).get(5));
                    break;
                }
                else if (WNES.equals(tempRules)) {
                    finalRow.add(rules.get(i).get(5));
                    break;
                }
            }
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
        // Temp list that we update.
        //System.out.println();
       // System.out.print("i = ");
        List<Cell> finalRow = new ArrayList<>();
        for(int i = 0; i < middle.size(); i++) {
           // System.out.print(i + ", ");
            // This is for normal checking.
            if ((i != 0) && (i != middle.size() - 1)) {
                List<Cell> NESW = new ArrayList<>();
                NESW.add(top.get(i));
                NESW.add(middle.get(i + 1));
                NESW.add(bottom.get(i));
                NESW.add(middle.get(i - 1));

                List<Cell> ESWN = new ArrayList<>();
                ESWN.add(middle.get(i + 1));
                ESWN.add(bottom.get(i));
                ESWN.add(middle.get(i - 1));
                ESWN.add(top.get(i));

                List<Cell> SWNE = new ArrayList<>();
                SWNE.add(bottom.get(i));
                SWNE.add(middle.get(i - 1));
                SWNE.add(top.get(i));
                SWNE.add(middle.get(i + 1));

                List<Cell> WNES = new ArrayList<>();
                WNES.add(middle.get(i - 1));
                WNES.add(top.get(i));
                WNES.add(middle.get(i + 1));
                WNES.add(bottom.get(i));

                cellChecking(middle.get(i),NESW,ESWN,SWNE,WNES,finalRow);
            }

            // Checking the very left index of the row.
            else if (i == 0) {
                List<Cell> NESW = new ArrayList<>();
                NESW.add(top.get(i));
                NESW.add(middle.get(i + 1));
                NESW.add(bottom.get(i));
                NESW.add(middle.get(middle.size() - 1));

                List<Cell> ESWN = new ArrayList<>();
                ESWN.add(middle.get(i + 1));
                ESWN.add(bottom.get(i));
                ESWN.add(middle.get(middle.size() - 1));
                ESWN.add(top.get(i));

                List<Cell> SWNE = new ArrayList<>();
                SWNE.add(bottom.get(i));
                SWNE.add(middle.get(middle.size() - 1));
                SWNE.add(top.get(i));
                SWNE.add(middle.get(i + 1));

                List<Cell> WNES = new ArrayList<>();
                WNES.add(middle.get(middle.size() - 1));
                WNES.add(top.get(i));
                WNES.add(middle.get(i + 1));
                WNES.add(bottom.get(i));

                cellChecking(middle.get(i),NESW,ESWN,SWNE,WNES,finalRow);
            }
            // Checking the very right index of the row.
            else if (i == middle.size() - 1) {
                List<Cell> NESW = new ArrayList<>();
                NESW.add(top.get(i));
                NESW.add(middle.get(0));
                NESW.add(bottom.get(i));
                NESW.add(middle.get(i - 1));

                List<Cell> ESWN = new ArrayList<>();
                ESWN.add(middle.get(0));
                ESWN.add(bottom.get(i));
                ESWN.add(middle.get(i - 1));
                ESWN.add(top.get(i));

                List<Cell> SWNE = new ArrayList<>();
                SWNE.add(bottom.get(i));
                SWNE.add(middle.get(i - 1));
                SWNE.add(top.get(i));
                SWNE.add(middle.get(0));

                List<Cell> WNES = new ArrayList<>();
                WNES.add(middle.get(i - 1));
                WNES.add(top.get(i));
                WNES.add(middle.get(0));
                WNES.add(bottom.get(i));

                cellChecking(middle.get(i),NESW,ESWN,SWNE,WNES,finalRow);

            }
        }
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

            else if(r == 0) {
                rowEvolve(oldGens.get(oldGens.size() - 1),
                        oldGens.get(r + 1), oldGens.get(r),
                        newGen);
            }
            else if(r == oldGens.size() - 1) {
                rowEvolve(oldGens.get(r - 1),
                        oldGens.get(0), oldGens.get(r),
                        newGen);
            }
        }
        // Replace the older list with the new one.
        //printList(oldGens);
        oldGens = newGen;
    }

    private void printList(List<List<Cell>> list) {
        System.out.println();
        for (int z = 0; z < list.size(); z++) {
            for (int i = 0; i < list.get(z).size(); i++) {
                System.out.print(list.get(z).get(i).getInt());
            }
            System.out.println();
        }
    }

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

    public void nextGeneration() {
        show();
        evolve();
    }
}