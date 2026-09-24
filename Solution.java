import java.util.*;

public class Solution {

    public static void main(String[] args) {
        System.out.println("========== Problem 1: Ghost Order Validator ==========");
        String[][] rawOrders = {
            {"Ravi", "Paneer Butter Masala"},
            {"", "Chole Bhature"},
            {"Meera", " "},
            {"Divya", "Veg Biryani"}
        };
        FoodOrder.processBatch(rawOrders);

        System.out.println("\n========== Problem 2: ASAP or Scheduled Delivery Slot Booking ==========");
        DeliverySlot slot1 = new DeliverySlot("ORD101", "13:00-14:00");
        System.out.println("new DeliverySlot(\"ORD101\", \"13:00-14:00\").isPeakHour() -> " + slot1.isPeakHour());
        DeliverySlot slot2 = new DeliverySlot("ORD102");
        System.out.println("new DeliverySlot(\"ORD102\").isPeakHour() -> " + slot2.isPeakHour());

        System.out.println("\n========== Problem 3: Canteen Trust-Score Ranking Engine ==========");
        Canteen[] canteens = {
            new Canteen("HB3-C", "Spice Junction", 3),
            new Canteen("hb1-c", "Grand Mess", 5),
            new Canteen("HB2-C", "Southern Treats")
        };
        Canteen[] ranked = Canteen.rankCanteens(canteens);
        System.out.print("[");
        for (int i = 0; i < ranked.length; i++) {
            System.out.print("\"" + ranked[i].getCanteenCode() + "\"" + (i < ranked.length - 1 ? ", " : ""));
        }
        System.out.println("]");

        System.out.println("\n========== Problem 4: Exam-Week Surge Fee Calculator ==========");
        SurgeFeeCalculator sfc = new SurgeFeeCalculator(1.0);
        System.out.printf("orderValue = 500, delayMinutes = 0  -> Rs %.1f%n", sfc.calculateSurgeFee(500, 0));
        System.out.printf("orderValue = 500, delayMinutes = 1  -> Rs %.1f%n", sfc.calculateSurgeFee(500, 1));
        System.out.printf("orderValue = 500, delayMinutes = 16 -> Rs %.1f%n", sfc.calculateSurgeFee(500, 16));

        System.out.println("\n========== Problem 5: Nightly Multi-Kitchen Reconciliation Engine ==========");
        DeliveryAccount[] accounts = {
            new PremiumDeliveryAccount("STU001", 500),
            null,
            new DeliveryAccount("STU002", 300)
        };
        double[] amounts = {500, 400, 300};
        int[] delayMinutesArray = {10, 5, 0};
        DeliveryAccount.processBatch(accounts, amounts, delayMinutesArray);
    }
}

/*
================================================================================
PROBLEM 1 • Basic
Ghost Order Validator
Problem Statement:
A campus food delivery app aggregates orders from every hostel-block canteen...
Design FoodOrder so an invalid order can never be constructed in the first place,
then write a batch processor that reads a list of raw {studentName, dishName} attempts
and reports how many were accepted and how many were rejected.
An order that's already been marked delivered should not silently accept being marked delivered a second time.

Requirements:
• FoodOrder must have no usable no-argument constructor — only a parameterized one that validates both fields.
• A blank, null, or whitespace-only studentName or dishName must be rejected at construction time.
• markDelivered() must print a different message the second time it's called on the same order.

Function Signature(s):
public FoodOrder(String studentName, String dishName)
void markDelivered()
static void processBatch(String[][] rawOrders)

Examples:
Input: {{"Ravi","Paneer Butter Masala"}, {"","Chole Bhature"}, {"Meera"," "}, {"Divya","Veg Biryani"}}
Output: Valid: 2 | Rejected: 2
================================================================================
*/
// Ans 1:
class FoodOrder {
    private String studentName;
    private String dishName;
    private boolean isDelivered;

    public FoodOrder(String studentName, String dishName) {
        if (studentName == null || dishName == null) {
            throw new IllegalArgumentException("Student name and dish name cannot be null");
        }
        String s = studentName.trim();
        String d = dishName.trim();
        if (s.isEmpty() || d.isEmpty()) {
            throw new IllegalArgumentException("Student name and dish name cannot be blank");
        }
        this.studentName = s;
        this.dishName = d;
        this.isDelivered = false;
    }

    public void markDelivered() {
        if (this.isDelivered) {
            System.out.println("Warning: Order has already been delivered!");
        } else {
            this.isDelivered = true;
            System.out.println("Order delivered successfully.");
        }
    }

    public static void processBatch(String[][] rawOrders) {
        int valid = 0;
        int rejected = 0;

        if (rawOrders != null) {
            for (String[] order : rawOrders) {
                if (order == null || order.length < 2) {
                    rejected++;
                    continue;
                }
                try {
                    new FoodOrder(order[0], order[1]);
                    valid++;
                } catch (IllegalArgumentException e) {
                    rejected++;
                }
            }
        }
        System.out.printf("Valid: %d | Rejected: %d%n", valid, rejected);
    }
}

/*
================================================================================
PROBLEM 2 • Basic
ASAP or Scheduled — Delivery Slot Booking
Problem Statement:
When a student places an order, they can either pick a specific delivery time slot,
or just tap “ASAP” and let the app assign the next available default slot.

Requirements:
• DeliverySlot must offer two constructors, linked through this(...) chaining.
• A slot created without an explicit time must default to "ASAP" through the chained constructor.
• isPeakHour() must correctly flag exactly these four slot values as peak:
  "12:00-13:00", "13:00-14:00", "19:00-20:00", "20:00-21:00".

Function Signature(s):
public DeliverySlot(String orderId, String timeSlot)
public DeliverySlot(String orderId) // chains via this(...)
boolean isPeakHour()

Examples:
new DeliverySlot("ORD101", "13:00-14:00").isPeakHour() -> true
new DeliverySlot("ORD102").isPeakHour() -> false
================================================================================
*/
// Ans 2:
class DeliverySlot {
    private String orderId;
    private String timeSlot;

    public DeliverySlot(String orderId, String timeSlot) {
        this.orderId = orderId;
        this.timeSlot = (timeSlot != null && !timeSlot.trim().isEmpty()) ? timeSlot.trim() : "ASAP";
    }

    public DeliverySlot(String orderId) {
        this(orderId, "ASAP");
    }

    public boolean isPeakHour() {
        return timeSlot.equals("12:00-13:00") ||
               timeSlot.equals("13:00-14:00") ||
               timeSlot.equals("19:00-20:00") ||
               timeSlot.equals("20:00-21:00");
    }
}

/*
================================================================================
PROBLEM 3 • Intermediate
Canteen Trust-Score Ranking Engine
Problem Statement:
The app's homepage lists hostel-block canteens ordered by a trust score out of 5...
Design Canteen's comparison logic and a ranking function precisely.

Requirements:
• Resolve any field/parameter naming clash with this, offer a second constructor that chains to the first with default trust score.
• The comparison method must return a signed result.
• Implement the ranking sort yourself; do not call a built-in sort utility.

Function Signature(s):
public Canteen(String canteenCode, String canteenName, int trustScore)
public Canteen(String canteenCode, String canteenName) // chains via this(...)
int compareTo(Canteen other)
static Canteen[] rankCanteens(Canteen[] canteens)

Examples:
Input: [Canteen("HB3-C","Spice Junction",3), Canteen("hb1-c","Grand Mess",5), Canteen("HB2-C","Southern Treats")]
Output: ["hb1-c", "HB2-C", "HB3-C"]
================================================================================
*/
// Ans 3:
class Canteen implements Comparable<Canteen> {
    private String canteenCode;
    private String canteenName;
    private int trustScore;

    public Canteen(String canteenCode, String canteenName, int trustScore) {
        this.canteenCode = canteenCode;
        this.canteenName = canteenName;
        this.trustScore = trustScore;
    }

    public Canteen(String canteenCode, String canteenName) {
        this(canteenCode, canteenName, 3);
    }

    public String getCanteenCode() {
        return canteenCode;
    }

    @Override
    public int compareTo(Canteen other) {
        // 1. Higher trust score first
        if (this.trustScore != other.trustScore) {
            return Integer.compare(other.trustScore, this.trustScore);
        }
        // 2. Case-insensitive canteen code
        int codeCmp = this.canteenCode.compareToIgnoreCase(other.canteenCode);
        if (codeCmp != 0) {
            return codeCmp;
        }
        // 3. Name length
        return Integer.compare(this.canteenName.length(), other.canteenName.length());
    }

    public static Canteen[] rankCanteens(Canteen[] canteens) {
        if (canteens == null) return new Canteen[0];
        Canteen[] sorted = canteens.clone();
        for (int i = 1; i < sorted.length; i++) {
            Canteen key = sorted[i];
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
PROBLEM 4 • Intermediate
Exam-Week Surge Fee Calculator
Problem Statement:
During exam week, kitchen queues back up and the app wants a fairer surge-fee model...
Rates: 0.5% per minute for minutes 1–5, 1% per minute for minutes 6–15, and 2% per minute from minute 16 onward.
Configured rate acts as minimum surge floor, which only applies once an order is genuinely delayed.

Requirements:
• The calculation rule, the field it depends on, and the class itself must each be locked against modification (final).
• Negative orderValue or delayMinutes must be rejected at point of calculation.

Function Signature(s):
public SurgeFeeCalculator(double minimumSurgePercent)
final double calculateSurgeFee(double orderValue, int delayMinutes)

Examples:
orderValue = 500, delayMinutes = 0  -> Rs 0.0
orderValue = 500, delayMinutes = 1  -> Rs 5.0
orderValue = 500, delayMinutes = 16 -> Rs 72.5
================================================================================
*/
// Ans 4:
final class SurgeFeeCalculator {
    private final double minimumSurgePercent;

    public SurgeFeeCalculator(double minimumSurgePercent) {
        this.minimumSurgePercent = minimumSurgePercent;
    }

    public final double calculateSurgeFee(double orderValue, int delayMinutes) {
        if (orderValue < 0 || delayMinutes < 0) {
            throw new IllegalArgumentException("Order value and delay minutes must be non-negative");
        }
        if (delayMinutes == 0) {
            return 0.0;
        }

        double tiered = 0.0;
        // Bracket 1: Minutes 1-5 @ 0.5%
        int b1 = Math.min(delayMinutes, 5);
        tiered += b1 * (0.005 * orderValue);

        // Bracket 2: Minutes 6-15 @ 1.0%
        if (delayMinutes > 5) {
            int b2 = Math.min(delayMinutes - 5, 10);
            tiered += b2 * (0.010 * orderValue);
        }

        // Bracket 3: Minutes 16+ @ 2.0%
        if (delayMinutes > 15) {
            int b3 = delayMinutes - 15;
            tiered += b3 * (0.020 * orderValue);
        }

        double floor = (minimumSurgePercent / 100.0) * orderValue;
        return Math.max(tiered, floor);
    }
}

/*
================================================================================
PROBLEM 5 • Advanced
Nightly Multi-Kitchen Reconciliation Engine
Problem Statement:
Every night, the app reconciles every delivery account across campus — a mix of regular
student accounts and premium-member accounts, and tolerates null placeholder entries.

Requirements:
• DeliveryAccount must set up one-time class-level state using a static block, support full & chained constructor.
• Processor must use instanceof to decide how each account is settled, and never throw on null.
• Surge fee calculation must be final.

Function Signature(s):
public DeliveryAccount(String studentId, double orderValue)
public DeliveryAccount(String studentId) // chains via this(...)
final double calculateSurgeFee(int delayMinutes)
void processAccount(DeliveryAccount account, double amount, int delayMinutes)
static void processBatch(DeliveryAccount[] accounts, double[] amounts, int[] delayMinutesArray)

Examples:
Input:
accounts = {Premium("STU001",500), null, DeliveryAccount("STU002",300)}
amounts = {500, 400, 300}
delayMinutesArray = {10, 5, 0}
Output:
2 processed | 1 null skipped | 1 premium | 1 regular | grand total surge fees = ...
================================================================================
*/
// Ans 5:
class DeliveryAccount {
    private static double standardRate;
    static {
        standardRate = 5.0; // standard surge rate per 5-minute block
    }

    private String studentId;
    private double orderValue;

    public DeliveryAccount(String studentId, double orderValue) {
        this.studentId = studentId;
        this.orderValue = orderValue;
    }

    public DeliveryAccount(String studentId) {
        this(studentId, 0.0);
    }

    public final double calculateSurgeFee(int delayMinutes) {
        if (delayMinutes <= 0) return 0.0;
        return (delayMinutes / 5.0) * standardRate;
    }

    public static void processBatch(DeliveryAccount[] accounts, double[] amounts, int[] delayMinutesArray) {
        int processed = 0;
        int nullSkipped = 0;
        int premiumCount = 0;
        int regularCount = 0;
        double grandTotal = 0.0;

        if (accounts != null) {
            for (int i = 0; i < accounts.length; i++) {
                DeliveryAccount acc = accounts[i];
                if (acc == null) {
                    nullSkipped++;
                    continue;
                }
                processed++;
                if (acc instanceof PremiumDeliveryAccount) {
                    premiumCount++;
                } else {
                    regularCount++;
                }
                int delay = (delayMinutesArray != null && i < delayMinutesArray.length) ? delayMinutesArray[i] : 0;
                grandTotal += acc.calculateSurgeFee(delay);
            }
        }
        System.out.printf("%d processed | %d null skipped | %d premium | %d regular | grand total surge fees = Rs %.1f%n",
                processed, nullSkipped, premiumCount, regularCount, grandTotal);
    }
}

class PremiumDeliveryAccount extends DeliveryAccount {
    public PremiumDeliveryAccount(String studentId, double orderValue) {
        super(studentId, orderValue);
    }
}