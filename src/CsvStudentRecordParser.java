public class CsvStudentRecordParser {

    public static void parseStudentRecord(String csvLine) {
        if (csvLine == null) {
            System.out.println("Invalid Record");
            return;
        }

        // Split by comma
        String[] fields = csvLine.split(",");

        // Validate exactly 3 non-empty fields
        if (fields.length != 3) {
            System.out.println("Invalid Record");
            return;
        }

        String name = fields[0].trim();
        String rollNo = fields[1].trim();
        String dept = fields[2].trim();

        if (name.isEmpty() || rollNo.isEmpty() || dept.isEmpty()) {
            System.out.println("Invalid Record");
            return;
        }

        System.out.printf("Name: %s | Roll No: %s | Dept: %s%n", name, rollNo, dept);
    }

    public static void main(String[] args) {
        System.out.println("=== Day 2 Problem 2: CSV Student Record Parser ===");
        
        System.out.print("\"Ananya Verma,RA2211003010123,CSE\" -> ");
        parseStudentRecord("Ananya Verma,RA2211003010123,CSE");

        System.out.print("\"Ananya Verma,CSE\" -> ");
        parseStudentRecord("Ananya Verma,CSE");
    }
}