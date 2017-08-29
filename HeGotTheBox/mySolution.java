import java.util.*;

/*
    Ah yes, a beautiful BFS problem. I love BFS. It is so intuitive. I love
    BFS and I love Dijkstra's, but i'll do BFS for this problem first, since
    frankly the algorithm is just a little less messy and should absolutely
    run in time for this problem with a max of 400 nodes and ~1600 edges.

*/

public class mySolution
{
    public static void main(String[] args)
    {
        Scanner scan = new Scanner(System.in);
        while(true)
        {
            int numRows = scan.nextInt();
            int numCols = scan.nextInt();

            if (numRows == 0 || numCols == 0)
                break;

            //Scan in our board (O(r*c))
            Point startPoint = null;
            Point endPoint = null;
            char[][] board = new char[numRows][numCols];
            for(int r = 0; r < numRows; r++)
            {
                String line = scan.next();
                for(int c = 0; c < numCols; c++)
                {
                    board[r][c] = line.charAt(c);
                    if (board[r][c] == 'B')
                    {
                        startPoint = new Point(r, c);
                    }
                    if (board[r][c] == 'X')
                    {
                        endPoint = new Point(r, c);
                    }
                }
            }

            //Link up our portals (O(r*c) since at most 10 portals)
            HashMap<Point, Point> portals = new HashMap<>();
            for(int r = 0; r < numRows; r++)
            {
                for(int c = 0; c < numCols; c++)
                {
                    if (Character.isDigit(board[r][c]))
                    {
                        Point start = new Point(r, c);
                        Point end = getOtherPoint(board, new Point(r, c));
                        portals.put(start, end);
                        portals.put(end, start);
                    }
                }
            }

            //Do the thing! (O(r*c) since V+E <= r*c + 4*r*c)
            System.out.println("He got the Box in " + findBox(startPoint, endPoint, board, portals) + " steps!");

        }
    }

    private static Point getOtherPoint(char[][] board, Point entrance)
    {
        char target = board[entrance.r][entrance.c];
        for(int r = 0; r < board.length; r++)
        {
            for(int c = 0; c < board[r].length; c++)
            {
                if (board[r][c] == target && !(entrance.equals(new Point(r, c))))
                    return new Point(r, c);
            }
        }
        return null;
    }

    private static final int[] dr = {0, 0, 1, -1};
    private static final int[] dc = {1, -1, 0, 0};

    public static int findBox(Point start, Point end, char[][] board, HashMap<Point, Point> portals)
    {
        //Instead of an explicit visited array, I will wall off visited places
        //since that has the same effect and requires no additional space.

        //Parallel BFS Queues, one for location and one for state distance
        Queue<Point> qloc = new LinkedList<Point>();
        Queue<Integer> qdist = new LinkedList<Integer>();

        //Enqueue and visit start!
        qloc.add(start);
        qdist.add(0);
        board[start.r][start.c] = 'W';

        while(!qloc.isEmpty())
        {
            Point curLoc = qloc.poll();
            int curDist = qdist.poll();

            //Are we done?
            if (curLoc.equals(end))
                return curDist;

            //Where can we go?
            for(int d = 0; d < dr.length; d++)
            {
                Point newLoc = new Point(curLoc.r+dr[d], curLoc.c+dc[d]);

                //Range check and visited check
                if (!isValid(board, newLoc))
                    continue;

                //We are good to go! Enqueue and visit that point!
                qloc.add(newLoc);
                qdist.add(curDist+1);
                board[newLoc.r][newLoc.c] = 'W';

                //If this was a portal, we must also enqueue the exit of it...
                Point portalExit = portals.get(newLoc);
                if (portalExit != null)
                {
                    qloc.add(portalExit);
                    qdist.add(curDist+1);
                    board[portalExit.r][portalExit.c] = 'W';
                }
            }
        }

        //We should never make it here
        return -1;
    }

    private static boolean isValid(char[][] board, Point loc)
    {
        //Make sure loc is in range and not visited/a wall
        if (loc.r >= board.length || loc.r < 0)
            return false;
        if (loc.c >= board[loc.r].length || loc.c < 0)
            return false;
        if (board[loc.r][loc.c] == 'W')
            return false;
        return true;
    }

    private static class Point
    {
        int r, c;
        public Point(int r, int c)
        {
            this.r=r;
            this.c=c;
        }

        public int hashCode()
        {
            int h = 31;
            h = 7 * h * r;
            h = 13 * h * c;
            return h;
        }

        public boolean equals(Object o)
        {
            if (this == o)
                return true;
            if (o == null)
                return false;
            if (!(o instanceof Point))
                return false;
            Point other = (Point)o;
            return this.r == other.r && this.c == other.c;
        }
    }
}
