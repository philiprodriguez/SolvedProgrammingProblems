import java.util.*;

/*
    Okay, that's really bizzare. I literally coded this up as closely to my
    original python solution as possible, and this thing runs BlAzInG fast in
    comparison to the python version! What the heck dude! Is python really that
    much slower? I thought python was also converted to bytecode! I know python
    lacks a JIT compiler though, so I guess that may be it, but damn dude I
    did not expect that much of a time difference. More reason to use Java for
    competitive programming (and in the real world, for that matter) I guess.

    Anyways, praise the Java gods, and I'll just leave my two python solutions
    here for anyone who wants to take a peek at them. This is a Java clone of
    the second python version.
*/

public class mySolution
{

    public static final double inf = 999999999.0;

    public static void main(String[] args)
    {
        Scanner scan = new Scanner(System.in);

        int numCases = scan.nextInt();
        for(int c = 0; c < numCases; c++)
        {
            int numRides = scan.nextInt();
            int numBlockedPaths = scan.nextInt();

            Point[] nodeLoc = new Point[numRides+1];
            nodeLoc[0] = new Point(0, 0);
            boolean[][] blocked = new boolean[numRides+1][numRides+1];

            for(int r = 1; r < numRides+1; r++)
            {
                int x = scan.nextInt();
                int y = scan.nextInt();
                nodeLoc[r] = new Point(x, y);
            }

            for(int b = 0; b < numBlockedPaths; b++)
            {
                int start = scan.nextInt();
                int end = scan.nextInt();
                blocked[start][end] = true;
                blocked[end][start] = true;
            }

            boolean[] visited = new boolean[nodeLoc.length];
            visited[0] = true;
            double answer = bestTime(1, 0, 0, visited, nodeLoc, blocked);
            System.out.println("Vacation #" + (c+1) + ":");
            if (answer >= inf)
            {
                System.out.println("Jimmy should plan this vacation a different day.");
                System.out.println();
            }
            else
            {
                System.out.format("Jimmy can finish all of the rides in %.3f seconds.", answer);
                System.out.println();
                System.out.println();
            }
        }
    }

    public static double bestTime(int index, int last, double timeTaken, boolean[] visited, Point[] nodeLoc, boolean[][] blocked)
    {
        if(index >= nodeLoc.length)
        {
            return timeTaken;
        }

        double minTime = inf;
        for(int nextNode = 1; nextNode < nodeLoc.length; nextNode++)
        {
            if (!visited[nextNode] && !blocked[last][nextNode])
            {
                double nextTimeTaken = timeTaken+120+distance(nodeLoc[last], nodeLoc[nextNode]);
                if (nextTimeTaken < minTime)
                {
                    visited[nextNode] = true;
                    minTime = Math.min(minTime, bestTime(index+1, nextNode, nextTimeTaken, visited, nodeLoc, blocked));
                    visited[nextNode] = false;
                }
            }
        }
        return minTime;
    }

    public static double distance(Point p1, Point p2)
    {
        return Math.sqrt(Math.pow(p2.x-p1.x, 2.0) + Math.pow(p2.y-p1.y, 2.0));
    }

    private static class Point
    {
        int x;
        int y;
        public Point(int x, int y)
        {
            this.x = x;
            this.y = y;
        }
    }
}
