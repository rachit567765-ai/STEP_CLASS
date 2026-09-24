public class Solution {

    public static void main(String[] args) {
        System.out.println("========== F1: Library Fine System ==========");
        runF1();

        System.out.println("\n========== F2: Extending Employee Without Touching It ==========");
        runF2();

        System.out.println("\n========== F3: Object References, Null Safety, and a Mutating Method ==========");
        runF3();

        System.out.println("\n========== F4: Designing the Instance/Static Boundary ==========");
        runF4();

        System.out.println("\n========== F5: Capstone HR + Parking Allocation Mini-System ==========");
        runF5();
    }

    private static void runF1() {
        BookIssue[] issues = {
            new BookIssue("Clean Code", "Alice", 18),
            new BookIssue("Effective Java", "Bob", 5),
            new BookIssue("Refactoring", "Charlie", 0),
            new BookIssue("DSA Handbook", "David", 21),
            new BookIssue("Design Patterns", "Emma", 9)
        };

        for (BookIssue issue : issues) {
            String status = issue.isSeverelyOverdue() ? "Severely overdue" : "OK";
            System.out.printf("%s - %d days - %s%n", issue.getTitle(), issue.getDaysOverdue(), status);
        }

        double totalFine = BookIssue.totalFineCollected(issues);
        System.out.printf("Total fine collected: Rs %.1f%n", totalFine);
    }

    private static void runF2() {
        Employee plain = new Employee("EMP01", "Alex", 40000.0);
        ManagerEmployee manager = new ManagerEmployee("MGR01", "Divya", 70000.0, 8000.0);
        InternEmployee intern = new InternEmployee("INT01", "Meera", 12000.0, 10000.0);

        printPay(plain);
        printPay(manager);
        printPay(intern);
    }

    public static void printPay(Employee emp) {
        if (emp instanceof ManagerEmployee) {
            ManagerEmployee mgr = (ManagerEmployee) emp;
            System.out.printf("Manager effective pay: Rs %.1f%n", mgr.effectiveSalary());
        } else if (emp instanceof InternEmployee) {
            InternEmployee intern = (InternEmployee) emp;
            System.out.printf("Intern effective pay: Rs %.1f%n", intern.effectiveSalary());
        } else if (emp instanceof Employee) {
            System.out.printf("Plain employee pay: Rs %.1f%n", emp.getSalary());
        }
    }

    private static void runF3() {
        System.out.println("Slots: A1 (3/4), A2 (5/5)");
        ParkingSlot[] slots1 = {
            new ParkingSlot("A1", 4, 3),
            new ParkingSlot("A2", 5, 5)
        };
        safeAllot(slots1, "TN09AB1234");

        System.out.println("\nSlots: A1 (4/4), A2 (5/5)");
        ParkingSlot[] slots2 = {
            new ParkingSlot("A1", 4, 4),
            new ParkingSlot("A2", 5, 5)
        };
        safeAllot(slots2, "TN09AB1234");
    }

    public static ParkingSlot findAvailableSlot(ParkingSlot[] slots) {
        if (slots == null) return null;
        for (ParkingSlot slot : slots) {
            if (slot != null && slot.getOccupiedCount() < slot.getCapacity()) {
                return slot;
            }
        }
        return null;
    }

    public static void safeAllot(ParkingSlot[] slots, String vehicleNo) {
        ParkingSlot availableSlot = findAvailableSlot(slots);
        if (availableSlot != null) {
            availableSlot.allot(vehicleNo);
            System.out.printf("%s allotted to slot %s%n", vehicleNo, availableSlot.getSlotNo());
        } else {
            System.out.printf("No slots available for %s%n", vehicleNo);
        }
    }

    private static void runF4() {
        System.out.println("Broken version:");
        BrokenLibraryMember m1 = new BrokenLibraryMember("Aditi", "LM-1001", 2);
        BrokenLibraryMember m2 = new BrokenLibraryMember("Rohan", "LM-1002", 1);
        m1.printName();
        m2.printName();
        System.out.println("(Aditi's data was overwritten — both members now show \"Rohan\")\n");

        System.out.println("Fixed version: same two members created");
        LibraryMember fixed1 = new LibraryMember("Aditi", 2);
        LibraryMember fixed2 = new LibraryMember("Rohan", 1);
        fixed1.printMemberCard();
        fixed2.printMemberCard();
        LibraryMember.printTotalMembers();
    }

    private static void runF5() {
        ParkingSlot[] parkingLot = {
            new ParkingSlot("A1", 1, 0),
            new ParkingSlot("A2", 1, 0)
        };

        ManagerEmployee divyaEmp = new ManagerEmployee("M101", "Divya", 70000.0, 8000.0);
        Employee karanEmp = new Employee("E102", "Karan", 40000.0);
        InternEmployee meeraEmp = new InternEmployee("I103", "Meera", 12000.0, 10000.0);

        ParkingSlot divyaSlot = findAvailableSlot(parkingLot);
        if (divyaSlot != null) divyaSlot.allot("TN01AA1111");

        ParkingSlot karanSlot = findAvailableSlot(parkingLot);
        if (karanSlot != null) karanSlot.allot("TN01BB2222");

        ParkingSlot meeraSlot = null;

        CompanyEmployeeRecord r1 = new CompanyEmployeeRecord("Divya", "M101", divyaEmp, divyaSlot);
        CompanyEmployeeRecord r2 = new CompanyEmployeeRecord("Karan", "E102", karanEmp, karanSlot);
        CompanyEmployeeRecord r3 = new CompanyEmployeeRecord("Meera", "I103", meeraEmp, meeraSlot);

        System.out.println(r1.fullProfile());
        System.out.println(r2.fullProfile());
        System.out.println(r3.fullProfile());
        System.out.println("Total records: " + CompanyEmployeeRecord.totalRecords);
    }
}

/*
================================================================================
F1. From Procedural Mess to a Working Library Fine System
Scenario: The library assistant currently tracks five overdue books
Task:
• Design a class BookIssue with fields title, borrowerName, and daysOverdue, all set through a constructor.
• Add an instance method fineAmount() that returns daysOverdue * 5 (Rs 5 per day) when daysOverdue > 0, else 0.
• Add an instance method isSeverelyOverdue() returning true when daysOverdue is greater than 14.
• Add a static method totalFineCollected(BookIssue[] issues) that sums fineAmount() across an array of BookIssue
objects — this belongs to the class as a whole, not to any one book.
• In main, build an array of five BookIssue objects, print each one's title and overdue status, then print the total
fine using the class name.
• In a code comment, justify why totalFineCollected is static while fineAmount is not.
Suggested method signature(s):
double fineAmount()
boolean isSeverelyOverdue()
static double totalFineCollected(BookIssue[] issues)

Sample Input / Output:
Clean Code - 18 days - Severely overdue
Effective Java - 5 days - OK
Refactoring - 0 days - OK
DSA Handbook - 21 days - Severely overdue
Design Patterns - 9 days - OK
Total fine collected: Rs 265.0
================================================================================
*/
// Ans F1:
class BookIssue {
    private String title;
    private String borrowerName;
    private int daysOverdue;

    public BookIssue(String title, String borrowerName, int daysOverdue) {
        this.title = title;
        this.borrowerName = borrowerName;
        this.daysOverdue = daysOverdue;
    }

    public String getTitle() {
        return title;
    }

    public int getDaysOverdue() {
        return daysOverdue;
    }

    // fineAmount() is an instance method because fine calculation depends directly
    // on the individual book's instance state (daysOverdue).
    public double fineAmount() {
        return daysOverdue > 0 ? daysOverdue * 5.0 : 0.0;
    }

    public boolean isSeverelyOverdue() {
        return daysOverdue > 14;
    }

    // totalFineCollected() is static because it operates over an entire array/collection
    // of BookIssue objects as an aggregate utility and belongs to the class as a whole,
    // not to any single book instance.
    public static double totalFineCollected(BookIssue[] issues) {
        if (issues == null) return 0.0;
        double total = 0.0;
        for (BookIssue issue : issues) {
            if (issue != null) {
                total += issue.fineAmount();
            }
        }
        return total;
    }
}

/*
================================================================================
F2. Extending Employee Without Touching It
Scenario: HR already relies on a tested Employee class
Task:
• Define Employee with private empId, empName, and salary; a constructor; and a method getSalary().
• Define ManagerEmployee extends Employee, adding a private double teamBonus set through its own
constructor, plus a new method effectiveSalary() that returns getSalary() + teamBonus. Do not modify Employee
to make this work.
• Define InternEmployee extends Employee, adding a private double stipendCap set through its own constructor,
plus a new method effectiveSalary() that returns whichever is smaller: getSalary() or stipendCap.
• In main, create one of each of the three employee types, and print each one's pay — using instanceof to decide
which extra behaviour to invoke for each type.
Suggested method signature(s):
class ManagerEmployee extends Employee { double effectiveSalary() }
class InternEmployee extends Employee { double effectiveSalary() }

Sample Input / Output:
Plain employee pay: Rs 40000.0
Manager effective pay: Rs 78000.0
Intern effective pay: Rs 10000.0
================================================================================
*/
// Ans F2:
class Employee {
    private String empId;
    private String empName;
    private double salary;

    public Employee(String empId, String empName, double salary) {
        this.empId = empId;
        this.empName = empName;
        this.salary = salary;
    }

    public String getEmpId() {
        return empId;
    }

    public String getEmpName() {
        return empName;
    }

    public double getSalary() {
        return salary;
    }
}

class ManagerEmployee extends Employee {
    private double teamBonus;

    public ManagerEmployee(String empId, String empName, double salary, double teamBonus) {
        super(empId, empName, salary);
        this.teamBonus = teamBonus;
    }

    public double getTeamBonus() {
        return teamBonus;
    }

    public double effectiveSalary() {
        return getSalary() + teamBonus;
    }
}

class InternEmployee extends Employee {
    private double stipendCap;

    public InternEmployee(String empId, String empName, double salary, double stipendCap) {
        super(empId, empName, salary);
        this.stipendCap = stipendCap;
    }

    public double getStipendCap() {
        return stipendCap;
    }

    public double effectiveSalary() {
        return Math.min(getSalary(), stipendCap);
    }
}

/*
================================================================================
F3. Object References, Null Safety, and a Mutating Method
Scenario: The campus parking allocation service
Task:
• Define ParkingSlot with fields slotNo, capacity, occupiedCount, and a method allot(String vehicleNo) that fills a
spot if one is free.
• Write a static method ParkingSlot findAvailableSlot(ParkingSlot[] slots) that returns the first slot with
occupiedCount < capacity, or null if every slot is full.
• Write a static method safeAllot(ParkingSlot[] slots, String vehicleNo) that calls findAvailableSlot, checks the
result for null before touching it, and either allots the vehicle or prints a clear “no slots available” message —
with zero risk of a NullPointerException, regardless of input.
• In main, call safeAllot() twice: once against an array containing an available slot, and once against an array
where every slot is already full, to prove both paths behave correctly.
• In a comment, explain why passing the ParkingSlot array into these methods does not copy the slots themselves.
Suggested method signature(s):
static ParkingSlot findAvailableSlot(ParkingSlot[] slots)
static void safeAllot(ParkingSlot[] slots, String vehicleNo)

Sample Input / Output:
Slots: A1 (3/4), A2 (5/5)
safeAllot(slots, "TN09AB1234") -> TN09AB1234 allotted to slot A1
Slots: A1 (4/4), A2 (5/5)
safeAllot(slots, "TN09AB1234") -> No slots available for TN09AB1234
================================================================================
*/
// Ans F3:
// Explanation: In Java, arrays and objects are passed by reference value. Passing ParkingSlot[]
// into a method passes a copy of the reference to the existing array on the heap.
// The ParkingSlot objects themselves are never copied or cloned; any mutation via allot()
// directly alters the original object instances.
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

/*
================================================================================
F4. Designing the Instance/Static Boundary for a Library Membership System
Scenario: A junior developer's first draft of LibraryMember marks every single field static
Task:
• First reproduce the broken version: class LibraryMember { static String name; static String memberId; static int
booksIssued; ... } and create two members one after another.
• In a comment, explain — for each field — exactly why marking it static is wrong here.
• Redesign the class with the correct split: name, memberId, and booksIssued as instance fields; libraryName and
memberCount as static fields.
• Add a constructor that derives memberId automatically from memberCount, an instance method
printMemberCard(), and a static method printTotalMembers().
• In main, run the broken version first and show how creating the second member silently overwrote the first
member's data. Then run the corrected version and show both members now keep fully independent data.
Suggested method signature(s):
void printMemberCard() | static void printTotalMembers()

Sample Input / Output:
Broken version:
Rohan
Rohan
(Aditi's data was overwritten — both members now show “Rohan”)

Fixed version: same two members created
Aditi | LM-1001
Rohan | LM-1002
Total members: 2
================================================================================
*/
// Ans F4:
// Why marking these fields static is wrong:
// 1. name: Static variables are shared class-wide; setting a second member's name overwrites the first.
// 2. memberId: Member ID must uniquely identify a person; a static ID is shared by everyone.
// 3. booksIssued: Tracks personal checkout counts; a static field shares a single counter across all members.
class BrokenLibraryMember {
    static String name;
    static String memberId;
    static int booksIssued;

    public BrokenLibraryMember(String name, String memberId, int booksIssued) {
        BrokenLibraryMember.name = name;
        BrokenLibraryMember.memberId = memberId;
        BrokenLibraryMember.booksIssued = booksIssued;
    }

    public void printName() {
        System.out.println(name);
    }
}

class LibraryMember {
    private String name;
    private String memberId;
    private int booksIssued;

    private static String libraryName = "City Central Library";
    private static int memberCount = 0;

    public LibraryMember(String name, int booksIssued) {
        this.name = name;
        this.booksIssued = booksIssued;
        memberCount++;
        this.memberId = "LM-" + (1000 + memberCount);
    }

    public void printMemberCard() {
        System.out.printf("%s | %s%n", name, memberId);
    }

    public static void printTotalMembers() {
        System.out.printf("Total members: %d%n", memberCount);
    }
}

/*
================================================================================
F5. Capstone: A Small HR + Parking Allocation Mini-System
Scenario: Combine everything from this week into one small but complete mini-system
Task:
• Reuse Employee and ManagerEmployee extends Employee (as in F2), and ParkingSlot with the null-safe
allotment process (as in F3).
• Write a class CompanyEmployeeRecord that ties an employee's name, empId, an Employee (or
ManagerEmployee) object, and a parking slot assignment together as fields of one object — demonstrating that
an object's fields can themselves be objects.
• Add a static int totalRecords counter, incremented in the CompanyEmployeeRecord constructor, and an
instance method fullProfile() that prints the employee's name, effective pay, and slot number in one line —
printing “no parking assigned” instead of a slot number when the slot reference is null.
• In main, build a small system of three employee records, allot parking to only two of them (leaving the third
unallotted on purpose), and print each record's fullProfile(), followed by
CompanyEmployeeRecord.totalRecords.
Suggested method signature(s):
class CompanyEmployeeRecord { String name; String empId; Employee employee; ParkingSlot slot; }
String fullProfile()

Sample Input / Output:
Divya | Pay: Rs 78000.0 | Slot: A1
Karan | Pay: Rs 40000.0 | Slot: A2
Meera | Pay: Rs 10000.0 | Slot: no parking assigned
Total records: 3
================================================================================
*/
// Ans F5:
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