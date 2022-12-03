package othello;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Stone extends JPanel implements MouseListener {
    private Player player;
    private final int x, y;
    private boolean isClickable;
    private final GameRender gameRender;
    private final GameLogic gameLogic;

    // Overloaded Constructors
    public Stone(GameRender gameRender, int x, int y){
        this.x = x;
        this.y = y;
        this.gameRender = gameRender;
        this.gameLogic = this.gameRender.getGameLogic();
        this.addMouseListener(this);
    }
    public Stone(GameRender gameRender, Player player, int x, int y){
        this(gameRender, x, y); // calling the first constructor
        this.player = player;
    }

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

    // GETTERS + SETTERS
    @Override
    public int getX() {
        return x;
    }
    @Override
    public int getY() {
        return y;
    }
    public Player getPlayer() {
        return this.player;
    }
    public void setClickable(boolean clickable) {
        this.isClickable = clickable;
    }
    public void setPlayer(Player player) {
        this.player = player;
    }

    // LISTENERS
    @Override
    public void mouseReleased(MouseEvent e) {
        if(this.isClickable) {
            this.player = this.gameRender.getPlayerOnTurn();
            this.gameLogic.flip(this);
            this.gameRender.flipPlayerTurn();
            this.gameLogic.checkAllDirections();
        }
    }
    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}