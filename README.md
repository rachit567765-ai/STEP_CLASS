# Week 2 Assignment — 5 Problems

This branch contains clean, fully-tested Java implementations for the Week 2 Assignment problems:

1. **ATM PIN Length Validator (`AtmPinValidator.java`)**
   - Method: `void checkPinLength(String pin)`
   - Basic length validation using `length()` and conditional check without loops.

2. **Word Reversal Encoder (`WordReversalEncoder.java`)**
   - Method: `String reverseEachWord(String sentence)`
   - Splits sentence using `split(" ")`, reverses each individual word with `StringBuilder`, and rejoins.

3. **Product Inventory CSV Parser (`ProductInventoryCsvParser.java`)**
   - Method: `void parseInventoryRecord(String csvLine)`
   - Parses CSV strings using `split(",")`, validates token count, and formats output.

4. **Library ISBN Normalizer & Validator (`LibraryIsbnValidator.java`)**
   - Methods:
     - `String normalizeCode(String raw)`
     - `String validateAndFormat(String code)`
   - Normalizes input with `trim()` and `substring()`, validates using `Character.isLetter()` / `isDigit()` without regex, and generates formatted ISBN records.

5. **Stop-Word-Filtered Word Frequency Report (`StopWordFrequencyReport.java`)**
   - Method: `void printFilteredWordFrequency(String feedback)`
   - Cleans text using `replace()`, splits by whitespace, filters out standard stop words, and tallies frequency sorted in descending order.

## Compilation & Execution
```bash
javac src/*.java
java -cp src AtmPinValidator
java -cp src WordReversalEncoder
java -cp src ProductInventoryCsvParser
java -cp src LibraryIsbnValidator
java -cp src StopWordFrequencyReport
```