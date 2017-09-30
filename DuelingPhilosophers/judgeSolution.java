// Arup Guha
// 11/18/2012
// Solution to 2012 South East Regional (D2) Problem D: Dueling Philosophers

/*** Edited on 3/1/2013 to utilize iterative Topological Sort Algorithm taught
 *   in recitation.
 ***/

import java.util.*;

public class duel {

	public static void main(String[] args) {

		Scanner stdin = new Scanner(System.in);

		int n = stdin.nextInt();
		int numE = stdin.nextInt();

		while (n != 0) {

			// Set up ordering matrix.
			vertex[] eList = new vertex[n];
			for (int i=0; i<n; i++)
				eList[i] = new vertex();

			// Put in edges in both the appropriate in and out degree lists.
			for (int i=0; i<numE; i++) {
				int prev = stdin.nextInt();
				int next = stdin.nextInt();
				eList[prev-1].out.add(next-1);
				eList[next-1].in.add(prev-1);
			}

			// Run a topological sort.
			topSort prob = new topSort(eList);
			System.out.println(prob.runAlg());

			// Get next case.
			n = stdin.nextInt();
			numE = stdin.nextInt();
		}
	}

}

// Allows us to quickly add and subtract vertices from a graph...
class vertex {

	public HashSet<Integer> in;
	public HashSet<Integer> out;

	public vertex() {
		in = new HashSet<Integer>();
		out = new HashSet<Integer>();
	}

}

class topSort {

	// Easier to run our sort storing all this stuff in an object.
	private int[] ordering;
	private boolean[] used;
	private int n;
	private vertex[] eList;

	// Set everything up to start the algorithm.
	public topSort(vertex[] list) {
		eList = list;
		n = list.length;
		used = new boolean[n];
		ordering = new int[n];
	}

	// Here is our topological sort, adjusted to return 0, 1 or 2, for
	// 0, 1 or more than 1 possible sorted output.
	public int runAlg() {

		// Fill spots forward.
		int curIndex = 0;
		int retVal = 1;
		while (curIndex < n) {

			// Pre-count vertices in this step.
			int cnt = 0;
			int[] remove = new int[n];
			for (int i=0; i<n; i++)
				if (!used[i] && eList[i].in.size() == 0)
					remove[cnt++] = i;

			// If no vertices have in degree 0 at any iteration, algorithm fails.
			if (cnt == 0) return 0;

			// Stores that if we find 1 viable option later, there must at least be 2.
			if (cnt > 1) retVal = 2;

			// Now process 0 degree vertices, deleting them.
			for (int i=0; i<cnt; i++) {

				// Put this item in our list.
				ordering[curIndex] = remove[i];
				curIndex++;
				used[remove[i]] = true;

				// Remove edges leaving i.
				for (Integer e: eList[remove[i]].out)
					eList[e].in.remove(remove[i]);
			}
		}

		return retVal;
	}

}