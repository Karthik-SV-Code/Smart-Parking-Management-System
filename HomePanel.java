import javax.swing.*;
import java.awt.*;

public class HomePanel extends JPanel {
    public HomePanel(String user, String role) {
        setLayout(new BorderLayout(8,8));
        setOpaque(false);
        JLabel welcome = new JLabel("<html><div style='text-align:left;padding:12px;'>Welcome <b>" + user + "</b><br/>Role: " + role + "</div></html>", SwingConstants.LEFT);
        welcome.setFont(welcome.getFont().deriveFont(Font.PLAIN,16f));
        add(welcome, BorderLayout.NORTH);
        JTextArea info = new JTextArea();
        info.setText("This dashboard shows current slot availability and recent bookings.\nUse the sidebar to navigate.");
        info.setEditable(false);
        info.setBorder(BorderFactory.createEmptyBorder(12,12,12,12));
        add(info, BorderLayout.CENTER);
    }
}
