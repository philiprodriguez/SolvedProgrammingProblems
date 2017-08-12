
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 *
 * Author Philip
 */
public class e {
    public static void main(String[] args) throws IOException
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int numNums;
        while((numNums = Integer.parseInt(br.readLine())) != 0)
        {
            int[] nums = new int[numNums];
            for(int n = 0; n < numNums; n++)
            {
                nums[n] = Integer.parseInt(br.readLine());
            }
            
            int numGcd = 0;
            for(int gcd = 1; gcd <= 100; gcd++)
            {
                //Can we make a gcd of gcd?
                if (canMakeGCD(gcd, nums))
                    numGcd++;
            }
            System.out.println(numGcd);
        }
    }
    
    /*
        The key to answering this question in linear time is knowing
        that after dividing the supposed gcd from a set of numbers,
        that number was their gcd if and only if the remaining numbers
        are relatively prime after having the gcd divided out of them.
        (Also, of course, if any of the numbers was not divisible by
        the supposed gcd, then the supposed gcd was not their gcd)
    
        Why is that the case? Well, suppose after dividing the actual
        GCD out of the numbers your numbers still had some common divisor.
        Then, you could have multiplied the GCD by that remaining common
        factor (you could have divided out a greater number)! Thus, if
        your numbers still had a common divisor, then the number you divided
        out was not the greatest common divisor.
    */
    public static boolean canMakeGCD(int gcd, int[] nums)
    {
        int rollingGCD = -1;
        for(int i = 0; i < nums.length; i++)
        {
            if (nums[i]%gcd == 0)
            {
                //gcd is a divisor of nums[i]...
                if (rollingGCD == -1)
                    rollingGCD = nums[i]/gcd;
                else
                    rollingGCD = gcd(rollingGCD, nums[i]/gcd);
                if (rollingGCD == 1)
                    return true;
            }
            else
            {
                //Not divisible! Certainly, then, the gcd cannot be found with a range involving this number...
                rollingGCD = -1;
            }
        }
        return false;
    }
        
    
    static int[][] memo = new int[101][101];
    static
    {
        for(int r = 0; r < memo.length; r++)
            Arrays.fill(memo[r], -1);
    }
    public static int gcd(int a, int b)
    {
        if (a < b)
            return gcd(b, a);
        if (b == 0)
            return a;
        if (memo[a][b] != -1)
            return memo[a][b];
        return memo[a][b] = gcd(b, a%b);
    }
}
