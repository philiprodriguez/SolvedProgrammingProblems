import java.util.*;


/*

  Looked like a BFS problem, smelled like a BFS problem, was a BFS problem.
  500*500 is only 250000. BFS is O(V+E). 250,000 nodes, and thus at most
  1004*250000 = 251000000 edges. Big number? Yes. Run in under a second still?
  Probably, on a modern machine.

*/

//begin 7:39
//end 8:15

public class mySolution
{
  public static void main(String[] args)
  {
    //System.out.println(getPossibleNextMoves(new Loc(2, 2), 3, 10, new boolean[10][10]));


    Scanner scan = new Scanner(System.in);
    int numCases = scan.nextInt();
    for(int c = 0; c < numCases; c++)
    {
      int boardSize = scan.nextInt();
      int k = scan.nextInt();

      //-1 to 0 base the board
      Loc start = new Loc(scan.nextInt()-1, scan.nextInt()-1);
      Loc finish = new Loc(scan.nextInt()-1, scan.nextInt()-1);

      //To avoid hashing, I'll use a boolean visited array!
      boolean[][] visited = new boolean[boardSize][boardSize];

      //BFS for that space!

      //Parallel Q's
      Queue<Loc> q = new LinkedList<Loc>();
      Queue<Integer> distq = new LinkedList<Integer>();

      //We can get to start in 0 moves!
      q.add(start);
      distq.add(0);
      visited[start.r][start.c]=true;

      while(!q.isEmpty())
      {
        Loc curLoc = q.poll();
        int curDist = distq.poll();
        //Manual equality check to avoid writing hashcode and equals...
        if (curLoc.r == finish.r && curLoc.c == finish.c)
        {
          //We made it!
          System.out.println(curDist);
          break;
        }

        //Otherwise, we aren't there, so look around us!
        for(Loc nextLoc : getPossibleNextMoves(curLoc, k, boardSize, visited))
        {
          //Visit and enqueue!
          visited[nextLoc.r][nextLoc.c] = true;
          q.add(nextLoc);
          distq.add(curDist + 1);
        }
      }

      //Should never get here?
      //System.out.println("ENDING");
    }
  }

  //Knight moves...
  public static final int[] dr = {-2, -2,  2,  2, -1,  1, -1,  1};
  public static final int[] dc = {-1,  1, -1,  1, -2, -2,  2,  2};
  public static ArrayList<Loc> getPossibleNextMoves(Loc l, int k, int boardSize, boolean[][] visited)
  {
    ArrayList<Loc> nextMoves = new ArrayList<Loc>();

    //Generate Knight moves...
    for(int d = 0; d < dr.length; d++)
    {
      Loc newLoc = new Loc(l.r+dr[d], l.c+dc[d]);
      if (rangeCheck(newLoc, boardSize) && !visited[newLoc.r][newLoc.c])
      {
        //In range and not yet visited! Good to go!
        nextMoves.add(newLoc);
      }
    }

    //Generate other moves...
    //(along rows and cols from -k to +k row modification)
    for(int m = -k; m <= k; m++)
    {
      //Vertically
      Loc newLoc = new Loc(l.r+m, l.c);
      if (rangeCheck(newLoc, boardSize) && !visited[newLoc.r][newLoc.c])
      {
        nextMoves.add(newLoc);
      }

      //Horizontally
      newLoc = new Loc(l.r, l.c+m);
      if (rangeCheck(newLoc, boardSize) && !visited[newLoc.r][newLoc.c])
      {
        nextMoves.add(newLoc);
      }
    }


    return nextMoves;
  }

  public static boolean rangeCheck(Loc location, int boardSize)
  {
    return location.r >= 0 && location.c >= 0 && location.r < boardSize && location.c < boardSize;
  }

  private static class Loc
  {
    int r,c;
    public Loc(int r, int c)
    {
      this.r=r;
      this.c=c;
    }

    public String toString()
    {
      return "[" + r + ", " + c + "]";
    }
  }
}
