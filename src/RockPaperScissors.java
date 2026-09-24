import java.util.Random;

public class RockPaperScissors {

    public static String playRound(String playerMove, String computerMove) {
        if (playerMove.equalsIgnoreCase(computerMove)) {
            return "Draw";
        }

        String p = playerMove.toLowerCase();
        String c = computerMove.toLowerCase();

        if ((p.equals("rock") && c.equals("scissors")) ||
            (p.equals("paper") && c.equals("rock")) ||
            (p.equals("scissors") && c.equals("paper"))) {
            return "Player Wins";
        } else {
            return "Computer Wins";
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Day 1 Problem 1: Rock-Paper-Scissors Game ===");
        
        // Sample demonstration matching PDF
        String[] samplePlayerMoves = {"Rock", "Paper", "Scissors", "Rock", "Scissors"};
        String[] sampleComputerMoves = {"Scissors", "Paper", "Rock", "Paper", "Scissors"};

        runMatch(samplePlayerMoves, sampleComputerMoves);
    }

    public static void runMatch(String[] playerMoves, String[] computerMoves) {
        int rounds = playerMoves.length;
        String[] results = new String[rounds];
        int wins = 0;
        int losses = 0;
        int draws = 0;

        for (int i = 0; i < rounds; i++) {
            results[i] = playRound(playerMoves[i], computerMoves[i]);
            if (results[i].equals("Player Wins")) {
                wins++;
            } else if (results[i].equals("Computer Wins")) {
                losses++;
            } else {
                draws++;
            }
            System.out.printf("Round %d — Player: %s, Computer: %s -> %s%n",
                    (i + 1), playerMoves[i], computerMoves[i], results[i]);
        }

        System.out.println("\n------------------------------------------------------------");
        System.out.printf("%-8s | %-12s | %-14s | %-15s%n", "Round", "Player Move", "Computer Move", "Result");
        System.out.println("------------------------------------------------------------");
        for (int i = 0; i < rounds; i++) {
            System.out.printf("%-8d | %-12s | %-14s | %-15s%n",
                    (i + 1), playerMoves[i], computerMoves[i], results[i]);
        }
        System.out.println("------------------------------------------------------------");

        double winPercentage = ((double) wins / rounds) * 100.0;
        System.out.printf("Final Summary (after %d rounds) Wins: %d | Losses: %d | Draws: %d | Win %% = %.1f%%%n",
                rounds, wins, losses, draws, winPercentage);
    }
}
