/*
    So the key, in my case, to this solution was to stare at the problem for a
    long while and just try different subset summy things on it. I eventually
    was simulating in my head doing an 0/1 subset sum 1D DP, and found out to
    myself that if you take the smallest number in your list of numbers and do
    the right to left thing in the 1D SS DP with it, if there is a "gap", then
    you already lost and can never fill that gap since all other number are
    bigger...

    Thus, sort the input numbers and take the smallest first each time.... see
    if doing the thing with that number makes a gap and if it does, you're done.

    There are a couple other modifications to speed it up even more after that.
    Namely, the main one is just that you don't really even need to sim the thing,
    since you are guaranteed to "check off" all integers in order from 0 on,
    so just do range things!

    Just think about it in your head for a while! It makes sense!

    The solution below should run each case in O(numNums*log(numNums)) time...
*/

import java.util.*;

public class mySolution
{
    public static void main(String[] args)
    {
        Scanner scan = new Scanner(System.in);

        int numCases = scan.nextInt();
        for(int c = 1; c <= numCases; c++)
        {
            int numNums = scan.nextInt();
            PriorityQueue<Integer> q = new PriorityQueue<Integer>();
            for(int n = 0; n < numNums; n++)
            {
                q.add(scan.nextInt());
            }

            //Keep track of the last integer we met (since we must go in order)
            int lastSatisfied = 0;
            while(!q.isEmpty())
            {
                int curNum = q.poll();


                if (curNum > lastSatisfied+1)
                {
                    //We lost, since there IS a gap then...
                    break;
                }
                else
                {
                    //No gap! All is okay! We WILL be able to satisfy up to
                    //lastSatisfied + curNum
                    lastSatisfied = lastSatisfied+curNum;
                }

            }

            System.out.println("Set #" + c + ": " + (lastSatisfied+1));
            System.out.println();

        }
    }
}
