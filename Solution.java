import java.util.*;

public class Solution {

    public static void main(String[] args) {
        System.out.println("========== Problem 1: Checkout Payment Handler ==========");
        CreditCardPayment cc = new CreditCardPayment("4471");
        System.out.println("cc.processPayment(250.0) -> \"" + cc.processPayment(250.0) + "\"");
        CashPayment cash = new CashPayment();
        System.out.println("cash.processPayment(40.0) -> \"" + cash.processPayment(40.0) + "\"");
        System.out.println("cc.processPayment(250.0, \"Birthday gift\") -> \"" + cc.processPayment(250.0, "Birthday gift") + "\"");
        PaymentMethod ref = cc; // Upcasting
        PaymentMethod.printConfirmation(ref, 250.0);

        System.out.println("\n========== Problem 2: Home Safety Alert Network ==========");
        MotionSensor m = new MotionSensor("Living Room");
        System.out.println("m.sendAlert(\"Motion detected\") -> \"" + m.sendAlert("Motion detected") + "\"");
        DualZoneMotionSensor d = new DualZoneMotionSensor("Hallway", "Stairwell");
        System.out.println("d.sendAlert(\"Motion detected\") -> \"" + d.sendAlert("Motion detected") + "\"");
        SmokeDetector s = new SmokeDetector("SD-01");
        System.out.println("s.sendAlert(\"Smoke detected\") -> \"" + s.sendAlert("Smoke detected") + "\"");
        System.out.println("getZoneIfMotionSensor(m) -> \"" + Alertable.getZoneIfMotionSensor(m) + "\"");
        System.out.println("getZoneIfMotionSensor(s) -> \"" + Alertable.getZoneIfMotionSensor(s) + "\"");

        System.out.println("\n========== Problem 3: Quarterly Bonus Calculator ==========");
        TeamLead t = new TeamLead(60000, 5);
        System.out.println("t.calculateBonus() -> " + t.calculateBonus());
        TeamLead t2 = new TeamLead(60000, 0.20, 5);
        System.out.println("t2.calculateBonus() -> " + t2.calculateBonus());
        t.setSalary(-5000);
        StaffMember staffRef = t; // Upcasting
        System.out.println("getAuditIfApplicable(staffRef) -> \"" + StaffMember.getAuditIfApplicable(staffRef) + "\"");

        System.out.println("\n========== Problem 4: Universal Media Launcher ==========");
        AudioFile a = new AudioFile("Morning Jazz");
        System.out.println("a.play() -> \"" + a.play() + "\"");
        System.out.println("a.play(30) -> \"" + a.play(30) + "\"");
        System.out.println("a.getFormatInfo() -> \"" + a.getFormatInfo() + "\"");
        Podcast p = new Podcast("Tech Talk", 12);
        System.out.println("p.play() -> \"" + p.play() + "\"");
        Playable.launchAll(new Playable[]{a, p});

        System.out.println("\n========== Problem 5: Community Library Checkout System ==========");
        Textbook tb = new Textbook("Java Fundamentals");
        System.out.println("tb.getLoanPeriodDays() -> " + tb.getLoanPeriodDays());
        System.out.println("tb.renew() -> \"" + tb.renew() + "\"");
        System.out.println("tb.reserve() -> \"" + tb.reserve() + "\"");
        Magazine mag = new Magazine("Tech Monthly");
        System.out.println("reserveIfSupported(mag) -> \"" + LibraryItem.reserveIfSupported(mag) + "\"");
        DigitalPass dp = new DigitalPass("E-Journal Access");
        System.out.println("reserveIfSupported(dp) -> \"" + LibraryItem.reserveIfSupported(dp) + "\"");
        LibraryItem itemRef = tb; // Upcasting
        System.out.println("reserveIfSupported(itemRef) -> \"" + LibraryItem.reserveIfSupported(itemRef) + "\"");
    }
}

/*
================================================================================
PROBLEM 1 • Basic
Checkout Payment Handler
Problem Statement:
An online checkout system accepts multiple ways to pay...

Requirements:
• PaymentMethod must be an abstract class with processPayment(double amount), final transactionId via static counter.
• Overloaded processPayment(double amount, String note) appends note.
• CreditCardPayment and CashPayment implement processPayment(double amount).
• printConfirmation(PaymentMethod payment, double amount) prints confirmation.

Function Signature(s):
public abstract class PaymentMethod
public abstract String processPayment(double amount);
String processPayment(double amount, String note)
public String getTransactionId()
public CreditCardPayment(String cardNumberLastFour)
public CashPayment()
static void printConfirmation(PaymentMethod payment, double amount)

Examples:
CreditCardPayment cc = new CreditCardPayment("4471"); cc.processPayment(250.0) -> "Charged $250.0 to card ending 4471 - Txn TXN-1001"
CashPayment cash = new CashPayment(); cash.processPayment(40.0) -> "Received $40.0 in cash - Txn TXN-1002"
================================================================================
*/
// Ans 1:
abstract class PaymentMethod {
    private static int txnCounter = 0;
    private final String transactionId;

    public PaymentMethod() {
        txnCounter++;
        this.transactionId = "TXN-" + (1000 + txnCounter);
    }

    public String getTransactionId() {
        return transactionId;
    }

    public abstract String processPayment(double amount);

    public String processPayment(double amount, String note) {
        return processPayment(amount) + " (" + note + ")";
    }

    public static void printConfirmation(PaymentMethod payment, double amount) {
        if (payment != null) {
            System.out.println(payment.processPayment(amount));
        }
    }
}

class CreditCardPayment extends PaymentMethod {
    private String cardNumberLastFour;

    public CreditCardPayment(String cardNumberLastFour) {
        this.cardNumberLastFour = cardNumberLastFour;
    }

    @Override
    public String processPayment(double amount) {
        return String.format("Charged $%.1f to card ending %s - Txn %s",
                amount, cardNumberLastFour, getTransactionId());
    }
}

class CashPayment extends PaymentMethod {
    public CashPayment() {
        super();
    }

    @Override
    public String processPayment(double amount) {
        return String.format("Received $%.1f in cash - Txn %s", amount, getTransactionId());
    }
}

/*
================================================================================
PROBLEM 2 • Basic → Intermediate
Home Safety Alert Network
Problem Statement:
A home safety app wants any device capable of raising an alert to plug into one shared alert system...

Requirements:
• Interface Alertable with sendAlert(String message).
• SecuritySensor base class with zoneName and getZoneName().
• MotionSensor extends SecuritySensor implements Alertable.
• DualZoneMotionSensor extends MotionSensor, calls super.sendAlert(message) and appends second zone.
• SmokeDetector implements Alertable directly.
• broadcastAll(Alertable[] devices, String message).
• getZoneIfMotionSensor(Alertable a) checks with instanceof and downcasts.

Function Signature(s):
public interface Alertable { String sendAlert(String message); }
public SecuritySensor(String zoneName)
public MotionSensor(String zoneName)
public DualZoneMotionSensor(String zoneName, String secondZoneName)
public SmokeDetector(String deviceId)
static void broadcastAll(Alertable[] devices, String message)
static String getZoneIfMotionSensor(Alertable a)

Examples:
MotionSensor m = new MotionSensor("Living Room"); m.sendAlert("Motion detected") -> "[Living Room] Motion detected"
DualZoneMotionSensor d = new DualZoneMotionSensor("Hallway", "Stairwell"); d.sendAlert("Motion detected") -> "[Hallway] Motion detected [also covering Stairwell]"
SmokeDetector s = new SmokeDetector("SD-01"); s.sendAlert("Smoke detected") -> "[SD-01] Smoke detected"
================================================================================
*/
// Ans 2:
interface Alertable {
    String sendAlert(String message);

    static void broadcastAll(Alertable[] devices, String message) {
        if (devices != null) {
            for (Alertable d : devices) {
                if (d != null) {
                    System.out.println(d.sendAlert(message));
                }
            }
        }
    }

    static String getZoneIfMotionSensor(Alertable a) {
        if (a instanceof MotionSensor) {
            return ((MotionSensor) a).getZoneName();
        }
        return "Not a motion sensor";
    }
}

class SecuritySensor {
    private String zoneName;

    public SecuritySensor(String zoneName) {
        this.zoneName = zoneName;
    }

    public String getZoneName() {
        return zoneName;
    }
}

class MotionSensor extends SecuritySensor implements Alertable {
    public MotionSensor(String zoneName) {
        super(zoneName);
    }

    @Override
    public String sendAlert(String message) {
        return "[" + getZoneName() + "] " + message;
    }
}

class DualZoneMotionSensor extends MotionSensor {
    private String secondZoneName;

    public DualZoneMotionSensor(String zoneName, String secondZoneName) {
        super(zoneName);
        this.secondZoneName = secondZoneName;
    }

    @Override
    public String sendAlert(String message) {
        return super.sendAlert(message) + " [also covering " + secondZoneName + "]";
    }
}

class SmokeDetector implements Alertable {
    private String deviceId;

    public SmokeDetector(String deviceId) {
        this.deviceId = deviceId;
    }

    @Override
    public String sendAlert(String message) {
        return "[" + deviceId + "] " + message;
    }
}

/*
================================================================================
PROBLEM 3 • Intermediate
Quarterly Bonus Calculator
Problem Statement:
A company calculates a year-end bonus for every staff member...

Requirements:
• StaffMember abstract with calculateBonus(), private baseSalary with getSalary()/setSalary(baseSalary).
• Two constructors: one chaining via this(baseSalary, 0.10), one taking baseSalary and bonusRate.
• Interface Auditable with auditRecord().
• TeamLead extends StaffMember implements Auditable.
• getAuditIfApplicable(StaffMember s) checks with instanceof Auditable.

Function Signature(s):
public abstract class StaffMember(double baseSalary)
public abstract class StaffMember(double baseSalary, double bonusRate)
public abstract double calculateBonus();
double getSalary()
void setSalary(double baseSalary)
public interface Auditable { String auditRecord(); }
public TeamLead(double baseSalary, int teamSize)
public TeamLead(double baseSalary, double bonusRate, int teamSize)
static String getAuditIfApplicable(StaffMember s)

Examples:
TeamLead t = new TeamLead(60000, 5); t.calculateBonus() -> 6000.0
TeamLead t2 = new TeamLead(60000, 0.20, 5); t2.calculateBonus() -> 12000.0
================================================================================
*/
// Ans 3:
abstract class StaffMember {
    private double baseSalary;
    protected double bonusRate;

    public StaffMember(double baseSalary) {
        this(baseSalary, 0.10);
    }

    public StaffMember(double baseSalary, double bonusRate) {
        if (baseSalary < 0) throw new IllegalArgumentException("Base salary cannot be negative");
        this.baseSalary = baseSalary;
        this.bonusRate = bonusRate;
    }

    public double getSalary() {
        return baseSalary;
    }

    public void setSalary(double baseSalary) {
        if (baseSalary < 0) {
            System.out.println("rejected, salary unchanged");
            return;
        }
        this.baseSalary = baseSalary;
    }

    public abstract double calculateBonus();

    public static String getAuditIfApplicable(StaffMember s) {
        if (s instanceof Auditable) {
            return ((Auditable) s).auditRecord();
        }
        return "no audit is required";
    }
}

interface Auditable {
    String auditRecord();
}

class TeamLead extends StaffMember implements Auditable {
    private int teamSize;

    public TeamLead(double baseSalary, int teamSize) {
        super(baseSalary);
        this.teamSize = teamSize;
    }

    public TeamLead(double baseSalary, double bonusRate, int teamSize) {
        super(baseSalary, bonusRate);
        this.teamSize = teamSize;
    }

    @Override
    public double calculateBonus() {
        return getSalary() * bonusRate;
    }

    @Override
    public String auditRecord() {
        return String.format("TeamLead audit: %d team members, salary $%.1f", teamSize, getSalary());
    }
}

/*
================================================================================
PROBLEM 4 • Intermediate → Advanced
Universal Media Launcher
Problem Statement:
A media app wants to play anything 'playable'...

Requirements:
• Interface Playable with play(), play(int fromSecond), pause().
• MediaFile abstract with final fileId via static counter, abstract getFormatInfo().
• AudioFile extends MediaFile implements Playable.
• Podcast implements Playable directly with no MediaFile relationship.
• launchAll(Playable[] items) loops polymorphically.

Function Signature(s):
public interface Playable { String play(); String play(int fromSecond); String pause(); }
public abstract class MediaFile
public abstract String getFormatInfo();
String getFileId()
public AudioFile(String title)
public Podcast(String showName, int episodeNumber)
static void launchAll(Playable[] items)

Examples:
AudioFile a = new AudioFile("Morning Jazz");
a.play() -> "Playing audio: Morning Jazz"
a.play(30) -> "Playing audio: Morning Jazz from 0:30"
a.getFormatInfo() -> "Audio file, ID: MF-1001"
Podcast p = new Podcast("Tech Talk", 12); p.play() -> "Streaming episode 12 of Tech Talk"
================================================================================
*/
// Ans 4:
interface Playable {
    String play();
    String play(int fromSecond);
    String pause();

    static void launchAll(Playable[] items) {
        if (items != null) {
            for (Playable item : items) {
                if (item != null) {
                    System.out.println(item.play());
                }
            }
        }
    }
}

abstract class MediaFile {
    private static int fileCounter = 0;
    private final String fileId;

    public MediaFile() {
        fileCounter++;
        this.fileId = "MF-" + (1000 + fileCounter);
    }

    public String getFileId() {
        return fileId;
    }

    public abstract String getFormatInfo();
}

class AudioFile extends MediaFile implements Playable {
    private String title;

    public AudioFile(String title) {
        this.title = title;
    }

    @Override
    public String play() {
        return "Playing audio: " + title;
    }

    @Override
    public String play(int fromSecond) {
        int m = fromSecond / 60;
        int s = fromSecond % 60;
        return String.format("Playing audio: %s from %d:%02d", title, m, s);
    }

    @Override
    public String pause() {
        return "Audio paused: " + title;
    }

    @Override
    public String getFormatInfo() {
        return "Audio file, ID: " + getFileId();
    }
}

class Podcast implements Playable {
    private String showName;
    private int episodeNumber;

    public Podcast(String showName, int episodeNumber) {
        this.showName = showName;
        this.episodeNumber = episodeNumber;
    }

    @Override
    public String play() {
        return "Streaming episode " + episodeNumber + " of " + showName;
    }

    @Override
    public String play(int fromSecond) {
        return String.format("Streaming episode %d of %s from %ds", episodeNumber, showName, fromSecond);
    }

    @Override
    public String pause() {
        return "Podcast paused: " + showName;
    }
}

/*
================================================================================
PROBLEM 5 • Advanced
Community Library Checkout System
Problem Statement:
A community library checks out physical textbooks and issues digital passes...

Requirements:
• LibraryItem abstract with final itemId via static counter, abstract getLoanPeriodDays().
• Interfaces Renewable (renew()) and Reservable (reserve()).
• Textbook extends LibraryItem implements Renewable, Reservable.
• Magazine extends LibraryItem implements Renewable only.
• DigitalPass implements Renewable only.
• processCheckouts(LibraryItem[] items) prints loan periods polymorphically.
• reserveIfSupported(Object o) checks with instanceof Reservable.

Function Signature(s):
public abstract class LibraryItem
public abstract int getLoanPeriodDays();
String getItemId()
public interface Renewable { String renew(); }
public interface Reservable { String reserve(); }
public Textbook(String title)
public Magazine(String title)
public DigitalPass(String resourceName)
static void processCheckouts(LibraryItem[] items)
static String reserveIfSupported(Object o)

Examples:
Textbook t = new Textbook("Java Fundamentals");
t.getLoanPeriodDays() -> 14
t.renew() -> "Java Fundamentals renewed"
t.reserve() -> "Java Fundamentals reserved"
Magazine m = new Magazine("Tech Monthly"); reserveIfSupported(m) -> "Reservation not supported"
================================================================================
*/
// Ans 5:
abstract class LibraryItem {
    private static int itemCounter = 0;
    private final String itemId;

    public LibraryItem() {
        itemCounter++;
        this.itemId = "LIB-" + (1000 + itemCounter);
    }

    public String getItemId() {
        return itemId;
    }

    public abstract int getLoanPeriodDays();

    public static void processCheckouts(LibraryItem[] items) {
        if (items != null) {
            for (LibraryItem item : items) {
                if (item != null) {
                    System.out.printf("Item %s loan period: %d days%n", item.getItemId(), item.getLoanPeriodDays());
                }
            }
        }
    }

    public static String reserveIfSupported(Object o) {
        if (o instanceof Reservable) {
            return ((Reservable) o).reserve();
        }
        return "Reservation not supported";
    }
}

interface Renewable {
    String renew();
}

interface Reservable {
    String reserve();
}

class Textbook extends LibraryItem implements Renewable, Reservable {
    private String title;

    public Textbook(String title) {
        this.title = title;
    }

    @Override
    public int getLoanPeriodDays() {
        return 14;
    }

    @Override
    public String renew() {
        return title + " renewed";
    }

    @Override
    public String reserve() {
        return title + " reserved";
    }
}

class Magazine extends LibraryItem implements Renewable {
    private String title;

    public Magazine(String title) {
        this.title = title;
    }

    @Override
    public int getLoanPeriodDays() {
        return 7;
    }

    @Override
    public String renew() {
        return title + " renewed";
    }
}

class DigitalPass implements Renewable {
    private String resourceName;

    public DigitalPass(String resourceName) {
        this.resourceName = resourceName;
    }

    @Override
    public String renew() {
        return resourceName + " renewed";
    }
}