package othello;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Class which adds another option to expand the menu at the top of the screen.
 * Options offered are, for example save and load which can save the current
 * game state and respectively load a save of an unfinished game.
 *
 * @author Mário Laššú
 */
public class UpperFramePanel extends JMenuBar {
    /**
     * Constructor of the menu at the top.
     *
     * @param frame the main frame of the game
     */
    public UpperFramePanel(JFrame frame) {
        JMenu menu1 = new JMenu("Settings");

        menu1.add(new JMenuItem("Load"));
        menu1.add(new JMenuItem("Save"));
        menu1.add(new JSeparator());
        menu1.add(new JMenuItem(new AbstractAction("Rules") {
            @Override
            public void actionPerformed(ActionEvent e) {
                URI uri;
                try {
                    uri = new URI("https://www.worldothello.org/about/about-othello/othello-rules/official-rules/english");
                    java.awt.Desktop.getDesktop().browse(uri);
                } catch (URISyntaxException | IOException ignored) {
                }
            }
        }));

        this.add(menu1);
        frame.setJMenuBar(this);
    }
}
