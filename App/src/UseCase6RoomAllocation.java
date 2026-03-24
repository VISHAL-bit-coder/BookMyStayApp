import java.util.*;

/*
 * ============================================================
 * CLASS - Reservation
 * ============================================================
 */
class Reservation {

    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

/*
 * ============================================================
 * CLASS - RoomInventory
 * ============================================================
 */
class RoomInventory {

    private Map<String, Integer> availability;

    public RoomInventory() {
        availability = new HashMap<>();
    }

    public void addRoom(String type, int count) {
        availability.put(type, count);
    }

    public int getAvailableCount(String type) {
        return availability.getOrDefault(type, 0);
    }

    public void decrementRoom(String type) {
        availability.put(type, availability.get(type) - 1);
    }
}

/*
 * ============================================================
 * CLASS - RoomAllocationService
 * ============================================================
 */
class RoomAllocationService {

    // Track all allocated room IDs
    private Set<String> allocatedRooms;

    // Map room type → assigned room IDs
    private Map<String, Set<String>> assignedRoomsByType;

    public RoomAllocationService() {
        allocatedRooms = new HashSet<>();
        assignedRoomsByType = new HashMap<>();
    }

    // Allocate room
    public void allocateRoom(Reservation reservation, RoomInventory inventory) {

        String roomType = reservation.getRoomType();

        // Check availability
        if (inventory.getAvailableCount(roomType) > 0) {

            String roomId = generateRoomId(roomType);

            // Store allocated room
            allocatedRooms.add(roomId);

            assignedRoomsByType
                    .computeIfAbsent(roomType, k -> new HashSet<>())
                    .add(roomId);

            // Update inventory
            inventory.decrementRoom(roomType);

            System.out.println(
                    "Booking confirmed for Guest: "
                            + reservation.getGuestName()
                            + ", Room ID: "
                            + roomId
            );

        } else {
            System.out.println(
                    "No rooms available for Guest: "
                            + reservation.getGuestName()
            );
        }
    }

    // Generate unique room ID
    private String generateRoomId(String roomType) {
        int number = assignedRoomsByType
                .getOrDefault(roomType, new HashSet<>())
                .size() + 1;

        return roomType + "-" + number;
    }
}

/*
 * ============================================================
 * MAIN CLASS - UseCase6RoomAllocation
 * ============================================================
 */
public class UseCase6RoomAllocation {

    public static void main(String[] args) {

        System.out.println("Room Allocation Processing");

        // Create inventory
        RoomInventory inventory = new RoomInventory();
        inventory.addRoom("Single", 2);
        inventory.addRoom("Double", 1);
        inventory.addRoom("Suite", 1);

        // Create booking requests (FIFO simulation)
        Queue<Reservation> queue = new LinkedList<>();
        queue.offer(new Reservation("Adith", "Single"));
        queue.offer(new Reservation("Subha", "Double"));
        queue.offer(new Reservation("Vamrathi", "Suite"));

        // Allocation service
        RoomAllocationService service = new RoomAllocationService();

        // Process queue
        while (!queue.isEmpty()) {
            Reservation r = queue.poll();
            service.allocateRoom(r, inventory);
        }
    }
}