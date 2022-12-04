package othello;

import javax.swing.*;
import java.awt.*;

/**
 * This class Othello represents the game itself. <br>
 * There are <code>3 private fields</code>, the first and second being WIDTH and HEIGHT
 * and these are actually just <code>immutable</code> class parameters which determines the width
 * and height of the frame in which everything is displayed. The last parameter
 * is also an immutable class parameter which determines the initial size
 * of the board (note the frame itself!). <br>
 * This class consist of <code>main</code> frame with right panel (where we can see some information
 * about the current game, and we can also change the size of the game there) and a 'restart' button
 * which can reset the game after clicking. <br>
 * At the end, it sets the location of the frame to the center of the screen and set is visible for the user.
 *
 * @author Mário Laššú
 */
public class Othello {
    private static final int WIDTH = 940; // 840
    private static final int HEIGHT = 820; // 720
    private static final int INITIAL_SIZE = 8;

    /**
     * Constructor that creates the game itself with several amenities such as buttons and menu.
     */
    public Othello() {
        // MAIN window
        JFrame gameFrame = new JFrame("Othello");
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setSize(WIDTH, HEIGHT);
        gameFrame.setResizable(false);

        // JMenuBar - MAIN window bar
        new UpperFramePanel(gameFrame);

        // Game render
        GameRender render = new GameRender(INITIAL_SIZE);


        // Right panel Layout
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.setBackground(Color.getHSBColor(0.3f, 0.3f, 0.2f));

        // JSlider or JComboBox - Right panel + size of game
        RightFramePanel rightPanel = new RightFramePanel(render);

        // restart button
        JButton restartGame = new JButton("RESTART");
        restartGame.addActionListener(render);
        restartGame.addKeyListener(render);

        // Adding of menu + button into MAIN window
        panel.add(rightPanel);
        panel.add(restartGame);
        gameFrame.add(panel, BorderLayout.LINE_END);
        gameFrame.add(render);

        // MAIN window centering and setting it visible for user
        gameFrame.setLocationRelativeTo(null);
        gameFrame.setVisible(true);
    }
}
