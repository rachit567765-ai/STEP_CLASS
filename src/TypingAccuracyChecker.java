public class TypingAccuracyChecker {

    public static void checkTypingAccuracy(String original, String typed) {
        if (original == null || typed == null) {
            System.out.println("Invalid input");
            return;
        }

        int total = original.length();
        int matched = 0;
        int firstMismatchPos = -1;
        char origChar = '\0';
        char typedChar = '\0';

        int compareLen = Math.min(original.length(), typed.length());

        for (int i = 0; i < compareLen; i++) {
            if (original.charAt(i) == typed.charAt(i)) {
                matched++;
            } else {
                if (firstMismatchPos == -1) {
                    firstMismatchPos = i + 1; // 1-indexed position
                    origChar = original.charAt(i);
                    typedChar = typed.charAt(i);
                }
            }
        }

        // Account for unequal lengths if applicable
        if (firstMismatchPos == -1 && original.length() != typed.length()) {
            firstMismatchPos = compareLen + 1;
        }

        double accuracy = ((double) matched / total) * 100.0;

        if (firstMismatchPos == -1) {
            System.out.printf("Matched: %d/%d | Accuracy: %.2f%% | No Mismatches%n",
                    matched, total, accuracy);
        } else {
            System.out.printf("Matched: %d/%d | Accuracy: %.2f%% | First Mismatch at position %d ('%c' vs '%c')%n",
                    matched, total, accuracy, firstMismatchPos, origChar, typedChar);
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Week 1 Problem 2: Typing Speed Test Accuracy Checker ===");
        
        System.out.println("Test 1:");
        checkTypingAccuracy("hello world", "hello worlt");

        System.out.println("Test 2:");
        checkTypingAccuracy("coding", "coding");
    }
}