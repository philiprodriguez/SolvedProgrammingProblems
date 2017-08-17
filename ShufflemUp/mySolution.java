import java.util.*;

/*
    I've spent a good 20 minutes trying to figure out what the "deeper meaning"
    in this problem is, but honestly I haven't figured out how the number of
    cards relates directly to the solution. I know I can simulate this in
    O(n*stepsTillRepeat) time, but I can't fully nail down stepsTillRepeat
    well. It's clearly less than 100!, but like besides that I haven't yet found
    the pattern. I figure I'll just whip up the easy code to solve the problem
    and see how it does.

    Alright, well it got correct within the time limit. It bothers me that I
    didn't nail down the runtime other than O(2*C*stepsTillRepeat). How do I
    know it will repeat in x number of steps?

    If we assume that one card getting back into the same position implies all
    other cards have also gotten back to the same position, then it should be
    2*C steps guarantees a repeat. This seems to hold true from the samples I
    did manually.
*/

public class mySolution {
    public static void main(String[] args)
    {
        Scanner scan = new Scanner(System.in);

        int numCases = scan.nextInt();

        for(int c = 0; c < numCases; c++)
        {
            int fullSize = scan.nextInt()*2;
            String initialStack = scan.next() + scan.next();
            String target = scan.next();

            //Keep shuffling until we either hit a repeat stack or our target!
            HashSet<String> visited = new HashSet<String>();
            String nextStack = initialStack;
            int numShuffles = 0;
            while(!nextStack.equals(target) && !visited.contains(nextStack))
            {
                visited.add(nextStack);
                nextStack = shuffle(nextStack);
                numShuffles++;
            }

            if (nextStack.equals(target))
            {
                //We found target in numShuffles steps!
                System.out.println((c+1) + " " + numShuffles);
            }
            else
            {
                //We repeated and did not find target
                System.out.println((c+1) + " -1");
            }
        }
    }

    //Get the next combined stack from the previous combined stack in O(n) time
    public static String shuffle(String prevStack)
    {
        //Create a StringBuilder with some filler characters of length
        //prevStack.length()
        StringBuilder newStack = new StringBuilder(prevStack);

        int stop = prevStack.length()/2;
        for(int i = 0; i < stop; i++)
        {
            char frontEnd = prevStack.charAt(i);
            char backEnd = prevStack.charAt(prevStack.length()-(i+1));

            //frontEnd will move up i+1 places, backEnd will move down i+1
            //places
            newStack.setCharAt(i+(i+1), frontEnd);
            newStack.setCharAt(prevStack.length()-(i+1)-(i+1), backEnd);
        }

        return newStack.toString();
    }
}
