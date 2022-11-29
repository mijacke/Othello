package othello;
import java.awt.*;

public class Player {
    private final Color color;
    private final String name;

    public Player(Color c, String name){
        this.color = c;
        this.name = name;
    }

    public Color getColor() {
        return color;
    }

    public String getName() {
        return name;
    }
}
