package langtonsLoop.backend;

import langtonsLoop.backend.neighborhood.Neighborhood;
import javafx.animation.AnimationTimer;

import java.util.concurrent.TimeUnit;

public class Runner {

    /**
     * Starts the animation timer that controls the visualization.
     * @param grid of cells to be shown to the screen. At each time step
     *             it is evolved using its given ruleset.
     */
    public static void run(Neighborhood grid) {
        AnimationTimer timer = new AnimationTimer() {
           // private long prevUpdate = 0;
            @Override
            public void handle(long now) {
/*
                if (now - prevUpdate >=
                        TimeUnit.MILLISECONDS.toNanos(200)) {

 */
                    grid.nextGeneration();
                    //prevUpdate = now;
               // }
            }
        };
        timer.start();
    }

}
