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

    private static int userRowMax;

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
                askMaxRows();
                userInputRow();
                recurUserInputRow();
                column = file.size();

                launch(args);
                break;
            default:
                System.out.println("Invalid INPUT!");
                main(args);
                break;
        }
    }

    private static void askMaxRows() {
        System.out.println("How Many Rows Do You Want to Input? [1 - 10000]");
        Scanner scanner = new Scanner (System.in);
        int choice = scanner.nextInt();
        if ((choice > 0) && (choice < 10001)) {
            userRowMax = choice;
        }
        else {
            System.out.println("Invalid Choice!");
            askMaxRows();
        }
    }

    private static boolean checkNum(String string) {
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
        System.out.println(userRowMax + " rows remaining.");
        System.out.println("Input Row w/ 1 or 0");

        Scanner scanner = new Scanner(System.in);
        String choice = scanner.next();

        boolean valid = checkNum(choice);

        if (valid) {
            row = choice.length();
            file.add(choice);
            userRowMax--;
        } else {
            userInputRow();
        }
    }

    private static void recurUserInputRow () {
        if (userRowMax > 0) {
            System.out.println();
            if (userRowMax == 1) {
                System.out.println(userRowMax + " row remaining.");
            }
            else {
                System.out.println(userRowMax + " rows remaining.");
            }
            checkRowLength();
            userRowMax--;
            recurUserInputRow();
        }
    }

    private static void checkRowLength() {
        System.out.println("Input Row w/ 1 or 0. Length must" +
                " be " + row);

        Scanner scanner = new Scanner (System.in);
        String choice = scanner.next();
        if (choice.length() == row) {
            boolean valid = checkNum(choice);

            if (valid) {
                row = choice.length();
                file.add(choice);
            }
            else {
                checkRowLength();
            }
        }
        else {
            System.out.println("Invalid ROW length!");
            checkRowLength();
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
