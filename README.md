# Week 1 Assignment — 5 Problems

This branch contains clean, fully-tested Java implementations for the Week 1 Assignment problems:

1. **The Exam Hall Seat Duplication Checker (`ExamSeatDuplicationChecker.java`)**
   - Method: `void checkDuplicateSeats(int[] seatNumbers)`
   - Pure nested loop and array-based duplicate detection without any Collections framework.

2. **The Typing Speed Test Accuracy Checker (`TypingAccuracyChecker.java`)**
   - Method: `void checkTypingAccuracy(String original, String typed)`
   - Position-by-position character comparison, matching percentage calculation, and first-mismatch indexing.

3. **The Traffic Signal Streak Analyzer (`TrafficSignalStreakAnalyzer.java`)**
   - Method: `void findLongestStreak(String signalLog)`
   - Single-pass scanning to track consecutive repeating signal colors and detect the maximum streak.

4. **The Warehouse Inventory Balancer (`WarehouseInventoryBalancer.java`)**
   - Method: `void analyzeInventory(int[] sectionA, int[] sectionB)`
   - Computes section totals, compares balance status, and identifies the maximum quantity item across sections.

5. **The Movie Review Word Length Profiler (`MovieReviewWordLengthProfiler.java`)**
   - Method: `void classifyWordLengths(String review)`
   - Splits review text into words and buckets them into Short (1–4), Medium (5–8), and Long (9+) words.

## Compilation & Execution
```bash
javac src/*.java
java -cp src ExamSeatDuplicationChecker
java -cp src TypingAccuracyChecker
java -cp src TrafficSignalStreakAnalyzer
java -cp src WarehouseInventoryBalancer
java -cp src MovieReviewWordLengthProfiler
```