// Arup Guha
// 3/10/2014
// Solution to 2014 FHSPS Playoff Problem: Seating Arrangements(seating)

import java.util.*;

public class seating {

	public static void main(String[] args) {

		Scanner stdin = new Scanner(System.in);
		int numCases = stdin.nextInt();

		// Process each case.
		for (int loop=0; loop<numCases; loop++) {
			int n = stdin.nextInt();
			boolean[] used = new boolean[2*n];
			System.out.println(solve(used));
		}
	}

	// Returns the number of solutions for filling in the unfilled seats in used
	// with remaining couples.
	public static int solve(boolean[] used) {

		// One way to fill 0 seats!
		if (allUsed(used)) return 1;

		int sum = 0;
		
		// i will represent the first unused seat.
		for (int i=0; i<used.length; i++) {
			
			// The next couple must sit at seat i and somewhere else.
			if (!used[i]) {
				used[i] = true;
				
				// Try this couple's mate at seat j, skipping the adjacent seat.
				for (int j=i+2; j<used.length; j++) {
					
					// Add up the answer from this seating for a couple.
					if (!used[j]) {
						used[j] = true;
						sum += solve(used);
						used[j] = false;
					}
				}
				used[i] = false;
				break;
			}
		}

		// Here is our answer.
		return sum;
	}

	// Returns true if the whole array is true.
	public static boolean allUsed(boolean[] used) {
		for (int i=0; i<used.length; i++)
			if (!used[i])
				return false;
		return true;
	}
}