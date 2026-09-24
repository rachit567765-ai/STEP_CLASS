import java.util.*;

public class Solution {

    public static void main(String[] args) {
        System.out.println("========== Problem 1: Ticket Hierarchy Foundation & Batch Registration Validator ==========");
        try {
            new EventTicket("ST1", 500);
        } catch (IllegalArgumentException e) {
            System.out.println("new EventTicket(\"ST1\", 500) -> construction rejected");
        }
        WorkshopTicket w = new WorkshopTicket("STU2", 1200, "AI/ML");
        w.pay(500);
        System.out.println("w.getBalanceDue() -> " + w.getBalanceDue());
        String[] batch = {"STU1", "ST1", "STU2", " ", "STU3"};
        System.out.println("registerBatch -> \"" + EventTicket.registerBatch(batch, 500) + "\"");

        System.out.println("\n========== Problem 2: Three Shapes of One Family Tree ==========");
        EventTicket std = new EventTicket("STU1", 500);
        WorkshopTicket ws = new WorkshopTicket("STU2", 1200, "AI/ML");
        PremiumWorkshopTicket prem = new PremiumWorkshopTicket("STU3", 2000, "Cloud Native", 300);
        HackathonTicket hack = new HackathonTicket("STU4", 800, "Byte Force");

        System.out.println(std.printTicket());
        System.out.println(ws.printTicket());
        System.out.println(prem.printTicket());
        System.out.println(hack.printTicket());

        System.out.println("classifyGeneration(prem) -> \"" + EventTicket.classifyGeneration(prem) + "\"");
        System.out.println("classifyGeneration(hack) -> \"" + EventTicket.classifyGeneration(hack) + "\"");

        EventTicket[] allTickets = {std, ws, prem, hack};
        System.out.println("getTotalBalanceDue -> " + EventTicket.getTotalBalanceDue(allTickets));

        System.out.println("\n========== Problem 3: The Late-Registration Penalty Override & Audit Trail ==========");
        WorkshopTicket wLate = new WorkshopTicket("STU2", 1200, "AI/ML");
        wLate.pay(1200);
        wLate.applyLateFee(100);
        System.out.println("wLate.getBalanceDue() after doubled late fee -> " + wLate.getBalanceDue());
        double[] history = wLate.getLateFeeHistory();
        System.out.println("getLateFeeHistory() -> " + Arrays.toString(history));
        history[0] = 999;
        System.out.println("After history[0]=999, getLateFeeHistory() -> " + Arrays.toString(wLate.getLateFeeHistory()));

        System.out.println("\n========== Problem 4: The Nightly Ticket Announcer ==========");
        EventTicket[] fleet = {new EventTicket("STU1", 500), new WorkshopTicket("STU2", 1200, "AI/ML")};
        System.out.println("batchPrint(fleet) -> \"" + EventTicket.batchPrint(fleet) + "\"");

        System.out.println("\n========== Problem 5: Fest-Wide Ticket Issuance, Promo Codes & Nightly Settlement Engine ==========");
        EventTicket t1 = new EventTicket(500);
        System.out.println("t1.ticketId -> \"" + t1.getTicketId() + "\"");
        System.out.println("EventTicket.getTicketsIssued() -> " + EventTicket.getTicketsIssued());

        System.out.println("isValidPromoCode(\"F123A\") -> " + EventTicket.isValidPromoCode("F123A"));
        System.out.println("isValidPromoCode(\"F12A\")  -> " + EventTicket.isValidPromoCode("F12A"));
        System.out.println("isValidPromoCode(\"X123A\") -> " + EventTicket.isValidPromoCode("X123A"));

        t1.pay(200);
        t1.pay(200, "UPI");
        System.out.println("t1.getBalanceDue() -> " + t1.getBalanceDue());

        EventTicket[] nightBatch = {new GroupTicket(2000, 5), null, new EventTicket(500)};
        System.out.println("processNightlySettlement -> \"" + EventTicket.processNightlySettlement(nightBatch) + "\"");
    }
}

/*
================================================================================
PROBLEM 1 • Basic → Intermediate
Ticket Hierarchy Foundation & Batch Registration Validator
Problem Statement:
CineHub's tech fest needs its ticket system built from the ground up, and registration desks submit attendee IDs in bulk...
Design EventTicket as the shared foundation with a validated constructor, WorkshopTicket as a single-inheritance specialization,
and a batch validator that processes a whole array of registration attempts at once.

Requirements:
• WorkshopTicket must extend EventTicket directly (single inheritance) and forward shared fields via super(...).
• EventTicket's constructor must reject an attendeeId that's blank, whitespace-only, or shorter than 4 characters.
• registerBatch(...) must attempt to construct one EventTicket per array entry, using try/catch to count rejections.

Function Signature(s):
public EventTicket(String attendeeId, double basePrice)
public WorkshopTicket(String attendeeId, double basePrice, String track)
void pay(double amount)
double getBalanceDue()
static String registerBatch(String[] attendeeIds, double basePrice)

Examples:
new EventTicket("ST1", 500) -> construction rejected
WorkshopTicket w = new WorkshopTicket("STU2", 1200, "AI/ML"); w.pay(500); w.getBalanceDue() -> 700.0
registerBatch({"STU1", "ST1", "STU2", " ", "STU3"}, 500) -> "Registered: 3 | Rejected: 2"
================================================================================
*/
// Ans 1:
class EventTicket {
    private static int ticketCounter = 0;

    private String attendeeId;
    protected double basePrice;
    protected double amountPaid;
    protected double lateFees;
    private final String ticketId;
    private double[] lateFeeHistory;
    private int lateFeeCount;

    public EventTicket(String attendeeId, double basePrice) {
        if (attendeeId == null || attendeeId.trim().length() < 4) {
            throw new IllegalArgumentException("attendeeId must be at least 4 characters and not blank");
        }
        this.attendeeId = attendeeId.trim();
        this.basePrice = basePrice;
        this.amountPaid = 0.0;
        this.lateFees = 0.0;
        this.lateFeeHistory = new double[10];
        this.lateFeeCount = 0;

        ticketCounter++;
        this.ticketId = "TCK-" + (1000 + ticketCounter);
    }

    // Overload for Problem 5
    public EventTicket(double basePrice) {
        this.attendeeId = "GENERIC";
        this.basePrice = basePrice;
        this.amountPaid = 0.0;
        this.lateFees = 0.0;
        this.lateFeeHistory = new double[10];
        this.lateFeeCount = 0;

        ticketCounter++;
        this.ticketId = "TCK-" + (1000 + ticketCounter);
    }

    public String getAttendeeId() {
        return attendeeId;
    }

    public String getTicketId() {
        return ticketId;
    }

    public static int getTicketsIssued() {
        return ticketCounter;
    }

    public void pay(double amount) {
        if (amount > 0) {
            this.amountPaid += amount;
        }
    }

    public void pay(double amount, String mode) {
        System.out.println("Paying via " + mode);
        pay(amount);
    }

    public double getBalanceDue() {
        return Math.max(0.0, (basePrice + lateFees) - amountPaid);
    }

    public String printTicket() {
        return String.format("Standard Event Ticket | Balance Due: %.1f", getBalanceDue());
    }

    public static String registerBatch(String[] attendeeIds, double basePrice) {
        int registered = 0;
        int rejected = 0;
        if (attendeeIds != null) {
            for (String id : attendeeIds) {
                try {
                    new EventTicket(id, basePrice);
                    registered++;
                } catch (IllegalArgumentException e) {
                    rejected++;
                }
            }
        }
        return String.format("Registered: %d | Rejected: %d", registered, rejected);
    }

/*
================================================================================
PROBLEM 2 • Intermediate
Three Shapes of One Family Tree
Function Signature(s):
public PremiumWorkshopTicket(String attendeeId, double basePrice, String track, double kitFee)
public HackathonTicket(String attendeeId, double basePrice, String teamName)
static String classifyGeneration(EventTicket ticket)
static double getTotalBalanceDue(EventTicket[] tickets)

Examples:
classifyGeneration(premiumTicket) -> "Multilevel descendant (3 generations deep)"
classifyGeneration(hackathonTicket) -> "Hierarchical sibling (independent branch)"
getTotalBalanceDue(...) -> 4500.0
================================================================================
*/
// Ans 2:
    public static String classifyGeneration(EventTicket ticket) {
        if (ticket instanceof PremiumWorkshopTicket) {
            return "Multilevel descendant (3 generations deep)";
        } else if (ticket instanceof HackathonTicket) {
            return "Hierarchical sibling (independent branch)";
        } else {
            return "Single-level descendant";
        }
    }

    public static double getTotalBalanceDue(EventTicket[] tickets) {
        double sum = 0.0;
        if (tickets != null) {
            for (EventTicket t : tickets) {
                if (t != null) {
                    sum += t.getBalanceDue();
                }
            }
        }
        return sum;
    }

/*
================================================================================
PROBLEM 3 • Intermediate
The Late-Registration Penalty Override & Audit Trail
Function Signature(s):
protected void applyLateFee(double amount)
double[] getLateFeeHistory()

Examples:
WorkshopTicket w = new WorkshopTicket(1200); w.pay(1200); w.applyLateFee(100); w.getBalanceDue() -> 200.0
getLateFeeHistory() -> defensive copy [200.0]
================================================================================
*/
// Ans 3:
    protected void applyLateFee(double amount) {
        if (amount > 0) {
            this.lateFees += amount;
            if (lateFeeCount < lateFeeHistory.length) {
                lateFeeHistory[lateFeeCount++] = amount;
            }
        }
    }

    public double[] getLateFeeHistory() {
        double[] copy = new double[lateFeeCount];
        System.arraycopy(lateFeeHistory, 0, copy, 0, lateFeeCount);
        return copy;
    }

/*
================================================================================
PROBLEM 4 • Intermediate → Advanced
The Nightly Ticket Announcer
Function Signature(s):
static String batchPrint(EventTicket[] tickets)

Examples:
batchPrint(...) -> "Standard | Balance: 500.0 | Workshop | Track: AI/ML | Balance: 1200.0 [Track via downcast: AI/ML] | "
================================================================================
*/
// Ans 4:
    public static String batchPrint(EventTicket[] tickets) {
        StringBuilder sb = new StringBuilder();
        if (tickets != null) {
            for (EventTicket t : tickets) {
                if (t != null) {
                    if (t instanceof WorkshopTicket) {
                        WorkshopTicket wt = (WorkshopTicket) t;
                        sb.append(String.format("Workshop | Track: %s | Balance: %.1f [Track via downcast: %s] | ",
                                wt.getTrack(), wt.getBalanceDue(), wt.getTrack()));
                    } else {
                        sb.append(String.format("Standard | Balance: %.1f | ", t.getBalanceDue()));
                    }
                }
            }
        }
        return sb.toString();
    }

/*
================================================================================
PROBLEM 5 • Advanced
Fest-Wide Ticket Issuance, Promo Codes & Nightly Settlement Engine
Function Signature(s):
static boolean isValidPromoCode(String code)
static String processNightlySettlement(EventTicket[] tickets)

Examples:
isValidPromoCode("F123A") -> true
isValidPromoCode("F12A")  -> false
isValidPromoCode("X123A") -> false
processNightlySettlement(...) -> "2 processed | 1 null skipped | 1 group | 1 individual"
================================================================================
*/
// Ans 5:
    public static boolean isValidPromoCode(String code) {
        if (code == null || code.length() != 5) {
            return false;
        }
        if (code.charAt(0) != 'F') {
            return false;
        }
        for (int i = 1; i <= 3; i++) {
            if (!Character.isDigit(code.charAt(i))) {
                return false;
            }
        }
        return Character.isUpperCase(code.charAt(4));
    }

    public static String processNightlySettlement(EventTicket[] tickets) {
        int processed = 0;
        int nullSkipped = 0;
        int groupCount = 0;
        int individualCount = 0;

        if (tickets != null) {
            for (EventTicket t : tickets) {
                if (t == null) {
                    nullSkipped++;
                    continue;
                }
                processed++;
                if (t instanceof GroupTicket) {
                    groupCount++;
                } else {
                    individualCount++;
                }
            }
        }
        return String.format("%d processed | %d null skipped | %d group | %d individual",
                processed, nullSkipped, groupCount, individualCount);
    }
}

class WorkshopTicket extends EventTicket {
    private String track;

    public WorkshopTicket(String attendeeId, double basePrice, String track) {
        super(attendeeId, basePrice);
        this.track = track;
    }

    public WorkshopTicket(double basePrice, String track) {
        super(basePrice);
        this.track = track;
    }

    public String getTrack() {
        return track;
    }

    @Override
    public String printTicket() {
        return String.format("Workshop Ticket | Track: %s | Balance Due: %.1f", track, getBalanceDue());
    }

    @Override
    protected void applyLateFee(double amount) {
        super.applyLateFee(amount * 2.0);
    }
}

class PremiumWorkshopTicket extends WorkshopTicket {
    private double kitFee;

    public PremiumWorkshopTicket(String attendeeId, double basePrice, String track, double kitFee) {
        super(attendeeId, basePrice + kitFee, track);
        this.kitFee = kitFee;
    }

    @Override
    public String printTicket() {
        return String.format("Premium Workshop Ticket | Track: %s | Kit Fee: %.1f | Balance Due: %.1f",
                getTrack(), kitFee, getBalanceDue());
    }
}

class HackathonTicket extends EventTicket {
    private String teamName;

    public HackathonTicket(String attendeeId, double basePrice, String teamName) {
        super(attendeeId, basePrice);
        this.teamName = teamName;
    }

    @Override
    public String printTicket() {
        return String.format("Hackathon Ticket | Team: %s | Balance Due: %.1f", teamName, getBalanceDue());
    }
}

class GroupTicket extends EventTicket {
    private int groupSize;

    public GroupTicket(double basePrice, int groupSize) {
        super(basePrice);
        this.groupSize = groupSize;
    }
}