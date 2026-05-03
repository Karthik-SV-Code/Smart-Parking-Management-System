import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ManageSlotsPanel extends JPanel {
    private DefaultTableModel model;
    private JTable table;
    private String slotsFile = "slots.xml";
    private String bookingsFile = "bookings.xml";

    public ManageSlotsPanel(String admin) {
        setLayout(new BorderLayout(8,8));
        setOpaque(false);

        JLabel lbl = new JLabel("Manage Slots");
        lbl.setFont(lbl.getFont().deriveFont(Font.BOLD,16f));
        add(lbl, BorderLayout.NORTH);

        model = new DefaultTableModel(new String[]{"Slot ID","Status","Type","Location","Booked By"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.LEFT));
        actions.setOpaque(false);
        RoundButton add = new RoundButton("Add Slot");
        RoundButton del = new RoundButton("Delete Selected Slot");
        RoundButton refresh = new RoundButton("Refresh");
        actions.add(add); actions.add(del); actions.add(refresh);
        add(actions, BorderLayout.SOUTH);

        add.addActionListener(e -> showAddDialog());
        del.addActionListener(e -> deleteSelected());
        refresh.addActionListener(e -> refreshTable());

        refreshTable();
        new javax.swing.Timer(5000, e -> refreshTable()).start();
    }

    private void showAddDialog() {
        JTextField idField = new JTextField();
        JComboBox<String> typeBox = new JComboBox<>(new String[]{"Compact","Large"});
        JTextField locField = new JTextField();
        JPanel p = new JPanel(new GridLayout(3,2,8,8));
        p.add(new JLabel("Slot ID:")); p.add(idField);
        p.add(new JLabel("Type:")); p.add(typeBox);
        p.add(new JLabel("Location:")); p.add(locField);
        int ok = JOptionPane.showConfirmDialog(this, p, "Add Slot", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if(ok == JOptionPane.OK_OPTION) {
            String id = idField.getText().trim();
            String type = (String)typeBox.getSelectedItem();
            String loc = locField.getText().trim();
            if(id.isEmpty() || loc.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields required.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            XMLUtils.addSlot(new Slot(id, "free", type, loc), slotsFile);
            Window w = SwingUtilities.getWindowAncestor(this);
            if(w instanceof DashboardFrame) ((DashboardFrame)w).setStatus("Added slot: " + id, new Color(34,139,34));
            refreshTable();
        }
    }

    private void deleteSelected() {
        int row = table.getSelectedRow();
        if(row < 0) {
            JOptionPane.showMessageDialog(this, "Select a slot to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String id = (String)model.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Delete slot " + id + " ? This will fail if the slot is currently booked.", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if(confirm == JOptionPane.YES_OPTION) {
            boolean ok = XMLUtils.deleteSlot(id, slotsFile, bookingsFile);
            Window w = SwingUtilities.getWindowAncestor(this);
            if(ok) {
                if(w instanceof DashboardFrame) ((DashboardFrame)w).setStatus("Deleted slot: " + id, new Color(34,139,34));
                refreshTable();
            } else {
                if(w instanceof DashboardFrame) ((DashboardFrame)w).setStatus("Cannot delete. Slot may be booked.", Color.RED);
            }
        }
    }

    private void refreshTable() {
        model.setRowCount(0);
        List<Slot> list = XMLUtils.readAllSlots(slotsFile);
        for(Slot s: list) model.addRow(new Object[]{s.getId(), s.getStatus(), s.getType(), s.getLocation(), s.getBookedBy()});
    }
}
