public class WordReversalEncoder {

    public static String reverseEachWord(String sentence) {
        if (sentence == null) {
            return null;
        }

        String[] words = sentence.split(" ");
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < words.length; i++) {
            StringBuilder reversedWord = new StringBuilder();
            String w = words[i];
            for (int j = w.length() - 1; j >= 0; j--) {
                reversedWord.append(w.charAt(j));
            }
            result.append(reversedWord);
            if (i < words.length - 1) {
                result.append(" ");
            }
        }

        return result.toString();
    }

    public static void main(String[] args) {
        System.out.println("=== Week 2 Problem 2: Word Reversal Encoder ===");
        
        String input = "hello club";
        String encoded = reverseEachWord(input);
        System.out.printf("\"%s\" -> %s%n", input, encoded);
    }
}