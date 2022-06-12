package xyz.firework.autentification.HwidCheck;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import xyz.firework.autentification.NoStackTraceThrowable;

public class HwidError {
    public static void Display() {
        Frame frame = new Frame();
        frame.setVisible(false);
        throw new NoStackTraceThrowable("Verification was unsuccessful!");
    }

    public static class Frame
    extends JFrame {
        public Frame() {
            this.setTitle("Hwid does not mached!");
            this.setDefaultCloseOperation(2);
            this.setLocationRelativeTo(null);
            JOptionPane.showMessageDialog(this, "Cry about it :)))", "Hwid does not mached!", -1, UIManager.getIcon("OptionPane.errorIcon"));
        }
    }
}
