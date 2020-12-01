package langtonsLoop.backend;

import langtonsLoop.backend.neighborhood.Neighborhood;
import javafx.animation.AnimationTimer;

/**
 * Class given by Joe.
 */
public class Runner {

    /**
     * Starts the animation timer that controls the visualization.
     * @param grid of cells to be shown to the screen. At each time step
     *             it is evolved using its given ruleset.
     */
    public static void run(Neighborhood grid) {
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                    grid.nextGeneration();
            }
        };
        timer.start();
    }

}
