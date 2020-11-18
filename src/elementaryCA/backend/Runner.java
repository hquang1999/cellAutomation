package elementaryCA.backend;

import elementaryCA.backend.grid.Grid1D;
import javafx.animation.AnimationTimer;

import java.util.concurrent.TimeUnit;

public class Runner {

    /**
     * Starts the animation timer that controls the visualization.
     * @param grid of cells to be shown to the screen. At each time step
     *             it is evolved using its given ruleset.
     */
    public static void run(Grid1D grid) {
        AnimationTimer timer = new AnimationTimer() {
            private long prevUpdate = 0;
            @Override
            public void handle(long now) {
                grid.nextGeneration();
                prevUpdate = now;
            }
        };
        timer.start();
    }
}
