/*

    Took less than half an hour. Straight forward brute force problem with a
    runtime of O(2^N * M) which is less than 4 million basic statement
    executions for the given problem...

*/

import java.util.*;

public class mySolution
{
    public static void main(String[] args)
    {
        Scanner scan = new Scanner(System.in);

        int numCases = scan.nextInt();
        for(int c = 0; c < numCases; c++)
        {
            int numBots = scan.nextInt();
            int numStatements = scan.nextInt();
            Statement[] statements = new Statement[numStatements];
            for(int s = 0; s < numStatements; s++)
            {
                statements[s] = new Statement(scan.nextInt()-1, scan.nextInt()-1, scan.next().equals("T"));
            }

            System.out.println("Case #" + (c+1) + ": " + numAssignments(0, new boolean[numBots], statements));
        }
    }

    public static int numAssignments(int index, boolean[] bots, Statement[] statements)
    {
        if (index >= bots.length)
        {
            //We have built an assignment fully! Check it!
            if (Statement.checkAssignment(bots, statements))
                return 1;
            else
                return 0;
        }

        //The bot at index is either true or false
        //Suppose true!
        bots[index] = true;
        int trueWays = numAssignments(index+1, bots, statements);

        //Suppose false!
        bots[index] = false;
        int falseWays = numAssignments(index+1, bots, statements);

        return trueWays + falseWays;
    }

    private static class Statement
    {
        int author;
        int subject;

        //Let true be Tautobot, false be contradicticon
        boolean assertion;

        public Statement(int author, int subject, boolean assertion)
        {
            this.author = author;
            this.subject = subject;
            this.assertion = assertion;
        }

        public static boolean checkAssignment(boolean[] bots, Statement[] statements)
        {
            //Make sure all statements are valid!
            for(int s = 0; s < statements.length; s++)
            {
                if (bots[statements[s].author] == true)
                {
                    //The author is telling the truth!
                    if (bots[statements[s].subject] != statements[s].assertion)
                    {
                        //Broken!
                        return false;
                    }
                }
                else
                {
                    //The author is lying!
                    if (bots[statements[s].subject] == statements[s].assertion)
                    {
                        //Broken!
                        return false;
                    }
                }
            }

            //If we made it here, there were no issues!
            return true;
        }
    }
}
