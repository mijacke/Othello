package othello;

import javax.swing.*;

public class GameLogic {
    private final GameRender gameRender;
    private static int playerFlips = 0;
    public GameLogic(GameRender gameRender) {
        this.gameRender = gameRender;
    }

    private void setClickableFalse() {
        int boardSize = this.gameRender.getBoardSize();
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                this.gameRender.getStone(i, j).setClickable(false);
            }
        }
    }

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

    private Player getWinner(int whiteStones, int blackStones) {
        return whiteStones < blackStones ? gameRender.getPlayer1() : gameRender.getPlayer2();
    }

    private void gameOver() {
        int blackStones = playerStonesOnBoard(this.gameRender.getPlayer1());
        int whiteStones = playerStonesOnBoard(this.gameRender.getPlayer2());
        String gameState = "Game over, ";
        if(blackStones == whiteStones) { // draw
            gameState += ": draw";
        }else{
            gameState += "the winner is: " + getWinner(whiteStones, blackStones).getName();
        }

        JOptionPane.showMessageDialog(this.gameRender, gameState);
        this.gameRender.resetBoard();
    }
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
