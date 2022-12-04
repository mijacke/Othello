package othello;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Class which represents the stone which can be owned by the players. <br>
 * There are 6 private fields. The first is the player which can be set as
 * the owner of the stone, second and third respectively are the positions
 * of the stone on the x-axis and y-axis. There is also isClickable field
 * which can either mean that the stone is clickable or not. Last two fields
 * are gameRender and gameLogic which are needed for us to see these stones
 * but mainly for stones because they contain some logics of the game.
 *
 * @author Mário Laššú
 */
public class Stone extends JPanel implements MouseListener {
    private Player player;
    private final int x;
    private final int y;
    private boolean isClickable;
    private final GameRender gameRender;
    private final GameLogic gameLogic;

    /**
     * Constructor for Stone which is not owned by anyone.
     *
     * @param gameRender the current render of the game
     * @param x          the position on x-axis
     * @param y          the position on y-axis
     */
    public Stone(GameRender gameRender, int x, int y) {
        this.x = x;
        this.y = y;
        this.gameRender = gameRender;
        this.gameLogic = this.gameRender.getGameLogic();
        this.addMouseListener(this);
    }

    /**
     * Overloaded constructor for Stone which is owned by certain player.
     *
     * @param player     the player who owns this particular stone
     * @param gameRender the current render sent to main constructor
     * @param x          the position on x-axis sent to main constructor
     * @param y          the position on y-axis sent to main constructor
     */
    public Stone(GameRender gameRender, Player player, int x, int y) {
        this(gameRender, x, y);
        this.player = player;
    }

    /**
     * Overridden method which paints the grid to green and the stone
     * accordingly to the owner (if exists). If there are any positions
     * which are legal for the player to make then the stone is set to grey.
     *
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(Color.getHSBColor(0.3F, 1F, 0.5F));  // board background color
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        g.setColor(Color.BLACK);  // rectangle color
        g.drawRect(0, 0, this.getWidth(), this.getHeight());
        if (this.player != null) {
            g.setColor(player.getColor());
            g.fillOval(10, 10, this.getWidth() - 20, this.getHeight() - 20);
            g.setColor(Color.BLACK);
        }
        if (this.isClickable) {
            g.setColor(Color.gray);
            g.fillOval(10, 10, this.getWidth() - 20, this.getHeight() - 20);
            g.setColor(Color.BLACK);
        }
    }

    /**
     * Getter for the x position of the stone.
     *
     * @return x position
     */
    @Override
    public int getX() {
        return x;
    }

    /**
     * Getter for the y position of the stone.
     *
     * @return y position
     */
    @Override
    public int getY() {
        return y;
    }

    /**
     * Getter of the player.
     *
     * @return the player
     */
    public Player getPlayer() {
        return this.player;
    }

    /**
     * Setter for changing the state of the stone.
     *
     * @param clickable if stone is or is not clickable
     */
    public void setClickable(boolean clickable) {
        this.isClickable = clickable;
    }

    /**
     * Setter for changing the owner of the stone.
     *
     * @param player the player who will be the owner
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Overridden method for listening mouse clicks. <br>
     * If the stone is set to clickable, then after releasing the mouse click
     * the stones will flip to current player stones and the next player turn follows.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        if (this.isClickable) {
            this.player = this.gameRender.getPlayerOnTurn();
            this.gameLogic.flip(this);
            this.gameRender.flipPlayerTurn();
            this.gameLogic.checkAllDirections();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}