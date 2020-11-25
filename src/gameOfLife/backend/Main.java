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
        Scanner scanner = new Scanner (System.in);

        System.out.println("John Conway's Game of Life");
        System.out.println("[r] Read in file");
        System.out.println("[u] User input");

        String choice = scanner.next();
        choice.toLowerCase();

        switch (choice) {
            case "r":
                File input = new File(args[0]);
                Scanner scan = new Scanner(input);

                while (scan.hasNextLine()) {
                    file.add(scan.nextLine());
                }
                file.remove(0);

                row = file.get(0).length();
                column = file.size();

                launch(args);
                break;
            case "u":
                userInputRow();
                contUserInputRow();
                column = file.size();

                launch(args);
                break;
            default:
                System.out.println("Invalid INPUT!");
                main(args);
                break;
        }
    }

    private static boolean checker(String string) {
        boolean temp = true;
        // Checking loop.
        for (int i = 0; i < string.length(); i++) {
            // If not 1.
            if (string.charAt(i) != '1') {
                // If not 0.
                if (string.charAt(i) != '0') {
                    // It's an invalid choice.
                    System.out.println(string + " is not a valid " +
                            "choice!");
                    temp = false;
                    break;
                }
            }
        }
        return temp;
    }

    private static void userInputRow () {
        System.out.println("Input Row w/ 1 or 0");

        Scanner scanner = new Scanner (System.in);
        String choice = scanner.next();

        boolean valid = checker(choice);

        if (valid) {
            row = choice.length();
            file.add(choice);
        }
        else {
            userInputRow();
        }
    }

    private static void contUserInputRow () {
        System.out.println("Keep inputting? [y] / [n]");

        Scanner scanner = new Scanner (System.in);
        String choice = scanner.next();

        switch(choice) {
            case "y":
                uInputRow();
                contUserInputRow();
                break;
            case "n":
                break;
            default:
                System.out.println(choice + " is not a valid " +
                        "choice!");
                contUserInputRow();
                break;
        }
    }

    private static void uInputRow() {
        System.out.println("Input Row w/ 1 or 0. Length must" +
                " be " + row);

        Scanner scanner = new Scanner (System.in);
        String choice = scanner.next();
        if (choice.length() == row) {
            boolean valid = checker(choice);

            if (valid) {
                row = choice.length();
                file.add(choice);
            }
            else {
                uInputRow();
            }
        }
        else {
            System.out.println("Invalid ROW length!");
            uInputRow();
        }

    }

    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Game of Life");

        GridPane root = new GridPane();
        double width = row * 20;
        double height = column * 20;
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
                Neighborhood(root, allGens,20, row);

        Runner.run(grid);
    }
}
