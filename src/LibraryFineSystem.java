class BookIssue {
    private String title;
    private String borrowerName;
    private int daysOverdue;

    public BookIssue(String title, String borrowerName, int daysOverdue) {
        this.title = title;
        this.borrowerName = borrowerName;
        this.daysOverdue = daysOverdue;
    }

    public String getTitle() {
        return title;
    }

    public int getDaysOverdue() {
        return daysOverdue;
    }

    // Instance method: fine amount depends on the specific book issue's daysOverdue
    public double fineAmount() {
        return daysOverdue > 0 ? daysOverdue * 5.0 : 0.0;
    }

    // Instance method: checks if this individual book issue is severely overdue
    public boolean isSeverelyOverdue() {
        return daysOverdue > 14;
    }

    // Static method: totalFineCollected calculates the aggregate fine across an array of BookIssue objects.
    // JUSTIFICATION:
    // `fineAmount()` is an instance method because it directly relies on the instance-specific state (`daysOverdue`)
    // of an individual book issue. In contrast, `totalFineCollected()` is static because it operates over an entire
    // collection/array of issues as an aggregate utility computation that belongs to the BookIssue class as a whole,
    // rather than to any individual book instance.
    public static double totalFineCollected(BookIssue[] issues) {
        if (issues == null) {
            return 0.0;
        }
        double total = 0.0;
        for (BookIssue issue : issues) {
            if (issue != null) {
                total += issue.fineAmount();
            }
        }
        return total;
    }
}

public class LibraryFineSystem {

    public static void main(String[] args) {
        System.out.println("=== Week 3 Problem F1: From Procedural Mess to a Working Library Fine System ===");

        BookIssue[] issues = {
            new BookIssue("Clean Code", "Alice", 18),
            new BookIssue("Effective Java", "Bob", 5),
            new BookIssue("Refactoring", "Charlie", 0),
            new BookIssue("DSA Handbook", "David", 21),
            new BookIssue("Design Patterns", "Emma", 9)
        };

        for (BookIssue issue : issues) {
            String status = issue.isSeverelyOverdue() ? "Severely overdue" : "OK";
            System.out.printf("%s - %d days - %s%n", issue.getTitle(), issue.getDaysOverdue(), status);
        }

        double totalFine = BookIssue.totalFineCollected(issues);
        System.out.printf("Total fine collected: Rs %.1f%n", totalFine);
    }
}