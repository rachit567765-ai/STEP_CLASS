public class MovieReviewWordLengthProfiler {

    public static void classifyWordLengths(String review) {
        if (review == null || review.trim().isEmpty()) {
            System.out.println("Short: 0 | Medium: 0 | Long: 0");
            return;
        }

        // Split by whitespace
        String[] words = review.trim().split("\\s+");

        int shortCount = 0;
        int mediumCount = 0;
        int longCount = 0;

        for (String word : words) {
            // Strip any extraneous punctuation from edges to get true letter length
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
        System.out.println("=== Week 1 Problem 5: The Movie Review Word Length Profiler ===");
        
        String sample = "This movie was absolutely fantastic and thrilling";
        System.out.println("Review: \"" + sample + "\"");
        classifyWordLengths(sample);
    }
}