import java.util.*;

public class Solution {

    public static void main(String[] args) {
        System.out.println("========== Problem 1: Field Visibility & Intake Validator ==========");
        System.out.println("classifyAccess(\"private\", \"SAME_CLASS\") -> " + AccessRuleEngine.classifyAccess("private", "SAME_CLASS"));
        System.out.println("classifyAccess(\"default\", \"DIFFERENT_PACKAGE\") -> " + AccessRuleEngine.classifyAccess("default", "DIFFERENT_PACKAGE"));
        String[][] batch1 = {
            {"protected", "SAME_PACKAGE"},
            {"protected", "DIFFERENT_PACKAGE"},
            {"public", "DIFFERENT_PACKAGE"}
        };
        System.out.println("summarizeBatch -> " + AccessRuleEngine.summarizeBatch(batch1));
        try {
            new PatientRecord("MT9", "W3", 98.2, "MediTrack Central");
        } catch (IllegalArgumentException e) {
            System.out.println("new PatientRecord(\"MT9\", ...) -> construction rejected");
        }

        System.out.println("\n========== Problem 2: Cross-Package Inheritance Reach ==========");
        System.out.println("classifyAccess(\"protected\", \"SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE\") -> " +
                AccessRuleEngine.classifyAccess("protected", "SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE"));
        System.out.println("classifyAccess(\"protected\", \"SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE\") -> " +
                AccessRuleEngine.classifyAccess("protected", "SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE"));
        System.out.println("describeContext(\"SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE\") -> \"" +
                AccessRuleEngine.describeContext("SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE") + "\"");

        System.out.println("\n========== Problem 3: Vitals Monitoring Encapsulation Guard ==========");
        PatientVitals v = new PatientVitals(new double[]{36.5, -2, 37.1});
        System.out.println("v.getAllReadings() -> " + Arrays.toString(v.getAllReadings()));
        double[] copy = v.getAllReadings();
        copy[0] = 999;
        System.out.println("After copy[0]=999, v.getAllReadings()[0] -> " + v.getAllReadings()[0]);

        System.out.println("\n========== Problem 4: PatientProfile JavaBean, Chained Constructors & Locker PIN ==========");
        System.out.println("new PatientProfile(\"Arjun Iyer\").getPatientId() -> " + new PatientProfile("Arjun Iyer").getPatientId());
        System.out.println("new PatientProfile(\"MT2026-0142\", \"Arjun Iyer\").getPatientId() -> " +
                new PatientProfile("MT2026-0142", "Arjun Iyer").getPatientId());
        PatientProfile p = new PatientProfile();
        p.setPatientId("MT2026-0142");
        p.setPatientId("HACKED-0000");
        System.out.println("After write-once test, p.getPatientId() -> \"" + p.getPatientId() + "\"");

        System.out.println("\n========== Problem 5: Immutable Discharge Summary & Nightly Ledger ==========");
        try {
            new DischargeSummary("MT2026-0142", new String[]{"MED-A", "bad"});
        } catch (IllegalArgumentException e) {
            System.out.println("new DischargeSummary(\"MT2026-0142\", {\"MED-A\", \"bad\"}) -> construction rejected");
        }
        DischargeSummary d = new DischargeSummary("MT2026-0142", new String[]{"MED-A", "MED-B"});
        String[] codes = d.getMedicationCodes();
        codes[0] = "TAMPERED";
        System.out.println("After codes[0]=TAMPERED, d.getMedicationCodes()[0] -> \"" + d.getMedicationCodes()[0] + "\"");

        DischargeSummary[] nightly = {
            new CriticalCareDischargeSummary("MT001", new String[]{"MED-X"}, 4),
            null,
            new DischargeSummary("MT002", new String[]{"MED-Y"})
        };
        System.out.println("processNightlyBatch -> \"" + DischargeSummary.processNightlyBatch(nightly) + "\"");
    }
}

/*
================================================================================
PROBLEM 1 • Basic → Intermediate
Field Visibility & Intake Validator
Problem Statement:
MediTrack Clinic's internal tooling team wants a lightweight static-analysis linter...
Design an AccessRuleEngine that classifies a single access attempt as ALLOWED or DENIED,
and summarizes a whole batch of attempts.
Alongside the engine, build the actual PatientRecord class the rules are protecting.

Requirements:
• classifyAccess(...) and summarizeBatch(...) must decide answers purely from real Java visibility rules.
• Choose the right access level for each of PatientRecord's four fields, and give it no usable no-arg constructor.
• PatientRecord's constructor must reject patientId that's blank, whitespace-only, or shorter than 4 characters.

Function Signature(s):
static String classifyAccess(String fieldModifier, String accessorContext)
static String summarizeBatch(String[][] attempts)
public PatientRecord(String patientId, String wardCode, double vitalsScore, String facilityName)

Examples:
classifyAccess("private", "SAME_CLASS") -> "ALLOWED"
classifyAccess("default", "DIFFERENT_PACKAGE") -> "DENIED"
summarizeBatch(...) -> "Allowed: 2 | Denied: 1"
new PatientRecord("MT9", "W3", 98.2, "MediTrack Central") -> construction rejected
================================================================================
*/
// Ans 1:
class AccessRuleEngine {

    public static String classifyAccess(String fieldModifier, String accessorContext) {
        if (fieldModifier == null || accessorContext == null) {
            return "DENIED";
        }
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

    public static String summarizeBatch(String[][] attempts) {
        int allowed = 0;
        int denied = 0;
        if (attempts != null) {
            for (String[] attempt : attempts) {
                if (attempt != null && attempt.length >= 2) {
                    if ("ALLOWED".equals(classifyAccess(attempt[0], attempt[1]))) {
                        allowed++;
                    } else {
                        denied++;
                    }
                }
            }
        }
        return String.format("Allowed: %d | Denied: %d", allowed, denied);
    }

    public static String describeContext(String accessorContext) {
        if (accessorContext == null || accessorContext.trim().isEmpty()) {
            return "";
        }
        String[] parts = accessorContext.trim().toLowerCase().split("_");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < parts.length; i++) {
            if (!parts[i].isEmpty()) {
                sb.append(Character.toUpperCase(parts[i].charAt(0)))
                  .append(parts[i].substring(1));
                if (i < parts.length - 1) {
                    sb.append(" ");
                }
            }
        }
        return sb.toString();
    }
}

class PatientRecord {
    private String patientId;
    private String wardCode;
    private double vitalsScore;
    private String facilityName;

    public PatientRecord(String patientId, String wardCode, double vitalsScore, String facilityName) {
        if (patientId == null || patientId.trim().length() < 4) {
            throw new IllegalArgumentException("patientId must not be blank and must be at least 4 characters");
        }
        this.patientId = patientId.trim();
        this.wardCode = wardCode;
        this.vitalsScore = vitalsScore;
        this.facilityName = facilityName;
    }

    public String getPatientId() { return patientId; }
    public String getWardCode() { return wardCode; }
    public double getVitalsScore() { return vitalsScore; }
    public String getFacilityName() { return facilityName; }
}

/*
================================================================================
PROBLEM 2 • Intermediate
Cross-Package Inheritance Reach
(Implemented inside AccessRuleEngine above with SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE,
SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE, and describeContext)
================================================================================
*/

/*
================================================================================
PROBLEM 3 • Intermediate
Vitals Monitoring Encapsulation Guard
Problem Statement:
Redesign PatientVitals so every field is private, and the only way in or out is through validated methods.

Requirements:
• All fields private. recordReading(...) must silently reject readings <= 0 or > 45°C.
• PatientVitals(double[] initialReadings) must reuse recordReading(...) itself.
• Any accessor returning readings must return a defensive copy.

Function Signature(s):
PatientVitals(double[] initialReadings)
void recordReading(double reading)
double getAverage()
double[] getAllReadings()

Examples:
new PatientVitals(new double[]{36.5, -2, 37.1}).getAllReadings() -> [36.5, 37.1]
================================================================================
*/
// Ans 3:
class PatientVitals {
    private List<Double> readings;

    public PatientVitals(double[] initialReadings) {
        this.readings = new ArrayList<>();
        if (initialReadings != null) {
            for (double r : initialReadings) {
                recordReading(r);
            }
        }
    }

    public void recordReading(double reading) {
        if (reading > 0 && reading <= 45.0) {
            readings.add(reading);
        }
    }

    public double getAverage() {
        if (readings.isEmpty()) return 0.0;
        double sum = 0.0;
        for (double d : readings) sum += d;
        return sum / readings.size();
    }

    public double[] getAllReadings() {
        double[] copy = new double[readings.size()];
        for (int i = 0; i < readings.size(); i++) {
            copy[i] = readings.get(i);
        }
        return copy;
    }
}

/*
================================================================================
PROBLEM 4 • Intermediate → Advanced
PatientProfile JavaBean, Chained Constructors & Locker PIN
Problem Statement:
The hospital's records dashboard auto-generates forms using a JavaBean-scanning framework...

Requirements:
• Build three constructors — no-arg, name-only, and id+name — chained with this(...).
• JavaBean compliant getters/setters; setPatientId(...) must only take effect once, ever.
• lockerPin must be settable, but never retrievable again in any form.

Function Signature(s):
public PatientProfile()
public PatientProfile(String name)
public PatientProfile(String patientId, String name)
String getPatientId(); void setPatientId(String id)
boolean isDischarged(); void setDischarged(boolean discharged)
void setLockerPin(String pin)

Examples:
new PatientProfile("Arjun Iyer").getPatientId() -> null
new PatientProfile("MT2026-0142", "Arjun Iyer").getPatientId() -> "MT2026-0142"
p.setPatientId("MT2026-0142"); p.setPatientId("HACKED-0000"); p.getPatientId() -> "MT2026-0142"
================================================================================
*/
// Ans 4:
class PatientProfile {
    private String patientId;
    private String name;
    private boolean discharged;
    private String hashedLockerPin;
    private boolean patientIdSet = false;

    public PatientProfile() {
        this(null, null);
    }

    public PatientProfile(String name) {
        this(null, name);
    }

    public PatientProfile(String patientId, String name) {
        if (patientId != null) {
            this.patientId = patientId;
            this.patientIdSet = true;
        }
        this.name = name;
        this.discharged = false;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String id) {
        if (!patientIdSet && id != null) {
            this.patientId = id;
            this.patientIdSet = true;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDischarged() {
        return discharged;
    }

    public void setDischarged(boolean discharged) {
        this.discharged = discharged;
    }

    public void setLockerPin(String pin) {
        if (pin != null && pin.matches("\\d{4,6}")) {
            this.hashedLockerPin = Integer.toHexString(pin.hashCode());
        }
    }
}

/*
================================================================================
PROBLEM 5 • Advanced
Immutable Discharge Summary & Nightly Ledger
Problem Statement:
Build a permanent DischargeSummary as genuinely immutable, validating medication code format...
reconcile a night's worth of discharges — including a critical-care variant — without crashing on null.

Requirements:
• Fields all final, class final; medication codes must match "MED-" followed by exactly one uppercase letter.
• Defensive copying in and out; withCorrectedMedication returns brand-new object.
• Nightly processor uses instanceof; never throws on null; static block for shared state.

Function Signature(s):
public DischargeSummary(String patientId, String[] medicationCodes)
String[] getMedicationCodes()
DischargeSummary withCorrectedMedication(int index, String newCode)
public CriticalCareDischargeSummary(String patientId, String[] medicationCodes, int icuDays)
static String processNightlyBatch(DischargeSummary[] summaries)

Examples:
new DischargeSummary("MT2026-0142", new String[]{"MED-A", "bad"}) -> construction rejected
processNightlyBatch(...) -> "2 processed | 1 null skipped | 1 critical-care | 1 routine"
================================================================================
*/
// Ans 5:
class DischargeSummary {
    private static String ledgerHeader;
    static {
        ledgerHeader = "MEDITRACK NIGHTLY RECONCILIATION LEDGER";
    }

    private final String patientId;
    private final String[] medicationCodes;

    public DischargeSummary(String patientId, String[] medicationCodes) {
        if (medicationCodes == null) {
            throw new IllegalArgumentException("Medication codes cannot be null");
        }
        for (String code : medicationCodes) {
            if (code == null || !code.matches("^MED-[A-Z]$")) {
                throw new IllegalArgumentException("Invalid medication code: " + code);
            }
        }
        this.patientId = patientId;
        this.medicationCodes = medicationCodes.clone();
    }

    public String getPatientId() {
        return patientId;
    }

    public String[] getMedicationCodes() {
        return medicationCodes.clone();
    }

    public DischargeSummary withCorrectedMedication(int index, String newCode) {
        if (index < 0 || index >= medicationCodes.length) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
        String[] newCodes = medicationCodes.clone();
        newCodes[index] = newCode;
        return new DischargeSummary(this.patientId, newCodes);
    }

    public static String processNightlyBatch(DischargeSummary[] summaries) {
        int processed = 0;
        int nullSkipped = 0;
        int criticalCareCount = 0;
        int routineCount = 0;

        if (summaries != null) {
            for (DischargeSummary s : summaries) {
                if (s == null) {
                    nullSkipped++;
                    continue;
                }
                processed++;
                if (s instanceof CriticalCareDischargeSummary) {
                    criticalCareCount++;
                } else {
                    routineCount++;
                }
            }
        }
        return String.format("%d processed | %d null skipped | %d critical-care | %d routine",
                processed, nullSkipped, criticalCareCount, routineCount);
    }
}

class CriticalCareDischargeSummary extends DischargeSummary {
    private final int icuDays;

    public CriticalCareDischargeSummary(String patientId, String[] medicationCodes, int icuDays) {
        super(patientId, medicationCodes);
        this.icuDays = icuDays;
    }

    public int getIcuDays() {
        return icuDays;
    }
}