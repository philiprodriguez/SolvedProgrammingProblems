import java.util.*;

/*
    We're trying to "collapse" a list of numbers s.t. we minimize our cost,
    which my immediate response to is just "can I just take the min cost at
    each step?"

    I'm going to go with that.
*/

public class mySolution
{
    public static void main(String[] args)
    {
        Scanner scan = new Scanner(System.in);

        //How many cases we got?
        int numCases = scan.nextInt();

        for(int c = 0; c < numCases; c++)
        {
            //The number of numbers in this case
            int numNums = scan.nextInt();

            PriorityQueue<Long> minHeap = new PriorityQueue<>();
            for(int n = 0; n < numNums; n++)
            {
                minHeap.add(scan.nextLong());
            }
            long totalCost = 0;
            while(minHeap.size() > 1)
            {
                //I have at least two numbers still that need to be collapsed...
                long one = minHeap.poll();
                long two = minHeap.poll();
                totalCost += one+two;
                minHeap.add(one+two);
            }
            System.out.println(totalCost);
        }
    }
}
