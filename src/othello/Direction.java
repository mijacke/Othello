package othello;

/**
 * Enumeration class Direction - enum for better
 * iteration on the x-axis and y-axis. <br>
 * Inspiration and implementation from week T11 - Train
 * <ul>
 *    <li>0: represents zero movement in direction</li>
 *    <li>1: represents movement to the right in x-axis, downward in y-axis</li>
 *    <li>-1: represents movement to the left in x-axis, upward in y-axis</li>
 * </ul>
 *
 * @author Mário Laššú
 */
public enum Direction {
    UP(0, -1),
    UP_RIGHT(1, -1),
    RIGHT(1, 0),
    DOWN_RIGHT(1, 1),
    DOWN(0, 1),
    DOWN_LEFT(-1, 1),
    LEFT(-1, 0),
    UP_LEFT(-1, -1);

    private final int x;
    private final int y;

    /**
     * Constructor for various directions on x-axis and y-axis <br>
     * @param x changes on x-axis
     * @param y changes on y-axis
     */
    Direction(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
