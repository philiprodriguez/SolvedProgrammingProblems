// Arup Guha
// 3/30/2012
// Solution to 2012 UCF HS Contest Problem: Vacation
// Translated to Java on 3/18/2015

import java.util.*;

class pt {

    public int x;
    public int y;

    public pt(int myx, int myy) {
        x = myx;
        y = myy;
    }

    public double dist(pt other) {
        return Math.sqrt((x-other.x)*(x-other.x) + (y-other.y)*(y-other.y));
    }
}


public class vacation {

    final public static int NOSOL = 1000000;

    public static void main(String[] args) {

        Scanner stdin = new Scanner(System.in);
        int numparks = stdin.nextInt();

        // Go through each case.
        for (int loop=1; loop<=numparks; loop++) {

            int numrides = stdin.nextInt();
            int numblockedpaths = stdin.nextInt();

            // Read in the location of each ride.
            pt[] rideloc = new pt[numrides];
            for (int i=0; i<numrides; i++) {
                int x = stdin.nextInt();
                int y = stdin.nextInt();
                rideloc[i] = new pt(x, y);
            }

            // Store which paths are blocked in an adjacency matrix.
            // if blocked[i][j] = 1, it's blocked.
            int[][] blocked = new int[numrides][numrides];
            for (int i=0; i<numblockedpaths; i++) {
                int v1 = stdin.nextInt() - 1;
                int v2 = stdin.nextInt() - 1;
                blocked[v1][v2] = 1;
                blocked[v2][v1] = 1;
            }

            // Set up recursive call.
            int[] perm = new int[numrides];
            boolean[] used = new boolean[numrides];
            double ans = solve(perm, used, 0, rideloc, blocked);

            // Output result.
            System.out.printf("Vacation #%d:\n", loop);
            if (ans < NOSOL)
                System.out.printf("Jimmy can finish all of the rides in %.3f seconds.\n\n", ans+numrides*120);
            else
                System.out.printf("Jimmy should plan this vacation a different day.\n\n");
        }
    }

    // Returns the length of the shortest path that hits each ride exactly
    // once (a Hamiltonian Path). If none exists, 1000000 is returned. Note
    // that for this problem, this value is sufficient because no single edge
    // will be more than 2000, and at most there are 10 of them. I just picked
    // a million because it's easily big enough an a nice round number.
    public static double solve(int[] perm, boolean[] used, int k, pt[] rideloc, int[][] blocked) {

        // Finished case!
        if (k == perm.length) return calcdist(perm, rideloc);

        double best = NOSOL;
        for (int i=0; i<perm.length; i++) {

            // See if ride i is a valid place to go next.
            if (!used[i]) {
                if (k == 0 || blocked[perm[k-1]][i] == 0) {

                    perm[k] = i;
                    used[i] = true;

                    // See if going this way helps us.
                    best = Math.min(best, solve(perm, used, k+1, rideloc, blocked));

                    // Need to mark this as unused, so a new ride can be put in spot k.
                    used[i] = false;
                }
            }
        } // end for

        return best;
    }

    public static double calcdist(int[] perm, pt[] rideloc) {

        // Add in the distance of the first segment.
        pt cur = new pt(0,0);
        double dist = 0;

        // Add in each subsequent edge in the path.
        for (int i=0; i<perm.length; i++) {
            dist = dist + cur.dist(rideloc[perm[i]]);
            cur = rideloc[perm[i]];
        }
        return dist;
    }
}
