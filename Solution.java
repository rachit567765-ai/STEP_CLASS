import java.util.*;

public class Solution {

    public static void main(String[] args) {
        System.out.println("========== Problem 1: Basic Drawing Canvas ==========");
        CircleShape c = new CircleShape(5.0);
        System.out.printf("c.calculateArea() -> %.2f%n", c.calculateArea());
        SquareShape sq = new SquareShape(4.0);
        System.out.println("sq.calculateArea() -> " + sq.calculateArea());
        sq.scale(2.0);
        System.out.println("After sq.scale(2.0), sq.calculateArea() -> " + sq.calculateArea());
        Shape.printArea(c);

        System.out.println("\n========== Problem 2: One-Click Data Export ==========");
        ReportGenerator r = new ReportGenerator("Sales Q1");
        System.out.println("r.exportData() -> \"" + r.exportData() + "\"");
        UserProfile u = new UserProfile("jane_doe");
        System.out.println("u.exportData() -> \"" + u.exportData() + "\"");
        Exportable ref = r;
        Exportable.exportAll(new Exportable[]{ref, u});
        System.out.println("getTotalExports() -> " + Exportable.getTotalExports());

        System.out.println("\n========== Problem 3: Fleet Maintenance Tracker ==========");
        Forklift f = new Forklift("FL-22");
        f.addMileage(120);
        System.out.println("f.getMileage() -> " + f.getMileage());
        System.out.println("f.performMaintenance() -> \"" + f.performMaintenance() + "\"");
        HeavyDutyForklift hd = new HeavyDutyForklift("HD-9");
        System.out.println("hd.performMaintenance() -> \"" + hd.performMaintenance() + "\"");
        System.out.println("getInsuranceIfApplicable(f) -> \"" + ServiceableVehicle.getInsuranceIfApplicable(f) + "\"");

        System.out.println("\n========== Problem 4: Arena Battle Simulator ==========");
        Warrior w = new Warrior("Kael");
        System.out.println("w.attack() -> \"" + w.attack() + "\"");
        System.out.println("w.attack(\"Iron Sword\") -> \"" + w.attack("Iron Sword") + "\"");
        System.out.println("w.defend() -> \"" + w.defend() + "\"");
        System.out.println("w.getSpecialMove() -> \"" + w.getSpecialMove() + "\"");
        Trap t = new Trap("Spike Pit");
        System.out.println("t.defend() -> \"" + t.defend() + "\"");
        Defendable.resolveDefense(new Defendable[]{w, t});

        System.out.println("\n========== Problem 5: Connected Home Control Panel ==========");
        WashingMachine wm = new WashingMachine(500.0);
        System.out.println("wm.activate() -> \"" + wm.activate() + "\"");
        System.out.println("wm.connect(\"HomeConnect\") -> \"" + wm.connect("HomeConnect") + "\"");
        Refrigerator fridge = new Refrigerator(150.0);
        System.out.println("getConsumptionIfTrackable(fridge) -> " + HomeDevice.getConsumptionIfTrackable(fridge));
        MobileApp app = new MobileApp("HomeConnect App");
        System.out.println("app.connect(\"HomeConnect\") -> \"" + app.connect("HomeConnect") + "\"");
        HomeDevice devRef = wm;
        System.out.println("getConsumptionIfTrackable(devRef) -> " + HomeDevice.getConsumptionIfTrackable(devRef));
    }
}

/*
================================================================================
PROBLEM 1 • Basic
Basic Drawing Canvas
Problem Statement:
A drawing app supports multiple shapes. Every shape needs an area calculation...

Requirements:
• Shape must be an abstract class with calculateArea(), final shapeId via static counter.
• Concrete overloaded scale(factor) and scale(xFactor, yFactor).
• CircleShape and SquareShape extend Shape.
• printArea(Shape s) prints area polymorphically.

Function Signature(s):
public abstract class Shape
public abstract double calculateArea();
void scale(double factor)
void scale(double xFactor, double yFactor)
String getShapeId()
public CircleShape(double radius)
public SquareShape(double side)
static void printArea(Shape s)

Examples:
CircleShape c = new CircleShape(5.0); c.calculateArea() -> ~78.54
SquareShape sq = new SquareShape(4.0); sq.calculateArea() -> 16.0
sq.scale(2.0); sq.calculateArea() -> 64.0
================================================================================
*/
// Ans 1:
abstract class Shape {
    private static int shapeCounter = 0;
    private final String shapeId;

    public Shape() {
        shapeCounter++;
        this.shapeId = "SHP-" + (1000 + shapeCounter);
    }

    public String getShapeId() {
        return shapeId;
    }

    public abstract double calculateArea();

    public abstract void scale(double factor);

    public void scale(double xFactor, double yFactor) {
        scale(xFactor);
    }

    public static void printArea(Shape s) {
        if (s != null) {
            System.out.printf("Shape %s Area: %.2f%n", s.getShapeId(), s.calculateArea());
        }
    }
}

class CircleShape extends Shape {
    private double radius;

    public CircleShape(double radius) {
        if (radius <= 0) throw new IllegalArgumentException("Radius must be positive");
        this.radius = radius;
    }

    @Override
    public double calculateArea() {
        return Math.PI * radius * radius;
    }

    @Override
    public void scale(double factor) {
        if (factor > 0) this.radius *= factor;
    }
}

class SquareShape extends Shape {
    private double side;

    public SquareShape(double side) {
        if (side <= 0) throw new IllegalArgumentException("Side must be positive");
        this.side = side;
    }

    @Override
    public double calculateArea() {
        return side * side;
    }

    @Override
    public void scale(double factor) {
        if (factor > 0) this.side *= factor;
    }
}

/*
================================================================================
PROBLEM 2 • Basic → Intermediate
One-Click Data Export
Problem Statement:
A workplace app wants many unrelated kinds of data — reports and user profiles — to support
one shared 'export' action, without forcing them into any shared class hierarchy.

Requirements:
• Define interface Exportable with method exportData().
• ReportGenerator and UserProfile implement Exportable directly.
• Shared static export counter incremented every time exportData() is called.
• exportAll(Exportable[] items) loops through array and calls exportData().

Function Signature(s):
public interface Exportable { String exportData(); }
public ReportGenerator(String reportName)
public UserProfile(String username)
static int getTotalExports()
static void exportAll(Exportable[] items)

Examples:
ReportGenerator r = new ReportGenerator("Sales Q1"); r.exportData() -> "Exported report: Sales Q1"
UserProfile u = new UserProfile("jane_doe"); u.exportData() -> "Exported profile: jane_doe"
getTotalExports() -> 2
================================================================================
*/
// Ans 2:
interface Exportable {
    String exportData();

    class ExportCounter {
        static int totalExports = 0;
    }

    static int getTotalExports() {
        return ExportCounter.totalExports;
    }

    static void exportAll(Exportable[] items) {
        if (items != null) {
            for (Exportable item : items) {
                if (item != null) {
                    System.out.println(item.exportData());
                }
            }
        }
    }
}

class ReportGenerator implements Exportable {
    private String reportName;

    public ReportGenerator(String reportName) {
        this.reportName = reportName;
    }

    @Override
    public String exportData() {
        Exportable.ExportCounter.totalExports++;
        return "Exported report: " + reportName;
    }
}

class UserProfile implements Exportable {
    private String username;

    public UserProfile(String username) {
        this.username = username;
    }

    @Override
    public String exportData() {
        Exportable.ExportCounter.totalExports++;
        return "Exported profile: " + username;
    }
}

/*
================================================================================
PROBLEM 3 • Intermediate
Fleet Maintenance Tracker
Problem Statement:
A construction company tracks maintenance on heavy equipment...

Requirements:
• ServiceableVehicle abstract with performMaintenance(), private mileage with getMileage()/addMileage(km).
• Interface Insurable with getInsuranceInfo().
• Forklift extends ServiceableVehicle and implements Insurable.
• HeavyDutyForklift extends Forklift, calls super.performMaintenance() and appends hydraulic check.
• getInsuranceIfApplicable checks with instanceof.

Function Signature(s):
public abstract class ServiceableVehicle
public abstract String performMaintenance();
double getMileage()
void addMileage(double km)
public interface Insurable { String getInsuranceInfo(); }
public Forklift(String assetTag)
public HeavyDutyForklift(String assetTag)
static String getInsuranceIfApplicable(ServiceableVehicle v)

Examples:
f.performMaintenance() -> "Forklift FL-22: hydraulic and fork inspection complete"
hd.performMaintenance() -> "Forklift HD-9: hydraulic and fork inspection complete | high-pressure hydraulic check complete"
getInsuranceIfApplicable(f) -> "Insured under fleet policy - Asset FL-22"
================================================================================
*/
// Ans 3:
abstract class ServiceableVehicle {
    private double mileage;

    public ServiceableVehicle() {
        this.mileage = 0.0;
    }

    public double getMileage() {
        return mileage;
    }

    public void addMileage(double km) {
        if (km < 0) {
            System.out.println("Rejected: negative distance");
            return;
        }
        this.mileage += km;
    }

    public abstract String performMaintenance();

    public static String getInsuranceIfApplicable(ServiceableVehicle v) {
        if (v instanceof Insurable) {
            return ((Insurable) v).getInsuranceInfo();
        }
        return "No insurance record exists";
    }
}

interface Insurable {
    String getInsuranceInfo();
}

class Forklift extends ServiceableVehicle implements Insurable {
    protected String assetTag;

    public Forklift(String assetTag) {
        this.assetTag = assetTag;
    }

    @Override
    public String performMaintenance() {
        return "Forklift " + assetTag + ": hydraulic and fork inspection complete";
    }

    @Override
    public String getInsuranceInfo() {
        return "Insured under fleet policy - Asset " + assetTag;
    }
}

class HeavyDutyForklift extends Forklift {
    public HeavyDutyForklift(String assetTag) {
        super(assetTag);
    }

    @Override
    public String performMaintenance() {
        return super.performMaintenance() + " | high-pressure hydraulic check complete";
    }
}

/*
================================================================================
PROBLEM 4 • Intermediate → Advanced
Arena Battle Simulator
Problem Statement:
A game engine needs combat behavior...

Requirements:
• Interface Attackable with attack() and attack(weaponName).
• Interface Defendable with defend().
• GameCharacter abstract with final characterId via static counter, abstract getSpecialMove().
• Warrior extends GameCharacter implements Attackable, Defendable.
• Trap implements Defendable with no relationship to GameCharacter.
• resolveDefense(Defendable[] combatants) calls defend() polymorphically.

Function Signature(s):
public interface Attackable { String attack(); String attack(String weaponName); }
public interface Defendable { String defend(); }
public abstract class GameCharacter
public abstract String getSpecialMove();
String getCharacterId()
public Warrior(String name)
public Trap(String trapType)
static void resolveDefense(Defendable[] combatants)

Examples:
Warrior w = new Warrior("Kael");
w.attack() -> "Kael strikes with a blade"
w.attack("Iron Sword") -> "Kael strikes with an Iron Sword"
w.defend() -> "Kael raises a shield"
w.getSpecialMove() -> "Kael unleashes Whirlwind Slash"
Trap t = new Trap("Spike Pit"); t.defend() -> "Spike Pit triggers automatically"
================================================================================
*/
// Ans 4:
interface Attackable {
    String attack();
    String attack(String weaponName);
}

interface Defendable {
    String defend();

    static void resolveDefense(Defendable[] combatants) {
        if (combatants != null) {
            for (Defendable d : combatants) {
                if (d != null) {
                    System.out.println(d.defend());
                }
            }
        }
    }
}

abstract class GameCharacter {
    private static int charCounter = 0;
    private final String characterId;

    public GameCharacter() {
        charCounter++;
        this.characterId = "CHR-" + (1000 + charCounter);
    }

    public String getCharacterId() {
        return characterId;
    }

    public abstract String getSpecialMove();
}

class Warrior extends GameCharacter implements Attackable, Defendable {
    private String name;

    public Warrior(String name) {
        this.name = name;
    }

    @Override
    public String attack() {
        return name + " strikes with a blade";
    }

    @Override
    public String attack(String weaponName) {
        String article = (weaponName.matches("^[AEIOUaeiou].*")) ? "an " : "a ";
        return name + " strikes with " + article + weaponName;
    }

    @Override
    public String defend() {
        return name + " raises a shield";
    }

    @Override
    public String getSpecialMove() {
        return name + " unleashes Whirlwind Slash";
    }
}

class Trap implements Defendable {
    private String trapType;

    public Trap(String trapType) {
        this.trapType = trapType;
    }

    @Override
    public String defend() {
        return trapType + " triggers automatically";
    }
}

/*
================================================================================
PROBLEM 5 • Advanced
Connected Home Control Panel
Problem Statement:
A smart home app connects to devices for control and energy tracking...

Requirements:
• HomeDevice abstract with final serialNumber, abstract activate().
• Interfaces RemoteControllable (connect(appId)) and EnergyTrackable (getConsumptionWatts()).
• WashingMachine extends HomeDevice implements RemoteControllable, EnergyTrackable.
• Refrigerator extends HomeDevice implements EnergyTrackable only.
• MobileApp implements RemoteControllable directly with no HomeDevice relationship.
• connectAll(RemoteControllable[] items, String appId) loops polymorphically.
• getConsumptionIfTrackable(HomeDevice d) checks with instanceof.

Function Signature(s):
public abstract class HomeDevice
public abstract String activate();
String getSerialNumber()
public interface RemoteControllable { String connect(String appId); }
public interface EnergyTrackable { double getConsumptionWatts(); }
public WashingMachine(double consumptionWatts)
public Refrigerator(double consumptionWatts)
public MobileApp(String appName)
static void connectAll(RemoteControllable[] items, String appId)
static double getConsumptionIfTrackable(HomeDevice d)

Examples:
WashingMachine wm = new WashingMachine(500.0);
wm.activate() -> "Washing machine HD-1001 started a cycle"
wm.connect("HomeConnect") -> "HD-1001 connected to HomeConnect"
getConsumptionIfTrackable(fridge) -> 150.0
================================================================================
*/
// Ans 5:
abstract class HomeDevice {
    private static int deviceCounter = 0;
    private final String serialNumber;

    public HomeDevice() {
        deviceCounter++;
        this.serialNumber = "HD-" + (1000 + deviceCounter);
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public abstract String activate();

    public static double getConsumptionIfTrackable(HomeDevice d) {
        if (d instanceof EnergyTrackable) {
            return ((EnergyTrackable) d).getConsumptionWatts();
        }
        return 0.0;
    }
}

interface RemoteControllable {
    String connect(String appId);

    static void connectAll(RemoteControllable[] items, String appId) {
        if (items != null) {
            for (RemoteControllable item : items) {
                if (item != null) {
                    System.out.println(item.connect(appId));
                }
            }
        }
    }
}

interface EnergyTrackable {
    double getConsumptionWatts();
}

class WashingMachine extends HomeDevice implements RemoteControllable, EnergyTrackable {
    private double consumptionWatts;

    public WashingMachine(double consumptionWatts) {
        this.consumptionWatts = consumptionWatts;
    }

    @Override
    public String activate() {
        return "Washing machine " + getSerialNumber() + " started a cycle";
    }

    @Override
    public String connect(String appId) {
        return getSerialNumber() + " connected to " + appId;
    }

    @Override
    public double getConsumptionWatts() {
        return consumptionWatts;
    }
}

class Refrigerator extends HomeDevice implements EnergyTrackable {
    private double consumptionWatts;

    public Refrigerator(double consumptionWatts) {
        this.consumptionWatts = consumptionWatts;
    }

    @Override
    public String activate() {
        return "Refrigerator " + getSerialNumber() + " cooling active";
    }

    @Override
    public double getConsumptionWatts() {
        return consumptionWatts;
    }
}

class MobileApp implements RemoteControllable {
    private String appName;

    public MobileApp(String appName) {
        this.appName = appName;
    }

    @Override
    public String connect(String appId) {
        return appName + " connected to " + appId;
    }
}