import javax.swing.*;
import java.awt.*;

public class RegisterFrame extends JFrame {
    private static final String USERS_FILE = "users.xml";

    public RegisterFrame() {
        setTitle("Smart Parking - Register");
        setSize(560,420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        JPanel root = new RoundPanel(16, new Color(245,250,255));
        root.setBorder(BorderFactory.createEmptyBorder(12,12,12,12));
        root.setLayout(new GridBagLayout());
        add(root, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8,8,8,8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx=0; gbc.gridy=0; gbc.gridwidth=2;
        JLabel t = new JLabel("User Registration (includes vehicle)");
        t.setFont(t.getFont().deriveFont(Font.BOLD,16f));
        root.add(t, gbc);

        gbc.gridwidth=1;
        gbc.gridy=1; gbc.gridx=0; root.add(new JLabel("Full Name:"), gbc);
        JTextField name = new JTextField(20); gbc.gridx=1; root.add(name, gbc);

        gbc.gridy=2; gbc.gridx=0; root.add(new JLabel("Username:"), gbc);
        JTextField uname = new JTextField(20); gbc.gridx=1; root.add(uname, gbc);

        gbc.gridy=3; gbc.gridx=0; root.add(new JLabel("Password:"), gbc);
        JPasswordField pass = new JPasswordField(20); gbc.gridx=1; root.add(pass, gbc);

        gbc.gridy=4; gbc.gridx=0; root.add(new JLabel("Vehicle Plate No.:"), gbc);
        JTextField plate = new JTextField(12); gbc.gridx=1; root.add(plate, gbc);

        gbc.gridy=5; gbc.gridx=0; root.add(new JLabel("Vehicle Model:"), gbc);
        JTextField vmodel = new JTextField(18); gbc.gridx=1; root.add(vmodel, gbc);

        RoundButton create = new RoundButton("Create Account");
        create.addActionListener(e -> {
            String nm = name.getText().trim();
            String u = uname.getText().trim();
            String p = new String(pass.getPassword());
            String vp = plate.getText().trim();
            String vm = vmodel.getText().trim();
            if(nm.isEmpty()||u.isEmpty()||p.isEmpty()||vp.isEmpty()||vm.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields required.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            XMLUtils.ensureFileExists(USERS_FILE, "users");
            if(XMLUtils.userExists(u, USERS_FILE)) {
                JOptionPane.showMessageDialog(this, "Username exists.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            XMLUtils.appendUserWithVehicle(nm,u,p,"user",vp,vm,USERS_FILE);
            dispose();
            new LoginFrame().setVisible(true);
        });
        gbc.gridy=6; gbc.gridx=0; gbc.gridwidth=2; root.add(create, gbc);
    }
}
