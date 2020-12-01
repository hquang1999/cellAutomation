package elementaryCA.backend.cell;

import javafx.scene.paint.Color;
/**
 * Class given by Joe.
 */
public enum Cell {
    ZERO(false),
    ONE(true);

    private boolean state;

    Cell(boolean state) {
        this.state = state;
    }

    public boolean isOn() {
        return state;
    }

    public Color getColor() {
        if (this == ZERO) {
            return Color.WHITE;
        }
        else {
            return Color.BLACK;
        }
    }

    public static Cell fromChar(char c) throws IllegalArgumentException {
        if (c == '0') {
            return ZERO;
        }
        else if (c == '1') {
            return ONE;
        }
        else {
            throw new IllegalArgumentException("Input char must be either 0 or 1.");
        }
    }
}
