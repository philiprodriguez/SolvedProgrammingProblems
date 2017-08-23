import java.util.*;

/*

    Holy geez. This is a problem that I didn't get in contest before because I
    frankly didn't even understand what the question was asking. I finally now
    sat down with the problem for a good hour and a half here and gave it my
    best shot.

    I legitimately didn't go into this thinking it'd be a bitmast DP. So
    basically, this problem is first of all not asking us to place people into
    seats so much as it is asking us to assign seats to ordered pairs. This was
    key understanding moment #1. Given n pairs and 2n integers, we want to, for
    each pair, assign 2 non-consecutive integers (corresponding to seat numbers
    for the people in the pair/couple) into it. We are talking about unordered
    pairs too, so that is why in my DP you'll see I do a choose 2 inside of it
    because I iterate the index over pairs, not individuals.

    The brute force is basically just to go through each pair, choosing every
    possible valid pair of non-consecutive integers at each step and then
    continuing along with the counting from there etc. Then, this is actually
    not our answer, because the problem also asks us to not account for the
    swapping of pairs (the pairs themselves are unordered!). Thus, we take
    the answer from that brute force and divide it by the number of ways to
    permute the pairs themselves (n!). This is our answer. I originally did this
    using a boolean array, thinking I could just pre-compute the 9 answers and
    hack my way through it, but really I didn't feel like waiting all day for
    the 9 case to finish, and I remembered that I can make that boolean array
    into an integer and then maybe do a bitmask DP, so I did that. The state
    space is actually very reasonable since we at most have 18 seats and 9
    pairs ---> 2^18 * 9 = 262144 * 9 = 2359296 is our state space size. Yay for
    DP magic! No pre-computing all answers hacks required!

    Thus, I'm actually rather proud of this solution. It runs in the worst case
    in O(n*2^n) time, which is actually quite reasonable since n is bounded by
    to <= 9.

*/

public class mySolution
{
    public static void main(String[] args)
    {
        Scanner scan = new Scanner(System.in);
        int numCases = scan.nextInt();
        for(int c = 0; c < numCases; c++)
        {
            int numCouples = scan.nextInt();
            memo = new long[numCouples][262144];
            for(int r = 0; r < numCouples; r++)
                Arrays.fill(memo[r], -1);
            System.out.println(numArrangements(0, 0, numCouples) / fac(numCouples));
        }
    }

    public static long fac(int n)
    {
        long result = 1;
        for(int i = 2; i <= n; i++)
        {
            result *= i;
        }
        return result;
    }

    //used refers to a seat being used or not
    public static long[][] memo;
    public static long numArrangements(int curPair, int used, int numPairs)
    {
        if (curPair >= numPairs)
        {
            //We're done! We made an arrangement!
            return 1;
        }

        if (memo[curPair][used] != -1)
        {
            return memo[curPair][used];
        }

        //Try using every possible (choose 2) pair of seats (a, b) for this pair.
        long total = 0;
        for(int a = 0; a < numPairs*2; a++)
        {
            for(int b = a+2; b < numPairs*2; b++)
            {
                //Can we use seats a and b?

                //We cannot if a and be are right next to each other!


                //We cannot if a or b have been used!
                if (isOn(a, used) || isOn(b, used))
                    continue;

                //Otherwise, we can use these two seats for this pair
                //So, suppose we do!

                used = flipBit(a, used);
                used = flipBit(b, used);
                total += numArrangements(curPair+1, used, numPairs);
                used = flipBit(a, used);
                used = flipBit(b, used);
            }
        }

        return memo[curPair][used] = total;
    }

    public static int flipBit(int bit, int arr)
    {
        return arr ^ (1<<bit);
    }

    public static boolean isOn(int bit, int arr)
    {
        return (arr & (1<<bit)) > 0;
    }
}
