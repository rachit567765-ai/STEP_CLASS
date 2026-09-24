public class ExamSeatDuplicationChecker {

    // Compare seat numbers using arrays and nested loops only (no Collections class)
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

    public static void main(String[] args) {
        System.out.println("=== Week 1 Problem 1: Exam Hall Seat Duplication Checker ===");
        
        int[] test1 = {101, 102, 103, 102, 105};
        System.out.print("{101, 102, 103, 102, 105} -> ");
        checkDuplicateSeats(test1);

        int[] test2 = {101, 102, 103, 104, 105};
        System.out.print("{101, 102, 103, 104, 105} -> ");
        checkDuplicateSeats(test2);
    }
}