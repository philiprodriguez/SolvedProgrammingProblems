import java.util.*;

/*
    I'm just going to BFS this one being careful to not visit things more
    than once... I have a gut feeling we won't explore super far before we
    overlap a lot...

    Assuming it TLE's, I COULD just "hack" this problem since there are
    literally only 100 possible input values 0-99. Let's see if it comes to
    that.
*/

// Philip Rodriguez 8-13-2017

public class mySolution {
    public static void main(String[] args)
    {
        Scanner scan = new Scanner(System.in);
        int numNums = scan.nextInt();
        for(int n = 0; n < numNums; n++)
        {
            int startNum = scan.nextInt();

            //Do the BFS until we hit all 100!
            //General visited set
            HashSet<Integer> visited = new HashSet<Integer>();

            //Special map for only values in [0, 99]
            HashMap<Integer, Integer> visitedSpecial = new HashMap<Integer, Integer>();

            Queue<Integer> q = new LinkedList<Integer>();
            Queue<Integer> qd = new LinkedList<Integer>();

            //Visit that start state!
            q.add(startNum);
            qd.add(0);
            visited.add(startNum);
            visitedSpecial.put(startNum, 0);

            //BFS until we have hit all 0-99!
            while(visitedSpecial.keySet().size() < 100)
            {
                int curNum = q.poll();
                int curDist = qd.poll();

                int newNum;
                int newDist = curDist+1;

                //Alright, we can do three things now...
                //Suppose we add 1
                newNum = curNum + 1;
                if (newNum < 1000000000 && !visited.contains(newNum))
                {
                    visited.add(newNum);
                    if (newNum >= 0 && newNum <= 99)
                    {
                        visitedSpecial.put(newNum, newDist);
                    }
                    q.add(newNum);
                    qd.add(newDist);
                }

                //Suppose we multiply by 3
                newNum = curNum * 3;
                if (newNum < 1000000000 && !visited.contains(newNum))
                {
                    visited.add(newNum);
                    if (newNum >= 0 && newNum <= 99)
                    {
                        visitedSpecial.put(newNum, newDist);
                    }
                    q.add(newNum);
                    qd.add(newDist);
                }

                //Suppose we divide by 2
                newNum = curNum / 2;
                if (newNum < 1000000000 && !visited.contains(newNum))
                {
                    visited.add(newNum);
                    if (newNum >= 0 && newNum <= 99)
                    {
                        visitedSpecial.put(newNum, newDist);
                    }
                    q.add(newNum);
                    qd.add(newDist);
                }

            }

            int worst = -1;
            for(Integer k : visitedSpecial.keySet())
            {
                worst = Math.max(worst, visitedSpecial.get(k));
            }
            System.out.println(worst);
        }
    }
}
