import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SlotsPanel extends JPanel {
    private JPanel gridPanel;
    private String user;
    private String role;
    private String slotsFile = "slots.xml";
    private String bookingsFile = "bookings.xml";

    public SlotsPanel(String user, String role) {
        this.user = user; this.role = role;
        setLayout(new BorderLayout(8,8));
        setOpaque(false);

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.setOpaque(false);
        JLabel lbl = new JLabel("Parking Slots");
        lbl.setFont(lbl.getFont().deriveFont(Font.BOLD,16f));
        top.add(lbl);
        RoundButton refresh = new RoundButton("Refresh");
        refresh.addActionListener(e -> buildGrid());
        top.add(refresh);
        add(top, BorderLayout.NORTH);

        gridPanel = new JPanel();
        gridPanel.setOpaque(false);
        gridPanel.setLayout(new GridLayout(2,3,14,14)); // 6 slots
        add(gridPanel, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.LEFT)); bottom.setOpaque(false);
        RoundButton autoBook = new RoundButton("Auto-Allocate Slot");
        RoundButton manualBook = new RoundButton("Manual Book Slot");
        bottom.add(autoBook); bottom.add(manualBook);
        add(bottom, BorderLayout.SOUTH);

        autoBook.addActionListener(e -> {
            XMLUtils.ensureFileExists(slotsFile, "slots");
            XMLUtils.ensureFileExists(bookingsFile, "bookings");
            String vehicle = XMLUtils.getUserVehiclePlate(user, "users.xml");
            Slot s = SlotManager.autoAllocate(user, vehicle, slotsFile, bookingsFile);
            if(s!=null) {
                Window w = SwingUtilities.getWindowAncestor(this);
                if(w instanceof DashboardFrame) ((DashboardFrame)w).setStatus("Allocated slot: " + s.getId(), new Color(34,139,34));
                buildGrid();
            } else {
                Window w = SwingUtilities.getWindowAncestor(this);
                if(w instanceof DashboardFrame) ((DashboardFrame)w).setStatus("No free slots available.", Color.RED);
            }
        });

        manualBook.addActionListener(e -> {
            List<Slot> list = XMLUtils.readAllSlots(slotsFile);
            String[] opts = list.stream().filter(Slot::isFree).map(Slot::getId).toArray(String[]::new);
            if(opts.length==0) {
                Window w = SwingUtilities.getWindowAncestor(this);
                if(w instanceof DashboardFrame) ((DashboardFrame)w).setStatus("No free slots available.", Color.RED);
                return;
            }
            String choice = (String)JOptionPane.showInputDialog(this, "Choose slot:", "Manual booking", JOptionPane.PLAIN_MESSAGE,null,opts,opts[0]);
            if(choice!=null) {
                String vehicle = XMLUtils.getUserVehiclePlate(user, "users.xml");
                boolean ok = SlotManager.manualAllocate(user, vehicle, choice, slotsFile, bookingsFile);
                Window w = SwingUtilities.getWindowAncestor(this);
                if(ok) { if(w instanceof DashboardFrame) ((DashboardFrame)w).setStatus("Booked " + choice, new Color(34,139,34)); buildGrid(); }
                else { if(w instanceof DashboardFrame) ((DashboardFrame)w).setStatus("Failed to book.", Color.RED); }
            }
        });

        buildGrid();
        new javax.swing.Timer(5000, e -> buildGrid()).start();
    }

    private void buildGrid() {
        gridPanel.removeAll();
        java.util.List<Slot> list = XMLUtils.readAllSlots(slotsFile);
        for(Slot s : list) {
            RoundButton btn = new RoundButton(s.getId());
            btn.setPreferredSize(new Dimension(160,110));
            Color bg = s.isFree() ? new Color(200,255,200) : new Color(255,220,220);
            btn.setBackground(bg);
            btn.setToolTipText("Status: " + s.getStatus() + " | Type: " + s.getType());
            btn.addActionListener(e -> {
                Window w = SwingUtilities.getWindowAncestor(this);
                if(w instanceof DashboardFrame) ((DashboardFrame)w).setStatus("Slot: " + s.getId() + " Status: " + s.getStatus(), Color.BLACK);
            });
            gridPanel.add(btn);
        }
        revalidate(); repaint();
    }
}
