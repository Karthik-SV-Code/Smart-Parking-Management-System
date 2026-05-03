public class User {
    private String name, username, password, role, vehiclePlate, vehicleModel;
    public User() {}
    public User(String name, String username, String password, String role, String vehiclePlate, String vehicleModel) {
        this.name = name; this.username = username; this.password = password; this.role = role; this.vehiclePlate = vehiclePlate; this.vehicleModel = vehicleModel;
    }
    public String getName(){return name;} public String getUsername(){return username;} public String getPassword(){return password;} public String getRole(){return role;}
    public String getVehiclePlate(){return vehiclePlate;} public String getVehicleModel(){return vehicleModel;}
}
