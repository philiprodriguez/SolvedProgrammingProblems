
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Stack;
import java.util.StringTokenizer;

/**
 *
 * Author Philip
 */
public class d {
    
    public static StringTokenizer st;
    public static void main(String[] args) throws IOException
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int numCases = Integer.parseInt(br.readLine());
        for(int c = 0; c < numCases; c++)
        {
            st = new StringTokenizer(br.readLine());
            int numNodes = Integer.parseInt(st.nextToken());
            int numEdges = Integer.parseInt(st.nextToken());
            
            ArrayList<Integer>[] adjList = new ArrayList[numNodes];
            for(int n = 0; n < numNodes; n++)
                adjList[n] = new ArrayList<Integer>();
            
            for(int e = 0; e < numEdges; e++)
            {
                st = new StringTokenizer(br.readLine());
                int start = Integer.parseInt(st.nextToken());
                int end = Integer.parseInt(st.nextToken());
                adjList[start].add(end);
            }
            
            int[] id = kosaraju(adjList);
            
            int maxId = -999;
            for(int i = 0; i < id.length; i++)
                maxId = Math.max(maxId, id[i]);
            
            int[] incomingId = new int[maxId+1];
            
            for(int n = 0; n < numNodes; n++)
                for(int dest : adjList[n])
                {
                    //Edge from n to dest
                    int idstart = id[n];
                    int idend = id[dest];
                    
                    //Self loop edge!
                    if (idstart == idend)
                        continue;
                    
                    //Edge from ssc start to ssc end, so ssc end has one more incoming edge!
                    incomingId[idend]++;
                }
            
            //System.out.println(Arrays.toString(incomingId));
            int numZero = 0;
            for(int i : incomingId)
                if (i == 0)
                    numZero++;
            
            if (numZero == 1)
            {
                //Okay, now print all valid starting points!
                //This is just everything in the one SCC with 0 incoming.
                ArrayList<Integer> ans = new ArrayList<Integer>();
                for(int i = 0; i < incomingId.length; i++)
                {
                    if (incomingId[i] == 0)
                    {
                        //This (i) is the scc we're interested in!
                        for(int n = 0; n < numNodes; n++)
                        {
                            if (id[n] == i)
                            {
                                ans.add(n);
                            }
                        }
                        break;
                    }
                }
                
                Collections.sort(ans);
                for(Integer n : ans)
                    System.out.println(n);
            }
            else
            {
                System.out.println("Confused");
            }
            System.out.println();
            
            if (c != numCases-1)
                br.readLine();
        }
    }
    
    //Returns an array that maps each node number to the id of it's SCC.
    public static int[] kosaraju(ArrayList<Integer>[] adjList)
    {
        int[] id = new int[adjList.length];
        
        //Build stack
        Stack<Integer> stack = new Stack<>();
        boolean[] visited = new boolean[adjList.length];
        for(int n = 0; n < adjList.length; n++)
            if (!visited[n])
                buildDFS(n, visited, adjList, stack);
        
        //Reverse the graph
        ArrayList<Integer>[] reverseGraph = new ArrayList[adjList.length];
        for(int n = 0; n < adjList.length; n++)
            reverseGraph[n] = new ArrayList<Integer>();
        for(int n = 0; n < adjList.length; n++)
            for(int dest : adjList[n])
                //Edge from node n to dest in the normal graph...
                reverseGraph[dest].add(n);
        
        //Destroy stack..
        int nid = 0;
        Arrays.fill(visited, false);
        while(!stack.isEmpty())
        {
            int nodeToVisit = stack.pop();
            if (!visited[nodeToVisit])
            {
                breakDFS(nodeToVisit, visited, reverseGraph, id, nid);
                nid++;
            }
        }
        
        return id;
    }
    
    public static void buildDFS(int curNode, boolean[] visited, ArrayList<Integer>[] adjList, Stack<Integer> stack)
    {
        visited[curNode] = true;
        for(int dest : adjList[curNode])
            if (!visited[dest])
                buildDFS(dest, visited, adjList, stack);
        stack.add(curNode);
    }
    
    public static void breakDFS(int curNode, boolean[] visited, ArrayList<Integer>[] reverseGraph, int[] id, int nid)
    {
        id[curNode] = nid;
        visited[curNode] = true;
        for(int dest : reverseGraph[curNode])
        {
            if (!visited[dest])
            {
                breakDFS(dest, visited, reverseGraph, id, nid);
            }
        }
    }
}
