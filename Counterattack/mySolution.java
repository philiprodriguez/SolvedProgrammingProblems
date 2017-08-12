
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 *
 * Author Philip
 */
public class c {
    public static StringTokenizer st;
    public static void main(String[] args) throws IOException
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int numCases = Integer.parseInt(br.readLine());
        for(int c = 0; c < numCases; c++)
        {
            st = new StringTokenizer(br.readLine());
            int numPoints = Integer.parseInt(st.nextToken());
            int l0 = Integer.parseInt(st.nextToken());
            int l1 = Integer.parseInt(st.nextToken());
            int s0 = Integer.parseInt(st.nextToken());
            int s1 = Integer.parseInt(st.nextToken());
            
            //Build the graph!
            ArrayList<Edge>[] adjList = new ArrayList[2+2*numPoints];
            for(int i = 0; i < adjList.length; i++)
                adjList[i] = new ArrayList<Edge>();
            
            //Let node 0 be defender and node adjList.length-1 be the goal
            //Let all other evens be points for striker 0 and all odds be for
            //striker 1
            
            //Add edges from defender to strikers...
            adjList[0].add(new Edge(2, l0));
            adjList[0].add(new Edge(1, l1));
            
            //Add edges from strikers to goal...
            adjList[adjList.length-2].add(new Edge(adjList.length-1, s0));
            adjList[adjList.length-3].add(new Edge(adjList.length-1, s1));
            
            st = new StringTokenizer(br.readLine());
            //For each of striker 0's points except last one...
            for(int p = 2; p <= numPoints*2-2; p+=2)
            {
                //Add the pass edge...
                adjList[p].add(new Edge(p+1, Integer.parseInt(st.nextToken())));
            }
            
            st = new StringTokenizer(br.readLine());
            //For each of striker 0's points except last one...
            for(int p = 2; p <= numPoints*2-2; p+=2)
            {
                //Add the dribble edge...
                adjList[p].add(new Edge(p+2, Integer.parseInt(st.nextToken())));
            }
            
            st = new StringTokenizer(br.readLine());
            //For each of striker 1's points except last one...
            for(int p = 1; p <= numPoints*2-2; p+=2)
            {
                //Add the pass edge...
                adjList[p].add(new Edge(p+3, Integer.parseInt(st.nextToken())));
            }
            
            st = new StringTokenizer(br.readLine());
            //For each of striker 1's points except last one...
            for(int p = 1; p <= numPoints*2-2; p+=2)
            {
                //Add the dribble edge...
                adjList[p].add(new Edge(p+2, Integer.parseInt(st.nextToken())));
            }
            
            //Graph should be built now... run dijkstra to solve!
            int[] dist = dijkstra(adjList, 0);
            System.out.println(dist[adjList.length-1]);
        }
    }
    
    public static final int oo = 999999999;
    public static int[] dijkstra(ArrayList<Edge>[] adjList, int source)
    {
        int[] dist = new int[adjList.length];
        Arrays.fill(dist, oo);
        boolean[] visited = new boolean[adjList.length];
        int numVisited = 0;
        PriorityQueue<DijState> q = new PriorityQueue<DijState>();
        
        //Deal with start node!
        dist[source] = 0;
        q.add(new DijState(source, 0));
        
        while(!q.isEmpty() && numVisited < adjList.length)
        {
            DijState state = q.poll();
            if (visited[state.node])
                continue;
            visited[state.node] = true;
            numVisited++;
            
            //Look around at my neighbors...
            for(Edge e : adjList[state.node])
            {
                //Can I make an improvement?
                if (dist[state.node]+e.weight < dist[e.dest])
                {
                    dist[e.dest] = dist[state.node]+e.weight;
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

        @Override
        public int compareTo(DijState t) {
            return dist-t.dist;
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
