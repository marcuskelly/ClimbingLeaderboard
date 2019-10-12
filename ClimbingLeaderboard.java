import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;

public class Solution {

    // Complete the climbingLeaderboard function below.
    static int[] climbingLeaderboard(int[] scores, int[] alice) {

        int previousScore = -1;
        int startPosition = scores.length-1; // To remember position on leaderboard
        int[] leaderboard = new int[scores.length];

        // Remove duplicates from scores and add to leaderboard
        for (int i = 0, j = 0; i < scores.length; i++, j++) {
            if (scores[i] != previousScore) {
                leaderboard[j] = scores[i];
            }
            else {
                j--;
                startPosition--;
            }
            previousScore = scores[i];
        } // end for

        final int LOWEST_RANK = startPosition +2;
        final int LOWEST_SCORE = leaderboard[startPosition];
        
        for (int i = 0; i < alice.length; i++) {
            // If Alice's score is >= top score
            if (alice[i] >= leaderboard[0]) {
                alice[i] = 1; // Alice's rank is 1
                continue;
            }
            // If Alice's score is < the lowest score
            if (alice[i] < LOWEST_SCORE) {
                alice[i] = LOWEST_RANK; // Alices's rank is the lowest rank
                continue;
            }
            // Start at end of leaderboard and work back
            for (int j = startPosition; j >=0; j--) {
                // If Alice's score > current leaderboard score 
                if (alice[i] > leaderboard[j]) {
                    continue; // Move on to next leaderboard score
                }
                if (alice[i] == leaderboard[j]) {
                    alice[i] = (j+1); // Alice's rank is index position + 1
                }
                else {
                    alice[i] = (j+2); // Alice's rank is index position + 2
                }
                startPosition = j;
                break;
            } // end inner for
        } // end outer for
        return alice;
    } // end climbingLeaderboard

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int scoresCount = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        int[] scores = new int[scoresCount];

        String[] scoresItems = scanner.nextLine().split(" ");
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int i = 0; i < scoresCount; i++) {
            int scoresItem = Integer.parseInt(scoresItems[i]);
            scores[i] = scoresItem;
        }

        int aliceCount = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        int[] alice = new int[aliceCount];

        String[] aliceItems = scanner.nextLine().split(" ");
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int i = 0; i < aliceCount; i++) {
            int aliceItem = Integer.parseInt(aliceItems[i]);
            alice[i] = aliceItem;
        }

        int[] result = climbingLeaderboard(scores, alice);

        for (int i = 0; i < result.length; i++) {
            bufferedWriter.write(String.valueOf(result[i]));

            if (i != result.length - 1) {
                bufferedWriter.write("\n");
            }
        }

        bufferedWriter.newLine();

        bufferedWriter.close();

        scanner.close();
    }
}

