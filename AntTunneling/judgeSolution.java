// Arup Guha
// 5/2/2014
// Solution to UCF HS Contest Problem: Anti-absolute Values

import java.util.*;
import java.io.*;

public class ant {

	final public static int NOMST = -1;

	public static void main(String[] args) throws Exception {

		Scanner fin = new Scanner(new File("ant.in"));

		// Process all cases.
		int numCases = fin.nextInt();
		for (int loop=1; loop<=numCases; loop++) {

			int v = fin.nextInt();
			int e = fin.nextInt();
			edge[] eList = new edge[e];

			// Read in edges.
			for (int i=0; i<e; i++) {
				int v1 = fin.nextInt()-1;
				int v2 = fin.nextInt()-1;
				int w = fin.nextInt();
				eList[i] = new edge(v1, v2, w);
			}

			// Get mst weight.
			int res = mst(eList, v);

			// Not connected.
			if (res == NOMST)
				System.out.println("Campus #"+loop+": I'm a programmer, not a miracle worker!");

			// This is our solution.
			else
				System.out.println("Campus #"+loop+": "+res);
		}
		fin.close();
	}

	public static int mst(edge[] eList, int n) {

		djset myDJ = new djset(n);
		int edgeCount = 0, mstWeight = 0, i = 0;

		// Must have these in order for Kruskal's.
		Arrays.sort(eList);

		// Regular Kruskal's cutting out if all edges have been considered.
		while (edgeCount < n-1 && i < eList.length) {

			// Try to add this edge in.
			boolean res = myDJ.union(eList[i].v1, eList[i].v2);

			// Success!
			if (res) {
				edgeCount++;
				mstWeight += eList[i].w;
			}

			// Go to next edge.
			i++;
		}

		// Return weight or that no MST exists.
		if (edgeCount < n-1) return NOMST;
		return mstWeight;
	}
}

class djset {

	public int[] parents;
	public int[] heights;

	// New disjoint set of size n.
	public djset(int n) {
		parents = new int[n];
		heights = new int[n];
		for (int i=0; i<n; i++)
			parents[i] = i;
	}

	// Returns root of tree with node.
	public int find(int node) {
		if (parents[node] == node) return node;
		return find(parents[node]);
	}

	public boolean union(int node1, int node2) {

		// Check if same tree.
		int par1 = find(node1);
		int par2 = find(node2);
		if (par1 == par2) return false;

		// Attach shorter tree to longer tree.
		if (heights[par1] < heights[par2])
			parents[par1] = par2;
		else if (heights[par2] < heights[par1])
			parents[par2] = par1;

		// Equal to add 1 to height.
		else {
			parents[par1] = par2;
			heights[par2]++;
		}

		// We did it!
		return true;
	}
}

class edge implements Comparable<edge> {

	public int v1;
	public int v2;
	public int w;

	public edge(int start, int end, int weight) {
		v1 = start;
		v2 = end;
		w = weight;
	}

	public int compareTo(edge other) {
		return this.w - other.w;
	}
}
