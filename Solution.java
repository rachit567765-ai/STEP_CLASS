import java.util.*;

public class Solution {

    public static void main(String[] args) {
        System.out.println("========== Problem 1: Bus Ticket Booking Validator ==========");
        String[][] rawBookings = {
            {"Divya", "Chennai"},
            {"", "Bangalore"},
            {"Ravi123", "Pune"},
            {"Divya", "Chennai"},
            {" ", " "}
        };
        BusTicket.processBatch(rawBookings);

        System.out.println("\n========== Problem 2: Remainder-Fair FareSplitter ==========");
        FareSplitter fs1 = new FareSplitter("TRIP001", 100000, 3);
        System.out.println("new FareSplitter(\"TRIP001\", 100000, 3).fareBreakdown() -> " + Arrays.toString(fs1.fareBreakdown()));
        FareSplitter fs2 = new FareSplitter("TRIP003");
        System.out.println("new FareSplitter(\"TRIP003\").fareBreakdown() -> " + Arrays.toString(fs2.fareBreakdown()));

        System.out.println("\n========== Problem 3: Bus Route Ranking Engine ==========");
        BusRoute[] routes = {
            new BusRoute("RT205L", "Airport Express", 3),
            new BusRoute("rt201j", "City Central", 4),
            new BusRoute("RT299T", "Night Service")
        };
        BusRoute[] ranked = BusRoute.rankRoutes(routes);
        System.out.print("[");
        for (int i = 0; i < ranked.length; i++) {
            System.out.print("\"" + ranked[i].getRouteCode() + "\"" + (i < ranked.length - 1 ? ", " : ""));
        }
        System.out.println("]");

        System.out.println("\n========== Problem 4: Tiered Boarding Penalty Calculator ==========");
        BoardingPenaltyCalculator bpc = new BoardingPenaltyCalculator(1.0); // 1% floor
        System.out.printf("ticketFare = 1000, minutesLate = 0  -> Rs %.1f%n", bpc.calculatePenalty(1000, 0));
        System.out.printf("ticketFare = 1000, minutesLate = 1  -> Rs %.1f%n", bpc.calculatePenalty(1000, 1));
        System.out.printf("ticketFare = 1000, minutesLate = 16 -> Rs %.1f%n", bpc.calculatePenalty(1000, 16));

        System.out.println("\n========== Problem 5: Nightly Fleet Reconciliation Engine ==========");
        BusTicketAccount[] accounts = {
            new SleeperBusTicketAccount("BK001", 2000),
            null,
            new BusTicketAccount("BK002", 1200)
        };
        double[] amounts = {1200, 900, 700};
        int[] minutesLateArray = {10, 5, 0};
        BusTicketAccount.processBatch(accounts, amounts, minutesLateArray);
    }
}

/*
================================================================================
PROBLEM 1 • Basic → Intermediate
Bus Ticket Booking Validator
Problem Statement:
An online bus booking platform ingests booking attempts from several channels...
Design BusTicket so an invalid booking can never be constructed in the first place,
then write a batch processor that reads a list of raw {passengerName, destination} attempts
and classifies each one as accepted, rejected, or a duplicate of an already-accepted pair.
A ticket, once marked as checked in, should not silently accept being checked in a second time.

Requirements:
• BusTicket must have no usable no-argument constructor — only a parameterized one.
• Invalid input must fail at construction time, not be discovered afterward.
• The same passengerName + destination pair must never be counted as a valid booking more than once.

Function Signature(s):
public BusTicket(String passengerName, String destination)
void markCheckedIn()
static void processBatch(String[][] rawBookings)

Examples:
Input: {{"Divya","Chennai"}, {"","Bangalore"}, {"Ravi123","Pune"}, {"Divya","Chennai"}, {" "," "}}
Output: Valid: 1 | Rejected: 3 | Duplicates skipped: 1
================================================================================
*/
// Ans 1:
class BusTicket {
    private String passengerName;
    private String destination;
    private boolean isCheckedIn;

    public BusTicket(String passengerName, String destination) {
        if (passengerName == null || destination == null) {
            throw new IllegalArgumentException("Name and destination cannot be null");
        }
        String p = passengerName.trim();
        String d = destination.trim();
        if (p.isEmpty() || d.isEmpty()) {
            throw new IllegalArgumentException("Name and destination cannot be empty or blank");
        }
        // Validate name contains only alphabetic characters / spaces
        for (int i = 0; i < p.length(); i++) {
            char ch = p.charAt(i);
            if (!Character.isLetter(ch) && ch != ' ') {
                throw new IllegalArgumentException("Invalid character in passenger name");
            }
        }
        this.passengerName = p;
        this.destination = d;
        this.isCheckedIn = false;
    }

    public void markCheckedIn() {
        if (this.isCheckedIn) {
            System.out.println("Warning: Ticket already checked in.");
        } else {
            this.isCheckedIn = true;
        }
    }

    public static void processBatch(String[][] rawBookings) {
        int valid = 0;
        int rejected = 0;
        int duplicates = 0;
        Set<String> acceptedPairs = new HashSet<>();

        if (rawBookings != null) {
            for (String[] booking : rawBookings) {
                if (booking == null || booking.length < 2) {
                    rejected++;
                    continue;
                }
                try {
                    BusTicket ticket = new BusTicket(booking[0], booking[1]);
                    String key = ticket.passengerName.toLowerCase() + "::" + ticket.destination.toLowerCase();
                    if (acceptedPairs.contains(key)) {
                        duplicates++;
                    } else {
                        acceptedPairs.add(key);
                        valid++;
                    }
                } catch (IllegalArgumentException e) {
                    rejected++;
                }
            }
        }
        System.out.printf("Valid: %d | Rejected: %d | Duplicates skipped: %d%n", valid, rejected, duplicates);
    }
}

/*
================================================================================
PROBLEM 2 • Intermediate
Remainder-Fair FareSplitter
Problem Statement:
A group-travel booking feature needs a FareSplitter class that can be created three different ways...
Your fareBreakdown() must never lose money to rounding, no matter how unevenly the fare divides.

Requirements:
• The three constructors must be linked through this(...) chaining.
• A negative fare, or zero/negative passenger count, must be rejected at construction.
• fareBreakdown() must sum to exactly the original fare.

Function Signature(s):
public FareSplitter(String tripId, double totalFare, int passengerCount)
public FareSplitter(String tripId, double totalFare)
public FareSplitter(String tripId)
double[] fareBreakdown()
boolean isConfirmationOverdue(int confirmed, int expected)

Examples:
new FareSplitter("TRIP001", 100000, 3).fareBreakdown() -> [33333.33, 33333.33, 33333.34]
new FareSplitter("TRIP003").fareBreakdown() -> [0.0, 0.0]
================================================================================
*/
// Ans 2:
class FareSplitter {
    private String tripId;
    private double totalFare;
    private int passengerCount;

    public FareSplitter(String tripId, double totalFare, int passengerCount) {
        if (totalFare < 0) {
            throw new IllegalArgumentException("Total fare cannot be negative");
        }
        if (passengerCount <= 0) {
            throw new IllegalArgumentException("Passenger count must be positive");
        }
        this.tripId = tripId;
        this.totalFare = totalFare;
        this.passengerCount = passengerCount;
    }

    public FareSplitter(String tripId, double totalFare) {
        this(tripId, totalFare, 2);
    }

    public FareSplitter(String tripId) {
        this(tripId, 0.0, 2);
    }

    public double[] fareBreakdown() {
        if (totalFare == 0.0) {
            double[] breakdown = new double[passengerCount];
            Arrays.fill(breakdown, 0.0);
            return breakdown;
        }

        long totalPaisa = Math.round(totalFare * 100.0);
        long baseShare = totalPaisa / passengerCount;
        long remainder = totalPaisa % passengerCount;

        double[] breakdown = new double[passengerCount];
        for (int i = 0; i < passengerCount; i++) {
            long currentPaisa = baseShare;
            // Leftover paisa distributed to the last shares consistently
            if (i >= passengerCount - remainder) {
                currentPaisa += 1;
            }
            breakdown[i] = currentPaisa / 100.0;
        }
        return breakdown;
    }

    public boolean isConfirmationOverdue(int confirmed, int expected) {
        return confirmed < expected;
    }
}

/*
================================================================================
PROBLEM 3 • Intermediate
Bus Route Ranking Engine
Problem Statement:
The route-planning team needs a way to rank a depot's bus routes for a priority-dispatch report...
Encode a specific tie-breaking order without altering how a route's code is stored or displayed.

Requirements:
• Resolve field/parameter naming clash with this, offer a second constructor that chains to the first with a default priority.
• compareTo must return a signed result.
• Implement the sort yourself; do not call a built-in sort utility.

Function Signature(s):
public BusRoute(String routeCode, String routeName, int priority)
public BusRoute(String routeCode, String routeName)
int compareTo(BusRoute other)
static BusRoute[] rankRoutes(BusRoute[] routes)

Examples:
Input: [BusRoute("RT205L","Airport Express",3), BusRoute("rt201j","City Central",4), BusRoute("RT299T","Night Service")]
Output: ["rt201j", "RT205L", "RT299T"]
================================================================================
*/
// Ans 3:
class BusRoute implements Comparable<BusRoute> {
    private String routeCode;
    private String routeName;
    private int priority;

    public BusRoute(String routeCode, String routeName, int priority) {
        this.routeCode = routeCode;
        this.routeName = routeName;
        this.priority = priority;
    }

    public BusRoute(String routeCode, String routeName) {
        this(routeCode, routeName, 1);
    }

    public String getRouteCode() {
        return routeCode;
    }

    @Override
    public int compareTo(BusRoute other) {
        // 1. Higher priority first
        if (this.priority != other.priority) {
            return Integer.compare(other.priority, this.priority);
        }
        // 2. Tie break: case-insensitive route code
        int codeCmp = this.routeCode.compareToIgnoreCase(other.routeCode);
        if (codeCmp != 0) {
            return codeCmp;
        }
        // 3. Tie break: route name length
        return Integer.compare(this.routeName.length(), other.routeName.length());
    }

    public static BusRoute[] rankRoutes(BusRoute[] routes) {
        if (routes == null) return new BusRoute[0];
        BusRoute[] sorted = routes.clone();
        // Stable insertion sort
        for (int i = 1; i < sorted.length; i++) {
            BusRoute key = sorted[i];
            int j = i - 1;
            while (j >= 0 && sorted[j].compareTo(key) > 0) {
                sorted[j + 1] = sorted[j];
                j--;
            }
            sorted[j + 1] = key;
        }
        return sorted;
    }
}

/*
================================================================================
PROBLEM 4 • Intermediate → Advanced
Tiered Boarding Penalty Calculator
Problem Statement:
The depot wants to replace its flat late-boarding penalty with a fairer, escalating system...
Rates: 0.5% per minute for minutes 1–5, 1% per minute for minutes 6–15, and 2% per minute from minute 16 onward.
Configured rate acts as minimum flat-fee floor, which only applies once a passenger is genuinely late.

Requirements:
• The calculation rule, the field it depends on, and the class itself must each be locked against modification (final).
• Negative ticketFare or minutesLate must be rejected at calculation point.

Function Signature(s):
public BoardingPenaltyCalculator(double minimumPenaltyPercent)
final double calculatePenalty(double ticketFare, int minutesLate)

Examples:
ticketFare = 1000, minutesLate = 0 -> Rs 0.0
ticketFare = 1000, minutesLate = 1 -> Rs 10.0
ticketFare = 1000, minutesLate = 16 -> Rs 145.0
================================================================================
*/
// Ans 4:
final class BoardingPenaltyCalculator {
    private final double minimumPenaltyPercent;

    public BoardingPenaltyCalculator(double minimumPenaltyPercent) {
        this.minimumPenaltyPercent = minimumPenaltyPercent;
    }

    public final double calculatePenalty(double ticketFare, int minutesLate) {
        if (ticketFare < 0 || minutesLate < 0) {
            throw new IllegalArgumentException("Fare and minutes late must be non-negative");
        }
        if (minutesLate == 0) {
            return 0.0;
        }

        double tieredAmount = 0.0;
        // Bracket 1: Minutes 1-5 @ 0.5%
        int b1 = Math.min(minutesLate, 5);
        tieredAmount += b1 * (0.005 * ticketFare);

        // Bracket 2: Minutes 6-15 @ 1.0%
        if (minutesLate > 5) {
            int b2 = Math.min(minutesLate - 5, 10);
            tieredAmount += b2 * (0.010 * ticketFare);
        }

        // Bracket 3: Minutes 16+ @ 2.0%
        if (minutesLate > 15) {
            int b3 = minutesLate - 15;
            tieredAmount += b3 * (0.020 * ticketFare);
        }

        double floor = (minimumPenaltyPercent / 100.0) * ticketFare;
        return Math.max(tieredAmount, floor);
    }
}

/*
================================================================================
PROBLEM 5 • Advanced
Nightly Fleet Reconciliation Engine
Problem Statement:
The depot's nightly reconciliation job processes every ticket account for the day's fleet...
Mix of real accounts, sleeper-coach accounts, and outright nulls.

Requirements:
• BusTicketAccount must set up one-time class-level state using a static block, support full & provisional constructors.
• The processor must use instanceof, and must never throw an uncaught exception on null.
• Penalty calculation must be final.

Function Signature(s):
public BusTicketAccount(String bookingId, double ticketFare)
public BusTicketAccount(String bookingId)
final double calculatePenalty(int minutesLate)
void processAccount(BusTicketAccount account, double amount, int minutesLate)
static void processBatch(BusTicketAccount[] accounts, double[] amounts, int[] minutesLateArray)

Examples:
Input:
accounts = {Sleeper("BK001",2000), null, BusTicketAccount("BK002",1200)}
amounts = {1200, 900, 700}
minutesLateArray = {10, 5, 0}
Output:
2 processed | 1 null skipped | 1 sleeper | 1 regular | grand total penalties = ...
================================================================================
*/
// Ans 5:
class BusTicketAccount {
    private static double standardPenaltyRate;
    static {
        standardPenaltyRate = 10.0; // base penalty rate per 5-min block
    }

    private String bookingId;
    private double ticketFare;

    public BusTicketAccount(String bookingId, double ticketFare) {
        this.bookingId = bookingId;
        this.ticketFare = ticketFare;
    }

    public BusTicketAccount(String bookingId) {
        this(bookingId, 0.0);
    }

    public final double calculatePenalty(int minutesLate) {
        if (minutesLate <= 0) return 0.0;
        return (minutesLate / 5.0) * standardPenaltyRate;
    }

    public static void processBatch(BusTicketAccount[] accounts, double[] amounts, int[] minutesLateArray) {
        int processed = 0;
        int nullSkipped = 0;
        int sleeperCount = 0;
        int regularCount = 0;
        double grandTotalPenalties = 0.0;

        if (accounts != null) {
            int len = accounts.length;
            for (int i = 0; i < len; i++) {
                BusTicketAccount acc = accounts[i];
                if (acc == null) {
                    nullSkipped++;
                    continue;
                }
                processed++;
                if (acc instanceof SleeperBusTicketAccount) {
                    sleeperCount++;
                } else {
                    regularCount++;
                }
                int late = (minutesLateArray != null && i < minutesLateArray.length) ? minutesLateArray[i] : 0;
                grandTotalPenalties += acc.calculatePenalty(late);
            }
        }
        System.out.printf("%d processed | %d null skipped | %d sleeper | %d regular | grand total penalties = Rs %.1f%n",
                processed, nullSkipped, sleeperCount, regularCount, grandTotalPenalties);
    }
}

class SleeperBusTicketAccount extends BusTicketAccount {
    public SleeperBusTicketAccount(String bookingId, double ticketFare) {
        super(bookingId, ticketFare);
    }
}