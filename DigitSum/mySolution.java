/*
    There may be some sort of super fast greedy solution here
    involving the facts that the length of the numbers is going
    to be essentially evenly split to minimize the sum and
    the numerals in each number will be sorted to minimize the
    highest place in each number.... I then imagine that we would
    want the larger of the two numbers to take the smallest numeral
    for its left most place to keep things small...

    There's also a O(2^n) brute force involving putting each numeral
    either in the first number or the second number, but I'll try
    greedy stuff first...

    Looks like the greedy worked... yay.
*/

import java.util.*;

public class mySolution
{
    public static void main(String[] args)
    {
        Scanner scan = new Scanner(System.in);
        while(true)
        {
            int numNums = scan.nextInt();
            if (numNums == 0)
                break;
            int[] numerals = new int[10];
            for(int n = 0; n < numNums; n++)
            {
                //Add one to numeral [index]...
                numerals[scan.nextInt()]++;
            }

            ArrayList<Integer> num1 = new ArrayList<Integer>();
            ArrayList<Integer> num2 = new ArrayList<Integer>();

            //Starting from numeral 1, split into num1/num2...
            for(int n = 1; n < 10; n++)
            {
                while(numerals[n]>0)
                {
                    //Tack on numeral n to the smaller number...
                    if (isLEQ(num1, num2))
                    {
                        num1.add(n);
                    }
                    else
                    {
                        num2.add(n);
                    }
                    numerals[n]--;
                }
            }

            //Now handle those 0's...
            while(numerals[0] > 0)
            {
                if (isLEQ(num1, num2))
                {
                    //Shove into index 1 a zero...
                    num1.add(1, 0);
                }
                else
                {
                    num2.add(1, 0);
                }
                numerals[0]--;
            }

            //Convert back to numbers and add them...
            System.out.println((toInt(num1)+toInt(num2)));
        }
    }

    //Return true if the number in a is less than or equal to the number in b.
    //Note that an empty number is less than or equal to everything.
    public static boolean isLEQ(ArrayList<Integer> a, ArrayList<Integer> b)
    {
        if (a.size() < b.size())
        {
            return true;
        }
        else if (b.size() < a.size())
        {
            return false;
        }
        else
        {
            //Equal size, gotta look at the numbers!
            for(int i = 0; i < a.size(); i++)
            {
                if (a.get(i) < b.get(i))
                {
                    return true;
                }
                if (b.get(i) < a.get(i))
                {
                    return false;
                }
            }

            //Equal
            return true;
        }
    }

    public static int toInt(ArrayList<Integer> num)
    {
        int result = 0;
        for(int i = 0; i < num.size(); i++)
        {
            if (num.get(i)!=0)
                result += num.get(i)*Math.pow(10, num.size()-(i+1));
        }
        return result;
    }
}
