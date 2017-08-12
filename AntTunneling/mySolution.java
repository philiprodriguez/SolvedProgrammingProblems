import java.util.*;

/*
    So, this is just a MST problem. Gonna use kruskal's, because yay
    disjoint sets!
*/

public class mySolution
{
    public static void main(String[] arg)
    {
        Scanner scan = new Scanner(System.in);

        int numCampuses = scan.nextInt();

        for(int c = 0; c < numCampuses; c++)
        {
            int numHills = scan.nextInt();
            int numTunnels = scan.nextInt();

            //Do the kruskals!
            DisjointSets ds = new DisjointSets(numHills);
            int numComponents = numHills;

            PriorityQueue<Tunnel> q = new PriorityQueue<Tunnel>();
            for(int t = 0; t < numTunnels; t++)
            {
                q.add(new Tunnel(scan.nextInt()-1, scan.nextInt()-1, scan.nextInt()));
            }

            int totalCost = 0;
            while(numComponents > 1 && !q.isEmpty())
            {
                Tunnel t = q.poll();

                //If this tunnel forms a new meaningful connection...
                if (!ds.sameSet(t.start, t.end))
                {
                    //Take the tunnel!
                    totalCost += t.cost;
                    ds.union(t.start, t.end);
                    numComponents--;
                }

            }

            System.out.print("Campus #" + (c+1) + ": ");
            if (numComponents == 1)
            {
                System.out.println(totalCost);
            }
            else
            {
                //We must not have been able to connect them all...
                System.out.println("I'm a programmer, not a miracle worker!");
            }

        }
    }

    private static class Tunnel implements Comparable<Tunnel>
    {
        int start, end, cost;

        public Tunnel(int start, int end, int cost)
        {
            this.start = start;
            this.end = end;
            this.cost = cost;
        }

        public int compareTo(Tunnel t2)
        {
            return this.cost - t2.cost;
        }
    }

    private static class DisjointSets
    {
        private int[] parent;

        public DisjointSets(int numSets)
        {
            parent = new int[numSets];
            for(int i = 0; i < numSets; i++)
            {
                parent[i] = i;
            }
        }

        public int getRoot(int setNum)
        {
            if (parent[setNum] == setNum)
                return parent[setNum];
            return parent[setNum] = getRoot(parent[setNum]);
        }

        public boolean sameSet(int set1, int set2)
        {
            return getRoot(set1) == getRoot(set2);
        }

        public void union(int set1, int set2)
        {
            parent[getRoot(set2)] = getRoot(set1);
        }
    }
}
