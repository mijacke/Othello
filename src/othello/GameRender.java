package othello;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameRender extends JPanel implements KeyListener, ActionListener {
    private int boardSize;
    private Stone[][] stones;
     private final Player player1;
    private final Player player2;
    private final GameLogic gameLogic;
    private Player playerOnTurn;
    public GameRender(int cols) {
        this.boardSize = cols;
        super.setLayout(new GridLayout(cols, cols));
        this.stones = new Stone[cols][cols];
        this.gameLogic = new GameLogic(this);
        this.player1 = new Player(Color.BLACK, "BLACK");
        this.player2 = new Player(Color.WHITE, "WHITE");
        this.playerOnTurn = this.player1;

        this.drawBoard();
    }

    public void drawBoard() {
        int middleStones = this.boardSize / 2;
        for (int i = 0; i < this.boardSize; i++) {
            for (int j = 0; j < this.boardSize; j++) {
                if ((i == middleStones && j == middleStones) || (i == (middleStones - 1) && j == (middleStones - 1))) {
                    this.stones[i][j] = new Stone(this, this.player1, i, j);
                } else if ((i == (middleStones - 1) && j == middleStones) || (i == middleStones && j == (middleStones - 1))) {
                    this.stones[i][j] = new Stone(this, this.player2, i , j);
                } else {
                    this.stones[i][j] = new Stone(this, i, j);
                }
//              this.stones[i][j] = new Stone(this, i, j); For Testing (45 row)
                super.add(this.stones[i][j]);
            }
        }
/*        TESTING ending of the game, where no player can make valid move
 *        this.stones[3][3].setPlayer(this.player1);
 *        this.stones[3][4].setPlayer(this.player1);
 *        this.stones[4][4].setPlayer(this.player1);
 *        this.stones[4][3].setPlayer(this.player2);
 */       this.gameLogic.checkAllDirections();
    }
    public void resetBoard() {
        this.playerOnTurn = this.player1;
        super.removeAll();
        super.revalidate();
        super.repaint();
        this.drawBoard();
    }
    public void newGameChangedSize(int size) {
        this.boardSize = size;
        super.setLayout(new GridLayout(size, size));
        this.stones = new Stone[size][size];
        resetBoard();
    }
    public void flipPlayerTurn() {
        if(this.playerOnTurn == this.player1){
            this.playerOnTurn = this.player2;
        } else {
            this.playerOnTurn = this.player1;
        }
    }
    // GETTERS
    public int getBoardSize() {
        return boardSize;
    }
    public Player getPlayerOnTurn() {
        return this.playerOnTurn;
    }
    public Stone getStone(int x, int y) {
        return this.stones[x][y];
    }
    public Player getPlayer1() {
        return this.player1;
    }
    public Player getPlayer2() {
        return this.player2;
    }

    public GameLogic getGameLogic() {
        return this.gameLogic;
    }

    public Stone[][] getStones() {
        return this.stones;
    }

    /**
     *
     * @param g the <code>Graphics</code> object to repaint the board
     */
    @Override
    public void paintComponent(Graphics g) {
        for (Stone[] pole : this.stones) {
            for (Stone stone : pole) {
                stone.repaint();
            }
        }
    }

    /**
     * Checking if you clicked on the button 'Restart'.
     * If button was clicked, then the board will reset.
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        this.resetBoard();
    }

    /**
     * @param e the event to be processed. It listens to user input and
     * affects the window in various way such as 'ESC', 'R'.
     *      If 'ESC' was pushed down the window and the whole game will shut down.
     *      If 'R' was pushed down the whole game will reset.
     */
    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }
        if(e.getKeyCode() == KeyEvent.VK_R) {
            this.resetBoard();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyPressed(KeyEvent e) {}
}
