
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Scanner;

/*
    This one took me actually quite a while. There were many overflowy things
    happening with it that took me a while to finally give up on and just move
    to using BigInteger.

    I "solved" the problem in my head conceptually withing 10 minutes, though,
    so that was good.
 */

/**
 *
 * @author phili
 */
public class mySolution {
    public static final double eps = 1e-2;
    public static void main(String[] args)
    {
        Scanner scan = new Scanner(System.in);
        int numCases = scan.nextInt();
        
        for(int c = 0; c < numCases; c++)
        {
            int numPoints = scan.nextInt();
            
            long[] px = new long[numPoints+1];
            long[] py = new long[numPoints+1];
            px[0] = px[numPoints] = 0;
            py[0] = py[numPoints] = 0;
            for(int p = 1; p < numPoints; p++)
            {
                px[p] = scan.nextLong();
                py[p] = scan.nextLong();
            }
            
            //Do the thing in pairs!
            BigInteger totLp = BigInteger.ZERO;
            for(int sp = 0; sp < px.length-1; sp++)
            {
                //How many lattice points from point sp to point sp+1
                BigInteger slopeTop = BigInteger.valueOf(py[sp+1]-py[sp]);
                BigInteger slopeBottom = BigInteger.valueOf(px[sp+1]-px[sp]);
                double magLine = Math.sqrt(slopeTop.pow(2).doubleValue()+slopeBottom.pow(2).doubleValue())+eps;
                //System.out.println("ml " + magLine);
                BigInteger gcd = gcd(slopeTop, slopeBottom);
                //System.out.println("GCD = " + gcd);
                //Reduce fraction
                slopeTop = slopeTop.divide(gcd);
                slopeBottom = slopeBottom.divide(gcd);
                
                double magSlope = Math.sqrt(slopeTop.pow(2).doubleValue()+slopeBottom.pow(2).doubleValue());
                //System.out.println("ms "+ magSlope);
                
                long latticePoints = ((long)Math.floor(magLine/magSlope));
                //System.out.println("adding " + latticePoints);
                totLp = totLp.add(BigInteger.valueOf(latticePoints));
            }
            System.out.println(totLp);
        }
    }
    
    public static BigInteger gcd(BigInteger a, BigInteger b)
    {
        if (a.compareTo(BigInteger.valueOf(0)) < 0)
            a = a.multiply(BigInteger.valueOf(-1));
        if (b.compareTo(BigInteger.valueOf(0)) < 0)
            b = b.multiply(BigInteger.valueOf(-1));
        if (b.compareTo(a) > 0)
            return gcd(b, a);
        if (b.equals(BigInteger.ZERO))
            return a;
        return gcd(b, a.mod(b));
    }
}
