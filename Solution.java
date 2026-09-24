public class Solution {

/*
================================================================================
1. The Exam Hall Seat Duplication Checker
Scenario: The Examination Cell
Task:
• Accept an array of seat numbers (integers) assigned to students in a hall.
• Compare every seat number against every other seat number to check for duplicates (do not use any
Collections class — arrays and loops only).
• If one or more duplicates are found, print the duplicated seat number(s).
• If no duplicates exist, print a clear confirmation message.
Suggested method signature(s): void checkDuplicateSeats(int[] seatNumbers)

Sample Input / Output:
{101, 102, 103, 102, 105} -> Duplicate Seat Number Found: 102
{101, 102, 103, 104, 105} -> No Duplicate Seats Found
================================================================================
*/
// Ans 1:
    public static void checkDuplicateSeats(int[] seatNumbers) {
        if (seatNumbers == null || seatNumbers.length == 0) {
            System.out.println("No Duplicate Seats Found");
            return;
        }

        boolean foundDuplicate = false;
        int n = seatNumbers.length;
        boolean[] alreadyReported = new boolean[n];

        for (int i = 0; i < n; i++) {
            if (alreadyReported[i]) {
                continue;
            }
            boolean isDup = false;
            for (int j = i + 1; j < n; j++) {
                if (seatNumbers[i] == seatNumbers[j]) {
                    isDup = true;
                    alreadyReported[j] = true;
                }
            }
            if (isDup) {
                System.out.println("Duplicate Seat Number Found: " + seatNumbers[i]);
                foundDuplicate = true;
            }
        }

        if (!foundDuplicate) {
            System.out.println("No Duplicate Seats Found");
        }
    }

/*
================================================================================
2. The Typing Speed Test Accuracy Checker
Scenario: An online typing-practice website
Task:
• Accept two strings of equal length: the original passage and the user's typed text.
• Compare the two strings character by character using their positions.
• Count how many characters match at the same position.
• Calculate the accuracy percentage: (matched characters ÷ total characters) × 100.
• Print the accuracy percentage and the position of the first mismatch (or a message confirming there were no
mismatches).
Suggested method signature(s): void checkTypingAccuracy(String original, String typed)

Sample Input / Output:
original="hello world", typed="hello worlt" -> Matched: 10/11 | Accuracy: 90.91% | First Mismatch at position 11 ('d' vs 't')
original="coding", typed="coding" -> Matched: 6/6 | Accuracy: 100.00% | No Mismatches
================================================================================
*/
// Ans 2:
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
                    firstMismatchPos = i + 1;
                    origChar = original.charAt(i);
                    typedChar = typed.charAt(i);
                }
            }
        }

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

/*
================================================================================
3. The Traffic Signal Streak Analyzer
Scenario: The city traffic control department
Task:
• Accept a string representing a sequence of signal readings (e.g., "RRGGGYRR").
• Scan through the string and track the length of each streak of consecutive identical characters.
• Keep a running record of the longest streak found so far — both its color and its length.
• Print the color and length of the longest streak.
Suggested method signature(s): void findLongestStreak(String signalLog)

Sample Input / Output:
"RRGGGYRR" -> Longest Streak: 'G' repeated 3 times
"RRRRYYGG" -> Longest Streak: 'R' repeated 4 times
================================================================================
*/
// Ans 3:
    public static void findLongestStreak(String signalLog) {
        if (signalLog == null || signalLog.isEmpty()) {
            System.out.println("No signals recorded");
            return;
        }

        char longestColor = signalLog.charAt(0);
        int maxStreak = 1;

        char currentColor = signalLog.charAt(0);
        int currentStreak = 1;

        for (int i = 1; i < signalLog.length(); i++) {
            if (signalLog.charAt(i) == currentColor) {
                currentStreak++;
            } else {
                if (currentStreak > maxStreak) {
                    maxStreak = currentStreak;
                    longestColor = currentColor;
                }
                currentColor = signalLog.charAt(i);
                currentStreak = 1;
            }
        }

        if (currentStreak > maxStreak) {
            maxStreak = currentStreak;
            longestColor = currentColor;
        }

        System.out.printf("Longest Streak: '%c' repeated %d times%n", longestColor, maxStreak);
    }

/*
================================================================================
4. The Warehouse Inventory Balancer
Scenario: Retail warehouse
Task:
• Accept two integer arrays of equal length representing item quantities in Section A and Section B.
• Compute the total quantity held in each section.
• Compare the two totals and print whether the sections are "Balanced" or "Not Balanced".
• Scan both arrays to find the single highest quantity value and report which section and index it was found at.
Suggested method signature(s): void analyzeInventory(int[] sectionA, int[] sectionB)

Sample Input / Output:
sectionA={20,15,30}, sectionB={25,10,30}
Output: Section A Total: 65 | Section B Total: 65 | Status: Balanced | Highest Quantity: 30 (Section A, Item 3)
================================================================================
*/
// Ans 4:
    public static void analyzeInventory(int[] sectionA, int[] sectionB) {
        if (sectionA == null || sectionB == null) {
            System.out.println("Invalid inventory data");
            return;
        }

        int totalA = 0;
        int totalB = 0;

        int highestQty = Integer.MIN_VALUE;
        String highestSection = "";
        int highestItemIndex = -1;

        for (int i = 0; i < sectionA.length; i++) {
            totalA += sectionA[i];
            if (sectionA[i] > highestQty) {
                highestQty = sectionA[i];
                highestSection = "Section A";
                highestItemIndex = i + 1;
            }
        }

        for (int i = 0; i < sectionB.length; i++) {
            totalB += sectionB[i];
            if (sectionB[i] > highestQty) {
                highestQty = sectionB[i];
                highestSection = "Section B";
                highestItemIndex = i + 1;
            }
        }

        String status = (totalA == totalB) ? "Balanced" : "Not Balanced";

        System.out.printf("Section A Total: %d | Section B Total: %d | Status: %s | Highest Quantity: %d (%s, Item %d)%n",
                totalA, totalB, status, highestQty, highestSection, highestItemIndex);
    }

/*
================================================================================
5. The Movie Review Word Length Profiler
Scenario: Movie-review platform
Task:
• Accept a movie review as a single string input.
• Split the review into individual words.
• Classify each word as Short (1–4 letters), Medium (5–8 letters), or Long (9+ letters).
• Count how many words fall into each category.
• Print the final counts for Short, Medium, and Long words.
Suggested method signature(s): void classifyWordLengths(String review)

Sample Input / Output:
"This movie was absolutely fantastic and thrilling" -> Short: 3 | Medium: 1 | Long: 3
================================================================================
*/
// Ans 5:
    public static void classifyWordLengths(String review) {
        if (review == null || review.trim().isEmpty()) {
            System.out.println("Short: 0 | Medium: 0 | Long: 0");
            return;
        }

        String[] words = review.trim().split("\\s+");

        int shortCount = 0;
        int mediumCount = 0;
        int longCount = 0;

        for (String word : words) {
            String cleaned = word.replaceAll("[^a-zA-Z]", "");
            int len = cleaned.isEmpty() ? word.length() : cleaned.length();

            if (len >= 1 && len <= 4) {
                shortCount++;
            } else if (len >= 5 && len <= 8) {
                mediumCount++;
            } else if (len >= 9) {
                longCount++;
            }
        }

        System.out.printf("Short: %d | Medium: %d | Long: %d%n", shortCount, mediumCount, longCount);
    }

    public static void main(String[] args) {
        System.out.println("========== Q1: Exam Hall Seat Duplication Checker ==========");
        int[] test1 = {101, 102, 103, 102, 105};
        System.out.print("{101, 102, 103, 102, 105} -> ");
        checkDuplicateSeats(test1);

        int[] test2 = {101, 102, 103, 104, 105};
        System.out.print("{101, 102, 103, 104, 105} -> ");
        checkDuplicateSeats(test2);

        System.out.println("\n========== Q2: Typing Speed Test Accuracy Checker ==========");
        checkTypingAccuracy("hello world", "hello worlt");
        checkTypingAccuracy("coding", "coding");

        System.out.println("\n========== Q3: Traffic Signal Streak Analyzer ==========");
        System.out.print("\"RRGGGYRR\" -> ");
        findLongestStreak("RRGGGYRR");
        System.out.print("\"RRRRYYGG\" -> ");
        findLongestStreak("RRRRYYGG");

        System.out.println("\n========== Q4: Warehouse Inventory Balancer ==========");
        int[] sA = {20, 15, 30};
        int[] sB = {25, 10, 30};
        analyzeInventory(sA, sB);

        System.out.println("\n========== Q5: Movie Review Word Length Profiler ==========");
        classifyWordLengths("This movie was absolutely fantastic and thrilling");
    }
}