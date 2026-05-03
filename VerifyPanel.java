import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.List;

public class VerifyPanel extends JPanel {
    private String user, role;
    private String bookingsFile = "bookings.xml";
    public VerifyPanel(String user, String role) {
        this.user = user; this.role = role;
        setLayout(new BorderLayout(8,8));
        setOpaque(false);
        JLabel lbl = new JLabel("Verify Bookings");
        lbl.setFont(lbl.getFont().deriveFont(Font.BOLD,16f));
        add(lbl, BorderLayout.NORTH);

        RoundButton refresh = new RoundButton("Refresh");
        refresh.addActionListener(e -> load());
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.setOpaque(false);
        top.add(refresh);
        add(top, BorderLayout.PAGE_START);

        load();
    }

    private void load() {
        List<Booking> list = XMLUtils.readAllBookings(bookingsFile);
        String[] cols;
        Object[][] data = new Object[list.size()][6];
        cols = new String[]{"ID","User","Slot","Vehicle","Status","Action"};
        for(int i=0;i<list.size();i++) {
            Booking b = list.get(i);
            data[i][0] = b.getId();
            data[i][1] = b.getUser();
            data[i][2] = b.getSlotId();
            data[i][3] = b.getVehicle();
            data[i][4] = b.getStatus();
            data[i][5] = ""; // placeholder for buttons
        }

        DefaultTableModel model = new DefaultTableModel(data, cols) {
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        JTable table = new JTable(model);
        table.setRowHeight(30);
        JScrollPane sp = new JScrollPane(table);
        removeAll();
        add(sp, BorderLayout.CENTER);

        // If role is security, allow approve/deny via popup menu (no major UI layout changes)
        if("security".equalsIgnoreCase(role)) {
            JPopupMenu pm = new JPopupMenu();
            JMenuItem verify = new JMenuItem("Verify & Approve");
            JMenuItem deny = new JMenuItem("Deny Booking");
            pm.add(verify); pm.add(deny);

            verify.addActionListener(e -> {
                int r = table.getSelectedRow();
                if(r==-1) { JOptionPane.showMessageDialog(this, "Select a booking row first."); return; }
                String bid = (String) table.getValueAt(r,0);
                String vehicle = (String) table.getValueAt(r,3);
                // Basic verification prompt for vehicle plate confirmation
                String vehInput = JOptionPane.showInputDialog(this, "Enter vehicle plate to verify:", vehicle);
                if(vehInput==null) return;
                if(!vehInput.trim().equalsIgnoreCase(vehicle.trim())) {
                    JOptionPane.showMessageDialog(this, "Vehicle plate does not match. Cannot approve.");
                    return;
                }
                XMLUtils.updateBookingStatus(bid, "approved", bookingsFile);
                JOptionPane.showMessageDialog(this, "Booking approved.");
                load();
            });

            deny.addActionListener(e -> {
                int r = table.getSelectedRow();
                if(r==-1) { JOptionPane.showMessageDialog(this, "Select a booking row first."); return; }
                String bid = (String) table.getValueAt(r,0);
                int conf = JOptionPane.showConfirmDialog(this, "Deny booking " + bid + "?", "Confirm", JOptionPane.YES_NO_OPTION);
                if(conf==JOptionPane.YES_OPTION) {
                    XMLUtils.cancelBooking(bid, bookingsFile, "slots.xml");
                    JOptionPane.showMessageDialog(this, "Booking denied and cancelled.");
                    load();
                }
            });

            table.setComponentPopupMenu(pm);
        }

        revalidate(); repaint();
    }
}