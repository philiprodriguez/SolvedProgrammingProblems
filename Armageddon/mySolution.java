
import java.util.Scanner;

/**
 *  So my immediate reaction to this is it's just asking for the least common
 * multiple of the input numbers.
 * 
 * We are given for each planet a number telling us how many days it takes for
 * that planet to go from the origin back to the origin. We want ALL planets
 * back at the origin. Suppose our numbers are n1, n2, n3, etc. We want the
 * number d such that all our numbers n1, n2, n3, etc evenly divide d. If this
 * is the case, then that means after d days, all planets are at the origin
 * since d was a multiple of any given planet's time back to origin.
 * 
 * We specifically want the smallest such d. Aka, the LEAST common multiple!
 * Cool.
 * 
 * How do we find the LCM? Well, the LCM of a and b is the max exponents.
 * The GCD is the min common exponents. This means lcm * gcd = a*b, so lcm
 * is just a*b / gcd. Let's do that, since we have a quick GCD method.
 */

public class mySolution {
    public static void main(String[] args)
    {
        Scanner scan = new Scanner(System.in);
        int solarSystemNumber = 1;
        while(true)
        {
            int numNums = scan.nextInt();
            if (numNums == 0)
                break;
            
            long[] nums = new long[numNums];
            for(int n = 0; n < numNums; n++)
            {
                nums[n] = scan.nextLong();
            }
            long days = lcm(nums);
            if (days <= 2147483647)
            {
                System.out.println(solarSystemNumber + ": THE WORLD ENDS IN " + days + " DAYS");
            }
            else
            {
                System.out.println(solarSystemNumber + ": NOT TO WORRY");
            }
            solarSystemNumber++;
        }
    }
    
    public static long lcm(long[] list)
    {
        long lcm = list[0];
        for(int i = 1; i < list.length; i++)
        {
            lcm = lcm(lcm, list[i]);
            
            //Just quit it if we're already too big
            if (lcm > 9999999999L)
                return 9999999999L;
        }
        return lcm;
    }
    
    public static long lcm(long a, long b)
    {
        return (a*b)/gcd(a, b);
    }
    
    public static long gcd(long a, long b)
    {
        if (a < 0)
            a *= -1;
        if (b < 0)
            b *= -1;
        if (b == 0)
            return a;
        if (b > a)
            return gcd(b, a);
        return gcd(b, a%b);
    }
}
