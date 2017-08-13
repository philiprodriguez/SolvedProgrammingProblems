import java.util.*;

// Philip Rodriguez 8-13-2017

public class mySolution {
    public static void main(String[] args)
    {
        Scanner scan = new Scanner(System.in);

        int numCases = scan.nextInt();
        for(int curCase = 0; curCase < numCases; curCase++)
        {
            int numBags = scan.nextInt();
            int maxTake = scan.nextInt();

            //We're gonna do some bit twiddling! There are only 31 candy types
            //in this problem, and boy an int can represent 31 true/false values
            //Also, we don't care about the count of candies, only the count of
            //unique types of candy.
            int[] bags = new int[numBags];

            for(int b = 0; b < numBags; b++)
            {
                int bagSize = scan.nextInt();
                for(int c = 0; c < bagSize; c++)
                {
                    int candyType = scan.nextInt()-1;
                    bags[b] = assertBit(bags[b], candyType);
                }

                //This was to sanity check that our bag building works!
                //System.out.println("Bag " + b + ": " + Integer.toBinaryString(bags[b]));
            }

            //Alright, we've read the input... now solve the problem.
            System.out.println(maxUniqueCandies(0, new boolean[numBags], 0, bags, maxTake));
        }
    }

    //Brute force all possible choices of bags since nCr(20, 10) <= 185000
    public static int maxUniqueCandies(int index, boolean[] bagTaken, int numTaken, int[] bags, int maxTake)
    {
        if (numTaken == maxTake)
        {
            //Merge all the bags and see how many unique candies we got!
            int result = 0;
            for(int b = 0; b < bagTaken.length; b++)
            {
                if (bagTaken[b])
                    result |= bags[b];
            }
            //Another sanity check...
            //System.out.println("Tried " + Arrays.toString(bagTaken));
            return bitCount(result);
        }
        if (index >= bags.length || numTaken > maxTake)
        {
            //Invalid territory! Second one should never happen, but eh...
            return -1;
        }

        //We either take or don't take the bag at index...

        //Suppose we take (if we can)...
        int takeResult = -1;
        if (numTaken < maxTake)
        {
            bagTaken[index] = true;
            takeResult = maxUniqueCandies(index+1, bagTaken, numTaken+1, bags, maxTake);
        }

        //Suppose we do not take...
        int dontTakeResult = -1;
        bagTaken[index] = false;
        dontTakeResult = maxUniqueCandies(index+1, bagTaken, numTaken, bags, maxTake);

        return Math.max(takeResult, dontTakeResult);
    }

    /*
        Bit twiddling helpers!
    */

    public static int assertBit(int current, int slot)
    {
        return current | (1<<slot);
    }

    public static int bitCount(int set)
    {
        return Integer.bitCount(set & (0b01111111111111111111111111111111));
    }
}
