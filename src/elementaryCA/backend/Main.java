package elementaryCA.backend;

import java.io.*;

import elementaryCA.backend.cell.Cell;
import elementaryCA.backend.grid.Grid1D;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Author: Hieu Quang
 * Version: 5.0
 * Date: 19/11/20
 * This class is the main of the program.
 */

public class Main extends Application {

    // List for each line of the File
    private static final List<String> file = new ArrayList<>();

    private static String behavior;
    private static String firstGeneration;

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner (System.in);

        System.out.println("1D Cell Automation");
        System.out.println("[r] Read in file");
        System.out.println("[u] User input");

        String choice = scanner.next();
        choice.toLowerCase();

        switch (choice){
            case "r":
                /*
                Inputting our file. Run -> Edit Configurations -> file.txt
                 */
                File input = new File(args[0]);
                Scanner scan = new Scanner(input);

                // Scans the file.
                while (scan.hasNextLine()) {
                    file.add(scan.nextLine());
                }
                // First line is the behavior.
                behavior = file.get(0);
                // Second line is the first generation.
                firstGeneration = file.get(1);
                launch(args);
                break;
            case "u":
                // Calls this function to get the user's behavior.
                userInputBehavior();
                // Calls this function to get the user's 1st generation.
                userInputGeneration();
                launch(args);
                break;
            default:
                // If invalid choice, recall main.
                System.out.println("Invalid Choice!");
                main(args);
                break;
        }
    }

    /**
     * This function takes the user's input for the behavior.
     * This is called recursively in order to prevent inputs
     * outside the range of [0 - 255].
     */
    private static void userInputBehavior() {
        System.out.println("Input Pattern [0 - 255]");
        Scanner scanner = new Scanner (System.in);
        int choice = scanner.nextInt();

        // If statement to check.
        if ((choice > -1) && (choice < 256)) {
            behavior = Integer.toBinaryString(choice);
            adjust();
        }
        // Else, call recursively.
        else {
            userInputBehavior();
        }
    }
    /**
     * This function takes the behaviour's binary string
     * and adds additional zeros to the front so the string can
     * have a length of 8.
     */
    private static void adjust () {
        // Finds how many zeroes we need.
        int zeroes = 8 - behavior.length();
        // New string builder.
        StringBuilder newString = new StringBuilder();
        // String builder adds zeros.
        newString.append("0".repeat(Math.max(0, zeroes)));
        // Append the behaviour string.
        newString.append(behavior);
        // Get the new behaviour.
        behavior = newString.toString();
    }

    /**
     * This function takes the user input for the first generation.
     * Since we want them to ONLY input 1 or 0, we need to loop through
     * and check our string. We call the function recursively if the string
     * doesn't contain a 1 or 0.
     */
    private static void userInputGeneration() {
        System.out.println("Input Starting String w/ 1 or 0");

        Scanner scanner = new Scanner (System.in);
        String choice = scanner.next();

        boolean valid = true;

        // Checking loop.
        for (int i = 0; i < choice.length(); i++) {
            // If not 1.
            if (choice.charAt(i) != '1') {
                // If not 0.
                if (choice.charAt(i) != '0') {
                    // It's an invalid choice.
                    System.out.println("Invalid choice!");
                    valid = false;
                    break;
                }
            }
        }

        // Checking if statements.
        if (valid) {
            firstGeneration = choice;
        }
        else {
            userInputGeneration();
        }
    }



    /**
     * Function to centralize the firstGeneration.
     * @param distance The distance to the center of the screen.
     */
    private void centralize (int distance) {
        int zeroes = 0;
        // Finds how many zeroes we have in the firstGeneration.
        // Only find the left half.
        for (int i = 0; i < firstGeneration.length(); i++) {
            if (firstGeneration.charAt(i) == '1') {
                break;
            }
            zeroes++;
        }
        // Then, we subtract the distance
        // to the center with the zeroes to our first one.
        distance -= zeroes;
        // New stringBuilder.
        StringBuilder sb = new StringBuilder();
        // Add our initial zeroes.
        sb.append("0".repeat(Math.max(0, distance)));
        // Append with our firstGeneration.
        sb.append(firstGeneration);
        // Add the remaining zeroes.
        sb.append("0".repeat(Math.max(0, distance)));
        // Set the stringBuilder to our new firstGeneration.
        firstGeneration = sb.toString();
    }

    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("1D Cellular Automata");

        GridPane root = new GridPane();
        double width = 1201;
        double height = 1000;
        Scene scene = new Scene(root, width, height);

        primaryStage.setScene(scene);
        primaryStage.show();

        int middle = (int) (width / 4); // Needs to be 4 b/c cellSize = 2
        centralize(middle);

        List<Cell> firstGen = new ArrayList<>();

        // Turns the firstGen string into a list of cells
        char state;
        for (int i = 0; i < firstGeneration.length(); i++) {
            state = firstGeneration.charAt(i);
            firstGen.add(Cell.fromChar(state));
        }

        Grid1D grid = new Grid1D(root, behavior, firstGen, 2);
        Runner.run(grid);
    }
}
