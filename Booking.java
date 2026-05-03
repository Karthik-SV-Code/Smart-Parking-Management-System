public class Booking {
    private String id, user, slotId, vehicle, status;
    public Booking(){}
    public Booking(String id, String user, String slotId, String vehicle, String status){
        this.id = id; this.user = user; this.slotId = slotId; this.vehicle = vehicle; this.status = status;
    }
    public String getId(){return id;} public String getUser(){return user;} public String getSlotId(){return slotId;} public String getVehicle(){return vehicle;} public String getStatus(){return status;}
    public void setStatus(String s){ this.status = s; }
    public String toString(){ return id + " | " + user + " | " + slotId + " | " + vehicle + " | " + status; }
}
