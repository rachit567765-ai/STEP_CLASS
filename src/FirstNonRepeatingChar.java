import java.util.LinkedHashMap;
import java.util.Map;

public class FirstNonRepeatingChar {

    public static char findFirstNonRepeatingChar(String text) {
        if (text == null || text.isEmpty()) {
            return '\0';
        }

        Map<Character, Integer> freqMap = new LinkedHashMap<>();
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            freqMap.put(ch, freqMap.getOrDefault(ch, 0) + 1);
        }

        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            if (freqMap.get(ch) == 1) {
                return ch;
            }
        }

        return '\0';
    }

    public static void testString(String input) {
        char result = findFirstNonRepeatingChar(input);
        if (result != '\0') {
            System.out.printf("\"%s\" -> First Non-Repeating Character: '%c'%n", input, result);
        } else {
            System.out.printf("\"%s\" -> No Non-Repeating Character Found%n", input);
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Day 1 Problem 4: First Non-Repeating Character ===");
        testString("swiss");
        testString("aabbcc");
    }
}
