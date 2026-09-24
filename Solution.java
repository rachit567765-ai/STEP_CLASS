import java.util.*;

public class Solution {

    public static void main(String[] args) {
        System.out.println("========== Problem 1: Race Entry Foundation & Batch Bib Validator ==========");
        try {
            new RaceEntry("B1", 50);
        } catch (IllegalArgumentException e) {
            System.out.println("new RaceEntry(\"B1\", 50) -> construction rejected");
        }
        RunnerEntry r = new RunnerEntry("BIB2001", 80, "Open 10K");
        r.pay(30);
        System.out.println("r.getBalanceDue() -> " + r.getBalanceDue());
        String[] batch = {"BIB1", "B1", "BIB2"};
        System.out.println("registerBatch -> \"" + RaceEntry.registerBatch(batch, 80) + "\"");

        System.out.println("\n========== Problem 2: Three Shapes of One Race Family ==========");
        RunnerEntry runner = new RunnerEntry("BIB2001", 80, "Open 10K");
        EliteRunnerEntry elite = new EliteRunnerEntry("BIB3001", 150, "Elite Full Marathon", 500);
        RelayTeamEntry relay = new RelayTeamEntry("BIB4001", 300, 4);
        System.out.println(runner.announce());
        System.out.println(elite.announce());
        System.out.println(relay.announce());
        System.out.println("classifyGeneration(elite) -> \"" + RaceEntry.classifyGeneration(elite) + "\"");
        System.out.println("classifyGeneration(relay) -> \"" + RaceEntry.classifyGeneration(relay) + "\"");
        RaceEntry[] mix = {runner, elite, relay};
        System.out.println("getTotalBalanceDue -> " + RaceEntry.getTotalBalanceDue(mix));

        System.out.println("\n========== Problem 3: The Late-Withdrawal Penalty Override & Audit Trail ==========");
        RunnerEntry rLate = new RunnerEntry("BIB2001", 80, "Open 10K");
        rLate.pay(30);
        rLate.applyLateFee(20);
        System.out.println("rLate.getBalanceDue() after doubled late fee -> " + rLate.getBalanceDue());
        double[] history = rLate.getLateFeeHistory();
        System.out.println("getLateFeeHistory() -> " + Arrays.toString(history));
        history[0] = 999;
        System.out.println("After history[0]=999, getLateFeeHistory() -> " + Arrays.toString(rLate.getLateFeeHistory()));

        System.out.println("\n========== Problem 4: The Race-Day Announcer Board ==========");
        RaceEntry[] fleet = {runner, relay};
        System.out.println("announceAll(fleet) -> \"" + RaceEntry.announceAll(fleet) + "\"");

        System.out.println("\n========== Problem 5: Race-Wide Bib Issuance, Discount Codes & Nightly Settlement Engine ==========");
        System.out.println("isValidDiscountCode(\"M123A\") -> " + RaceEntry.isValidDiscountCode("M123A"));
        System.out.println("isValidDiscountCode(\"M12A\")  -> " + RaceEntry.isValidDiscountCode("M12A"));
        System.out.println("isValidDiscountCode(\"X123A\") -> " + RaceEntry.isValidDiscountCode("X123A"));
        r.pay(10, "UPI");
        RaceEntry[] nightBatch = {elite, null, relay};
        System.out.println("settleNight -> \"" + RaceEntry.settleNight(nightBatch) + "\"");
        System.out.println("RaceEntry.getBibCounter() -> " + RaceEntry.getBibCounter());
    }
}

/*
================================================================================
PROBLEM 1 • Basic → Intermediate
Race Entry Foundation & Batch Bib Validator
Problem Statement:
The Riverside City Marathon needs its entry system built from scratch, and race-day kiosks submit bib numbers in bulk...
Design RaceEntry as the shared foundation with a validated constructor, RunnerEntry as a single-inheritance specialization,
and a batch validator that processes a whole array of registration attempts at once.

Requirements:
• RunnerEntry must extend RaceEntry directly (single inheritance) and forward shared fields via super(...).
• RaceEntry's constructor must reject a bibNumber that's blank, whitespace-only, or shorter than 4 characters.
• registerBatch(...) must attempt to construct one RaceEntry per array entry, using try/catch to count rejections.

Function Signature(s):
public RaceEntry(String bibNumber, double entryFee)
public RunnerEntry(String bibNumber, double entryFee, String category)
void pay(double amount)
double getBalanceDue()
static String registerBatch(String[] bibNumbers, double entryFee)

Examples:
new RaceEntry("B1", 50) -> construction rejected
RunnerEntry r = new RunnerEntry("BIB2001", 80, "Open 10K"); r.pay(30); r.getBalanceDue() -> 50.0
registerBatch({"BIB1", "B1", "BIB2"}, 80) -> "Registered: 2 | Rejected: 1"
================================================================================
*/
// Ans 1:
class RaceEntry {
    private static int bibCounter = 0;

    private String bibNumber;
    protected double entryFee;
    protected double amountPaid;
    protected double lateFees;
    private final String entryCode;
    private double[] lateFeeHistory;
    private int lateFeeCount;

    public RaceEntry(String bibNumber, double entryFee) {
        if (bibNumber == null || bibNumber.trim().length() < 4) {
            throw new IllegalArgumentException("bibNumber must be at least 4 non-whitespace characters");
        }
        this.bibNumber = bibNumber.trim();
        this.entryFee = entryFee;
        this.amountPaid = 0.0;
        this.lateFees = 0.0;
        this.lateFeeHistory = new double[10];
        this.lateFeeCount = 0;

        bibCounter++;
        this.entryCode = "RC-" + (1000 + bibCounter);
    }

    public String getBibNumber() {
        return bibNumber;
    }

    public String getEntryCode() {
        return entryCode;
    }

    public static int getBibCounter() {
        return bibCounter;
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
        return Math.max(0.0, (entryFee + lateFees) - amountPaid);
    }

    public String announce() {
        return String.format("Race Entry | Bib: %s | Balance: %.1f", bibNumber, getBalanceDue());
    }

    public static String registerBatch(String[] bibNumbers, double entryFee) {
        int registered = 0;
        int rejected = 0;
        if (bibNumbers != null) {
            for (String bib : bibNumbers) {
                try {
                    new RaceEntry(bib, entryFee);
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
Three Shapes of One Race Family
Function Signature(s):
public EliteRunnerEntry(String bibNumber, double entryFee, String category, double sponsorBonus)
public RelayTeamEntry(String bibNumber, double entryFee, int teamSize)
static String classifyGeneration(RaceEntry entry)
static double getTotalBalanceDue(RaceEntry[] entries)

Examples:
classifyGeneration(eliteEntry) -> "Multilevel descendant (3 generations deep)"
classifyGeneration(relayEntry) -> "Hierarchical sibling (independent branch)"
getTotalBalanceDue(...) -> 530.0
================================================================================
*/
// Ans 2:
    public static String classifyGeneration(RaceEntry entry) {
        if (entry instanceof EliteRunnerEntry) {
            return "Multilevel descendant (3 generations deep)";
        } else if (entry instanceof RelayTeamEntry) {
            return "Hierarchical sibling (independent branch)";
        } else {
            return "Single-level descendant";
        }
    }

    public static double getTotalBalanceDue(RaceEntry[] entries) {
        double sum = 0.0;
        if (entries != null) {
            for (RaceEntry e : entries) {
                if (e != null) {
                    sum += e.getBalanceDue();
                }
            }
        }
        return sum;
    }

/*
================================================================================
PROBLEM 3 • Intermediate
The Late-Withdrawal Penalty Override & Audit Trail
Function Signature(s):
protected void applyLateFee(double amount)
double[] getLateFeeHistory()

Examples:
r.applyLateFee(20) -> applies 40.0, balance becomes 90.0
getLateFeeHistory() returns defensive copy [40.0]
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
The Race-Day Announcer Board
Function Signature(s):
static String announceAll(RaceEntry[] entries)

Examples:
announceAll(fleet) -> "Runner Entry | Bib: BIB2001 | Category: Open 10K | Balance: 90.0 | Relay Team | Bib: BIB4001 | Team Size: 4 | Balance: 300.0 [Team size via downcast: 4] | "
================================================================================
*/
// Ans 4:
    public static String announceAll(RaceEntry[] entries) {
        StringBuilder sb = new StringBuilder();
        if (entries != null) {
            for (RaceEntry e : entries) {
                if (e != null) {
                    sb.append(e.announce());
                    if (e instanceof RelayTeamEntry) {
                        RelayTeamEntry rt = (RelayTeamEntry) e;
                        sb.append(String.format(" [Team size via downcast: %d]", rt.getTeamSize()));
                    }
                    sb.append(" | ");
                }
            }
        }
        return sb.toString();
    }

/*
================================================================================
PROBLEM 5 • Advanced
Race-Wide Bib Issuance, Discount Codes & Nightly Settlement Engine
Function Signature(s):
static boolean isValidDiscountCode(String code)
static String settleNight(RaceEntry[] entries)

Examples:
isValidDiscountCode("M123A") -> true
isValidDiscountCode("M12A")  -> false
isValidDiscountCode("X123A") -> false
settleNight(...) -> "2 processed | 1 null skipped | 1 relay | 1 individual"
================================================================================
*/
// Ans 5:
    public static boolean isValidDiscountCode(String code) {
        if (code == null || code.length() != 5) {
            return false;
        }
        if (code.charAt(0) != 'M') {
            return false;
        }
        for (int i = 1; i <= 3; i++) {
            if (!Character.isDigit(code.charAt(i))) {
                return false;
            }
        }
        return Character.isUpperCase(code.charAt(4));
    }

    public static String settleNight(RaceEntry[] entries) {
        int processed = 0;
        int nullSkipped = 0;
        int relayCount = 0;
        int individualCount = 0;

        if (entries != null) {
            for (RaceEntry e : entries) {
                if (e == null) {
                    nullSkipped++;
                    continue;
                }
                processed++;
                if (e instanceof RelayTeamEntry) {
                    relayCount++;
                } else {
                    individualCount++;
                }
            }
        }
        return String.format("%d processed | %d null skipped | %d relay | %d individual",
                processed, nullSkipped, relayCount, individualCount);
    }
}

class RunnerEntry extends RaceEntry {
    private String category;

    public RunnerEntry(String bibNumber, double entryFee, String category) {
        super(bibNumber, entryFee);
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public String announce() {
        return String.format("Runner Entry | Bib: %s | Category: %s | Balance: %.1f",
                getBibNumber(), category, getBalanceDue());
    }

    @Override
    protected void applyLateFee(double amount) {
        super.applyLateFee(amount * 2.0);
    }
}

class EliteRunnerEntry extends RunnerEntry {
    private double sponsorBonus;

    public EliteRunnerEntry(String bibNumber, double entryFee, String category, double sponsorBonus) {
        super(bibNumber, entryFee, category);
        this.sponsorBonus = sponsorBonus;
    }

    @Override
    public String announce() {
        return String.format("Elite Runner | Bib: %s | Category: %s | Sponsor Bonus: %.1f | Balance: %.1f",
                getBibNumber(), getCategory(), sponsorBonus, getBalanceDue());
    }
}

class RelayTeamEntry extends RaceEntry {
    private int teamSize;

    public RelayTeamEntry(String bibNumber, double entryFee, int teamSize) {
        super(bibNumber, entryFee);
        this.teamSize = teamSize;
    }

    public int getTeamSize() {
        return teamSize;
    }

    @Override
    public String announce() {
        return String.format("Relay Team | Bib: %s | Team Size: %d | Balance: %.1f",
                getBibNumber(), teamSize, getBalanceDue());
    }
}