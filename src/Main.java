import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) {
        try (BufferedReader stdIn =
                     new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("Hello Welcome To A Tour of Cellular Automata!");
            System.out.println("Which CA would you like to see?");
            System.out.println("[1d] 1 Dimensional Elementary CA?");
            System.out.println("[gl] Conway's Game of Life?");
            System.out.println("[ll] Langton's Loop?");

            String input = stdIn.readLine();
            if (input.equalsIgnoreCase("1d")) {
                elementaryCA.backend.Main.main(args);
            }
            else if (input.equalsIgnoreCase("gl")) {
                gameOfLife.backend.Main.main(args);
            }
            else if (input.equalsIgnoreCase("ll")) {
                langtonsLoop.backend.Main.main(args);
            }
            else {
                System.out.println(input + " is not a recognized command. Try" +
                        " again.");
                main(args);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
