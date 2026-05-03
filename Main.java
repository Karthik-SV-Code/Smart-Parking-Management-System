import javax.swing.*;
public class Main {
    public static void main(String[] args) {
        UIManager.put("Label.font", new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 16));
        UIManager.put("Button.font", new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 16));
        UIManager.put("Table.font", new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
        SwingUtilities.invokeLater(() -> {
            try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch(Exception ignored) {}
            new LoginFrame().setVisible(true);
        });
    }
}
