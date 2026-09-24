public class FileExtensionValidator {

    public static String validateFileExtension(String filename) {
        if (filename == null || !filename.contains(".")) {
            return "Rejected — invalid file type";
        }

        int dotIndex = filename.lastIndexOf('.');
        if (dotIndex == -1 || dotIndex == filename.length() - 1) {
            return "Rejected — invalid file type";
        }

        String extension = filename.substring(dotIndex + 1);

        if (extension.equalsIgnoreCase("pdf") ||
            extension.equalsIgnoreCase("docx") ||
            extension.equalsIgnoreCase("zip")) {
            return "Accepted";
        } else {
            return "Rejected — invalid file type";
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Day 2 Problem 3: File Extension Validator ===");
        
        System.out.printf("\"Assignment1.PDF\" -> %s%n", validateFileExtension("Assignment1.PDF"));
        System.out.printf("\"notes.txt\" -> %s%n", validateFileExtension("notes.txt"));
    }
}