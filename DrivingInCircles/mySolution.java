import java.util.*;

/*
    Alright, so really it sounds like this is just a disjkstra problem with some
    circle geometry stuff sprinkled in to make the graph building the hardest
    part. So, let's do it!

    This problem took me a long time to finish because I had a nasty bug:

    Arrays.fill(array, new ArrayList<E>());

    This is terrible! It places the EXACT SAME ADDRESSED Arraylist in all slots
    of array. It does NOT give each slot its own unique empty array list. Boy,
    I was staring for an extra hour at this code due to that.

    Other than that, it was just dijkstra the graph once the graph is built! I
    use TreeMap and TreeSet here unnecessarily, just because the input sizes
    are so little anyways and I didn't want to manually implement hashCode()...
    (I wrote all this code in a basic text editor, not an IDE that generates the
    hashCode() equals() for me)

    Philip Rodriguez 8-15-2017
*/

public class mySolution
{
    public static void main(String[] args)
    {
        Scanner scan = new Scanner(System.in);

        int numCases = scan.nextInt();
        for(int curCase = 0; curCase < numCases; curCase++)
        {
            int numCircles = scan.nextInt();
            int numRoads = scan.nextInt();

            //Represent the graph!
            TreeMap<Point, ArrayList<Edge>> adjList = new TreeMap<>();

            //Holds the radius of circle i in increasing order...
            int[] circleRadi = new int[numCircles];

            //Holds, for each circle, what points are on it!
            ArrayList<Point>[] circlePoints = new ArrayList[numCircles];
            for(int i = 0; i < circlePoints.length; i++)
                circlePoints[i] = new ArrayList<Point>();

            //Read in them radi!
            for(int c = 0; c < numCircles; c++)
            {
                circleRadi[c] = scan.nextInt();
            }

            //Read in roads.
            for(int r = 0; r < numRoads; r++)
            {
                Point p1 = new Point(scan.nextInt()-1, scan.nextInt());
                Point p2 = new Point(p1.circle+1, p1.angle);

                //Update circlePoints
                circlePoints[p1.circle].add(p1);
                circlePoints[p2.circle].add(p2);


                //Calculate the distance from p1 to p2 for the weight in the
                //graph!
                double dist = betweenCircleDistance(p1, p2, circleRadi);

                //There is an edge from p1 to p2 and vice versa...
                addEdge(p1, p2, dist, adjList);
            }

            //Read in start and end points!
            Point start = new Point(scan.nextInt()-1, scan.nextInt());
            Point end = new Point(scan.nextInt()-1, scan.nextInt());
            circlePoints[start.circle].add(start);
            circlePoints[end.circle].add(end);

            //Now we need to link points that are on same circle...
            for(int c = 0; c < numCircles; c++)
            {
                //Make points in increasing order by angle
                Collections.sort(circlePoints[c]);

                //Link them up!
                for(int p = 0; p < circlePoints[c].size()-1; p++)
                {
                    //Link point p and p+1
                    Point p1 = circlePoints[c].get(p);
                    Point p2 = circlePoints[c].get(p+1);

                    double dist = onCircleDistance(p1, p2, circleRadi);

                    //Edge between p1 and p2 and vice versa on the circle...
                    addEdge(p1, p2, dist, adjList);
                }
                //Don't forget to link the last point to the first point!
                if (circlePoints[c].size() >= 2)
                {
                    Point p1 = circlePoints[c].get(0);
                    Point p2 = circlePoints[c].get(circlePoints[c].size()-1);

                    double dist = onCircleDistance(p1, p2, circleRadi);

                    //Edge between p1 and p2 and vice versa on the circle...
                    addEdge(p1, p2, dist, adjList);
                }
            }

            //At this point I believe the graph is built! Phew! That was a
            //lot of effort for building a graph. Now we just run dijkstra.
            System.out.format("%.2f", dijkstra(start, end, adjList));
            System.out.println();
        }
    }

    private static final double oo = Double.MAX_VALUE;
    //Returns the shortest path length from start to end...
    public static double dijkstra(Point start, Point end, TreeMap<Point, ArrayList<Edge>> adjList)
    {
        TreeMap<Point, Double> dist = new TreeMap<>();

        //Init everything to infinity, except start is 0
        for(Point p : adjList.keySet())
        {
            dist.put(p, oo);
        }
        dist.put(start, 0.0);

        //Visited stuff
        TreeSet<Point> visited = new TreeSet<>();
        int numVisited = 0;

        //Note that here an edge actually just represents a node with a double,
        //and does not actually refer to an edge in any graph!
        PriorityQueue<Edge> q = new PriorityQueue<Edge>();

        //enqueue start node
        q.add(new Edge(start, 0));

        while(!q.isEmpty() && numVisited < adjList.size())
        {
            Edge curState = q.poll();
            //Skip if we already visited the node!
            if (visited.contains(curState.dest))
            {
                continue;
            }
            //Otherwise, visit it!
            visited.add(curState.dest);
            numVisited++;

            //Look around and update people!
            for(Edge e : adjList.get(curState.dest))
            {
                //Edge to e.dest from curState.dest, can we improve e.dest?
                if (!visited.contains(e.dest) && dist.get(curState.dest) + e.weight < dist.get(e.dest))
                {
                    dist.put(e.dest, dist.get(curState.dest) + e.weight);
                    q.add(new Edge(e.dest, dist.get(e.dest)));
                }
            }
        }

        return dist.get(end);
    }

    //Does what it looks like! Add a bi-directional edge.
    public static void addEdge(Point p1, Point p2, double dist, TreeMap<Point, ArrayList<Edge>> adjList)
    {
        adjList.putIfAbsent(p1, new ArrayList<Edge>());
        adjList.putIfAbsent(p2, new ArrayList<Edge>());
        adjList.get(p1).add(new Edge(p2, dist));
        adjList.get(p2).add(new Edge(p1, dist));
    }

    /*
        Some geometry stuff!
    */

    public static double betweenCircleDistance(Point p1, Point p2, int[] circleRadi)
    {
        //Fish out those radi
        int r1 = circleRadi[p1.circle], r2 = circleRadi[p2.circle];

        //We know both angles are the same between p1 and p2, so the answer
        //is just the difference.
        return Math.abs(r1-r2);
    }

    public static double onCircleDistance(Point p1, Point p2, int[] circleRadi)
    {
        //Fish out the radius
        int r = circleRadi[p1.circle];

        //Get the angle distance...
        int smallerAngle = Math.min(p1.angle, p2.angle);
        int largerAngle = Math.max(p1.angle, p2.angle);
        int angleDistance = Math.min(largerAngle-smallerAngle, smallerAngle+360-largerAngle);
        double radianDistance = Math.toRadians(angleDistance);

        //There are radianDistance radiuses from p1 to p2 on the circle...
        return radianDistance*r;
    }

    //Represents an edge between two points
    private static class Edge implements Comparable<Edge>
    {
        Point dest;
        double weight;

        public Edge(Point dest, double weight)
        {
            this.dest = dest;
            this.weight = weight;
        }

        //This is here for treating edge like DijState
        public int compareTo(Edge e2)
        {
            Double me = this.weight;
            Double them = e2.weight;
            return me.compareTo(them);
        }

        public String toString()
        {
            return "Edge to " + dest + " of weight " + weight;
        }
    }

    //Represetns a point on a circle
    private static class Point implements Comparable<Point>
    {
        int circle;
        int angle;

        public Point(int circle, int angle)
        {
            this.circle = circle;
            this.angle = angle;
        }

        public int compareTo(Point p2)
        {
            //Order first by circle/radius
            if (this.circle != p2.circle)
                return this.circle - p2.circle;

            //Order second by angle
            return this.angle - p2.angle;
        }

        public String toString()
        {
            return "(circle " + circle + ", " + angle + " degrees)";
        }
    }
}
