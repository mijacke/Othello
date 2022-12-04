package othello;

import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class whose task is to add a JComboBox menu for the player to choose a size
 * of the game, a button which will confirm the selection. It also creates a
 * timer for players to see exactly how long they have been playing and additional information
 * panel which shows some basic things like player colors or current player on the turn.
 *
 * @author Mário Laššú
 */
public class RightFramePanel extends JPanel implements ActionListener {
    private final GameRender gameRender;
    private final JComboBox<Integer> comboBox;
    private final MyTimer myTimer;

    /**
     * Constructor of the panel.
     *
     * @param gameRender the rendering of the game.
     */
    public RightFramePanel(GameRender gameRender) {
        /*        this.setLayout(new GridBagLayout());
         *        GridBagConstraints constraints = new GridBagConstraints();
         *        constraints.anchor = GridBagConstraints.FIRST_LINE_START;
         */
        this.setLayout(null); ////////////////////
        this.setPreferredSize(new Dimension(175, 0)); ////////////////////

        this.gameRender = gameRender;
        this.myTimer = new MyTimer();
        // Menu for changing the size of the board - only even number so that the starting stones are oriented to the center
        this.comboBox = new JComboBox<>(new Integer[]{6, 8, 10, 12});
        this.comboBox.setSelectedIndex(1); // 8x8

        // Adding button for ComboBox + Listeners
        JButton changeSizeButton = new JButton("CHANGE");
        changeSizeButton.addActionListener(this);
        changeSizeButton.addKeyListener(this.gameRender);

        /*        constraints.gridY = 0;
         *        this.add(this.comboBox, constraints);
         *        this.add(changeSizeButton, constraints);
         *
         *        constraints.gridY = 2;
         *        constraints.gridWidth = 2;
         *        this.add(myTimer, constraints);
         */
        // setting the exact bounds in the panel
        this.comboBox.setBounds(2, 2, 50, 20); ////////////////////
        changeSizeButton.setBounds(55, 2, 120, 20); ////////////////////
        this.myTimer.setBounds(26, 172, 150, 20); ////////////////////

        // Adding to panel + background-color
        this.add(this.comboBox);
        this.add(changeSizeButton);
        this.add(this.myTimer);
        this.comboBox.setFocusable(false);
        this.setBackground(Color.getHSBColor(0.33f, 0.3f, 0.3f));
    }

    /**
     * Method which display some useful information to the players.
     *
     * @param g the <code>Graphics</code> object which draws the text to the panel
     */
    public void gameInfo(Graphics g) {
        g.setFont(new Font("sans-serif", Font.BOLD, 12));
        g.setColor(Color.WHITE);
        g.drawRect(20, 128, 140, 65);
        g.drawString(String.format("%22s", "INFORMATION"), 10, 125);
        g.drawString(String.format("Player 1: %12s", this.gameRender.getPlayer1().getName()), 26, 142);
        g.drawString(String.format("Player 2: %12s", this.gameRender.getPlayer2().getName()), 26, 157);
        g.drawString(String.format("Game size: %1$4d x %1$d", this.gameRender.getBoardSize()), 26, 172);
        g.drawString(String.format("Turn: %s ", this.gameRender.getPlayerOnTurn().getName()), 50, 300);
    }

    /**
     * Overridden method for listening to user input. <br>
     * If you press the 'CHANGE' button a new game will be created with
     * a selected size, the timer will also reset.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (this.comboBox.getSelectedItem() != null) {
            int boardSize = (int)this.comboBox.getSelectedItem();
            this.gameRender.newGameChangedSize(boardSize);
            this.myTimer.reset();
        }
    }

    /**
     * Overridden method for painting the game info on the panel.
     *
     * @param g the <code>Graphics</code> context in which to paint
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        this.gameInfo(g);
        super.repaint();
    }
}
