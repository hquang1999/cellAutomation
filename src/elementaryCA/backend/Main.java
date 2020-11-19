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
    public static void main(String[] args) throws IOException  {
        File input = new File (args[0]);
        Scanner scan = new Scanner (input);
        BufferedReader reader = new BufferedReader(new FileReader(input));

        System.out.println(scan.nextLine());


    //    launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("1D Cellular Automata");

        GridPane root = new GridPane();
        double width = 1201;
        double height = 1000;
        Scene scene = new Scene(root, width, height);

        primaryStage.setScene(scene);
        primaryStage.show();

        int middle = (int) (width / 4);

        StringBuilder sb = new StringBuilder();
        sb.append("0".repeat(Math.max(0, middle)));
        sb.append("1");
        sb.append("0".repeat(Math.max(0, middle)));

        String firstGenStr = sb.toString();

        List<Cell> firstGen = new ArrayList<>();

        // Turns the firstGen string into a list of cells
        char state;
        for (int i = 0; i < firstGenStr.length(); i++) {
            state = firstGenStr.charAt(i);
            firstGen.add(Cell.fromChar(state));
        }

        Grid1D grid = new Grid1D(root, "01111110", firstGen, 2);
        Runner.run(grid);

        String test = Integer.toBinaryString(30);
        System.out.println(test);
        System.out.println(test.length());
    }
}
