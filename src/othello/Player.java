package othello;

import java.awt.Color;

/**
 * Class for creating players who will play against each other.
 * This class has 2 private immutable fields color and name, which
 * are specific for each player.
 *
 * @author Mário Laššú
 */
public class Player {
    private final Color color;
    private final String name;

    /**
     * Constructor for creating a player.
     *
     * @param c    is the color of the player
     * @param name is the name of the player
     */
    public Player(Color c, String name) {
        this.color = c;
        this.name = name;
    }

    /**
     * Getter of the color.
     *
     * @return the player's color
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * Getter of the name.
     *
     * @return the player's name
     */
    public String getName() {
        return this.name;
    }
}
