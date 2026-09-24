class ParkingSlot {
    private String slotNo;
    private int capacity;
    private int occupiedCount;

    public ParkingSlot(String slotNo, int capacity, int occupiedCount) {
        this.slotNo = slotNo;
        this.capacity = capacity;
        this.occupiedCount = occupiedCount;
    }

    public String getSlotNo() {
        return slotNo;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getOccupiedCount() {
        return occupiedCount;
    }

    public boolean allot(String vehicleNo) {
        if (occupiedCount < capacity) {
            occupiedCount++;
            return true;
        }
        return false;
    }
}

public class ParkingSlotAllocation {

    // Returns first available slot, or null if all slots are full
    public static ParkingSlot findAvailableSlot(ParkingSlot[] slots) {
        if (slots == null) {
            return null;
        }
        for (ParkingSlot slot : slots) {
            if (slot != null && slot.getOccupiedCount() < slot.getCapacity()) {
                return slot;
            }
        }
        return null;
    }

    // EXPLANATION ON OBJECT REFERENCES:
    // In Java, objects and arrays are accessed by reference. When `slots` is passed into `findAvailableSlot()`
    // or `safeAllot()`, Java copies the reference value (pointer) to the same underlying array and ParkingSlot objects
    // on the heap. The objects themselves are never cloned or duplicated. Therefore, when `slot.allot(vehicleNo)`
    // is called, it mutates the state of the original `ParkingSlot` object directly in memory.
    public static void safeAllot(ParkingSlot[] slots, String vehicleNo) {
        ParkingSlot availableSlot = findAvailableSlot(slots);
        if (availableSlot != null) {
            availableSlot.allot(vehicleNo);
            System.out.printf("%s allotted to slot %s%n", vehicleNo, availableSlot.getSlotNo());
        } else {
            System.out.printf("No slots available for %s%n", vehicleNo);
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Week 3 Problem F3: Object References, Null Safety, and a Mutating Method ===");

        // Test 1: Slots: A1 (3/4), A2 (5/5)
        System.out.println("Test 1 - Slots: A1 (3/4), A2 (5/5)");
        ParkingSlot[] slotsWithSpace = {
            new ParkingSlot("A1", 4, 3),
            new ParkingSlot("A2", 5, 5)
        };
        safeAllot(slotsWithSpace, "TN09AB1234");

        // Test 2: Slots: A1 (4/4), A2 (5/5)
        System.out.println("\nTest 2 - Slots: A1 (4/4), A2 (5/5)");
        ParkingSlot[] slotsFull = {
            new ParkingSlot("A1", 4, 4),
            new ParkingSlot("A2", 5, 5)
        };
        safeAllot(slotsFull, "TN09AB1234");
    }
}