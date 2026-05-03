import javax.swing.*;
import java.awt.*;

public class DashboardFrame extends JFrame {
    private String user, role;
    private CardLayout cards;
    private JPanel mainPanel;
    private JLabel statusLabel;

    public DashboardFrame(String user, String role) {
        this.user = user; this.role = role;
        setTitle("Smart Parking - " + user + " (" + role + ")");
        setSize(1200,760);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(8,8));

        // header
        JPanel header = new RoundPanel(18, new Color(230,240,255));
        header.setLayout(new BorderLayout());
        JLabel title = new JLabel("Smart Parking System", SwingConstants.LEFT);
        title.setFont(title.getFont().deriveFont(Font.BOLD,20f));
        title.setBorder(BorderFactory.createEmptyBorder(8,12,8,12));
        header.add(title, BorderLayout.WEST);
        JLabel userInfo = new JLabel(user + " (" + role + ")");
        userInfo.setBorder(BorderFactory.createEmptyBorder(8,12,8,12));
        header.add(userInfo, BorderLayout.EAST);
        add(header, BorderLayout.NORTH);

        // sidebar
        JPanel sidebar = new RoundPanel(16, new Color(235,245,255));
        sidebar.setPreferredSize(new Dimension(220, getHeight()));
        sidebar.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8,12,8,12);
        gbc.gridx=0; gbc.gridy=0;

        RoundButton homeBtn = new RoundButton("Home");
        RoundButton slotsBtn = new RoundButton("Slots");
        RoundButton manageSlotsBtn = new RoundButton("Manage Slots");
        RoundButton bookingsBtn = new RoundButton("Bookings");
        RoundButton profileBtn = new RoundButton("Profile");
        RoundButton logoutBtn = new RoundButton("Logout");

        sidebar.add(homeBtn, gbc); gbc.gridy++;
        if(!"security".equalsIgnoreCase(role)) { sidebar.add(slotsBtn, gbc); gbc.gridy++; }
        if("admin".equalsIgnoreCase(role)) { sidebar.add(manageSlotsBtn, gbc); gbc.gridy++; }
        sidebar.add(bookingsBtn, gbc); gbc.gridy++;
        sidebar.add(profileBtn, gbc); gbc.gridy++;
        sidebar.add(logoutBtn, gbc); gbc.gridy++;

        add(sidebar, BorderLayout.WEST);

        // main (card layout)
        cards = new CardLayout();
        mainPanel = new JPanel(cards);
        mainPanel.setOpaque(false);
        mainPanel.add(new HomePanel(user, role), "HOME");
        if(!"security".equalsIgnoreCase(role)) mainPanel.add(new SlotsPanel(user, role), "SLOTS");
        if("admin".equalsIgnoreCase(role)) mainPanel.add(new ManageSlotsPanel(user), "MANAGE_SLOTS");
        mainPanel.add(new BookingsPanel(user, role), "BOOKINGS");
        mainPanel.add(new ProfilePanel(user), "PROFILE");
        if("security".equalsIgnoreCase(role)) mainPanel.add(new VerifyPanel(user, role), "VERIFY");

        add(mainPanel, BorderLayout.CENTER);

        // status bar (inline notifications)
        JPanel statusBar = new JPanel(new BorderLayout());
        statusBar.setOpaque(false);
        statusLabel = new JLabel(" ");
        statusLabel.setBorder(BorderFactory.createEmptyBorder(8,12,8,12));
        statusBar.add(statusLabel, BorderLayout.CENTER);
        add(statusBar, BorderLayout.SOUTH);

        // button actions (switch cards)
        homeBtn.addActionListener(e -> show("HOME"));
        slotsBtn.addActionListener(e -> show("SLOTS"));
        manageSlotsBtn.addActionListener(e -> show("MANAGE_SLOTS"));
        bookingsBtn.addActionListener(e -> show("BOOKINGS"));
        profileBtn.addActionListener(e -> show("PROFILE"));
        logoutBtn.addActionListener(e -> { dispose(); new LoginFrame().setVisible(true); });

        // initial card
        show("HOME");
    }

    public void show(String name) { cards.show(mainPanel, name); clearStatusAfterDelay(); }

    public void setStatus(String msg, Color color) {
        statusLabel.setText(msg);
        statusLabel.setForeground(color==null?Color.BLACK:color);
        clearStatusAfterDelay();
    }

    private void clearStatusAfterDelay() {
        // clear after 4 seconds
        new javax.swing.Timer(4000, e -> statusLabel.setText(" ")).start();
    }
}
