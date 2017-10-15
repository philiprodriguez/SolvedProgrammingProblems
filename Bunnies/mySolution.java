/*
Should be an easy problem to solve. BFS from P to see if we can hit C.
O(V+E) = O(R*C) time

Yep, easy. Took about half an hour.
*/

import java.util.*;

public class mySolution
{
    public static void main(String[] args)
    {
        Scanner scan = new Scanner(System.in);
        int numCases = scan.nextInt();

        //Handle each case of input
        for(int curCase = 0; curCase < numCases; curCase++)
        {
            int numRows = scan.nextInt();
            int numCols = scan.nextInt();

            char[][] grid = new char[numRows][numCols];
            Point peterLoc = null;
            Point cottonLoc = null;

            //Read in our grid of numRows lines!
            for(int r = 0; r < numRows; r++)
            {
                String line = scan.next();
                for(int c = 0; c < numCols; c++)
                {
                    grid[r][c] = line.charAt(c);

                    //Keep track of where we see peter & cotton...
                    if (grid[r][c] == 'P')
                    {
                        peterLoc = new Point(r, c);
                    }
                    if (grid[r][c] == 'C')
                    {
                        cottonLoc = new Point(r, c);
                    }
                }
            }

            //Now we want to BFS the thing!
            if (hasPath(peterLoc, cottonLoc, grid))
            {
                System.out.println("yes");
            }
            else
            {
                System.out.println("no");
            }
        }
    }

    public static final int[] dr = {0, 0, 1, -1};
    public static final int[] dc = {1, -1, 0, 0};
    public static boolean hasPath(Point peterLoc, Point cottonLoc, char[][] grid)
    {
        //Assume that '#' means visited and wall...

        Queue<Point> q = new LinkedList<Point>();

        //Enqueue our start point, peterLoc
        q.add(peterLoc);

        while(!q.isEmpty())
        {
            Point curLoc = q.poll();

            if (curLoc.r == cottonLoc.r && curLoc.c == cottonLoc.c)
            {
                //We made it!
                return true;
            }

            //Look around curLoc...
            for(int d = 0; d < dr.length; d++)
            {
                //Check the point in direction d..
                Point newLoc = new Point(curLoc.r+dr[d], curLoc.c+dc[d]);

                //Validity check...
                if (validNextPoint(newLoc, grid))
                {
                    //It is valid! Visit it and enqueue!
                    q.add(newLoc);
                    grid[newLoc.r][newLoc.c] = '#';
                }
            }
        }

        //If we're here, we never made it to cottontail...
        return false;
    }

    public static boolean validNextPoint(Point point, char[][] grid)
    {
        if (point.r < 0)
            return false;
        if (point.c < 0)
            return false;
        if (point.r >= grid.length)
            return false;
        if (point.c >= grid[point.r].length)
            return false;

        //Now we know it is in range.. but is it a wall or visited?
        if (grid[point.r][point.c] == '#')
            return false;

        //If we're here, then it is in range and not a wall or visited
        return true;
    }

    private static class Point
    {
        int r, c;
        public Point(int r, int c)
        {
            this.r = r;
            this.c = c;
        }
    }
}
