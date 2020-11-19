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

public class Main extends Application {

    // List for each line of the File
    private static final List<String> file = new ArrayList<>();

    private static String behaviour;
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
                File input = new File(args[0]);
                Scanner scan = new Scanner(input);

                while (scan.hasNextLine()) {
                    file.add(scan.nextLine());
                }
                behaviour = file.get(0);
                firstGeneration = file.get(1);
                launch(args);
                break;
            case "u":
                userInputBehavior();
                userInputGeneration();
                launch(args);
                break;
            default:
                System.out.println("Invalid Choice!");
                main(args);
                break;
        }
    }

    private static void userInputBehavior() {
        System.out.println("Input Pattern [0 - 255]");
        Scanner scanner = new Scanner (System.in);
        int choice = scanner.nextInt();

        if ((choice > -1) && (choice < 256)) {
            behaviour = Integer.toBinaryString(choice);
            adjust();
        }
        else {
            userInputBehavior();
        }
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
            if (choice.charAt(i) != '1') {
                if (choice.charAt(i) != '0') {
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
     * This function takes the behaviour's binary string
     * and adds additional zeros to the front so the string can
     * have a length of 8.
     */
    private static void adjust () {
        // Finds how many zeroes we need.
        int zeroes = 8 - behaviour.length();
        // New string builder.
        StringBuilder newString = new StringBuilder();
        // String builder adds zeros.
        newString.append("0".repeat(Math.max(0, zeroes)));
        // Append the behaviour string.
        newString.append(behaviour);
        // Get the new behaviour.
        behaviour = newString.toString();
    }

    /**
     * Function to centralize the firstGeneration.
     * @param distance The distance to the center of the screen.
     */
    private void centralize (int distance) {
        int zeroes = 0;
        for (int i = 0; i < firstGeneration.length(); i++) {
            if (firstGeneration.charAt(i) == '1') {
                break;
            }
            zeroes++;
        }
        distance -= zeroes;
        StringBuilder sb = new StringBuilder();
        sb.append("0".repeat(Math.max(0, distance)));
        sb.append(firstGeneration);
        sb.append("0".repeat(Math.max(0, distance)));
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

        Grid1D grid = new Grid1D(root, behaviour, firstGen, 2);
        Runner.run(grid);
    }
}
