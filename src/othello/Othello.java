package othello;

import javax.swing.*;
import java.awt.*;

public class Othello {
    private static final int WIDTH = 940; // 840
    private static final int HEIGHT = 820; // 720
    private static final int INITIAL_SIZE = 8;

    public Othello() {
        // MAIN window
        JFrame gameFrame = new JFrame("Othello");
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setSize(WIDTH, HEIGHT);
        gameFrame.setResizable(false);

        // Game render
        GameRender render = new GameRender(INITIAL_SIZE);

        // JMenuBar - MAIN window bar
        UpperFramePanel upperPanel = new UpperFramePanel(gameFrame);
        gameFrame.setJMenuBar(upperPanel);

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
