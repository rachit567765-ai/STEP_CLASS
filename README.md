# Day 2 Live-Coding Session — 5 Problems

This branch contains clean, fully-tested Java implementations for the Day 2 Live-Coding Session problems:

1. **Vowel & Consonant Counter (`VowelConsonantCounter.java`)**
   - Method: `void countVowelsAndConsonants(String text)`
   - Iterates through characters using `charAt()`, classifying letters into vowels and consonants while ignoring spaces.

2. **CSV Student Record Parser (`CsvStudentRecordParser.java`)**
   - Method: `void parseStudentRecord(String csvLine)`
   - Parses CSV string using `split(",")`, validates the field count, and formats student data.

3. **File Extension Validator (`FileExtensionValidator.java`)**
   - Method: `String validateFileExtension(String filename)`
   - Extracts file extension using `lastIndexOf('.')` and `substring()`, matching against allowed extensions case-insensitively.

4. **Masked Phone Number Formatter (`MaskedPhoneNumberFormatter.java`)**
   - Method: `String maskPhoneNumber(String phone)`
   - Validates 10-digit numeric phone numbers and formats them using `StringBuilder` into `XXXXXX-####`.

5. **Bank Transaction Reference Generator & Validator (`BankReferenceValidator.java`)**
   - Methods:
     - `String normalizeReference(String raw)`
     - `String validateAndFormat(String reference)`
   - Trims and normalizes bank codes, validates format without regex using `Character.isLetter()` and `Character.isDigit()`, and formats into `[BANKCODE] DATE: dd/MM/yy | SEQ: 12345`.

## Compilation & Execution
```bash
javac src/*.java
java -cp src VowelConsonantCounter
java -cp src CsvStudentRecordParser
java -cp src FileExtensionValidator
java -cp src MaskedPhoneNumberFormatter
java -cp src BankReferenceValidator
```