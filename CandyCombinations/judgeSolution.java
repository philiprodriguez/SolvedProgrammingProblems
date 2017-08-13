// Arup Guha
// 7/23/2013
// Solution to SI@UCF Final Contest Problem: More Combos

import java.util.*;

public class morecombos {

	public static void main(String[] args) {

		Scanner stdin = new Scanner(System.in);
		int numCases = stdin.nextInt();

		// Go through each case.
		for (int loop=0; loop<numCases; loop++) {

			int numBags = stdin.nextInt();
			int numChoices = stdin.nextInt();

			// Read in each bag and just store its bitmask.
			// Shift items to be 0-based by subtracting 1, so bits are [0,30].
			int[] sets = new int[numBags];
			for (int i=0; i<numBags; i++) {
				int numItems = stdin.nextInt();
				for (int j=0; j<numItems; j++)
					sets[i] |= (1 << (stdin.nextInt()-1));
			}

			// Output result.
			System.out.println(solve(sets, numChoices));
		}
	}

	// Returns the maximum number of unique bits set on amongst any subset of
	// k items from sets.
	public static int solve(int[] sets, int k) {
		return solveRec(sets, k, 0, 0, 0);
	}

	public static int solveRec(int[] sets, int max, int curChosen, int curIndex, int curMask) {

		// Base case - all chosen or no more to choose from.
		if (curChosen == max || curIndex == sets.length)
			return Integer.bitCount(curMask);

		// Try both options and return the best.
		int take = solveRec(sets, max, curChosen+1, curIndex+1, curMask | sets[curIndex]);
		int skip = solveRec(sets, max, curChosen, curIndex+1, curMask);
		return Math.max(take, skip);
	}
}