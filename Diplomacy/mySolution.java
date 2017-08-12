
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 *
 * Author Philip
 */
/*
    So, the real key to this problem is realizing that you can brute force over
    the 100 start points, and then that, assuming that start point, the best
    you can do is always equivalent to the best you can do by always flipping
    that start node back and forth...

    I believe the logic is along the lines of this:
        All nodes need to be flipped at some point, so who you flip at any point
        really doesn't matter, just the fact that you perform a flip that brings
        down the number of divides...

    You can also represent this situation as the furthest away node from the
    start with a graph of 0/1 edges where if the edge is between two same color
    nodes, then the weight is 0. Otherwis,e the weight is 1.
*/
public class c {
    public static void main(String[] args)
    {
        Scanner scan = new Scanner(System.in);
        
        int numNodes;
        while((numNodes = scan.nextInt()) != 0)
        {
            int numEdges = scan.nextInt();
            
            int[] color = new int[numNodes];
            ArrayList<Integer>[] adjList = new ArrayList[numNodes];
            
            for(int n = 0; n < numNodes; n++)
            {
                color[n] = scan.nextInt();
                adjList[n] = new ArrayList<Integer>();
            }
            
            for(int e = 0; e < numEdges; e++)
            {
                int start = scan.nextInt()-1;
                int end = scan.nextInt()-1;
                adjList[start].add(end);
                adjList[end].add(start);
            }
            
            //Suppose we start at the node start...
            int min = 999999999;
            for(int start = 0; start < numNodes; start++)
            {
                int rm = requiredMonths(start, adjList, color);
                min = Math.min(min, rm);
            }
            System.out.println(min);
        }
    }
    
    //Returns the number of moves required to get everyone on the same color assuming we first flip node [start].
    public static int oo = 999999;
    public static int requiredMonths(int start, ArrayList<Integer>[] adjList, int[] color)
    {
        int numVisited = 0;
        boolean[] visited = new boolean[adjList.length];
        int[] dist = new int[adjList.length];
        Arrays.fill(dist, oo);
        dist[start] = 0;
        
        PriorityQueue<DijState> q = new PriorityQueue<>();
        q.add(new DijState(start, 0));
        
        
        while(!q.isEmpty() && numVisited < adjList.length)
        {
            DijState curState = q.poll();
            if (visited[curState.node])
                continue;
            visited[curState.node] = true;
            numVisited++;
            
            for(int dest : adjList[curState.node])
            {
                int weight = color[curState.node] == color[dest] ? 0 : 1;
                if (dist[curState.node]+weight < dist[dest])
                {
                    dist[dest] = dist[curState.node]+weight;
                    q.add(new DijState(dest, dist[dest]));
                }
            }
        }
        
        int maxDist = -oo;
        for(int d : dist)
            maxDist = Math.max(maxDist, d);
        return maxDist;
    }
    
    private static class DijState implements Comparable<DijState>
    {
        int node;
        int dist;
        public DijState(int node, int dist)
        {
            this.node = node;
            this.dist = dist;
        }

        @Override
        public int compareTo(DijState t) {
            return dist-t.dist;
        }
    }
}
