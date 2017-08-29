// Arup Guha
// 9/4/2014, edited an error on 10/16/2015 - need to use a priority queue for the search.
// Solution to UCF 2005 High School Contest Problem: He got the Box!

import java.util.*;

public class box {

	public static void main(String[] args) {

		Scanner stdin = new Scanner(System.in);
		int r = stdin.nextInt();
		int c = stdin.nextInt();

        // Go through each case.
		while (r != 0) {

            // Read the input.
			char[][] grid = new char[r][];
			for (int i=0; i<r; i++)
				grid[i] = stdin.next().toCharArray();

            // Print the solution/
			System.out.println("He got the Box in "+solve(grid)+" steps!");

            // Get the next case.
			r = stdin.nextInt();
			c = stdin.nextInt();
		}
	}

	public static int solve(char[][] grid) {

        // Store answers in 2D array.
		int[][] dist = new int[grid.length][grid[0].length];
		for (int i=0; i<dist.length; i++)
			Arrays.fill(dist[i], -1);

        // Set up BFS.
		pair ben = findBen(grid);
		dist[ben.x][ben.y] = 0;
		PriorityQueue<pair> q = new PriorityQueue<pair>();
		q.offer(ben);

        // Run BFS.
		while (q.size() > 0) {

            // Get next item.
			pair cur = q.poll();
			int curd = dist[cur.x][cur.y];

            // We're done!
			if (grid[cur.x][cur.y] == 'X') return curd;

            // Get next places.
			ArrayList<pair>	next = cur.next(grid, dist);
			boolean teleport = grid[cur.x][cur.y] >= '0' && grid[cur.x][cur.y] <= '9';

            // Enqueue next places we can reach.
			for (int i=0; i<next.size(); i++) {
				pair item = next.get(i);
				q.offer(item);
				boolean toteleport = grid[item.x][item.y] >= '0' && grid[item.x][item.y] <= '9';

				// Teleporting doesn't count as a move...
				dist[next.get(i).x][next.get(i).y] = teleport && grid[cur.x][cur.y] == grid[item.x][item.y] ? curd: curd+1;
			}
		}

		// Should never get here.
		return -1;
	}

    // Returns Ben's location.
	public static pair findBen(char[][] grid) {
		for (int i=0; i<grid.length; i++)
			for (int j=0; j<grid[0].length; j++)
				if (grid[i][j] == 'B')
					return new pair(i,j,0);
		return null;
	}
}

class pair implements Comparable<pair> {

	public int x;
	public int y;
	public int distance;

	public pair(int myx, int myy, int d) {
		x = myx;
		y = myy;
		distance = d;
	}

    // Returns all of the subsequent locations from this square.
	public ArrayList<pair> next(char[][] grid, int[][] dist) {

		ArrayList<pair> ans = new ArrayList<pair>();

        // Teleport case.
		if (grid[x][y] >= '0' && grid[x][y] <= '9') {

            // Look for all places with this number.
			for (int i=0; i<grid.length; i++) {
				for (int j=0; j<grid[0].length; j++) {
					if (i==x && j==y) continue;
					if (grid[i][j] == grid[x][y] && dist[i][j] == -1)
						ans.add(new pair(i,j,distance));
				}
			}
		}

        // Just look to move in the cardinal directions.
		int[] dx = {-1,0,0,1};
		int[] dy = {0,-1,1,0};
		for (int i=0; i<4; i++)
			if (valid(x+dx[i], y+dy[i], dist, grid))
				ans.add(new pair(x+dx[i], y+dy[i], distance+1));

		return ans;
	}

    // Returns true iff this square is valid.
	public static boolean valid(int x, int y, int[][] dist, char[][] grid) {
		return x >= 0 && x < dist.length && y >= 0 && y < dist[0].length && dist[x][y] == -1 && grid[x][y] != 'W';
	}

	// How we want our priority queue to work.
	public int compareTo(pair other) {
		return this.distance - other.distance;
	}
}
