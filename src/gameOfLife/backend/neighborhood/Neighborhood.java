package gameOfLife.backend.neighborhood;

import gameOfLife.backend.cell.Cell;
import javafx.scene.layout.GridPane;

import java.util.List;

public class Neighborhood {

    private final GridPane gridPane;

    private final double cellSize;

    private List<List<Cell>> allGens;

    public Neighborhood(GridPane gridPane
             , List<List<Cell>> allGens, double cellSize) {
        this.gridPane = gridPane;
        this.cellSize = cellSize;
        this.allGens = allGens;
    }

    public void nextGeneration() {

    }
}
