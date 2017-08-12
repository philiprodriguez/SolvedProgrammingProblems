// Arup Guha
// 2/9/2016
// Solution to 2012 Mercer Contest Problem #2: Calculator Games

import java.util.*;

public class calc{

	final public static int SIZE = 100;
	final public static int MAX = 1000000000;

	public static void main(String[] args) {

		Scanner stdin = new Scanner(System.in);

		// Go through and solve each case.
		int numCases = stdin.nextInt();
		for (int i=0; i<numCases; i++) {

			int start = stdin.nextInt();
			System.out.println(solve(start));
		}
	}

	// Runs a BFS starting at value start, keeping track of
	// all values reached in the range [1..SIZE-1].
	public static int solve(int start) {

		// Nothing is visited yet.
		int[] visited = new int[SIZE];
		Arrays.fill(visited, -1);

		// Set up our queue.
		LinkedList<pair> queue = new LinkedList<pair>();
		queue.add(new pair(start, 0));
		visited[start] = 0;

		int numvisited = 1;
		int max = 0;
		int curlength = 0;

		// Do the BFS.
		while (numvisited < SIZE-1) {

            // Get next item and its potential neighbors.
			pair p = queue.poll();
			ArrayList<Integer> next = getNext(p.value);

			// Try enqueuing each item.
			for (int i=0; i<next.size(); i++) {

                int item = next.get(i);

                // Item is in range, process it.
                if (item > 0 && item < SIZE && visited[item] == -1) {
                    visited[item] = p.distance+1;
                    queue.add(new pair(item, visited[item]));
                    numvisited++;
                    max = visited[item];
                }

                // Just in case going out of bounds leads us to a valid answer.
                else if (item == 0 || item >= SIZE) queue.add(new pair(item, p.distance+1));
			}
		}

		// This was the most number of button presses we needed.
		return max;
	}

    // Returns the possible numbers that can follow x on the calculator display.
	public static ArrayList<Integer> getNext(int x) {
        ArrayList<Integer> res = new ArrayList<Integer>();

        // These always get added.
        res.add(x+1);
        res.add(x/2);

        // Just trying to avoid overflow.
        if (x < MAX/3+1) res.add(3*x);

        return res;
	}
}

class pair {

	public int value;
	public int distance;

	public pair(int v, int d) {
		value = v;
		distance = d;
	}
}
