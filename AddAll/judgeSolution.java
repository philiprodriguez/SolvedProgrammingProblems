// Arup Guha
// 9/13/2013
// Solution to UCF JV Practice Problem A: Add All

import java.util.*;

public class addall {

	public static void main(String[] args) {

		Scanner stdin = new Scanner(System.in);
		int numCases = stdin.nextInt();

		// Go through each case.
		for (int loop=0; loop<numCases; loop++) {

			int n = stdin.nextInt();
			PriorityQueue<Long> q = new PriorityQueue<Long>();

			// Put everything in the queue.
			for (int i=0; i<n; i++)
				q.offer(stdin.nextLong());

			// If all values are 10000, the cost might overflow int.
			// It's roughly 10000^2*log(10000) which is close enough I don't want to take a chance...
			long cost = 0;

			// Just do a greedy; take the two smallest remaining...
			while (q.size() > 1) {

				// Take out the two smallest.
				long a = q.poll();
				long b = q.poll();

				// Update our cost.
				cost += (a+b);

				// Put back the combined value...
				q.offer(a+b);
			}

			// This is our answer...
			System.out.println(cost);
		}
	}
}