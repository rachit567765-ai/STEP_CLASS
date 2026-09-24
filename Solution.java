public class Solution {

    public static void main(String[] args) {
        System.out.println("========== F1: Attendance System ==========");
        runF1();

        System.out.println("\n========== F2: Extending FeeAccount Without Touching It ==========");
        runF2();

        System.out.println("\n========== F3: Object References, Null Safety, and a Mutating Method ==========");
        runF3();

        System.out.println("\n========== F4: Designing the Instance/Static Boundary ==========");
        runF4();

        System.out.println("\n========== F5: Capstone Fee + Hostel Management Mini-System ==========");
        runF5();
    }

    private static void runF1() {
        SrmStudentAttendance[] students = {
            new SrmStudentAttendance("Ravi", "R101", 82),
            new SrmStudentAttendance("Anitha", "R102", 68),
            new SrmStudentAttendance("Karthik", "R103", 91),
            new SrmStudentAttendance("Meera", "R104", 74),
            new SrmStudentAttendance("Suresh", "R105", 60)
        };

        for (SrmStudentAttendance s : students) {
            String status = s.isEligible() ? "Eligible" : "Detained";
            System.out.printf("%s - %d%% - %s%n", s.getName(), s.getAttendance(), status);
        }

        double avg = SrmStudentAttendance.classAverage(students);
        System.out.printf("Class average: %.1f%%%n", avg);
    }

    private static void runF2() {
        FeeAccount plain = new FeeAccount("RA01", 150000.0, 150000.0);
        HostelFeeAccount hostel = new HostelFeeAccount("RA02", 200000.0, 60000.0);
        ScholarshipFeeAccount scholarship = new ScholarshipFeeAccount("RA03", 180000.0, 0.0, 20.0);

        printFeeAccountDue(plain);
        printFeeAccountDue(hostel);
        printFeeAccountDue(scholarship);
    }

    public static void printFeeAccountDue(FeeAccount acc) {
        if (acc instanceof ScholarshipFeeAccount) {
            ScholarshipFeeAccount s = (ScholarshipFeeAccount) acc;
            System.out.printf("Scholarship account effective due: Rs %.1f%n", s.effectiveDue());
        } else if (acc instanceof HostelFeeAccount) {
            System.out.printf("Hostel account due: Rs %.1f%n", acc.getDue());
        } else if (acc instanceof FeeAccount) {
            System.out.printf("Plain account due: Rs %.1f%n", acc.getDue());
        }
    }

    private static void runF3() {
        System.out.println("Rooms: C-214 (2/3), C-507 (2/2)");
        HostelRoom[] rooms1 = {
            new HostelRoom("C-214", 3, 2),
            new HostelRoom("C-507", 2, 2)
        };
        safeAllot(rooms1, "Divya");

        System.out.println("\nRooms: C-214 (3/3), C-507 (2/2)");
        HostelRoom[] rooms2 = {
            new HostelRoom("C-214", 3, 3),
            new HostelRoom("C-507", 2, 2)
        };
        safeAllot(rooms2, "Divya");
    }

    public static HostelRoom findAvailableRoom(HostelRoom[] rooms) {
        if (rooms == null) return null;
        for (HostelRoom room : rooms) {
            if (room != null && room.getOccupied() < room.getBeds()) {
                return room;
            }
        }
        return null;
    }

    public static void safeAllot(HostelRoom[] rooms, String studentName) {
        HostelRoom available = findAvailableRoom(rooms);
        if (available != null) {
            available.allot(studentName);
            System.out.printf("%s allotted to room %s%n", studentName, available.getRoomNo());
        } else {
            System.out.printf("No rooms available for %s%n", studentName);
        }
    }

    private static void runF4() {
        System.out.println("Broken version:");
        BrokenSrmStudent b1 = new BrokenSrmStudent("Ravi", "RA01", 80);
        BrokenSrmStudent b2 = new BrokenSrmStudent("Meera", "RA02", 85);
        b1.printName();
        b2.printName();
        System.out.println("(Ravi's data was overwritten — both students now show \"Meera\")\n");

        System.out.println("Fixed version: same two students created");
        SrmStudentProfile s1 = new SrmStudentProfile("Ravi", 80);
        SrmStudentProfile s2 = new SrmStudentProfile("Meera", 85);
        s1.printIdCard();
        s2.printIdCard();
        SrmStudentProfile.printTotalAdmissions();
    }

    private static void runF5() {
        HostelRoom[] hostelRooms = {
            new HostelRoom("C-214", 1, 0),
            new HostelRoom("C-507", 1, 0)
        };

        HostelFeeAccount acc1 = new HostelFeeAccount("RA01", 200000.0, 60000.0);
        HostelFeeAccount acc2 = new HostelFeeAccount("RA02", 200000.0, 20000.0);
        HostelFeeAccount acc3 = new HostelFeeAccount("RA03", 200000.0, 0.0);

        // Reject a negative payment test
        acc3.pay(-5000.0);

        HostelRoom room1 = findAvailableRoom(hostelRooms);
        if (room1 != null) room1.allot("Ravi");

        HostelRoom room2 = findAvailableRoom(hostelRooms);
        if (room2 != null) room2.allot("Anitha");

        HostelRoom room3 = null; // Unallotted

        SrmStudentSystem st1 = new SrmStudentSystem("Ravi", "RA01", acc1, room1);
        SrmStudentSystem st2 = new SrmStudentSystem("Anitha", "RA02", acc2, room2);
        SrmStudentSystem st3 = new SrmStudentSystem("Karthik", "RA03", acc3, room3);

        System.out.println(st1.fullStatus());
        System.out.println(st2.fullStatus());
        System.out.println(st3.fullStatus());
        System.out.println("Total students: " + SrmStudentSystem.totalStudents);
    }
}

/*
================================================================================
F1. From Procedural Mess to a Working Attendance System
Scenario: The class representative currently tracks five students' attendance
Task:
• Design a class SrmStudent with fields name, regNo, and attendance, all set through a constructor.
• Add an instance method addAttendanceUpdate(int newAttendance) that updates attendance after a re-check,
and an instance method isEligible() returning true when attendance is 75 or above.
• Add a static method classAverage(SrmStudent[] students) that computes the average attendance across an array
of students — this belongs to the class as a whole, not to any one student.
• In main, build an array of five SrmStudent objects, print each one's name and eligibility, then print the class
average using the class name.
• In a code comment, justify why classAverage is static while isEligible is not.
Suggested method signature(s):
boolean isEligible()
void addAttendanceUpdate(int newAttendance)
static double classAverage(SrmStudent[] students)

Sample Input / Output:
Ravi - 82% - Eligible
Anitha - 68% - Detained
Karthik - 91% - Eligible
Meera - 74% - Detained
Suresh - 60% - Detained
Class average: 75.0%
================================================================================
*/
// Ans F1:
class SrmStudentAttendance {
    private String name;
    private String regNo;
    private int attendance;

    public SrmStudentAttendance(String name, String regNo, int attendance) {
        this.name = name;
        this.regNo = regNo;
        this.attendance = attendance;
    }

    public String getName() {
        return name;
    }

    public int getAttendance() {
        return attendance;
    }

    public void addAttendanceUpdate(int newAttendance) {
        this.attendance = newAttendance;
    }

    // isEligible() is an instance method because eligibility is determined by the specific
    // student's individual attendance record.
    public boolean isEligible() {
        return attendance >= 75;
    }

    // classAverage() is static because calculating the average across a group of students
    // belongs to the class as a utility calculation rather than any individual student instance.
    public static double classAverage(SrmStudentAttendance[] students) {
        if (students == null || students.length == 0) return 0.0;
        double sum = 0.0;
        for (SrmStudentAttendance s : students) {
            if (s != null) {
                sum += s.getAttendance();
            }
        }
        return sum / students.length;
    }
}

/*
================================================================================
F2. Extending FeeAccount Without Touching It
Scenario: SRM already relies on a tested FeeAccount class
Task:
• Define FeeAccount with private regNo, totalFee, and amountPaid; a constructor; a pay(double amount) method
that rejects non-positive payments; and getDue().
• Define HostelFeeAccount extends FeeAccount, adding payInTwoInstallments(double amount).
• Define a second subclass ScholarshipFeeAccount extends FeeAccount, adding a private double
scholarshipPercent (0–100) set through its own constructor, plus a new method effectiveDue() that returns
getDue() reduced by that percentage. Do not modify FeeAccount to make this work.
• In main, create one of each of the three account types, apply payments and a scholarship, and print each
account's outstanding due — using instanceof to decide which extra behaviour to invoke for each type.
Suggested method signature(s):
class HostelFeeAccount extends FeeAccount { void payInTwoInstallments(double amount) }
class ScholarshipFeeAccount extends FeeAccount { double effectiveDue() }

Sample Input / Output:
Plain account due: Rs 0.0
Hostel account due: Rs 140000.0
Scholarship account effective due: Rs 144000.0
================================================================================
*/
// Ans F2:
class FeeAccount {
    private String regNo;
    private double totalFee;
    private double amountPaid;

    public FeeAccount(String regNo, double totalFee, double amountPaid) {
        this.regNo = regNo;
        this.totalFee = totalFee;
        this.amountPaid = amountPaid;
    }

    public void pay(double amount) {
        if (amount <= 0) {
            System.out.println("Payment rejected: amount must be positive.");
            return;
        }
        this.amountPaid += amount;
    }

    public double getDue() {
        return Math.max(0.0, totalFee - amountPaid);
    }
}

class HostelFeeAccount extends FeeAccount {
    public HostelFeeAccount(String regNo, double totalFee, double amountPaid) {
        super(regNo, totalFee, amountPaid);
    }

    public void payInTwoInstallments(double amount) {
        if (amount > 0) {
            pay(amount / 2.0);
            pay(amount / 2.0);
        }
    }
}

class ScholarshipFeeAccount extends FeeAccount {
    private double scholarshipPercent;

    public ScholarshipFeeAccount(String regNo, double totalFee, double amountPaid, double scholarshipPercent) {
        super(regNo, totalFee, amountPaid);
        this.scholarshipPercent = scholarshipPercent;
    }

    public double effectiveDue() {
        double due = getDue();
        return due - (due * (scholarshipPercent / 100.0));
    }
}

/*
================================================================================
F3. Object References, Null Safety, and a Mutating Method
Scenario: The hostel allocation service
Task:
• Define HostelRoom with fields roomNo, beds, occupied, and a method allot(String name) that fills a bed if one is
free.
• Write a static method HostelRoom findAvailableRoom(HostelRoom[] rooms) that returns the first room with
occupied < beds, or null if every room is full.
• Write a static method safeAllot(HostelRoom[] rooms, String studentName) that calls findAvailableRoom, checks
the result for null before touching it, and either allots the student or prints a clear “no rooms available” message
— with zero risk of a NullPointerException, regardless of input.
• In main, call safeAllot() twice: once against an array containing an available room, and once against an array
where every room is already full, to prove both paths behave correctly.
• In a comment, explain why passing the HostelRoom array into these methods does not copy the rooms
themselves.
Suggested method signature(s):
static HostelRoom findAvailableRoom(HostelRoom[] rooms)
static void safeAllot(HostelRoom[] rooms, String studentName)

Sample Input / Output:
Rooms: C-214 (2/3), C-507 (2/2)
safeAllot(rooms, "Divya") -> Divya allotted to room C-214
Rooms: C-214 (3/3), C-507 (2/2)
safeAllot(rooms, "Divya") -> No rooms available for Divya
================================================================================
*/
// Ans F3:
// Explanation: In Java, object variables and array elements hold references (pointers) to heap objects.
// When HostelRoom[] is passed into a method, only the array reference is copied, pointing to the exact same
// HostelRoom objects. Modifying a room via allot() mutates the original object in memory.
class HostelRoom {
    private String roomNo;
    private int beds;
    private int occupied;

    public HostelRoom(String roomNo, int beds, int occupied) {
        this.roomNo = roomNo;
        this.beds = beds;
        this.occupied = occupied;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public int getBeds() {
        return beds;
    }

    public int getOccupied() {
        return occupied;
    }

    public boolean allot(String name) {
        if (occupied < beds) {
            occupied++;
            return true;
        }
        return false;
    }
}

/*
================================================================================
F4. Designing the Instance/Static Boundary for a College-Wide System
Scenario: A junior developer's first draft of SrmStudent marks every single field static
Task:
• First reproduce the broken version: class SrmStudent { static String name; static String regNo; static int
attendance; ... } and create two students one after another.
• In a comment, explain — for each field — exactly why marking it static is wrong here.
• Redesign the class with the correct split: name, regNo, and attendance as instance fields; university and
admissionCount as static fields, following the concept guide's model.
• Add a constructor that derives regNo automatically from admissionCount, an instance method printIdCard(), and
a static method printTotalAdmissions().
• In main, run the broken version first and show how creating the second student silently overwrote the first
student's data. Then run the corrected version and show both students now keep fully independent data.
Suggested method signature(s):
void printIdCard() | static void printTotalAdmissions()

Sample Input / Output:
Broken version:
Meera
Meera
(Ravi's data was overwritten — both students now show “Meera”)

Fixed version: same two students created
Ravi | RA231100301011
Meera | RA231100301012
Students admitted so far: 2
================================================================================
*/
// Ans F4:
// Why marking these fields static is wrong:
// 1. name: Static makes the name class-wide, overwriting earlier student names upon new instantiations.
// 2. regNo: Every student must have a distinct registration number; static shares one regNo for all students.
// 3. attendance: Attendance is unique to each student; static shares an aggregate/overwritten value.
class BrokenSrmStudent {
    static String name;
    static String regNo;
    static int attendance;

    public BrokenSrmStudent(String name, String regNo, int attendance) {
        BrokenSrmStudent.name = name;
        BrokenSrmStudent.regNo = regNo;
        BrokenSrmStudent.attendance = attendance;
    }

    public void printName() {
        System.out.println(name);
    }
}

class SrmStudentProfile {
    private String name;
    private String regNo;
    private int attendance;

    private static String university = "SRM University";
    private static int admissionCount = 10;

    public SrmStudentProfile(String name, int attendance) {
        this.name = name;
        this.attendance = attendance;
        admissionCount++;
        this.regNo = "RA2311003010" + admissionCount;
    }

    public void printIdCard() {
        System.out.printf("%s | %s%n", name, regNo);
    }

    public static void printTotalAdmissions() {
        System.out.printf("Students admitted so far: %d%n", (admissionCount - 10));
    }
}

/*
================================================================================
F5. Capstone: A Small Fee + Hostel Management Mini-System
Scenario: Combine everything from this week into one small but complete mini-system
Task:
• Reuse FeeAccount and HostelFeeAccount extends FeeAccount (as in F2), and HostelRoom with the null-safe
allotment process (as in F3).
• Write a class SrmStudent that ties a student's name, regNo, a HostelFeeAccount, and a room assignment
together as fields of one object — demonstrating that an object's fields can themselves be objects.
• Add a static int totalStudents counter, incremented in the SrmStudent constructor, and an instance method
fullStatus() that prints the student's name, fee due, and room number in one line — printing “unallotted”
instead of a room number when the room reference is null.
• In main, build a small system of three students, allot rooms to only two of them (leaving the third unallotted on
purpose), process a mix of valid and rejected payments, print each student's fullStatus(), and finish by printing
SrmStudent.totalStudents.
Suggested method signature(s):
class SrmStudent { String name; String regNo; HostelFeeAccount feeAccount; HostelRoom room; }
String fullStatus()

Sample Input / Output:
Ravi | Due: Rs 140000.0 | Room: C-214
Anitha | Due: Rs 180000.0 | Room: C-507
Karthik | Due: Rs 200000.0 | Room: unallotted
Total students: 3
================================================================================
*/
// Ans F5:
class SrmStudentSystem {
    private String name;
    private String regNo;
    private HostelFeeAccount feeAccount;
    private HostelRoom room;

    public static int totalStudents = 0;

    public SrmStudentSystem(String name, String regNo, HostelFeeAccount feeAccount, HostelRoom room) {
        this.name = name;
        this.regNo = regNo;
        this.feeAccount = feeAccount;
        this.room = room;
        totalStudents++;
    }

    public String fullStatus() {
        double due = (feeAccount != null) ? feeAccount.getDue() : 0.0;
        String roomDesc = (room != null) ? room.getRoomNo() : "unallotted";
        return String.format("%s | Due: Rs %.1f | Room: %s", name, due, roomDesc);
    }
}