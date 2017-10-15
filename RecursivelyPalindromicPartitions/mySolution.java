import java.util.*;

/*
Alright, so I decomposed the problem like this:

For 7, I can either push 0 to my left and right or 1 to my left and right or
2 to my left and right or 3 to my left an right, but then NOT 4 or more, since
I only have 7 to work with and 4*2 > 7.

Push 0 to my sides: 7 (all ways to represent 0; 1 way)
Push 1 to my sides: 1+5+1 (all ways to represent 1; 1 way)
Push 2 to my sides: 2+3+2 AND 1+1+3+1+1 (all ways to represent 2; 2 ways)
Push 3 to my sides: 3+1+3 AND 1+1+1+1+1+1+1  (all ways to represent 3; 2 ways)

And then we basically have 1+1+2+2 = 6. Good.
Thus, let numways(x) be the number of ways to form integer x recursively and
palindromically... another way to think about numWays is that numWays(x) is the
number of ways to repersent x pushed to each side of a number... then we have
that numWays(x) = sum of what we did above..

Thus, numWays(x) = sum(from i=0 to x/2 (floor division), numWays(i))
Then, numWays(7) = numWays(0)+numWays(1)+numWays(2)+numWays(3)
    = 1+1+2+2 = 6

Now I could have just coded this up and it would be an O(n^2) DP, but with
cumulative sum arrays I think I could do better! Notice that each answer is just
a sum of some previous stuff... After studying the problem more, I found that
if I set up an array nW so that the i-th index is the sum of numWays(0) to
numWays(i), then we get that

nW[i] = numWays(i) + nW[i-1] = nW[i/2] + nW[i-1]

Now we're talking. O(n) time and space DP. Let's do it.

Alright, so now I've coded up a working version, and it passes sample and my
additional manually computed small cases...
I'm going to change everything from working with int to working with long
because of the ill problem specification...
*/

public class mySolution
{
    public static void main(String[] args)
    {
        Scanner scan = new Scanner(System.in);

        //Problem didn't specify a maximum input value making this somewhat hard
        //to just precompute... Let's do an arraylist and use looping to
        //grow our results as needed.

        //cumulative sum array as described above.
        ArrayList<Long> nW = new ArrayList<Long>();
        //Set it up! First 3 are like 1, 2, since numWays(0) == numWays(1) == 1
        nW.add(1L);
        nW.add(2L);

        int numCases = scan.nextInt();
        for(int c = 0; c < numCases; c++)
        {
            long num = scan.nextLong();
            //Answer for numWays(num) = nW[num/2]...

            if(nW.size() <= num/2)
            {
                //If we don't have our answer computed... compute it!
                for(int i = nW.size(); i <= num/2; i++)
                {
                    //Compute for i and add to list...
                    //The answer for position i is as defined above...
                    long ans = nW.get(i/2)+nW.get(i-1);
                    nW.add(ans);
                }
            }
            System.out.println((c+1) + " " + nW.get((int)(num/2)));
        }
    }
}
