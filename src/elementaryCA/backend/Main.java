package elementaryCA.backend;

import elementaryCA.backend.cell.Cell;
import elementaryCA.backend.grid.Grid1D;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
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
        for (int i = 0; i < middle; i++) {
            sb.append("0");
        }
        sb.append("1");
        for (int i = 0; i < middle; i++) {
            sb.append("0");
        }

        String firstGenStr = sb.toString();

        List<Cell> firstGen = new ArrayList<>();

        // TODO: Turn the firstGen string into a list of cells
        char state;
        for (int i = 0; i < firstGenStr.length(); i++) {
            state = firstGenStr.charAt(i);
            firstGen.add(Cell.fromChar(state));
        }
//  Omegalol
        Grid1D grid = new Grid1D(root, "00011110", firstGen, 2);
        Runner.run(grid);
    }
}
