public class MaskedPhoneNumberFormatter {

    public static String maskPhoneNumber(String phone) {
        if (phone == null || phone.length() != 10) {
            return "Invalid phone number";
        }

        // Validate all characters are digits
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

    public static void main(String[] args) {
        System.out.println("=== Day 2 Problem 4: Masked Phone Number Formatter ===");
        
        System.out.printf("\"9876543210\" -> %s%n", maskPhoneNumber("9876543210"));
        System.out.printf("\"98765\" -> %s%n", maskPhoneNumber("98765"));
    }
}