import javax.swing.*;
import java.awt.*;

public class BookingsPanel extends JPanel {
    private String user, role;
    private String bookingsFile = "bookings.xml";

    public BookingsPanel(String user, String role) {
        this.user = user; this.role = role;
        setLayout(new BorderLayout(8,8));
        setOpaque(false);

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.setOpaque(false);
        JLabel lbl = new JLabel("Bookings");
        lbl.setFont(lbl.getFont().deriveFont(Font.BOLD,16f));
        top.add(lbl);
        RoundButton refresh = new RoundButton("Refresh");
        refresh.addActionListener(e -> loadBookings());
        top.add(refresh);
        add(top, BorderLayout.NORTH);

        loadBookings();
    }

    private void loadBookings() {
        java.util.List<Booking> list = XMLUtils.readAllBookings(bookingsFile);
        StringBuilder sb = new StringBuilder();
        for(Booking b: list) sb.append(b.toString()).append("\n");
        if(sb.length()==0) sb.append("No bookings.");
        JTextArea ta = new JTextArea(sb.toString(), 20, 80);
        ta.setEditable(false);
        removeAll();
        add(new JScrollPane(ta), BorderLayout.CENTER);
        revalidate(); repaint();
    }
}
