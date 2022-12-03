package othello;

import javax.swing.*;
import java.awt.event.MouseEvent;

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
 *   clickable for the player which is currently on the move. </li>
 * </ul>
 */
public class GameLogic {
    private final GameRender gameRender;
    private static int playerFlips = 0;

    /**
     * Constructor for game logic which works hand in hand
     * with the render of the game.
     *
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
     *
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
     *
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
     *
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
        if (blackStones == whiteStones) { // draw
            gameState += "draw!";
        } else {
            gameState += "the winner is: " + getWinner(whiteStones, blackStones).getName();
        }

        JOptionPane.showMessageDialog(this.gameRender, gameState);
        this.gameRender.resetBoard();
    }

    /**
     * One of the main logics used in this game. <br>
     * This method is called in the {@link othello.Stone#mouseReleased(MouseEvent)} method
     * after clicking with mouse. It iterates through every {@link othello.Direction} and flips
     * all stones (there must be a continuous row of opponent stones in-between)
     * which have it's matching stone pair on the other side.
     *
     * @param stone is the current stone from which we iterate.
     */
    public void flip(Stone stone) {
        int x = stone.getX();
        int y = stone.getY();
        int boardSize = this.gameRender.getBoardSize();

        for (Direction direction : Direction.values()) {
            boolean playerStoneFound = true;
            int tempX = x + direction.getX();
            int tempY = y + direction.getY();
            if (tempX > boardSize - 1 || tempY > boardSize - 1 || tempX < 0 || tempY < 0) {
                continue;
            }
            /*            if (this.gameRender.getStone(tempX, tempY).getPlayer() == this.gameRender.getPlayerOnMove() ||
             *                     this.gameRender.getStone(tempX, tempY).getPlayer() == null) {
             *                 continue;
             *            }
             */

            Player playerOnTurn = this.gameRender.getPlayerOnTurn();
            Player ownerOfStone;
            while ((ownerOfStone = gameRender.getStone(tempX, tempY).getPlayer()) != playerOnTurn) {
                if (ownerOfStone == null) {
                    playerStoneFound = false;
                    break;
                }
                tempX += direction.getX();
                tempY += direction.getY();
                if (tempX > boardSize - 1 || tempY > boardSize - 1 || tempX < 0 || tempY < 0) {
                    playerStoneFound = false;
                    break;
                }
            }

            if (playerStoneFound) {
                tempX = x + direction.getX();
                tempY = y + direction.getY();

                Stone currentStone;
                while ((currentStone = this.gameRender.getStone(tempX, tempY)).getPlayer() != playerOnTurn) {
                    // System.out.println(tempX + " " + tempY);
                    currentStone.setPlayer(playerOnTurn);
                    tempX += direction.getX();
                    tempY += direction.getY();
                }
                currentStone.setClickable(true);
            }
        }
        this.gameRender.repaint();
    }

    /**
     * <code>Main Logic</code> used in the game. <br>
     * This method check and displays all legal moves which can be made
     * by the particular player and display them as grey Ovals (stones).
     * If there are none such stones that can be clicked, we switch to the
     * next player's turn. If the other player has no moves then it
     * ends the game and decide the winner.
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

        for (int x = 0; x < boardSize; x++) {
            for (int y = 0; y < boardSize; y++) {

                Player currentPlayer = this.gameRender.getStone(x, y).getPlayer();
                Player playerOnTurn = this.gameRender.getPlayerOnTurn();

                if (currentPlayer == playerOnTurn) {
                    for (Direction direction : Direction.values()) {

                        boolean foundEmpty = false;
                        int tempX = x + direction.getX();
                        int tempY = y + direction.getY();

                        if (tempX > boardSize - 1 || tempY > boardSize - 1 || tempX < 0 || tempY < 0) {
                            continue;
                        }

                        Stone currentStone = this.gameRender.getStone(tempX, tempY);
                        //Player ownerOfStone = this.gameRender.getStone(tempX, tempY).getPlayer();
                        if (currentStone.getPlayer() == currentPlayer || currentStone.getPlayer() == null) {
                            continue;
                        }

                        Player ownerOfStone;
                        while ((ownerOfStone = this.gameRender.getStone(tempX, tempY).getPlayer()) != currentPlayer) {
                            if (ownerOfStone == null) {
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
        if (numberOfClickable == 0) {
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
