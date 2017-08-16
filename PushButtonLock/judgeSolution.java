// Arup Guha
// 5/19/2013
// Solution to 2006 Greater NY Regional Problem D: Push Lock

import java.util.*;

public class lock {

	public final static int MAX = 11;

	public static void main(String[] args) {

		// Will store all of our answers.
		long[] ans = new long[MAX+1];
		ans[0] = 0;
		ans[1] = 1;

		// Fill in chart for all the values.
		for (int i=2; i<=MAX; i++) {

			long sum = 0;

			// j represents the number of items in the first combination.
			for (int j=1; j<=i; j++) {

				// combo(i, j) is number of first combinations.
				// We append to this all sequences of length i-j, plus the empty sequence.
				sum = sum + combo(i, j)*(ans[i-j] + 1);
			}
			ans[i] = sum;
		}

		// Process cases.
		Scanner stdin = new Scanner(System.in);
		int numCases = stdin.nextInt();

		for (int loop=1; loop<=numCases; loop++) {
			int n = stdin.nextInt();
			System.out.println(loop+" "+n+" "+ans[n]);
		}

	}

	// Will suffice for our small range of values.
	public static long combo(int n, int k) {
		return fact(n)/fact(k)/fact(n-k);
	}

	public static long fact(int n) {
		long ans = 1;
		for (int i=1; i<=n; i++)
			ans *= i;
		return ans;
	}
}