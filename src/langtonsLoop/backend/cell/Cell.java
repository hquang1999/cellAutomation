package langtonsLoop.backend.cell;

import javafx.scene.paint.Color;
/**
 * Class given by Joe. I modified it to take in ints instead
 * because there are multiple numbers.
 */
public enum Cell {
    ZERO(0),
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7);

    private int num;

    Cell(int num) {
        this.num = num;
    }

    public int getInt() {
        return num;
    }

    public Color getColor() {
        if (this == ZERO) {
            return Color.BLACK;
        }
        else if (this == ONE){
            return Color.BLUE;
        }
        else if (this == TWO) {
            return Color.CRIMSON;
        }
        else if (this == THREE) {
            return Color.DARKORCHID;
        }
        else if (this == FOUR) {
            return Color.ORANGE;
        }
        else if (this == FIVE) {
            return Color.PINK;
        }
        else if (this == SIX) {
            return Color.GREENYELLOW;
        }
        else {
            return Color.KHAKI;
        }
    }

    public static Cell fromChar(char c) throws IllegalArgumentException {
        if (c == '0') {
            return ZERO;
        }
        else if (c == '1') {
            return ONE;
        }
        else if (c == '2') {
            return TWO;
        }
        else if (c == '3') {
            return THREE;
        }
        else if (c == '4') {
            return FOUR;
        }
        else if (c == '5') {
            return FIVE;
        }
        else if (c == '6') {
            return SIX;
        }
        else if (c == '7') {
            return SEVEN;
        }

        else {
            throw new IllegalArgumentException("Input char must be either 0 or 1.");
        }
    }
}
