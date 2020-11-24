package gameOfLife.backend.neighborhood;

import gameOfLife.backend.cell.Cell;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

import static gameOfLife.backend.cell.Cell.ONE;
import static gameOfLife.backend.cell.Cell.ZERO;

public class Neighborhood {

    private final GridPane gridPane;

    private final double cellSize;

    private List<List<Cell>> oldGens;

    private int row;

    private int column;

    private List<Cell> updateList;
    private int currentGenIndex;

    public Neighborhood(GridPane gridPane
             , List<List<Cell>> allGens, double cellSize
             , int rows, int columns) {
        this.gridPane = gridPane;
        this.cellSize = cellSize;
        this.oldGens = allGens;
        this.currentGenIndex = 0;
        this.row = rows;
        this.column = columns;

    }

    private void addNeighbors(int neighbors
            , List<Cell> temp, Cell cell) {
        if ((neighbors < 2) || (neighbors > 3)){
            temp.add(Cell.fromChar('0'));
        }


        if ((cell == ONE) && ((neighbors == 2) || neighbors == 3)){
            temp.add(Cell.fromChar('1'));
        }

        if ((cell == ZERO) && (neighbors == 3)) {
            temp.add(Cell.fromChar('1'));
        }
        else if ((cell == ZERO) &&
                (neighbors == 2)) {
            temp.add(Cell.fromChar('0'));
        }
    }
    private void rowEvolve (List<Cell> top
            , List<Cell> bottom, List <Cell> middle
            , List<List<Cell>> newGen) {
        int neighbors;
        List<Cell> temp = new ArrayList<>();
        // Looping through one line, going right
        for(int i = 0; i < middle.size(); i++) {
            if (i == 0) {
                neighbors = 0;
                for (int t = i; t < i + 2; t++) {
                    if (top.get(t) == ONE) {
                        neighbors++;
                    }
                    if (bottom.get(t) == ONE) {
                        neighbors++;
                    }
                }
                if (top.get(middle.size() - 1) == ONE) {
                    neighbors++;
                }
                if (bottom.get(middle.size() - 1) == ONE) {
                    neighbors++;
                }
                if (middle.get(i + 1) == ONE) {
                    neighbors++;
                }
                if (middle.get(middle.size() - 1) == ONE) {
                    neighbors++;
                }
                addNeighbors(neighbors,temp,middle.get(i));
            }
            else if (i == middle.size() - 1) {
                neighbors = 0;
                for (int t = i; t > i - 2; t--) {
                    if (top.get(t) == ONE) {
                        neighbors++;
                    }
                    if (bottom.get(t) == ONE) {
                        neighbors++;
                    }
                }
                if (top.get(0) == ONE) {
                    neighbors++;
                }
                if (bottom.get(0) == ONE) {
                    neighbors++;
                }
                if (middle.get(i - 1) == ONE) {
                    neighbors++;
                }
                if (middle.get(0) == ONE) {
                    neighbors++;
                }
                addNeighbors(neighbors,temp,middle.get(i));
            }
            else {
                neighbors = 0;
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
                addNeighbors(neighbors,temp,middle.get(i));
            }
        }
        newGen.add(temp);
    }

    private void evolve() {
        List<List<Cell>> newGen = new ArrayList<>();
        for (int r = 0; r < row; r++) {
            if(r == 0) {
                rowEvolve(oldGens.get(oldGens.size() - 1),
                        oldGens.get(r + 1), oldGens.get(r), newGen);

            }
            else if(r == oldGens.get(0).size() - 1) {
                rowEvolve(oldGens.get(r - 1),
                        oldGens.get(0), oldGens.get(r), newGen);

            }
            else {
                rowEvolve(oldGens.get(r - 1),
                        oldGens.get(r + 1), oldGens.get(r), newGen);
            }
        }

        printList(oldGens);
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
        //show();
        evolve();
    }
}
