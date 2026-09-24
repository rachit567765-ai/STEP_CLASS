// Broken Version: Static fields misuse
class BrokenLibraryMember {
    // WHY MARKING THESE FIELDS STATIC IS WRONG:
    // 1. `name`: Static fields belong to the class, not individual objects. Storing a person's name statically
    //    means all instances share the single name variable, causing subsequent members to overwrite preceding ones.
    // 2. `memberId`: An ID uniquely identifies a single member. A static memberId forces every member to share the exact same ID.
    // 3. `booksIssued`: Each borrower tracks their own count of borrowed books. A static counter aggregates/overwrites
    //    everyone's balance into one shared variable.
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

// Fixed Version: Proper instance and static separation
class LibraryMember {
    // Instance fields (unique per member)
    private String name;
    private String memberId;
    private int booksIssued;

    // Static fields (shared across all members)
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

public class LibraryMembershipSystem {

    public static void main(String[] args) {
        System.out.println("=== Week 3 Problem F4: Designing the Instance/Static Boundary ===");

        System.out.println("--- Broken Version ---");
        BrokenLibraryMember m1 = new BrokenLibraryMember("Aditi", "LM-1001", 2);
        BrokenLibraryMember m2 = new BrokenLibraryMember("Rohan", "LM-1002", 1);
        m1.printName();
        m2.printName();
        System.out.println("(Aditi's data was overwritten — both members now show \"Rohan\")\n");

        System.out.println("--- Fixed Version ---");
        LibraryMember fixed1 = new LibraryMember("Aditi", 2);
        LibraryMember fixed2 = new LibraryMember("Rohan", 1);
        fixed1.printMemberCard();
        fixed2.printMemberCard();
        LibraryMember.printTotalMembers();
    }
}