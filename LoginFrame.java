import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    private static final String USERS_FILE = "users.xml";
    public LoginFrame() {
        setTitle("Smart Parking - Login");
        setSize(560,360);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        JPanel root = new RoundPanel(18, new Color(245,250,255));
        root.setBorder(BorderFactory.createEmptyBorder(16,16,16,16));
        root.setLayout(new GridBagLayout());
        add(root, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Login to Smart Parking", SwingConstants.CENTER);
        title.setFont(title.getFont().deriveFont(Font.BOLD,20f));
        gbc.gridx=0; gbc.gridy=0; gbc.gridwidth=2; root.add(title, gbc);

        gbc.gridwidth=1;
        gbc.gridy=1; gbc.gridx=0; root.add(new JLabel("Username:"), gbc);
        JTextField userField = new JTextField(18); gbc.gridx=1; root.add(userField, gbc);

        gbc.gridy=2; gbc.gridx=0; root.add(new JLabel("Password:"), gbc);
        JPasswordField passField = new JPasswordField(18); gbc.gridx=1; root.add(passField, gbc);

        JCheckBox remember = new JCheckBox("Remember me"); remember.setBackground(new Color(245,250,255));
        gbc.gridy=3; gbc.gridx=0; gbc.gridwidth=2; root.add(remember, gbc);

        JPanel btns = new JPanel(new FlowLayout(FlowLayout.CENTER,8,0)); btns.setOpaque(false);
        RoundButton login = new RoundButton("Login");
        RoundButton register = new RoundButton("Register");

        login.addActionListener(e -> {
            String u = userField.getText().trim();
            String p = new String(passField.getPassword());
            if(u.isEmpty()||p.isEmpty()) { JOptionPane.showMessageDialog(this, "Enter username and password.", "Error", JOptionPane.ERROR_MESSAGE); return; }
            XMLUtils.ensureFileExists(USERS_FILE, "users");
            if(XMLUtils.authenticateUser(u,p,USERS_FILE)) {
                String role = XMLUtils.getUserRole(u, USERS_FILE);
                if(remember.isSelected()) XMLUtils.saveConfig("config.properties","lastUser",u); else XMLUtils.saveConfig("config.properties","lastUser","");
                dispose();
                new DashboardFrame(u, role).setVisible(true);
            } else JOptionPane.showMessageDialog(this, "Invalid credentials.", "Error", JOptionPane.ERROR_MESSAGE);
        });

        register.addActionListener(e -> { dispose(); new RegisterFrame().setVisible(true); });

        btns.add(login); btns.add(register);
        gbc.gridy=4; gbc.gridx=0; gbc.gridwidth=2; root.add(btns, gbc);

        String last = XMLUtils.loadConfig("config.properties","lastUser");
        if(last!=null && !last.isEmpty()) { userField.setText(last); remember.setSelected(true); }
    }
}
