public class BmiCalculator {

    public static String getBmiStatus(double bmi) {
        if (bmi < 18.5) {
            return "Underweight";
        } else if (bmi <= 24.9) {
            return "Normal";
        } else if (bmi <= 29.9) {
            return "Overweight";
        } else {
            return "Obese";
        }
    }

    public static void printWellnessReport(double[] heights, double[] weights) {
        int n = Math.min(heights.length, weights.length);
        System.out.println("----------------------------------------------------------------------");
        System.out.printf("%-10s | %-12s | %-12s | %-8s | %-12s%n",
                "Person", "Height (m)", "Weight (kg)", "BMI", "Status");
        System.out.println("----------------------------------------------------------------------");

        for (int i = 0; i < n; i++) {
            double h = heights[i];
            double w = weights[i];
            double bmi = w / (h * h);
            String status = getBmiStatus(bmi);
            System.out.printf("Person %-3d | %-12.2f | %-12.2f | %-8.2f | %-12s%n",
                    (i + 1), h, w, bmi, status);
        }
        System.out.println("----------------------------------------------------------------------");
    }

    public static void main(String[] args) {
        System.out.println("=== Day 1 Problem 3: BMI Calculator for a Team ===");
        
        // Sample demonstration
        double[] sampleHeights = {1.75, 1.60, 1.80, 1.65, 1.70, 1.55, 1.85, 1.68, 1.72, 1.62};
        double[] sampleWeights = {70.0, 90.0, 65.0, 75.0, 80.0, 42.0, 95.0, 58.0, 72.0, 85.0};

        // Showing sample records
        double bmi1 = sampleWeights[0] / (sampleHeights[0] * sampleHeights[0]);
        System.out.printf("Person 1 — Height: %.2f m, Weight: %.0f kg -> BMI: %.2f | Status: %s%n",
                sampleHeights[0], sampleWeights[0], bmi1, getBmiStatus(bmi1));

        double bmi2 = sampleWeights[1] / (sampleHeights[1] * sampleHeights[1]);
        System.out.printf("Person 2 — Height: %.2f m, Weight: %.0f kg -> BMI: %.2f | Status: %s%n%n",
                sampleHeights[1], sampleWeights[1], bmi2, getBmiStatus(bmi2));

        printWellnessReport(sampleHeights, sampleWeights);
    }
}
