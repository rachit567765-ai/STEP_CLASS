public class PalindromeChecker {

    public static boolean isPalindromeIterative(String text) {
        if (text == null) return false;
        int left = 0;
        int right = text.length() - 1;
        while (left < right) {
            if (text.charAt(left) != text.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    public static boolean isPalindromeRecursive(String text) {
        if (text == null) return false;
        if (text.length() <= 1) return true;
        if (text.charAt(0) != text.charAt(text.length() - 1)) {
            return false;
        }
        return isPalindromeRecursive(text.substring(1, text.length() - 1));
    }

    public static boolean isPalindromeArrayReversal(String text) {
        if (text == null) return false;
        char[] original = text.toCharArray();
        char[] reversed = new char[original.length];
        for (int i = 0; i < original.length; i++) {
            reversed[i] = original[original.length - 1 - i];
        }
        for (int i = 0; i < original.length; i++) {
            if (original[i] != reversed[i]) {
                return false;
            }
        }
        return true;
    }

    private static String formatResult(boolean isPal) {
        return isPal ? "Palindrome" : "Not Palindrome";
    }

    public static void checkAllApproaches(String text) {
        boolean iter = isPalindromeIterative(text);
        boolean rec = isPalindromeRecursive(text);
        boolean arr = isPalindromeArrayReversal(text);

        System.out.printf("\"%s\" -> Iterative: %s | Recursive: %s | Array Reversal: %s%n",
                text, formatResult(iter), formatResult(rec), formatResult(arr));
    }

    public static void main(String[] args) {
        System.out.println("=== Day 1 Problem 2: Palindrome Checker (3 Approaches) ===");
        checkAllApproaches("madam");
        checkAllApproaches("hello");
    }
}
