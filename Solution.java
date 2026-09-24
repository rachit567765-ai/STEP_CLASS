import java.util.*;

public class Solution {

    public static void main(String[] args) {
        System.out.println("========== Problem 1: Membership Field Reach Checker ==========");
        System.out.println("classifyAccess(\"private\", \"SAME_CLASS\") -> " + LibraryAccessRuleEngine.classifyAccess("private", "SAME_CLASS"));
        System.out.println("classifyAccess(\"protected\", \"DIFFERENT_PACKAGE\") -> " + LibraryAccessRuleEngine.classifyAccess("protected", "DIFFERENT_PACKAGE"));
        String[][] attempts = {
            {"private", "SAME_CLASS"},
            {"private", "SAME_PACKAGE"},
            {"default", "SAME_PACKAGE"},
            {"default", "DIFFERENT_PACKAGE"},
            {"protected", "SAME_PACKAGE"},
            {"protected", "SAME_CLASS"},
            {"public", "DIFFERENT_PACKAGE"}
        };
        System.out.println("summarizeByModifier -> \"" + LibraryAccessRuleEngine.summarizeByModifier(attempts) + "\"");
        try {
            new LibraryMember("LB9", "BR1", 0, "Priya Nair");
        } catch (IllegalArgumentException e) {
            System.out.println("new LibraryMember(\"LB9\", ...) -> construction rejected");
        }

        System.out.println("\n========== Problem 2: Reference Desk Subclass Reach ==========");
        System.out.println("classifyAccess(\"protected\", \"SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE\") -> " +
                LibraryAccessRuleEngine.classifyAccess("protected", "SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE"));
        System.out.println("classifyAccess(\"protected\", \"SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE\") -> " +
                LibraryAccessRuleEngine.classifyAccess("protected", "SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE"));
        System.out.println("describeContext(\"SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE\") -> \"" +
                LibraryAccessRuleEngine.describeContext("SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE") + "\"");

        System.out.println("\n========== Problem 3: Book Copy Circulation Guard ==========");
        try {
            new BookInventory(0);
        } catch (IllegalArgumentException e) {
            System.out.println("new BookInventory(0) -> construction rejected");
        }
        BookInventory b = new BookInventory(3);
        b.checkOut(); b.checkOut(); b.checkOut(); b.checkOut();
        System.out.println("After 4 checkouts from 3 copies -> b.getCopiesAvailable() = " + b.getCopiesAvailable());
        b.checkIn(); b.checkIn(); b.checkIn(); b.checkIn();
        System.out.println("After 4 check-ins into 3 copies -> b.getCopiesAvailable() = " + b.getCopiesAvailable());

        System.out.println("\n========== Problem 4: LibraryMember JavaBean, Chained Constructors & Security Answer ==========");
        System.out.println("new LibraryMemberProfile(\"Priya Nair\").getMembershipId() -> " + new LibraryMemberProfile("Priya Nair").getMembershipId());
        System.out.println("new LibraryMemberProfile(\"LIB-8841\", \"Priya Nair\").getMembershipId() -> \"" +
                new LibraryMemberProfile("LIB-8841", "Priya Nair").getMembershipId() + "\"");
        LibraryMemberProfile m = new LibraryMemberProfile();
        m.setMembershipId("LIB-8841");
        m.setMembershipId("FAKE-0000");
        System.out.println("After write-once test, m.getMembershipId() -> \"" + m.getMembershipId() + "\"");

        System.out.println("\n========== Problem 5: Immutable Loan Receipt & Nightly Circulation Ledger ==========");
        try {
            new LoanReceipt("LIB-8841", new String[]{"BK-100", "bad"});
        } catch (IllegalArgumentException e) {
            System.out.println("new LoanReceipt(\"LIB-8841\", {\"BK-100\", \"bad\"}) -> construction rejected");
        }
        LoanReceipt r = new LoanReceipt("LIB-8841", new String[]{"BK-100", "BK-101"});
        String[] ids = r.getBookIds();
        ids[0] = "HACKED";
        System.out.println("After ids[0] = HACKED, r.getBookIds()[0] -> \"" + r.getBookIds()[0] + "\"");

        LoanReceipt[] nightly = {
            new ReferenceOnlyLoanReceipt("LIB-001", new String[]{"BK-200"}, "Reading Room 3"),
            null,
            new LoanReceipt("LIB-002", new String[]{"BK-201"})
        };
        System.out.println("processNightlyCirculation -> \"" + LoanReceipt.processNightlyCirculation(nightly) + "\"");
    }
}

/*
================================================================================
PROBLEM 1 • Basic → Intermediate
Membership Field Reach Checker
Problem Statement:
PageTurner Library's IT team wants the same kind of pre-compile linter MediTrack built,
with results broken down by modifier instead of a flat total.
LibraryMember itself must refuse to be built with a blank or too-short membershipId.

Requirements:
• classifyAccess(...) and summarizeByModifier(...) must decide answers purely from real Java visibility rules.
• Choose the right access level for each of LibraryMember's four fields, and give it no usable no-arg constructor.
• Constructor must reject membershipId that's blank, whitespace-only, or shorter than 4 characters.

Function Signature(s):
static String classifyAccess(String fieldModifier, String accessorContext)
static String summarizeByModifier(String[][] attempts)
public LibraryMember(String membershipId, String branchCode, double finesOwed, String displayName)

Examples:
classifyAccess("private", "SAME_CLASS") -> "ALLOWED"
classifyAccess("protected", "DIFFERENT_PACKAGE") -> "DENIED"
summarizeByModifier(...) -> "private: 1 allowed / 1 denied | default: 1 allowed / 1 denied | protected: 2 allowed / 0 denied | public: 1 allowed / 0 denied"
new LibraryMember("LB9", "BR1", 0, "Priya Nair") -> construction rejected
================================================================================
*/
// Ans 1:
class LibraryAccessRuleEngine {

    public static String classifyAccess(String fieldModifier, String accessorContext) {
        if (fieldModifier == null || accessorContext == null) return "DENIED";
        String mod = fieldModifier.toLowerCase().trim();
        String ctx = accessorContext.toUpperCase().trim();

        if (mod.equals("public")) {
            return "ALLOWED";
        } else if (mod.equals("private")) {
            return ctx.equals("SAME_CLASS") ? "ALLOWED" : "DENIED";
        } else if (mod.equals("default")) {
            return (ctx.equals("SAME_CLASS") || ctx.equals("SAME_PACKAGE")) ? "ALLOWED" : "DENIED";
        } else if (mod.equals("protected")) {
            if (ctx.equals("SAME_CLASS") || ctx.equals("SAME_PACKAGE") ||
                ctx.equals("SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE")) {
                return "ALLOWED";
            } else {
                return "DENIED";
            }
        }
        return "DENIED";
    }

    public static String summarizeByModifier(String[][] attempts) {
        String[] order = {"private", "default", "protected", "public"};
        Map<String, int[]> counts = new LinkedHashMap<>();
        for (String mod : order) {
            counts.put(mod, new int[]{0, 0}); // [allowed, denied]
        }

        if (attempts != null) {
            for (String[] attempt : attempts) {
                if (attempt != null && attempt.length >= 2) {
                    String mod = attempt[0].toLowerCase().trim();
                    String ctx = attempt[1].toUpperCase().trim();
                    String result = classifyAccess(mod, ctx);
                    if (counts.containsKey(mod)) {
                        if ("ALLOWED".equals(result)) {
                            counts.get(mod)[0]++;
                        } else {
                            counts.get(mod)[1]++;
                        }
                    }
                }
            }
        }

        List<String> parts = new ArrayList<>();
        for (String mod : order) {
            int[] c = counts.get(mod);
            parts.add(String.format("%s: %d allowed / %d denied", mod, c[0], c[1]));
        }
        return String.join(" | ", parts);
    }

    public static String describeContext(String accessorContext) {
        if (accessorContext == null || accessorContext.trim().isEmpty()) {
            return "";
        }
        String[] tokens = accessorContext.trim().toLowerCase().split("_");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tokens.length; i++) {
            if (!tokens[i].isEmpty()) {
                sb.append(Character.toUpperCase(tokens[i].charAt(0)))
                  .append(tokens[i].substring(1));
                if (i < tokens.length - 1) {
                    sb.append(" ");
                }
            }
        }
        return sb.toString();
    }
}

class LibraryMember {
    private String membershipId;
    private String branchCode;
    private double finesOwed;
    private String displayName;

    public LibraryMember(String membershipId, String branchCode, double finesOwed, String displayName) {
        if (membershipId == null || membershipId.trim().length() < 4) {
            throw new IllegalArgumentException("membershipId must be at least 4 characters");
        }
        this.membershipId = membershipId.trim();
        this.branchCode = branchCode;
        this.finesOwed = finesOwed;
        this.displayName = displayName;
    }

    public String getMembershipId() { return membershipId; }
    public String getBranchCode() { return branchCode; }
    public double getFinesOwed() { return finesOwed; }
    public String getDisplayName() { return displayName; }
}

/*
================================================================================
PROBLEM 2 • Intermediate
Reference Desk Subclass Reach
(Implemented inside LibraryAccessRuleEngine above with SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE,
SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE, and describeContext)
================================================================================
*/

/*
================================================================================
PROBLEM 3 • Intermediate
Book Copy Circulation Guard
Problem Statement:
A previous version of BookInventory once let its available-copy count go negative.
Redesign it so the count can only move through validated methods.

Requirements:
• copiesTotal and copiesAvailable must both be private.
• Constructor must reject copiesTotal that's zero or negative.
• checkOut() and checkIn() must each silently reject transitions that push count out of bounds.

Function Signature(s):
BookInventory(int copiesTotal)
void checkOut()
void checkIn()
int getCopiesAvailable()

Examples:
new BookInventory(0) -> construction rejected
new BookInventory(3) -> 4 checkouts leaves 0; 4 checkins leaves 3
================================================================================
*/
// Ans 3:
class BookInventory {
    private int copiesTotal;
    private int copiesAvailable;

    public BookInventory(int copiesTotal) {
        if (copiesTotal <= 0) {
            throw new IllegalArgumentException("copiesTotal must be greater than zero");
        }
        this.copiesTotal = copiesTotal;
        this.copiesAvailable = copiesTotal;
    }

    public void checkOut() {
        if (copiesAvailable > 0) {
            copiesAvailable--;
        }
    }

    public void checkIn() {
        if (copiesAvailable < copiesTotal) {
            copiesAvailable++;
        }
    }

    public int getCopiesAvailable() {
        return copiesAvailable;
    }
}

/*
================================================================================
PROBLEM 4 • Intermediate → Advanced
LibraryMember JavaBean, Chained Constructors & Security Answer
Problem Statement:
PageTurner's kiosks use a JavaBean-scanning framework requiring a no-arg constructor,
signup also wants quicker constructors for partial data — chain all three with this(...).
Add a security-answer property that's write-only for good.

Requirements:
• Build three constructors — no-arg, name-only, and id+name — chained with this(...).
• JavaBean compliant getX/setX (isPremiumMember); setMembershipId(...) must only take effect once, ever.
• securityAnswer must be settable but never retrievable again in any form.

Function Signature(s):
public LibraryMemberProfile()
public LibraryMemberProfile(String name)
public LibraryMemberProfile(String membershipId, String name)
String getMembershipId(); void setMembershipId(String id)
boolean isPremiumMember(); void setPremiumMember(boolean premium)
void setSecurityAnswer(String answer)

Examples:
new LibraryMemberProfile("Priya Nair").getMembershipId() -> null
new LibraryMemberProfile("LIB-8841", "Priya Nair").getMembershipId() -> "LIB-8841"
setMembershipId write-once ignores second call.
================================================================================
*/
// Ans 4:
class LibraryMemberProfile {
    private String membershipId;
    private String name;
    private boolean premiumMember;
    private String storedSecurityHash;
    private boolean membershipIdSet = false;

    public LibraryMemberProfile() {
        this(null, null);
    }

    public LibraryMemberProfile(String name) {
        this(null, name);
    }

    public LibraryMemberProfile(String membershipId, String name) {
        if (membershipId != null) {
            this.membershipId = membershipId;
            this.membershipIdSet = true;
        }
        this.name = name;
        this.premiumMember = false;
    }

    public String getMembershipId() {
        return membershipId;
    }

    public void setMembershipId(String id) {
        if (!membershipIdSet && id != null) {
            this.membershipId = id;
            this.membershipIdSet = true;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPremiumMember() {
        return premiumMember;
    }

    public void setPremiumMember(boolean premium) {
        this.premiumMember = premium;
    }

    public void setSecurityAnswer(String answer) {
        if (answer != null) {
            this.storedSecurityHash = Integer.toHexString(answer.hashCode());
        }
    }
}

/*
================================================================================
PROBLEM 5 • Advanced
Immutable Loan Receipt & Nightly Circulation Ledger
Problem Statement:
Build LoanReceipt as genuinely immutable, validating each book ID's format ("BK-" + 3 digits) at construction,
reconcile a night's worth of receipts — including a reference-only variant — without crashing on null.

Requirements:
• Fields all final, class final; book IDs must match "BK-" followed by exactly 3 digits.
• Defensively copied in and out; withCorrectedBookId returns brand-new object.
• Nightly processor uses instanceof; never throws on null; static block for shared state.

Function Signature(s):
public LoanReceipt(String memberId, String[] bookIds)
String[] getBookIds()
LoanReceipt withCorrectedBookId(int index, String newId)
public ReferenceOnlyLoanReceipt(String memberId, String[] bookIds, String roomNumber)
static String processNightlyCirculation(LoanReceipt[] receipts)

Examples:
new LoanReceipt("LIB-8841", new String[]{"BK-100", "bad"}) -> construction rejected
processNightlyCirculation(...) -> "2 processed | 1 null skipped | 1 reference-only | 1 regular"
================================================================================
*/
// Ans 5:
class LoanReceipt {
    private static String ledgerName;
    static {
        ledgerName = "PAGETURNER NIGHTLY CIRCULATION LEDGER";
    }

    private final String memberId;
    private final String[] bookIds;

    public LoanReceipt(String memberId, String[] bookIds) {
        if (bookIds == null) {
            throw new IllegalArgumentException("bookIds cannot be null");
        }
        for (String id : bookIds) {
            if (id == null || !id.matches("^BK-\\d{3}$")) {
                throw new IllegalArgumentException("Invalid book ID format: " + id);
            }
        }
        this.memberId = memberId;
        this.bookIds = bookIds.clone();
    }

    public String getMemberId() {
        return memberId;
    }

    public String[] getBookIds() {
        return bookIds.clone();
    }

    public LoanReceipt withCorrectedBookId(int index, String newId) {
        if (index < 0 || index >= bookIds.length) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
        String[] newIds = bookIds.clone();
        newIds[index] = newId;
        return new LoanReceipt(this.memberId, newIds);
    }

    public static String processNightlyCirculation(LoanReceipt[] receipts) {
        int processed = 0;
        int nullSkipped = 0;
        int refOnlyCount = 0;
        int regularCount = 0;

        if (receipts != null) {
            for (LoanReceipt r : receipts) {
                if (r == null) {
                    nullSkipped++;
                    continue;
                }
                processed++;
                if (r instanceof ReferenceOnlyLoanReceipt) {
                    refOnlyCount++;
                } else {
                    regularCount++;
                }
            }
        }
        return String.format("%d processed | %d null skipped | %d reference-only | %d regular",
                processed, nullSkipped, refOnlyCount, regularCount);
    }
}

class ReferenceOnlyLoanReceipt extends LoanReceipt {
    private final String roomNumber;

    public ReferenceOnlyLoanReceipt(String memberId, String[] bookIds, String roomNumber) {
        super(memberId, bookIds);
        this.roomNumber = roomNumber;
    }

    public String getRoomNumber() {
        return roomNumber;
    }
}