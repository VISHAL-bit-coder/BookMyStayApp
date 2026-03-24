/**
 * ============================================================
 * MAIN CLASS - UseCase3InventorySetup
 * ============================================================
 *
 * Use Case 3: Centralized Room Inventory Management
 *
 * @version 3.1
 */
public class UseCase3InventorySetup {

    public static void main(String[] args) {

        // Create inventory
        RoomInventory inventory = new RoomInventory();

        // Create room objects (reuse from UC2)
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        System.out.println("Hotel Room Inventory Status\n");

        // Single Room
        System.out.println("Single Room:");
        single.displayRoomDetails();
        System.out.println("Available Rooms: " +
                inventory.getRoomAvailability().get("Single"));

        // Double Room
        System.out.println("\nDouble Room:");
        doubleRoom.displayRoomDetails();
        System.out.println("Available Rooms: " +
                inventory.getRoomAvailability().get("Double"));

        // Suite Room
        System.out.println("\nSuite Room:");
        suite.displayRoomDetails();
        System.out.println("Available Rooms: " +
                inventory.getRoomAvailability().get("Suite"));
    }
}
