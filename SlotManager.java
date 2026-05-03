import java.util.*;

public class SlotManager {
    public static Slot autoAllocate(String user, String vehiclePlate, String slotsFile, String bookingsFile) {
        List<Slot> slots = XMLUtils.readAllSlots(slotsFile);
        for(Slot s: slots) {
            if(s.isFree()) {
                String bid = "B" + System.currentTimeMillis();
                Booking b = new Booking(bid, user, s.getId(), vehiclePlate, "pending");
                XMLUtils.addBooking(b, bookingsFile);
                XMLUtils.markSlotBooked(s.getId(), user, slotsFile);
                return s;
            }
        }
        return null;
    }

    public static boolean manualAllocate(String user, String vehiclePlate, String slotId, String slotsFile, String bookingsFile) {
        List<Slot> slots = XMLUtils.readAllSlots(slotsFile);
        for(Slot s: slots) {
            if(s.getId().equals(slotId) && s.isFree()) {
                String bid = "B" + System.currentTimeMillis();
                Booking b = new Booking(bid, user, slotId, vehiclePlate, "pending");
                XMLUtils.addBooking(b, bookingsFile);
                XMLUtils.markSlotBooked(s.getId(), user, slotsFile);
                return true;
            }
        }
        return false;
    }
}
