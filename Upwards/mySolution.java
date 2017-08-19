import java.util.*;

/*
    I mean, just looking at this problem it seems like it would just be a simple
    "generate the first one and count up by one" kind of thing, since they said
    at most 100 queries, and at most 26*10,000 steps per query...

    26*10000*100 = 26,000,000 = Easy sauce for even a cell phone in under a
    second

    So, let's do that!

    Phew! that increment method was a little bit of a piece of work! The only
    key realization is that, of course, you cannot increment past a point where
    the characters to your right have no space left to pick characters from
    (you'd violate the size restraint if you tried to ignore this).

    Not too bad though.
*/

public class mySolution
{
    public static void main(String[] args)
    {
        Scanner scan = new Scanner(System.in);

        int numCases = scan.nextInt();
        for(int c = 0; c < numCases; c++)
        {
            int level = scan.nextInt(); //Required gap
            int length = scan.nextInt(); //String size
            int steps = scan.nextInt()-1; //Number of increment steps needed

            //Get the initial upward ready with this length and level
            UpwardsHelper uh = new UpwardsHelper(level, length);

            //Increment the thing however many times we need to to obtain
            //our rank
            for(int i = 0; i < steps; i++)
                uh.increment(0);

            //Print result
            System.out.println(uh);
        }
    }

    private static class UpwardsHelper
    {
        private static char[] alphabet = {'a','b','c','d','e','f','g','h','i',
                                          'j','k','l','m','n','o','p','q','r',
                                          's','t','u','v','w','x','y','z'};

        private int gap;
        private char[] upward;

        public UpwardsHelper(int level, int length)
        {
            gap = level;
            upward = new char[length];

            //Initialize the upward! (rank 1 string)
            int spot = 0;
            for(int i = 0; i < length; i++)
            {
                upward[i] = alphabet[spot];
                spot = spot + (gap+1);
            }
        }

        /*
            This needs to be pretty fast since it'll get called 10,000 times per
            case potentially...

            This method will try to increment the value pfr places from the
            right. If that is an issue (we exceed our max possible character we
            can use for a position), then it will first instruct the left side
            to increment itself by one and then find the next valid character
            based on the result from that.

            Oh yes, it is O(upward.length) in the worst case, and each step is
            otherwise extremely fast. I think this will work, and be essentially
            O(1) since length is at most 26
        */
        public void increment(int pfr)
        {
            //For sanity, find the correct character in question.
            int index = upward.length - 1 - pfr;

            //What is the greatest char we can be in this place before people to
            //the right of us run out of chars to possibly work with?
            int maxPossibleChar = alphabet.length-1 - (gap+1)*pfr;

            //Try to bump it!
            if (upward[index]-'a' < maxPossibleChar)
            {
                //Normal bump, since we have not yet hit our last possible char
                upward[index] = alphabet[upward[index]-'a'+1];
            }
            else
            {
                //We need our left guy to bump first!
                increment(pfr+1);

                //Now we need to reset ourselves
                upward[index] = alphabet[upward[index-1]-'a'+gap+1];
            }
        }

        public String toString()
        {
            StringBuilder result = new StringBuilder();
            for(int i = 0; i < upward.length; i++)
            {
                result.append(upward[i]);
            }
            return result.toString();
        }
    }
}
