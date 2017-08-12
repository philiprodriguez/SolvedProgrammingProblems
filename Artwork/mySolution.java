
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.StringTokenizer;

/**
 *
 * @author Philip
 */
/*
    Artwork

    This problem seems difficult but is actually not too hard once you are told
    to look at the problem in reverse order of the strokes, and use disjoint
    set union to get the results in reverse order. There is a lot of helper
    methods to write out, but overall the problem is relatively simple to 
    understand at a somewhat high level and becomes more of an implementation
    problem after that.
*/
public class a {
    public static StringTokenizer st;
    public static void main(String[] args) throws IOException
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());
        int width = Integer.parseInt(st.nextToken());
        int height = Integer.parseInt(st.nextToken());
        int numStrokes = Integer.parseInt(st.nextToken());
        
        FancyDisjointSets fds = new FancyDisjointSets(width*height);
        
        //0 means white, over 0 means count of overlapping black...
        int[][] board = new int[width][height];
        ArrayList<Stroke> strokes = new ArrayList<>();
        for(int s = 0; s < numStrokes; s++)
        {
            st = new StringTokenizer(br.readLine());
            Stroke ns = new Stroke(new Point(Integer.parseInt(st.nextToken())-1, Integer.parseInt(st.nextToken())-1), new Point(Integer.parseInt(st.nextToken())-1, Integer.parseInt(st.nextToken())-1));
            strokes.add(ns);
            ns.performOn(board, fds);
        }
        
        initializeFDS(fds, board);
        
        //Unperform strokes! Save results!
        Stack<Integer> results = new Stack<>();
        results.push(fds.totalSets-fds.disabledSets);
        for(int s = strokes.size()-1; s >= 0; s--)
        {
            ArrayList<ArrayList<Point>> ccs = strokes.get(s).undoStroke(board);
            processNewCCs(ccs, board, fds);
            int numSets =  fds.totalSets-fds.disabledSets;
            results.push(numSets);
        }
        results.pop();
        while(!results.isEmpty())
        {
            System.out.println(results.pop());
        }
    }
    
    public static void processNewCCs(ArrayList<ArrayList<Point>> ccs, int[][] board, FancyDisjointSets fds)
    {
        //Re-enable sets in fds the just became white
        for(int cc = 0; cc < ccs.size(); cc++)
        {
            fds.disabledSets -= ccs.get(cc).size();
        }
        
        //Union stuff
        for(int cc = 0; cc < ccs.size(); cc++)
        {
            for(int p = 0; p < ccs.get(cc).size(); p++)
            {
                Point thisPoint = ccs.get(cc).get(p);
                for(int d = 0; d < 4; d++)
                {
                    Point otherPoint = new Point(thisPoint.x+dx[d], thisPoint.y+dy[d]);
                    if (inRange(otherPoint, board) && board[otherPoint.x][otherPoint.y] == 0)
                    {
                        fds.union(getCellNumber(board.length, board[0].length, thisPoint), getCellNumber(board.length, board[0].length, otherPoint));
                    }
                }
            }
        }
    }
    
    public static void initializeFDS(FancyDisjointSets fds, int[][] board)
    {
        boolean[][] visited = new boolean[board.length][board[0].length];
        for(int y = 0; y < board[0].length; y++)
        {
            for(int x = 0; x < board.length; x++)
            {
                Point p = new Point(x, y);
                if (!visited[x][y] && board[x][y] == 0)
                {
                    BFS(p, board, visited, fds);
                }
            }
        }
    }
    
    public static final int[] dx = { 0, 0, 1,-1};
    public static final int[] dy = {-1, 1, 0, 0};
    public static void BFS(Point p, int[][] board, boolean[][] visited, FancyDisjointSets fds)
    {
        Queue<Point> q = new LinkedList<>();
        
        q.add(p);
        visited[p.x][p.y] = true;
        
        while(!q.isEmpty())
        {
            Point curPoint = q.poll();
            
            for(int d = 0; d < 4; d++)
            {
                Point newPoint = new Point(curPoint.x+dx[d], curPoint.y+dy[d]);
                if (inRange(newPoint, board) && !visited[newPoint.x][newPoint.y] && board[newPoint.x][newPoint.y] == 0)
                {
                    visited[newPoint.x][newPoint.y] = true;
                    q.add(newPoint);
                    fds.union(getCellNumber(board.length, board[0].length, curPoint), getCellNumber(board.length, board[0].length, newPoint));
                }
            }
        }
    }
    
    public static boolean inRange(Point p, int[][] board)
    {
        return p.x >= 0 && p.y >= 0 && p.x < board.length && p.y < board[0].length;
    }
    
    public static int getCellNumber(int width, int height, Point p)
    {
        return p.y*width+p.x;
    }
    
    public static void printBoard(int[][] board)
    {
        System.out.println("Board:");
        for(int y = 0; y < board[0].length; y++)
        {
            for(int x = 0; x < board.length; x++)
            {
                System.out.print(board[x][y]);
            }
            System.out.println();
        }
    }
    
    private static class FancyDisjointSets
    {
        int totalSets;
        int disabledSets;
        int[] parent;
        
        public FancyDisjointSets(int size)
        {
            parent = new int[size];
            for(int i = 0; i < size; i++)
                parent[i] = i;
            totalSets = size;
            disabledSets = 0;
        }
        
        public int findRoot(int n)
        {
            if (parent[n] == n)
                return parent[n];
            return parent[n] = findRoot(parent[n]);
        }
        
        public void union(int a, int b)
        {
            if (!sameSet(a, b))
                totalSets--;
            parent[findRoot(a)] = findRoot(b);
        }
        
        public boolean sameSet(int a, int b)
        {
            return findRoot(a) == findRoot(b);
        }
    }
    
    private static class Stroke
    {
        Point p1, p2;
        public Stroke(Point p1, Point p2)
        {
            this.p1 = p1;
            this.p2 = p2;
        }
        
        public void performOn(int[][] board, FancyDisjointSets fds)
        {
            if (p1.x == p2.x)
            {
                //vertical stroke
                int miny = Math.min(p1.y, p2.y);
                int maxy = Math.max(p1.y, p2.y);
                
                for(int y = miny; y <= maxy; y++)
                {
                    board[p1.x][y]++;
                    if (board[p1.x][y] == 1)
                    {
                        fds.disabledSets++;
                    }
                }
            }
            else
            {
                //y1 must equal y2
                //horizontal stroke
                int minx = Math.min(p1.x, p2.x);
                int maxx = Math.max(p1.x, p2.x);
                
                for(int x = minx; x <= maxx; x++)
                {
                    board[x][p1.y]++;
                    if (board[x][p1.y] == 1)
                    {
                        fds.disabledSets++;
                    }
                }
            }
        }
        
        //Returns a list of lists of points, each sub list represents one
        //connected component of points that has newly appeared from
        //undoing the operation!
        public ArrayList<ArrayList<Point>> undoStroke(int[][] board)
        {
            int lastPos = -10;
            ArrayList<ArrayList<Point>> results = new ArrayList<>();
            if (p1.x == p2.x)
            {
                //vertical stroke
                int miny = Math.min(p1.y, p2.y);
                int maxy = Math.max(p1.y, p2.y);
                
                for(int y = miny; y <= maxy; y++)
                {
                    board[p1.x][y]--;
                    if (board[p1.x][y] == 0)
                    {
                        if(Math.abs(lastPos-y) > 1)
                        {
                            //New cc
                            results.add(new ArrayList<Point>());
                            results.get(results.size()-1).add(new Point(p1.x, y));
                        }
                        else
                        {
                            //Old cc
                            results.get(results.size()-1).add(new Point(p1.x, y));
                        }
                        lastPos = y;
                    }
                }
            }
            else
            {
                //y1 must equal y2
                //horizontal stroke
                int minx = Math.min(p1.x, p2.x);
                int maxx = Math.max(p1.x, p2.x);
                
                for(int x = minx; x <= maxx; x++)
                {
                    board[x][p1.y]--;
                    if (board[x][p1.y] == 0)
                    {
                        if(Math.abs(lastPos-x) > 1)
                        {
                            //New cc
                            results.add(new ArrayList<Point>());
                            results.get(results.size()-1).add(new Point(x, p1.y));
                        }
                        else
                        {
                            //Old cc
                            results.get(results.size()-1).add(new Point(x, p1.y));
                        }
                        lastPos = x;
                    }
                }
            }
            return results;
        }
    }
    
    private static class Point
    {
        int x,y;
        public Point(int x, int y)
        {
            this.x = x;
            this.y = y;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 17 * hash + this.x;
            hash = 17 * hash + this.y;
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final Point other = (Point) obj;
            if (this.x != other.x) {
                return false;
            }
            if (this.y != other.y) {
                return false;
            }
            return true;
        }
        
        
        
        public String toString()
        {
            return "(" + x + ",  " + y + ")";
        }
    }
}
