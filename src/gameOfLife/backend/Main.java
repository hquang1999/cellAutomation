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

    // List to hold all the strings from file or user.
    private static final List<String> file = new ArrayList<>();

    // These are the max lengths.
    private static int row;
    private static int column;

    // This is the max amount of rows the user can have.
    // You can determine it below at askMaxRows().
    private static int userRowLimit;

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner (System.in);

        System.out.println("John Conway's Game of Life");
        System.out.println("[r] Read in file");
        System.out.println("[u] User input");

        String choice = scanner.next();
        choice.toLowerCase();

        switch (choice) {
            case "r":
                // Reading in our file.
                File input = new File(args[0]);
                Scanner scan = new Scanner(input);

                while (scan.hasNextLine()) {
                    file.add(scan.nextLine());
                }
                // I don't need the rows and column from the first line.
                file.remove(0);

                row = file.get(0).length();
                column = file.size();

                launch(args);
                break;
            case "u":
                // User input functions.
                askMaxRows();
                userInputRow();
                recurUserInputRow();
                column = file.size();

                launch(args);
                break;
            default:
                // Else, invalid input.
                System.out.println("Invalid input!");
                main(args);
                break;
        }
    }

    /**
     * This function asks how many rows the user wants to input.
     * I put 10,000 because no sane person will input more than
     * that many rows in a single sitting. Function is called
     * recursively to avoid user mistakes.
     */
    private static void askMaxRows() {
        System.out.println("How Many Rows Do You Want to Input? " +
                "[1 - 10000]");
        Scanner scanner = new Scanner (System.in);
        int choice = scanner.nextInt();
        // If choice is valid, we have a new row limit.
        if ((choice > 0) && (choice < 10001)) {
            userRowLimit = choice;
        }
        // Otherwise, call recursively.
        else {
            System.out.println("Invalid Choice!");
            askMaxRows();
        }
    }

    /**
     * This function asks the user for their first row. It also
     * sets the row size limit that all other rows will be using.
     */
    private static void userInputRow () {
        System.out.println(userRowLimit + " rows remaining.");
        System.out.println("Input Row w/ 1 or 0");

        Scanner scanner = new Scanner(System.in);
        String choice = scanner.next();

        boolean valid = checkNum(choice);
        // If valid, we have a new row max length and we add
        // it to our list.
        if (valid) {
            row = choice.length();
            file.add(choice);
            // Used up a row.
            userRowLimit--;
        } else {
            userInputRow();
        }
    }

    /**
     * This function checks if the string only contains 1 or 0.
     * @param string Inputting the string.
     * @return True = valid, false = invalid.
     */
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

    /**
     * This function recursively ask the user for the row input. It's
     * more like a backbone. Function ends when the row limit reaches
     * zero.
     */
    private static void recurUserInputRow () {
        if (userRowLimit > 0) {
            System.out.println();
            if (userRowLimit == 1) {
                System.out.println(userRowLimit + " row remaining.");
            }
            else {
                System.out.println(userRowLimit + " rows remaining.");
            }
            checkRowLength();
            // Used up a row.
            userRowLimit--;
            recurUserInputRow();
        }
    }

    /**
     * This function asks the user for their row. It also
     * checks if their row length for their input is valid.
     */
    private static void checkRowLength() {
        System.out.println("Input Row w/ 1 or 0. Length must" +
                " be " + row);

        Scanner scanner = new Scanner (System.in);
        String choice = scanner.next();
        // Checks string length.
        if (choice.length() == row) {
            // Checks if it only contains 0 or 1.
            boolean valid = checkNum(choice);

            if (valid) {
                // If valid, we add it to the file.
                file.add(choice);
            }
            else {
                System.out.println("Invalid input!");
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

        char state;

        // Converts our file into a list of list<cells>
        List<List<Cell>> allGens = new ArrayList<>();
        for (int c = 0; c < column; c++) {
            List<Cell> allGensRows = new ArrayList<>();
            for (int r = 0; r < row; r++) {
               state = file.get(c).charAt(r);
               allGensRows.add(Cell.fromChar(state));
            }
            allGens.add(allGensRows);
        }

        // Add the stuff to our neighborhood to do cell manipulation.
        Neighborhood grid = new
                Neighborhood(root, allGens,20, row);

        Runner.run(grid);
    }
}
