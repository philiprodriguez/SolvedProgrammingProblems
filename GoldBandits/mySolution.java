
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Stack;



/**
 *
 * Author Philip
 */
public class f {
    public static void main(String[] args)
    {
        Scanner scan = new Scanner(System.in);
        
        int numNodes;
        while((numNodes = scan.nextInt()) != 0)
        {
            int numEdges = scan.nextInt();
            
            //Node 0 really doesn't exist since numbering starts at 1.
            int[] gold = new int[numNodes+1];
            ArrayList<Edge>[] adjList = new ArrayList[numNodes+1];
            
            //Initialize our adjList...
            for(int i = 1; i < adjList.length; i++)
                adjList[i] = new ArrayList<Edge>();
            
            //Read in gold amounts in each village...
            for(int i = 3; i <= numNodes; i++)
                gold[i] = scan.nextInt();
            
            //Read in our edges, build graph...
            for(int e = 0; e < numEdges; e++)
            {
                int start = scan.nextInt();
                int end = scan.nextInt();
                adjList[start].add(new Edge(end, 1));
                adjList[end].add(new Edge(start, 1));
            }
            
            //Get the length of the shortest path to the end...
            int shortestPathLength = dijkstra(adjList, 1)[2];
            
            //Run dijkstra backwards to prepare to find all shortest paths...
            int[] distToEnd = dijkstra(adjList, 2);
            
            //Find all shortest paths!
            allShortestPaths = new ArrayList<>();
            Stack<Integer> psf = new Stack<Integer>();
            psf.push(1);
            getAllShortestPaths(1, 0, 2, shortestPathLength, distToEnd, adjList, psf);
            
            //Now, for each shortest path, determine the best we can do!
            int best = 0;
            for(int sp = 0; sp < allShortestPaths.size(); sp++)
            {
                int maxValueOfPath = valueOfPath(allShortestPaths.get(sp), gold);
                updateGraph(adjList, allShortestPaths.get(sp), gold);
                int returnTripCost = dijkstra(adjList, 2)[1];
                best = Math.max(best, (maxValueOfPath-returnTripCost));
            }
            System.out.println(best);
        }
    }
    
    //This method will make the graph be such that all edge weights become 0 except those
    //going into a member of path, which will have weight of gold[member].
    public static void updateGraph(ArrayList<Edge>[] adjList, ArrayList<Integer> path, int[] gold)
    {
        HashSet<Integer> pathQuick = new HashSet<Integer>();
        pathQuick.addAll(path);
        
        for(int n = 1; n < adjList.length; n++)
        {
            for(int e = 0; e < adjList[n].size(); e++)
            {
                int edgeDest = adjList[n].get(e).dest;
                
                //If our path contained edgeDest...
                if (pathQuick.contains(edgeDest))
                {
                    //Make this edge's weight the value of the destination!
                    adjList[n].get(e).weight = gold[edgeDest];
                }
                else
                {
                    //This edge is free!
                    adjList[n].get(e).weight = 0;
                }
            }
        }
    }
    
    public static int valueOfPath(ArrayList<Integer> path, int[] gold)
    {
        int sum = 0;
        for(Integer n : path)
        {
            sum += gold[n];
        }
        return sum;
    }
    
    public static ArrayList<ArrayList<Integer>> allShortestPaths;
    public static void getAllShortestPaths(int curNode, int dist, int endNode, int shortestPathLength, int[] distToEnd, ArrayList<Edge>[] adjList, Stack<Integer> pathSoFar)
    {
        if (curNode == endNode)
        {
            ArrayList<Integer> ans = new ArrayList<>();
            ans.addAll(pathSoFar);
            allShortestPaths.add(ans);
            return;
        }
        
        //Consider going to every next node!
        for(Edge e : adjList[curNode])
        {
            //If going there keeps me along a shortest path...
            if (dist+e.weight+distToEnd[e.dest] == shortestPathLength)
            {
                //Then, go there, since it is along a shortest path!
                pathSoFar.push(e.dest);
                
                getAllShortestPaths(e.dest, dist+e.weight, endNode, shortestPathLength, distToEnd, adjList, pathSoFar);
                
                pathSoFar.pop();
            }
        }
    }
    
    public static int oo = 999999999;
    public static int[] dijkstra(ArrayList<Edge>[] adjList, int start)
    {
        boolean[] visited = new boolean[adjList.length];
        int numVisited = 0;
        
        int[] dist = new int[adjList.length];
        Arrays.fill(dist, oo);
        dist[start] = 0;
        
        PriorityQueue<DijState> q = new PriorityQueue<DijState>();
        
        q.add(new DijState(start, 0));
        
        //Note the -1. That's because node 0 is fake!
        while(!q.isEmpty() && numVisited < adjList.length-1)
        {
            DijState ds = q.poll();
            
            //Skip if already visited...
            if (visited[ds.node])
                continue;
            
            //Otherwise, visit it!
            visited[ds.node] = true;
            numVisited++;
            
            //Look at neighbors...
            for(Edge e : adjList[ds.node])
            {
                //If we have not visited the destination, and we can improve on the destination's dist value...
                if (!visited[e.dest] && dist[ds.node]+e.weight < dist[e.dest])
                {
                    dist[e.dest] = dist[ds.node]+e.weight;
                    q.add(new DijState(e.dest, dist[e.dest]));
                }
            }
        }
        
        return dist;
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
        
        public int compareTo(DijState ds)
        {
            return dist-ds.dist;
        }
    }
    
    private static class Edge
    {
        int dest;
        int weight;
        
        public Edge(int dest, int weight)
        {
            this.dest = dest;
            this.weight = weight;
        }
    }
}
