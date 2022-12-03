package othello;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RightFramePanel extends JPanel implements ActionListener {
    private final GameRender gameRender;
    private final JComboBox<Integer> comboBox;
    private final MyTimer myTimer;

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
        this.add(myTimer);
        this.comboBox.setFocusable(false);
        this.setBackground(Color.getHSBColor(0.33f, 0.3f, 0.3f));
    }

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
    @Override
    public void actionPerformed(ActionEvent e) {
        if (this.comboBox.getSelectedItem() != null) {
            int boardSize = (int) this.comboBox.getSelectedItem();
            this.gameRender.newGameChangedSize(boardSize);

            this.myTimer.reset();
        }
    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        gameInfo(g);
        repaint();
    }

}
