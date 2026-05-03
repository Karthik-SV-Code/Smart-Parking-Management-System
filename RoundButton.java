import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.event.*;

public class RoundButton extends JButton {
    private int radius = 18;
    public RoundButton(String text) {
        super(text);
        setOpaque(false);
        setForeground(Color.BLACK);
        setFocusPainted(false);
        setBorderPainted(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setBackground(new Color(200,230,255));
        setMargin(new Insets(10,16,10,16));
        setHorizontalAlignment(SwingConstants.LEFT);
        addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { setBackground(getBackground().darker()); repaint(); }
            public void mouseExited(MouseEvent e) { setBackground(new Color(200,230,255)); repaint(); }
        });
    }

    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fill(new RoundRectangle2D.Float(0,0,getWidth(),getHeight(),radius,radius));
        super.paintComponent(g);
        g2.dispose();
    }

    public void setRadius(int r){ this.radius = r; }
}
