package othello;

import javax.swing.*;

public class UpperFramePanel extends JMenuBar {
    public UpperFramePanel(JFrame okno) {
        JMenu menu1 = new JMenu("Menu");
        JMenuItem i1=new JMenuItem("Settings");
        JMenuItem i2=new JMenuItem("Save");
        JMenuItem i3=new JMenuItem("Load");
        menu1.add(i1);
        menu1.add(i2);
        menu1.add(i3);

        JMenu menu2 = new JMenu("About");
        this.add(menu1);
        this.add(menu2);
    }
}
