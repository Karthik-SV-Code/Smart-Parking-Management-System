public class Slot {
    private String id, status, type, location, bookedBy;
    public Slot(){}
    public Slot(String id, String status, String type, String location){
        this.id = id; this.status = status; this.type = type; this.location = location; this.bookedBy = "";
    }
    public String getId(){return id;} public String getStatus(){return status;} public String getType(){return type;} public String getLocation(){return location;} 
    public String getBookedBy(){return bookedBy==null?"":bookedBy;} 
    public void setBookedBy(String b){ this.bookedBy = b; }
    public void setStatus(String s){ this.status = s; }
    public boolean isFree(){ return "free".equalsIgnoreCase(status); }
    public String toString(){ return id + " (" + status + ")"; }
}
