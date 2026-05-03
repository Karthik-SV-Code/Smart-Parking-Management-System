import javax.swing.*;
import java.awt.*;

public class ProfilePanel extends JPanel {
    public ProfilePanel(String user) {
        setLayout(new BorderLayout());
        setOpaque(false);
        User u = null;
        for(User uu: XMLUtils.readAllUsers("users.xml")) if(uu.getUsername().equals(user)) { u = uu; break; }
        StringBuilder info = new StringBuilder();
        info.append("User: ").append(user).append("\n");
        if(u!=null) {
            info.append("Name: ").append(u.getName()).append("\n");
            info.append("Vehicle Plate: ").append(u.getVehiclePlate()).append("\n");
            info.append("Vehicle Model: ").append(u.getVehicleModel()).append("\n");
        }
        JTextArea ta = new JTextArea(info.toString());
        ta.setEditable(false);
        add(new JScrollPane(ta), BorderLayout.CENTER);
    }
}
