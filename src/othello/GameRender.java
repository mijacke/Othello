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

    /**
     * Constructor for rendering the game. <br>
     * It creates an initial layout of 8x8 (sent in a parameter) with
     * the same number of stones and also two players who will play
     * against each other. At the end it draws initial board layout.
     *
     * @param cols number of columns in the game
     */
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

    /**
     * This method draws <code>initial</code> board layout. <br>
     * It iterates through the board (left-to-right) and sets all the stones to unowned
     * by the players, except the initial position (located exactly in the middle of the board)
     * which belongs to the players. <br>
     * Finally, there is an initial call to the {@link GameLogic#checkAllDirections()} method.
     */
    public void drawBoard() {
        int middleStones = this.boardSize / 2;
        for (int i = 0; i < this.boardSize; i++) {
            for (int j = 0; j < this.boardSize; j++) {
                if ((i == middleStones && j == middleStones) || (i == (middleStones - 1) && j == (middleStones - 1))) {
                    this.stones[i][j] = new Stone(this, this.player1, i, j);
                } else if ((i == (middleStones - 1) && j == middleStones) || (i == middleStones && j == (middleStones - 1))) {
                    this.stones[i][j] = new Stone(this, this.player2, i, j);
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
         */
        this.gameLogic.checkAllDirections();
    }

    /**
     * Method that resets the entire board and sets up a black player
     * as player on turn.
     */
    public void resetBoard() {
        this.playerOnTurn = this.player1;
        super.removeAll();
        super.revalidate();
        //super.repaint();
        this.drawBoard();
    }

    /**
     * Method that resizes the game and calls a resetBoard() method.
     *
     * @param size the size we want to set the game to
     */
    public void newGameChangedSize(int size) {
        this.boardSize = size;
        super.setLayout(new GridLayout(size, size));
        this.stones = new Stone[size][size];
        this.resetBoard();
    }

    /**
     * Method that flips the player on turn to another player.
     */
    public void flipPlayerTurn() {
        if (this.playerOnTurn == this.player1) {
            this.playerOnTurn = this.player2;
        } else {
            this.playerOnTurn = this.player1;
        }
    }

    /**
     * Getter for board size.
     *
     * @return the size of the board
     */
    public int getBoardSize() {
        return boardSize;
    }

    /**
     * Getter for player on turn.
     *
     * @return the current player on turn
     */
    public Player getPlayerOnTurn() {
        return this.playerOnTurn;
    }

    /**
     * Getter for specific stone on the board.
     *
     * @param x a specific row
     * @param y a specific column
     * @return a specific stone
     */
    public Stone getStone(int x, int y) {
        return this.stones[x][y];
    }

    /**
     * Getter for player 1.
     *
     * @return a player 1
     */
    public Player getPlayer1() {
        return this.player1;
    }

    /**
     * Getter for player 2.
     *
     * @return a player 2
     */
    public Player getPlayer2() {
        return this.player2;
    }

    /**
     * Getter for game logic.
     *
     * @return a game logic
     */
    public GameLogic getGameLogic() {
        return this.gameLogic;
    }

    /**
     * Getter for two-dimensional stone array.
     *
     * @return a two-dimensional stone array
     */
    public Stone[][] getStones() {
        return this.stones;
    }

    /**
     * Overridden method that paints the whole Rendering to the JPanel.
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
     * Overridden method which checks if you clicked on the button 'Restart'.
     * If button was clicked, then the board will reset.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        this.resetBoard();
    }

    /**
     * Overridden method which listens to user input and affects the window in various ways.
     * <ul>
     *   <li>If 'ESC' was pushed down the window and the whole game will shut down.</li>
     *   <li>If 'R' was pushed down the whole game will reset.</li>
     * </ul>
     *
     * @param e the event to be processed
     */
    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }
        if (e.getKeyCode() == KeyEvent.VK_R) {
            this.resetBoard();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }
}
