// Arup Guha
// Solution to 2013 NCNA Problem G: Centroid of Point Masses
// 9/11/2016

import java.util.*;

public class centroid {

	public static void main(String[] args) {

		Scanner stdin = new Scanner(System.in);
		int n = stdin.nextInt();
		int loop = 1;

		// Process each case.
		while (n > 0) {

			// Just a weighted sum.
			long sumX = 0, sumY = 0, sumW = 0;
			for (int i=0; i<n; i++) {
				long x = stdin.nextLong();
				long y = stdin.nextLong();
				long w = stdin.nextLong();
				sumX += (x*w);
				sumY += (y*w);
				sumW += w;
			}

			// Output the weighted average for x and y.
			System.out.printf("Case %d: %.2f %.2f\n", loop, ((double)sumX)/sumW, ((double)sumY)/sumW);

			// Get next case.
			n = stdin.nextInt();
			loop++;
		}
	}
}