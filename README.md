# Day 1 Live-Coding Session — 5 Problems

This branch contains clean, fully-tested Java implementations for the Day 1 Live-Coding Session problems:

1. **Rock-Paper-Scissors Game (`RockPaperScissors.java`)**
   - Method: `String playRound(String playerMove, String computerMove)`
   - Computes winner, generates random computer moves, tracks wins/losses/draws, and outputs round tables and win percentages.

2. **Palindrome Checker (3 Approaches) (`PalindromeChecker.java`)**
   - Methods:
     - `boolean isPalindromeIterative(String text)`
     - `boolean isPalindromeRecursive(String text)`
     - `boolean isPalindromeArrayReversal(String text)`
   - Validates text using all three approaches and ensures consistency.

3. **BMI Calculator for a Team (`BmiCalculator.java`)**
   - Methods:
     - `String getBmiStatus(double bmi)`
     - `void printWellnessReport(double[] heights, double[] weights)`
   - Computes BMI, categorizes health status, and displays formatted reports.

4. **First Non-Repeating Character (`FirstNonRepeatingChar.java`)**
   - Method: `char findFirstNonRepeatingChar(String text)`
   - Scans string left-to-right to find the first character with a frequency of exactly 1.

5. **Reverse Customer Name (`ReverseCustomerName.java`)**
   - Method: `String reverseCustomerName(String customerName)`
   - In-place character array reversal keeping original name immutable.

## Compilation & Execution
```bash
javac src/*.java
java -cp src RockPaperScissors
java -cp src PalindromeChecker
java -cp src BmiCalculator
java -cp src FirstNonRepeatingChar
java -cp src ReverseCustomerName
```
