class CompanyEmployeeRecord {
    private String name;
    private String empId;
    private Employee employee;
    private ParkingSlot slot;

    public static int totalRecords = 0;

    public CompanyEmployeeRecord(String name, String empId, Employee employee, ParkingSlot slot) {
        this.name = name;
        this.empId = empId;
        this.employee = employee;
        this.slot = slot;
        totalRecords++;
    }

    public String fullProfile() {
        double pay = 0.0;
        if (employee instanceof ManagerEmployee) {
            pay = ((ManagerEmployee) employee).effectiveSalary();
        } else if (employee instanceof InternEmployee) {
            pay = ((InternEmployee) employee).effectiveSalary();
        } else if (employee != null) {
            pay = employee.getSalary();
        }

        String slotDesc = (slot != null) ? slot.getSlotNo() : "no parking assigned";
        return String.format("%s | Pay: Rs %.1f | Slot: %s", name, pay, slotDesc);
    }
}

public class CompanyHRMiniSystem {

    public static void main(String[] args) {
        System.out.println("=== Week 3 Problem F5: Capstone HR + Parking Allocation Mini-System ===");

        // Parking Slots
        ParkingSlot[] parkingLot = {
            new ParkingSlot("A1", 1, 0),
            new ParkingSlot("A2", 1, 0)
        };

        // Employees
        ManagerEmployee divyaEmp = new ManagerEmployee("M101", "Divya", 70000.0, 8000.0);
        Employee karanEmp = new Employee("E102", "Karan", 40000.0);
        InternEmployee meeraEmp = new InternEmployee("I103", "Meera", 12000.0, 10000.0);

        // Allot parking to Divya and Karan
        ParkingSlot divyaSlot = ParkingSlotAllocation.findAvailableSlot(parkingLot);
        if (divyaSlot != null) divyaSlot.allot("TN01AA1111");

        ParkingSlot karanSlot = ParkingSlotAllocation.findAvailableSlot(parkingLot);
        if (karanSlot != null) karanSlot.allot("TN01BB2222");

        // Meera is left unassigned (slot = null)
        ParkingSlot meeraSlot = null;

        // Build records
        CompanyEmployeeRecord r1 = new CompanyEmployeeRecord("Divya", "M101", divyaEmp, divyaSlot);
        CompanyEmployeeRecord r2 = new CompanyEmployeeRecord("Karan", "E102", karanEmp, karanSlot);
        CompanyEmployeeRecord r3 = new CompanyEmployeeRecord("Meera", "I103", meeraEmp, meeraSlot);

        System.out.println(r1.fullProfile());
        System.out.println(r2.fullProfile());
        System.out.println(r3.fullProfile());
        System.out.println("Total records: " + CompanyEmployeeRecord.totalRecords);
    }
}