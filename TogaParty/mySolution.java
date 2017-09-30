import java.util.HashMap;
import java.util.Scanner;

/*
    A shockingly annoying brute force problem. Thought it would be easier going
    in honestly. I mean, it was easy, it was just longer to code
    out compared to what I was expecting in my head...
*/

public class mySolution {
    public static void main(String[] args)
    {
        Scanner scan = new Scanner(System.in);
        int party = 1;
        while(true)
        {
            int numRelations = scan.nextInt();
            if (numRelations == 0)
                break;

            String[] input = new String[numRelations];
            HashMap<Character, Integer> boyMap = new HashMap<>();
            HashMap<Character, Integer> girlMap = new HashMap<>();
            int numBoys = 0, numGirls = 0;

            //Count and map boys and girls...
            for(int r = 0; r < numRelations; r++)
            {
                input[r] = scan.next();
                if (Character.isLowerCase(input[r].charAt(0)))
                {
                    //Boy is in 0
                    if (!boyMap.containsKey(input[r].charAt(0)))
                    {
                        boyMap.put(input[r].charAt(0), numBoys);
                        numBoys++;
                    }
                    if (!girlMap.containsKey(input[r].charAt(2)))
                    {
                        girlMap.put(input[r].charAt(2), numGirls);
                        numGirls++;
                    }
                }
                else
                {
                    //Girl is in 0
                    if (!boyMap.containsKey(input[r].charAt(2)))
                    {
                        boyMap.put(input[r].charAt(2), numBoys);
                        numBoys++;
                    }
                    if (!girlMap.containsKey(input[r].charAt(0)))
                    {
                        girlMap.put(input[r].charAt(0), numGirls);
                        numGirls++;
                    }
                }
            }

            // relationb[x][y] = the relationship from boy x to girl y
            char[][] relationb = new char[numBoys][numGirls];
            //vice versa
            char[][] relationg = new char[numGirls][numBoys];
            for(int r = 0; r < numRelations; r++)
            {
                //Form relations
                if (Character.isLowerCase(input[r].charAt(0)))
                {
                    relationb[boyMap.get(input[r].charAt(0))][girlMap.get(input[r].charAt(2))] = input[r].charAt(1);
                }
                else
                {
                    relationg[girlMap.get(input[r].charAt(0))][boyMap.get(input[r].charAt(2))] = input[r].charAt(1);
                }
            }

            //Now do the brute force! All possible pairs!
            int result = maxHappiness(0, numBoys, numGirls, relationb, relationg, new boolean[numGirls]);
            System.out.println("Party " + party + " has a maximum happiness quotient of " + result);

            party++;
        }
    }

    //If for some reason I had to, I could make this a bitmask DP.
    public static int maxHappiness(int bindex, int numBoys, int numGirls, char[][] relationb, char[][] relationg, boolean[] usedGirl)
    {
        //We have used all the boys!
        if (bindex >= numBoys)
            return 0;

        //Use boy bindex. Try pairing with all girls
        int maxHap = 0;
        for(int g = 0; g < numGirls; g++)
        {
            if (usedGirl[g])
                continue;

            //Try using this girl!
            usedGirl[g] = true;

            int hap = 0;
            if (relationb[bindex][g] == 'L' && relationg[g][bindex] == 'L')
            {
                hap  = 4;
            }
            else if (relationb[bindex][g] == 'T' && relationg[g][bindex] == 'L')
            {
                hap = 3;
            }
            else if (relationb[bindex][g] == 'L' && relationg[g][bindex] == 'T')
            {
                hap = 3;
            }
            else if (relationb[bindex][g] == 'T' && relationg[g][bindex] == 'T')
            {
                hap = 3;
            }
            else if (relationg[g][bindex] == 'T' || relationg[g][bindex] == 'L')
            {
                hap = 2;
            }
            else if (relationb[bindex][g] == 'T' || relationb[bindex][g] == 'L')
            {
                hap = 1;
            }
            hap = hap + maxHappiness(bindex+1, numBoys, numGirls, relationb, relationg, usedGirl);

            maxHap = Math.max(maxHap, hap);
            usedGirl[g] = false;
        }

        //Or, don't use the boy at bindex at all!
        maxHap = Math.max(maxHap, maxHappiness(bindex+1, numBoys, numGirls, relationb, relationg, usedGirl));

        return maxHap;
    }
}
