// Arup Guha
// 4/20/2014
// Solution to 2010 UCF HS Online Contest Problem: Driving in Circles

import java.util.*;

public class circles {

	final public static int NOEDGE = 1000000000;

	public static void main(String[] args) {

		Scanner stdin = new Scanner(System.in);
		int numCases = stdin.nextInt();

		// Go through each case.
		for (int loop=0; loop<numCases; loop++) {

			int numCircles = stdin.nextInt();
			int numStreets = stdin.nextInt();
			int n = 2*numStreets + 2;
			double[][] adj = new double[n][n];
			for (int i=0; i<n; i++) Arrays.fill(adj[i], NOEDGE);

			int[] radii = new int[numCircles];
			for (int i=0; i<numCircles; i++)
				radii[i] = stdin.nextInt();

			// Fill in connectors.
			point[] pts = new point[n];
			for (int i=0; i<numStreets; i++) {
				int street = stdin.nextInt();
				double angle = stdin.nextInt()*Math.PI/180;
				pts[2*i] = new point(radii[street-1], angle);
				pts[2*i+1] = new point(radii[street], angle);
				adj[2*i][2*i+1] = radii[street]-radii[street-1];
				adj[2*i+1][2*i] = radii[street]-radii[street-1];
			}

			// Read in start and end pts.
			for (int i=2*numStreets; i<2*numStreets+2; i++) {
				int street = stdin.nextInt();
				double angle = stdin.nextInt()*Math.PI/180;
				pts[i] = new point(radii[street-1],angle);
			}

			// Fill in distances on same circle.
			for (int i=0; i<n; i++) {
				for (int j=0; j<n; j++) {
					if (i == j) adj[i][j] = 0;
					else if (adj[i][j] == NOEDGE) {
						adj[i][j] = dist(pts,i,j);
						adj[j][i] = adj[i][j];
					}
				}
			}

			// Floyd's is fastest distance algorithm to type in, but
			// not the fastest running for a single source problem...
			for (int k=0; k<n; k++)
				for (int i=0; i<n; i++)
					for (int j=0; j<n; j++)
						adj[i][j] = Math.min(adj[i][j], adj[i][k]+adj[k][j]);

			// Voila! Here is our answer.
			System.out.printf("%.2f\n", adj[n-1][n-2]);
		}
	}

	// Calculates distance from pts[i] to pts[j] assuming no connectors.
	public static double dist(point[] pts, int i, int j) {
		if (pts[i].radius != pts[j].radius) return NOEDGE;
		double diff = Math.abs(pts[i].angle-pts[j].angle);
		double minAngle = Math.min(diff, 2*Math.PI-diff);
		return pts[i].radius*minAngle;
	}
}

class point {

	public int radius;
	public double angle;

	public point(int r, double a) {
		radius = r;
		angle = a;
	}
}