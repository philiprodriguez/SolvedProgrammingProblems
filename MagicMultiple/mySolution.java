/*
    This actually took me a solid hour. I mean, immediately I was thinking I
    could jsut simulate the thing, but I wanted to be more sure of how fast
    my program would be. After much thought and screwing around, I've got
    the following logic:

    you'll eventually increment a leftmost digit by 1 after adding enough x's,
    and that increment must happen in 10 steps.

    Thus, max steps is 10*10 = 100 steps.
*/

import java.util.*;

public class mySolution
{
    public static void main(String[] args)
    {
        Scanner scan = new Scanner(System.in);

        while(scan.hasNext())
        {
            long startVal = scan.nextLong();
            int k = 1;

            HashSet<Integer> used = new HashSet<Integer>();

            while(used.size() < 10)
            {
                long curVal = k*startVal;
                //Go through every digit of curVal, shoving it into used.
                String cur = curVal+"";
                for(char c : cur.toCharArray())
                {
                    used.add(Integer.parseInt(""+c));
                }
                k++;
            }
            //Answer is k-1.
            System.out.println(k-1);
        }
    }
}
