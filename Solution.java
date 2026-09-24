public class Solution {

/*
================================================================================
1. Vowel & Consonant Counter
Scenario: A library orientation kiosk
Task:
• Accept a string (assume only letters and spaces).
• Loop through each character using charAt().
• Count vowels (a, e, i, o, u — case-insensitive) and consonants separately; ignore spaces.
• Print the total vowels and total consonants.
Suggested method signature(s): void countVowelsAndConsonants(String text)

Sample Input / Output:
"Java Programming" -> Vowels: 5 | Consonants: 10
================================================================================
*/
// Ans 1:
    public static void countVowelsAndConsonants(String text) {
        if (text == null) {
            System.out.println("Vowels: 0 | Consonants: 0");
            return;
        }

        int vowels = 0;
        int consonants = 0;

        for (int i = 0; i < text.length(); i++) {
            char ch = Character.toLowerCase(text.charAt(i));
            if (ch >= 'a' && ch <= 'z') {
                if (ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u') {
                    vowels++;
                } else {
                    consonants++;
                }
            }
        }

        System.out.printf("Vowels: %d | Consonants: %d%n", vowels, consonants);
    }

/*
================================================================================
2. CSV Student Record Parser
Scenario: The T&P team receives student registration data
Task:
• Accept a CSV line in the form "Name,RollNumber,Department".
• Use split(",") to break it into fields.
• Validate that exactly 3 fields are present; if not, print "Invalid Record".
• Print a formatted record: "Name: ... | Roll No: ... | Dept: ...".
Suggested method signature(s): void parseStudentRecord(String csvLine)

Sample Input / Output:
"Ananya Verma,RA2211003010123,CSE" -> Name: Ananya Verma | Roll No: RA2211003010123 | Dept: CSE
"Ananya Verma,CSE" -> Invalid Record
================================================================================
*/
// Ans 2:
    public static void parseStudentRecord(String csvLine) {
        if (csvLine == null) {
            System.out.println("Invalid Record");
            return;
        }

        String[] fields = csvLine.split(",");

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

/*
================================================================================
3. File Extension Validator
Scenario: An assignment-upload portal
Task:
• Accept a filename string.
• Find the last '.' using lastIndexOf('.') and extract the extension with substring().
• Compare the extension case-insensitively against the accepted list: pdf, docx, zip.
• Print "Accepted" or "Rejected — invalid file type".
Suggested method signature(s): String validateFileExtension(String filename)

Sample Input / Output:
"Assignment1.PDF" -> Accepted
"notes.txt" -> Rejected — invalid file type
================================================================================
*/
// Ans 3:
    public static String validateFileExtension(String filename) {
        if (filename == null || !filename.contains(".")) {
            return "Rejected — invalid file type";
        }

        int dotIndex = filename.lastIndexOf('.');
        if (dotIndex == -1 || dotIndex == filename.length() - 1) {
            return "Rejected — invalid file type";
        }

        String extension = filename.substring(dotIndex + 1);

        if (extension.equalsIgnoreCase("pdf") ||
            extension.equalsIgnoreCase("docx") ||
            extension.equalsIgnoreCase("zip")) {
            return "Accepted";
        } else {
            return "Rejected — invalid file type";
        }
    }

/*
================================================================================
4. Masked Phone Number Formatter
Scenario: A student-support call center displays a partially masked version
Task:
• Accept a phone number as a string.
• Validate that it is exactly 10 digits (all numeric).
• Build a masked version showing "XXXXXX" followed by the last 4 digits, using StringBuilder.
• Insert a "-" between the mask and the last 4 digits for readability.
• Print the final masked number, or an error message if validation fails.
Suggested method signature(s): String maskPhoneNumber(String phone)

Sample Input / Output:
"9876543210" -> XXXXXX-3210
"98765" -> Invalid phone number
================================================================================
*/
// Ans 4:
    public static String maskPhoneNumber(String phone) {
        if (phone == null || phone.length() != 10) {
            return "Invalid phone number";
        }

        for (int i = 0; i < phone.length(); i++) {
            if (!Character.isDigit(phone.charAt(i))) {
                return "Invalid phone number";
            }
        }

        StringBuilder sb = new StringBuilder();
        sb.append("XXXXXX");
        sb.append("-");
        sb.append(phone.substring(6));

        return sb.toString();
    }

/*
================================================================================
5. Bank Transaction Reference Generator & Validator
Scenario: A fintech onboarding module
Task:
• Accept a raw reference string that may contain leading/trailing spaces.
• Normalize it: trim() the spaces, then uppercase only the first 3 characters using substring() +
concatenation — leave the rest untouched.
• Validate: exactly 14 characters after normalization; the first 3 characters are letters; the remaining
11 are digits (use Character.isLetter() / isDigit() in a loop — no regex).
• If valid, build a formatted display line with StringBuilder: "[BANKCODE] DATE: dd/MM/yy | SEQ:
12345".
• If invalid, print the specific reason: wrong length, non-letter bank code, or non-digit body.
Suggested method signature(s): 
String normalizeReference(String raw) + 
String validateAndFormat(String reference)

Sample Input / Output:
" hdf03022600042 " -> [HDF] DATE: 03/02/26 | SEQ: 00042
"12F03022600042" -> Invalid: bank code must be 3 letters
================================================================================
*/
// Ans 5:
    public static String normalizeReference(String raw) {
        if (raw == null) {
            return null;
        }
        String trimmed = raw.trim();
        if (trimmed.length() < 3) {
            return trimmed.toUpperCase();
        }
        return trimmed.substring(0, 3).toUpperCase() + trimmed.substring(3);
    }

    public static String validateAndFormat(String reference) {
        if (reference == null || reference.length() != 14) {
            return "Invalid: wrong length";
        }

        for (int i = 0; i < 3; i++) {
            if (!Character.isLetter(reference.charAt(i))) {
                return "Invalid: bank code must be 3 letters";
            }
        }

        for (int i = 3; i < 14; i++) {
            if (!Character.isDigit(reference.charAt(i))) {
                return "Invalid: non-digit body";
            }
        }

        String bankCode = reference.substring(0, 3);
        String day = reference.substring(3, 5);
        String month = reference.substring(5, 7);
        String year = reference.substring(7, 9);
        String seq = reference.substring(9, 14);

        StringBuilder sb = new StringBuilder();
        sb.append("[").append(bankCode).append("] ");
        sb.append("DATE: ").append(day).append("/").append(month).append("/").append(year);
        sb.append(" | SEQ: ").append(seq);

        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println("========== Q1: Vowel & Consonant Counter ==========");
        System.out.print("\"Java Programming\" -> ");
        countVowelsAndConsonants("Java Programming");

        System.out.println("\n========== Q2: CSV Student Record Parser ==========");
        System.out.print("\"Ananya Verma,RA2211003010123,CSE\" -> ");
        parseStudentRecord("Ananya Verma,RA2211003010123,CSE");
        System.out.print("\"Ananya Verma,CSE\" -> ");
        parseStudentRecord("Ananya Verma,CSE");

        System.out.println("\n========== Q3: File Extension Validator ==========");
        System.out.printf("\"Assignment1.PDF\" -> %s%n", validateFileExtension("Assignment1.PDF"));
        System.out.printf("\"notes.txt\" -> %s%n", validateFileExtension("notes.txt"));

        System.out.println("\n========== Q4: Masked Phone Number Formatter ==========");
        System.out.printf("\"9876543210\" -> %s%n", maskPhoneNumber("9876543210"));
        System.out.printf("\"98765\" -> %s%n", maskPhoneNumber("98765"));

        System.out.println("\n========== Q5: Bank Transaction Reference Generator & Validator ==========");
        String raw1 = " hdf03022600042 ";
        System.out.printf("\"%s\" -> %s%n", raw1, validateAndFormat(normalizeReference(raw1)));
        String raw2 = "12F03022600042";
        System.out.printf("\"%s\" -> %s%n", raw2, validateAndFormat(normalizeReference(raw2)));
    }
}