package gameOfLife.backend;

import gameOfLife.backend.cell.Cell;
import gameOfLife.backend.neighborhood.Neighborhood;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Author: Hieu Quang
 * Version: 2.0
 * Date: 20/11/20
 * This class is the main of the program.
 */
public class Main extends Application {

    private static final List<String> file = new ArrayList<>();

    private static int row;
    private static int column;

    public static void main(String[] args) throws IOException {

        File input = new File(args[0]);
        Scanner scan = new Scanner(input);

        while (scan.hasNextLine()) {
            file.add(scan.nextLine());
        }
        file.remove(0);

        row = file.get(0).length();
        column = file.size();

        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Game of Life");

        GridPane root = new GridPane();
        double width = 1201;
        double height = 1000;
        Scene scene = new Scene(root, width, height);

        primaryStage.setScene(scene);
        primaryStage.show();

        List<List<Cell>> allGens = new ArrayList<>();

        char state;
        for (int c = 0; c < column; c++) {
            List<Cell> allGensRows = new ArrayList<>();
            for (int r = 0; r < row; r++) {
               state = file.get(c).charAt(r);
               allGensRows.add(Cell.fromChar(state));
            }
            allGens.add(allGensRows);
        }

        Neighborhood grid = new
                Neighborhood(root, allGens,3, row, column);

        Runner.run(grid);
    }
}
