package othello;

import javax.swing.*;

/**
 * This class is the class which have the main game logic. <br>
 * <ul>
 *   <li>Firstly, it sets all Ovals (player stones) to un-clickable, and
 *   checks if the player's current stones are equal to <br>
 *   (board width * board height) and ends the game if that's the case.</li>
 *   <li>Secondly, it iterates through the whole game board one by one (left-to-right) <br>
 *   and checks whether there can be a stone which have his matching stone pair on
 *   the other side (there must be a continuous row of opponent stones in-between).</li>
 *   <li>Lastly, it repaints all Ovals (stones) which suits and sets them
 *   clickable for the player which is currently on move. </li>
 * </ul>
 */
public class GameLogic {
    private final GameRender gameRender;
    private static int playerFlips = 0;

    /**
     * Constructor for game logic which works hand in hand
     * with the render of the game.
     * @param gameRender the render which we will use
     */
    public GameLogic(GameRender gameRender) {
        this.gameRender = gameRender;
    }

    /**
     * All game-board <code>Ovals</code> (Stones) are going to be set to
     * <code>un-clickable</code> using this method.
     */
    private void setClickableFalse() {
        int boardSize = this.gameRender.getBoardSize();
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                this.gameRender.getStone(i, j).setClickable(false);
            }
        }
    }

    /**
     * <code>Summary</code> of all placed <code>Stones</code> on the game-board.
     * @return the sum of all current <code>Stones</code> placed on board.
     */
    private int placedStonesOnBoard() {
        int stonesOnBoard = 0;
        for (Stone[] stones : this.gameRender.getStones()) {
            for (Stone stone : stones) {
                if (stone.getPlayer() != null) {
                    stonesOnBoard++;
                }
            }
        }
        return stonesOnBoard;
    }

    /**
     * <code>Summary</code> of all placed <code>Stones</code> on the game-board,
     * but now by just <code>one</code> of the players.
     * @param player the player whose Stones will be calculated
     * @return the summary of stones <code>owned</code> by the player.
     */
    private int playerStonesOnBoard(Player player) {
        int stones = 0;
        for (Stone[] pole : this.gameRender.getStones()) {
            for (Stone stone : pole) {
                if (stone.getPlayer() == player) {
                    stones++;
                }
            }
        }
        return stones;
    }

    /**
     * Checks and returns which player has more stones after calling this method.
     * @param whiteStones number of stones of the <code>White</code> player.
     * @param blackStones number of stones of the <code>Black</code> player.
     * @return player that has more stones.
     */
    private Player getWinner(int whiteStones, int blackStones) {
        return whiteStones < blackStones ? gameRender.getPlayer1() : gameRender.getPlayer2();
    }

    /**
     * Method called in {@link #checkAllDirections()}, which
     * automatically calculate which player has more stones on
     * the board and accordingly write the winner
     * into the showMessageDialog of the JOptionPane property.
     */
    private void gameOver() {
        int blackStones = playerStonesOnBoard(this.gameRender.getPlayer1());
        int whiteStones = playerStonesOnBoard(this.gameRender.getPlayer2());
        String gameState = "Game over, ";
        if(blackStones == whiteStones) { // draw
            gameState += "draw!";
        }else{
            gameState += "the winner is: " + getWinner(whiteStones, blackStones).getName();
        }

        JOptionPane.showMessageDialog(this.gameRender, gameState);
        this.gameRender.resetBoard();
    }

    /**
     * One of the main logics used in this game. <br>
     *
     * @param stone is the current stone from which we iterate.
     */
    public void flip(Stone stone) {
        int x = stone.getX();
        int y = stone.getY();
        int boardSize = this.gameRender.getBoardSize();
        for (Direction s : Direction.values()) {
            boolean playerStoneFound = true;
            int tempX = x + s.getX();
            int tempY = y + s.getY();
            if (tempX > boardSize - 1 || tempY > boardSize - 1 || tempX < 0 || tempY < 0) {
                continue;
            }
/*            if (this.gameRender.getStone(tempX, tempY).getPlayer() == this.gameRender.getPlayerOnMove() ||
 *                     this.gameRender.getStone(tempX, tempY).getPlayer() == null) {
 *                 continue;
 *            }
 */            while (gameRender.getStone(tempX, tempY).getPlayer() != gameRender.getPlayerOnMove()) {
                if (gameRender.getStone(tempX, tempY).getPlayer() == null) {
                    playerStoneFound = false;
                    break;
                }
                tempX += s.getX();
                tempY += s.getY();
                if (tempX > boardSize - 1 || tempY > boardSize - 1 || tempX < 0 || tempY < 0) {
                    playerStoneFound = false;
                    break;
                }
            }
            // System.out.println(playerStoneFound);
            if (playerStoneFound) {
                tempX = x + s.getX();
                tempY = y + s.getY();

                while (gameRender.getStone(tempX, tempY).getPlayer() != gameRender.getPlayerOnMove()) {
                    // System.out.println(tempX + " " + tempY);
                    this.gameRender.getStone(tempX, tempY).setPlayer(this.gameRender.getPlayerOnMove());
                    tempX += s.getX();
                    tempY += s.getY();
                }
                gameRender.getStone(tempX, tempY).setClickable(true);
            }
        }
        this.gameRender.repaint();
    }

    /**
     * Main logic used in the game. <br>
     *
     */
    public void checkAllDirections() {
        this.setClickableFalse();
        int currentStonesNumber = placedStonesOnBoard();
        int maxStoneNumber = this.gameRender.getBoardSize() * this.gameRender.getBoardSize();
        if (currentStonesNumber == maxStoneNumber) {
            this.gameOver();
        }
        int numberOfClickable = 0;

        int boardSize = this.gameRender.getBoardSize();
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                Player player = this.gameRender.getStone(i, j).getPlayer();
                if (player == this.gameRender.getPlayerOnMove()) {

                    for (Direction direction : Direction.values()) {
                        boolean foundEmpty = false;
                        int tempX = i + direction.getX();
                        int tempY = j + direction.getY();
                        if (tempX > boardSize - 1 || tempY > boardSize - 1 || tempX < 0 || tempY < 0) {
                            continue;
                        }
                        if (this.gameRender.getStone(tempX, tempY).getPlayer() == this.gameRender.getPlayerOnMove() ||
                                this.gameRender.getStone(tempX, tempY).getPlayer() == null) {
                            continue;
                        }
                        while (this.gameRender.getStone(tempX, tempY).getPlayer() != this.gameRender.getPlayerOnMove()) {
                            if (this.gameRender.getStone(tempX, tempY).getPlayer() == null) {
                                foundEmpty = true;
                                break;
                            }
                            tempX += direction.getX();
                            tempY += direction.getY();
                            if (tempX > boardSize - 1 || tempY > boardSize - 1 || tempX < 0 || tempY < 0) {
                                break;
                            }
                        }
                        if (foundEmpty) {
                            numberOfClickable++;
                            this.gameRender.getStone(tempX, tempY).setClickable(true);
                        }
                    }
                }
            }
        }
        if(numberOfClickable == 0) {
            // Checking for Game over
            if (playerFlips == 2) {
                this.gameOver();
            } else {
                this.gameRender.flipPlayerTurn();
                playerFlips++;
                this.checkAllDirections();
            }
        } else {
            this.gameRender.repaint();
            playerFlips = 0;
        }
    }
}
