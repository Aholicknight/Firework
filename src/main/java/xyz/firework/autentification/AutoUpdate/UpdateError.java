package xyz.firework.autentification.AutoUpdate;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import xyz.firework.autentification.NoStackTraceThrowable;

public class UpdateError {
    public static void Display() {
        Frame frame = new Frame();
        frame.setVisible(false);
        throw new NoStackTraceThrowable("Verification was unsuccessful!");
    }

    public static class Frame
    extends JFrame {
        public Frame() {
            this.setTitle("Update!");
            this.setDefaultCloseOperation(2);
            this.setLocationRelativeTo(null);
            JOptionPane.showMessageDialog(this, "Open installer & update ur client, reload minecraft", "Update!", -1, UIManager.getIcon("OptionPane.errorIcon"));
        }
    }
}
