package langtonsLoop.backend;

import langtonsLoop.backend.cell.Cell;
import langtonsLoop.backend.neighborhood.Neighborhood;
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

    private static final List<String> initConfig
            = new ArrayList<>();
    private static final List<String> rules
            = new ArrayList<>();

    private static int row;
    private static int column;

    public static void main(String[] args) throws IOException {
        File input = new File(args[0]);
        Scanner scan = new Scanner(input);



        while (scan.hasNextLine()) {
            initConfig.add(scan.nextLine());
        }
        initConfig.remove(0);

        row = initConfig.get(0).length();
        column = initConfig.size();


        File input2 = new File(args[1]);
        Scanner scan2 = new Scanner(input2);

        while (scan2.hasNextLine()) {
            rules.add(scan2.nextLine());
        }

        launch(args);

    }

    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Langton's Loop");

        GridPane root = new GridPane();
        double width = 1000;
        double height = 1000;
        Scene scene = new Scene(root, width, height);

        primaryStage.setScene(scene);
        primaryStage.show();



        List<List<Cell>> allGens = new ArrayList<>();

        char state;
        for (int c = 0; c < column; c++) {
            List<Cell> allGensRows = new ArrayList<>();
            for (int r = 0; r < row; r++) {
               state = initConfig.get(c).charAt(r);
               allGensRows.add(Cell.fromChar(state));
            }
            allGens.add(allGensRows);
        }

        List<List<Cell>> listRules = new ArrayList<>();
        for (int i = 0; i < rules.size(); i++) {
            List<Cell> lRulesRows = new ArrayList<>();
            for (int z = 0; z < rules.get(0).length(); z++) {
                state = rules.get(i).charAt(z);
                lRulesRows.add(Cell.fromChar(state));
            }
            listRules.add(lRulesRows);
        }

        List<Cell> zeroes = new ArrayList<>();
        for (int i = 0; i < allGens.get(0).size(); i++) {
            zeroes.add(Cell.ZERO);
        }

        Neighborhood grid = new
                Neighborhood(root, allGens
                , listRules, zeroes,10, column);

        Runner.run(grid);
    }
}
