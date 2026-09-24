import java.util.*;

public class Solution {

/*
================================================================================
1. Rock-Paper-Scissors Game
Scenario: The College Coding Arcade
Task:
• Generate the computer's move randomly (Rock, Paper, or Scissors) for each round.
• Accept the player's move for each round (via input, or a predefined list for a live demo).
• Determine and display the winner of each round using standard Rock-Paper-Scissors rules.
• Repeat for N rounds (suggested N = 5).
• After all rounds, print a summary table: Round | Player Move | Computer Move | Result.
• Print the total Wins, Losses, Draws, and the player's Win Percentage.
Suggested method signature(s): String playRound(String playerMove, String computerMove)

Sample Input / Output:
Round 1 — Player: Rock, Computer: Scissors -> Player Wins
Round 2 — Player: Paper, Computer: Paper -> Draw
Round 3 — Player: Scissors, Computer: Rock -> Computer Wins
Final Summary (after 5 rounds) Wins: 2 | Losses: 2 | Draws: 1 | Win % = 40.0%
================================================================================
*/
// Ans 1:
    public static String playRound(String playerMove, String computerMove) {
        if (playerMove.equalsIgnoreCase(computerMove)) {
            return "Draw";
        }
        String p = playerMove.toLowerCase();
        String c = computerMove.toLowerCase();
        if ((p.equals("rock") && c.equals("scissors")) ||
            (p.equals("paper") && c.equals("rock")) ||
            (p.equals("scissors") && c.equals("paper"))) {
            return "Player Wins";
        } else {
            return "Computer Wins";
        }
    }

    public static void runRpsGame(String[] playerMoves, String[] computerMoves) {
        int rounds = playerMoves.length;
        String[] results = new String[rounds];
        int wins = 0, losses = 0, draws = 0;

        for (int i = 0; i < rounds; i++) {
            results[i] = playRound(playerMoves[i], computerMoves[i]);
            if (results[i].equals("Player Wins")) wins++;
            else if (results[i].equals("Computer Wins")) losses++;
            else draws++;
            System.out.printf("Round %d — Player: %s, Computer: %s -> %s%n",
                    (i + 1), playerMoves[i], computerMoves[i], results[i]);
        }

        System.out.println("\nRound | Player Move | Computer Move | Result");
        for (int i = 0; i < rounds; i++) {
            System.out.printf("%-5d | %-11s | %-13s | %-10s%n",
                    (i + 1), playerMoves[i], computerMoves[i], results[i]);
        }

        double winPercentage = ((double) wins / rounds) * 100.0;
        System.out.printf("Final Summary (after %d rounds) Wins: %d | Losses: %d | Draws: %d | Win %% = %.1f%%%n",
                rounds, wins, losses, draws, winPercentage);
    }

/*
================================================================================
2. Palindrome Checker (3 Approaches)
Scenario: The QA Text Verification Toolkit
Task:
• Accept a text input (e.g., a word or short phrase).
• Implement an iterative check: compare characters from both ends moving toward the middle.
• Implement a recursive check: recursively compare the first and last characters, shrinking the substring each call.
• Implement an array-reversal check: convert the string to a character array, reverse it, and compare it to the original.
• Print the result of all three approaches for the same input — they should always agree.
Suggested method signature(s): 
boolean isPalindromeIterative(String text) + 
boolean isPalindromeRecursive(String text) + 
boolean isPalindromeArrayReversal(String text)

Sample Input / Output:
"madam" -> Iterative: Palindrome | Recursive: Palindrome | Array Reversal: Palindrome
"hello" -> Iterative: Not Palindrome | Recursive: Not Palindrome | Array Reversal: Not Palindrome
================================================================================
*/
// Ans 2:
    public static boolean isPalindromeIterative(String text) {
        if (text == null) return false;
        int left = 0, right = text.length() - 1;
        while (left < right) {
            if (text.charAt(left) != text.charAt(right)) return false;
            left++;
            right--;
        }
        return true;
    }

    public static boolean isPalindromeRecursive(String text) {
        if (text == null) return false;
        if (text.length() <= 1) return true;
        if (text.charAt(0) != text.charAt(text.length() - 1)) return false;
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
            if (original[i] != reversed[i]) return false;
        }
        return true;
    }

    public static void checkPalindromeAllApproaches(String text) {
        String iter = isPalindromeIterative(text) ? "Palindrome" : "Not Palindrome";
        String rec = isPalindromeRecursive(text) ? "Palindrome" : "Not Palindrome";
        String arr = isPalindromeArrayReversal(text) ? "Palindrome" : "Not Palindrome";
        System.out.printf("\"%s\" -> Iterative: %s | Recursive: %s | Array Reversal: %s%n",
                text, iter, rec, arr);
    }

/*
================================================================================
3. BMI Calculator for a Team
Scenario: The Corporate Wellness Program
Task:
• Take height (m) and weight (kg) for a team of people (suggested: 10; use arrays, and random values for a fast live demo).
• Compute BMI = weight / (height × height).
• Classify status: BMI < 18.5 → Underweight; 18.5–24.9 → Normal; 25–29.9 → Overweight; ≥30 → Obese.
• Display a table: Person | Height (m) | Weight (kg) | BMI | Status.
Suggested method signature(s): 
String getBmiStatus(double bmi) + 
void printWellnessReport(double[] heights, double[] weights)

Sample Input / Output:
Person 1 — Height: 1.75 m, Weight: 70 kg -> BMI: 22.86 | Status: Normal
Person 2 — Height: 1.60 m, Weight: 90 kg -> BMI: 35.16 | Status: Obese
================================================================================
*/
// Ans 3:
    public static String getBmiStatus(double bmi) {
        if (bmi < 18.5) return "Underweight";
        else if (bmi <= 24.9) return "Normal";
        else if (bmi <= 29.9) return "Overweight";
        else return "Obese";
    }

    public static void printWellnessReport(double[] heights, double[] weights) {
        int n = Math.min(heights.length, weights.length);
        System.out.println("Person | Height (m) | Weight (kg) | BMI | Status");
        for (int i = 0; i < n; i++) {
            double bmi = weights[i] / (heights[i] * heights[i]);
            System.out.printf("Person %d — Height: %.2f m, Weight: %.0f kg -> BMI: %.2f | Status: %s%n",
                    (i + 1), heights[i], weights[i], bmi, getBmiStatus(bmi));
        }
    }

/*
================================================================================
4. First Non-Repeating Character
Scenario: The Unique Letter Hunt Mini-Game
Task:
• Accept an input string (a word or short sentence).
• Compute the frequency of every character in the string.
• Scan the string left to right and find the first character whose frequency is exactly 1.
• Print that character, or a clear message if no non-repeating character exists.
Suggested method signature(s): char findFirstNonRepeatingChar(String text)

Sample Input / Output:
"swiss" -> First Non-Repeating Character: 'w'
"aabbcc" -> No Non-Repeating Character Found
================================================================================
*/
// Ans 4:
    public static char findFirstNonRepeatingChar(String text) {
        if (text == null || text.isEmpty()) return '\0';
        Map<Character, Integer> freq = new LinkedHashMap<>();
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            freq.put(ch, freq.getOrDefault(ch, 0) + 1);
        }
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            if (freq.get(ch) == 1) return ch;
        }
        return '\0';
    }

    public static void checkFirstNonRepeating(String text) {
        char ch = findFirstNonRepeatingChar(text);
        if (ch != '\0') {
            System.out.printf("\"%s\" -> First Non-Repeating Character: '%c'%n", text, ch);
        } else {
            System.out.printf("\"%s\" -> No Non-Repeating Character Found%n", text);
        }
    }

/*
================================================================================
5. Reverse Customer Name
Scenario: The Customer Identity Verification System
Task:
• Develop a method that reverses the given customer name.
• The method takes one input argument: customerName.
• It should return the name in reverse order.
• Call this method from main and print both the original and reversed names.
Suggested method signature(s): String reverseCustomerName(String customerName)

Sample Input / Output:
"Sunil"
Original Name: Sunil
Reversed Name: linuS
================================================================================
*/
// Ans 5:
    public static String reverseCustomerName(String customerName) {
        if (customerName == null) return null;
        char[] chars = customerName.toCharArray();
        int left = 0, right = chars.length - 1;
        while (left < right) {
            char temp = chars[left];
            chars[left] = chars[right];
            chars[right] = temp;
            left++;
            right--;
        }
        return new String(chars);
    }

    public static void main(String[] args) {
        System.out.println("========== Q1: Rock-Paper-Scissors Game ==========");
        String[] pMoves = {"Rock", "Paper", "Scissors", "Rock", "Scissors"};
        String[] cMoves = {"Scissors", "Paper", "Rock", "Paper", "Scissors"};
        runRpsGame(pMoves, cMoves);

        System.out.println("\n========== Q2: Palindrome Checker (3 Approaches) ==========");
        checkPalindromeAllApproaches("madam");
        checkPalindromeAllApproaches("hello");

        System.out.println("\n========== Q3: BMI Calculator for a Team ==========");
        double[] heights = {1.75, 1.60};
        double[] weights = {70.0, 90.0};
        printWellnessReport(heights, weights);

        System.out.println("\n========== Q4: First Non-Repeating Character ==========");
        checkFirstNonRepeating("swiss");
        checkFirstNonRepeating("aabbcc");

        System.out.println("\n========== Q5: Reverse Customer Name ==========");
        String customer = "Sunil";
        System.out.println("Original Name: " + customer);
        System.out.println("Reversed Name: " + reverseCustomerName(customer));
    }
}