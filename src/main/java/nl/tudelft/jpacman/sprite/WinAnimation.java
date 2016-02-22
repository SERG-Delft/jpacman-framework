package nl.tudelft.jpacman.sprite;

import javax.swing.*;
import java.net.URL;


/**
 * Simplest way to show some sort of animation for now.
 */

public class WinAnimation {

    public void playAnimation() {
        URL url = this.getClass().getResource("/animation/win.gif");
        Icon icon = new ImageIcon(url);
        JLabel label = new JLabel(icon);

        JFrame f = new JFrame("Animation");
        f.add(label);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
}
