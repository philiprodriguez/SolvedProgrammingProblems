
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Student
 */
public class transport {
    public static void main(String[] args)
    {
        Scanner scan = new Scanner(System.in);
        int numCases = scan.nextInt();
        for(int c = 0; c < numCases; c++)
        {
            int numCities = scan.nextInt();
            
            HashMap<Node, ArrayList<Edge>> adjList = new HashMap<>();
            
            for(int city = 0; city < numCities; city++)
            {
                String name = scan.next();
                int costToSwitch = scan.nextInt();
                
                //Create 4 nodes for this city for all types...
                HashMap<Node, ArrayList<Edge>> subGraph = new HashMap<>();
                for(int i = 0; i < 4; i++)
                {
                    subGraph.put(new Node(name, i), new ArrayList<Edge>());
                }
                
                //Interlink them via cost to switch
                for(Node s : subGraph.keySet())
                {
                    for(Node e : subGraph.keySet())
                    {
                        if (s.equals(e))
                            continue;
                        subGraph.get(s).add(new Edge(e, costToSwitch));
                    }
                }
                
                adjList.putAll(subGraph);
            }
            
            int numEdges = scan.nextInt();
            for(int e = 0; e < numEdges; e++)
            {
                String startstr = scan.next();
                String endstr = scan.next();
                String typestr = scan.next();
                int type;
                if (typestr.equals("AIR"))
                    type = 0;
                else if (typestr.equals("SEA"))
                    type = 1;
                else if (typestr.equals("RAIL"))
                    type = 2;
                else if (typestr.equals("TRUCK"))
                    type = 3;
                else
                    throw new IllegalStateException();
                int weight = scan.nextInt();
                
                Node start = new Node(startstr, type);
                Node end = new Node(endstr, type);
                
                //Edge from [start] to [end] with weight [weight] and vice versa.
                adjList.get(start).add(new Edge(end, weight));
                adjList.get(end).add(new Edge(start, weight));
            }
            
            String beginStr = scan.next();
            String destStr = scan.next();
            
            //Add free edges from an origin node to all nodes with that name [originStr]
            Node originNode = new Node("origin", 12345);
            adjList.put(originNode, new ArrayList<Edge>());
            
            for(Node key : adjList.keySet())
            {
                if (key.name.equals(beginStr))
                {
                    //Edge from originNode to key with weight 0.
                    adjList.get(originNode).add(new Edge(key, 0));
                }
            }
            
            //Now dijkstra from originNode....
            HashMap<Node, Integer> dist = dijkstra(adjList, originNode);
            int result = oo;
            for(Node key : dist.keySet())
            {
                if (key.name.equals(destStr))
                {
                    result = Math.min(result, dist.get(key));
                }
            }
            System.out.println(result);
            
        }
    }
    
    public static final int oo = 999999999;
    public static HashMap<Node, Integer> dijkstra(HashMap<Node, ArrayList<Edge>> adjList, Node originNode)
    {
        int visitedCount = 0;
        HashSet<Node> visited = new HashSet<Node>();
        
        HashMap<Node, Integer> dist = new HashMap<>();
        for(Node n : adjList.keySet())
        {
            dist.put(n, oo);
        }
        dist.put(originNode, 0);
        
        PriorityQueue<DijState> q = new PriorityQueue<>();
        
        //Enqueue Start Node
        q.add(new DijState(originNode, 0));
        
        while(!q.isEmpty() && visitedCount < adjList.size())
        {
            DijState curState = q.poll();
            
            //Skip if we've already visited, otherwise visit!
            if (visited.contains(curState.node))
                continue;
            visited.add(curState.node);
            visitedCount++;
            
            //Look around, update unvisited neighbors
            for(Edge e : adjList.get(curState.node))
            {
                if (!visited.contains(e.dest) && (curState.dist+e.weight) < dist.get(e.dest))
                {
                    dist.put(e.dest, curState.dist+e.weight);
                    q.add(new DijState(e.dest, curState.dist+e.weight));
                }
            }
        }
        
        return dist;
    }
    
    private static class DijState implements Comparable<DijState>
    {
        Node node;
        int dist;
        
        public DijState(Node node, int dist)
        {
            this.node = node;
            this.dist = dist;
        }

        @Override
        public int compareTo(DijState o) {
            return this.dist-o.dist;
        }
    }
    
    private static class Node
    {
        String name;
        //0 = air, 1 = sea, 2 = rail, 3 = truck
        int type;
        
        public Node(String name, int type)
        {
            this.name = name;
            this.type = type;
        }

        @Override
        public int hashCode() {
            int hash = 3;
            hash = 53 * hash + Objects.hashCode(this.name);
            hash = 53 * hash + this.type;
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
            final Node other = (Node) obj;
            if (this.type != other.type) {
                return false;
            }
            if (!Objects.equals(this.name, other.name)) {
                return false;
            }
            return true;
        }
        
        public String toString()
        {
            return "(" + name + ", " + type + ")";
        }
        
    }
    
    private static class Edge
    {
        Node dest;
        int weight;
        
        public Edge(Node dest, int weight)
        {
            this.dest = dest;
            this.weight = weight;
        }
    }
}
