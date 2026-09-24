import java.util.*;

public class Solution {

/*
================================================================================
1. ATM PIN Length Validator
Scenario: An ATM app must check that a PIN a customer enters is exactly 4 digits long
Task:
• Accept a PIN string.
• Get its length using length().
• If the length is not exactly 4, print "Invalid PIN — must be exactly 4 digits."
• Otherwise, print "PIN length OK."
• This one needs no loop at all — just length() and a single if / else.
Suggested method signature(s): void checkPinLength(String pin)

Sample Input / Output:
"482" -> Invalid PIN — must be exactly 4 digits.
"4820" -> PIN length OK.
================================================================================
*/
// Ans 1:
    public static void checkPinLength(String pin) {
        if (pin == null || pin.length() != 4) {
            System.out.println("Invalid PIN — must be exactly 4 digits.");
        } else {
            System.out.println("PIN length OK.");
        }
    }

/*
================================================================================
2. Word Reversal Encoder
Scenario: The coding club's "mirror text" mini-game reverses every word in a sentence individually
Task:
• Accept a sentence (words separated by single spaces).
• Split it into words using split(" ").
• For each word, build its reverse using a loop and StringBuilder.
• Join the reversed words back together with spaces and print the result.
Suggested method signature(s): String reverseEachWord(String sentence)

Sample Input / Output:
"hello club" -> olleh bulc
================================================================================
*/
// Ans 2:
    public static String reverseEachWord(String sentence) {
        if (sentence == null) {
            return null;
        }

        String[] words = sentence.split(" ");
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < words.length; i++) {
            StringBuilder reversedWord = new StringBuilder();
            String w = words[i];
            for (int j = w.length() - 1; j >= 0; j--) {
                reversedWord.append(w.charAt(j));
            }
            result.append(reversedWord);
            if (i < words.length - 1) {
                result.append(" ");
            }
        }

        return result.toString();
    }

/*
================================================================================
3. Product Inventory CSV Parser
Scenario: The warehouse team receives inventory updates as CSV lines
Task:
• Accept a CSV line in the form "ProductName,SKU,Quantity".
• Use split(",") to break it into fields.
• Validate that exactly 3 fields are present; if not, print "Invalid Record".
• Print a formatted record: "Product: ... | SKU: ... | Qty: ...".
Suggested method signature(s): void parseInventoryRecord(String csvLine)

Sample Input / Output:
"Wireless Mouse,WM-2201,150" -> Product: Wireless Mouse | SKU: WM-2201 | Qty: 150
"Wireless Mouse,150" -> Invalid Record
================================================================================
*/
// Ans 3:
    public static void parseInventoryRecord(String csvLine) {
        if (csvLine == null) {
            System.out.println("Invalid Record");
            return;
        }

        String[] fields = csvLine.split(",");
        if (fields.length != 3) {
            System.out.println("Invalid Record");
            return;
        }

        String product = fields[0].trim();
        String sku = fields[1].trim();
        String qty = fields[2].trim();

        if (product.isEmpty() || sku.isEmpty() || qty.isEmpty()) {
            System.out.println("Invalid Record");
            return;
        }

        System.out.printf("Product: %s | SKU: %s | Qty: %s%n", product, sku, qty);
    }

/*
================================================================================
4. Library ISBN Normalizer & Validator
Scenario: A library system's book-intake scanner
Task:
• Accept a raw code string that may contain leading/trailing spaces.
• Normalize it: trim() the spaces, then uppercase only the first 3 characters using substring() +
concatenation — leave the rest untouched.
• Validate: exactly 13 characters after normalization; the first 3 characters are letters; the remaining
10 are digits (use Character.isLetter() / isDigit() in a loop — no regex).
• If valid, build a formatted display line with StringBuilder: "[PUBCODE] YEAR: 20XX | CATALOG:
123456".
• If invalid, print the specific reason: wrong length, non-letter publisher code, or non-digit body.
Suggested method signature(s): 
String normalizeCode(String raw) + 
String validateAndFormat(String code)

Sample Input / Output:
" pen2026004251 " -> [PEN] YEAR: 2026 | CATALOG: 004251
"12N2026004251" -> Invalid: publisher code must be 3 letters
================================================================================
*/
// Ans 4:
    public static String normalizeCode(String raw) {
        if (raw == null) {
            return null;
        }
        String trimmed = raw.trim();
        if (trimmed.length() < 3) {
            return trimmed.toUpperCase();
        }
        return trimmed.substring(0, 3).toUpperCase() + trimmed.substring(3);
    }

    public static String validateAndFormat(String code) {
        if (code == null || code.length() != 13) {
            return "Invalid: wrong length";
        }

        for (int i = 0; i < 3; i++) {
            if (!Character.isLetter(code.charAt(i))) {
                return "Invalid: publisher code must be 3 letters";
            }
        }

        for (int i = 3; i < 13; i++) {
            if (!Character.isDigit(code.charAt(i))) {
                return "Invalid: non-digit body";
            }
        }

        String pubCode = code.substring(0, 3);
        String year = code.substring(3, 7);
        String catalog = code.substring(7, 13);

        StringBuilder sb = new StringBuilder();
        sb.append("[").append(pubCode).append("] ");
        sb.append("YEAR: ").append(year);
        sb.append(" | CATALOG: ").append(catalog);

        return sb.toString();
    }

/*
================================================================================
5. Stop-Word-Filtered Word Frequency Report
Scenario: The T&P team wants word-frequency analysis of feedback paragraphs
Task:
• Accept a paragraph of feedback text, and treat a small fixed list of stop words as filler:
{"the","was","and","a","is","of","in"}.
• Normalize it: convert to lowercase, and strip punctuation such as periods and commas using
replace().
• Split the cleaned text into words using split("\\s+").
• Skip any word found in the stop-word list.
• Count the frequency of every remaining unique word (a HashMap is fine).
• Print each unique word with its count, sorted by count in descending order. Ties may appear in
any order.
Suggested method signature(s): void printFilteredWordFrequency(String feedback)

Sample Input / Output:
"The mentor was great, the session was great and clear."
great: 2
mentor: 1
session: 1
clear: 1
================================================================================
*/
// Ans 5:
    private static final Set<String> STOP_WORDS = new HashSet<>(
            Arrays.asList("the", "was", "and", "a", "is", "of", "in")
    );

    public static void printFilteredWordFrequency(String feedback) {
        if (feedback == null || feedback.trim().isEmpty()) {
            return;
        }

        String cleaned = feedback.toLowerCase()
                .replace(".", "")
                .replace(",", "")
                .replace("!", "")
                .replace("?", "")
                .replace(";", "")
                .replace(":", "");

        String[] words = cleaned.trim().split("\\s+");
        Map<String, Integer> freqMap = new LinkedHashMap<>();

        for (String word : words) {
            if (word.isEmpty() || STOP_WORDS.contains(word)) {
                continue;
            }
            freqMap.put(word, freqMap.getOrDefault(word, 0) + 1);
        }

        List<Map.Entry<String, Integer>> entryList = new ArrayList<>(freqMap.entrySet());
        entryList.sort((a, b) -> b.getValue().compareTo(a.getValue()));

        for (Map.Entry<String, Integer> entry : entryList) {
            System.out.printf("%s: %d%n", entry.getKey(), entry.getValue());
        }
    }

    public static void main(String[] args) {
        System.out.println("========== Q1: ATM PIN Length Validator ==========");
        System.out.print("\"482\" -> ");
        checkPinLength("482");
        System.out.print("\"4820\" -> ");
        checkPinLength("4820");

        System.out.println("\n========== Q2: Word Reversal Encoder ==========");
        System.out.println("\"hello club\" -> " + reverseEachWord("hello club"));

        System.out.println("\n========== Q3: Product Inventory CSV Parser ==========");
        System.out.print("\"Wireless Mouse,WM-2201,150\" -> ");
        parseInventoryRecord("Wireless Mouse,WM-2201,150");
        System.out.print("\"Wireless Mouse,150\" -> ");
        parseInventoryRecord("Wireless Mouse,150");

        System.out.println("\n========== Q4: Library ISBN Normalizer & Validator ==========");
        String raw1 = " pen2026004251 ";
        System.out.printf("\"%s\" -> %s%n", raw1, validateAndFormat(normalizeCode(raw1)));
        String raw2 = "12N2026004251";
        System.out.printf("\"%s\" -> %s%n", raw2, validateAndFormat(normalizeCode(raw2)));

        System.out.println("\n========== Q5: Stop-Word-Filtered Word Frequency Report ==========");
        printFilteredWordFrequency("The mentor was great, the session was great and clear.");
    }
}