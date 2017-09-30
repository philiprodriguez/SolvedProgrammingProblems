/*
    This problem was clearly some kind of topological sort question. My
    immediate reaction was to generate all topsorts, but then I quickly saw that
    I really only needed to run a single topsort which keeping track of if I
    ever had "many continuation paths" to determine the answer... so I did that.
*/


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Stack;


public class mySolution {
    public static void main(String[] args)
    {
        Scanner scan = new Scanner(System.in);
        while(true)
        {
            int numNodes = scan.nextInt();
            int numEdges = scan.nextInt();

            if (numNodes == 0 && numEdges == 0)
            {
                //We're done!
                break;
            }

            //Adjacency list for the graph we're gonna topsort...
            ArrayList<Integer>[] adjList = new ArrayList[numNodes];
            for(int n = 0; n < numNodes; n++)
            {
                adjList[n] = new ArrayList<Integer>();
            }

            //Store the number incoming for each node; index is the node...
            int[] numIncoming = new int[numNodes];

            for(int e = 0; e < numEdges; e++)
            {
                int defined = scan.nextInt()-1;
                int used = scan.nextInt()-1;

                //Used has an incoming edge to it from defined...
                numIncoming[used]++;

                //Edge from defined to used...
                adjList[defined].add(used);
            }

            //Which nodes have 0 incoming? Set up our stack...
            Stack<Integer> zeroIncoming = new Stack<>();
            for(int n = 0; n < numIncoming.length; n++)
            {
                if (numIncoming[n] == 0)
                    zeroIncoming.push(n);
            }

            //Now we begin out topsorting for real...

            //If we ever have more than 1 next option AND a successful topsort,
            //then there were 2 or more possible topsorts.
            boolean multipleOptions = false;

            HashSet<Integer> visited = new HashSet<Integer>();

            //While not everything was visited
            while(visited.size() < numNodes)
            {
                //Pick a next thing to visit, and visit it!

                //If no next thing exists, no topsorts exist.
                if (zeroIncoming.size() <= 0)
                    break;
                //Did we have more than one option at this step?
                if (zeroIncoming.size() > 1)
                    multipleOptions = true;

                //Visit next!
                int next = zeroIncoming.pop();
                visited.add(next);
                //Decrement everything next points to...
                for(int dest : adjList[next])
                {
                    numIncoming[dest]--;
                    if (numIncoming[dest] == 0)
                        zeroIncoming.push(dest);
                }
            }

            if (visited.size() == numNodes)
            {
                //There was a topsort
                if (multipleOptions)
                {
                    //Many
                    System.out.println("2");
                }
                else
                {
                    //Only one
                    System.out.println("1");
                }
            }
            else
            {
                //No topsort possible
                System.out.println("0");
            }
        }
    }
}
