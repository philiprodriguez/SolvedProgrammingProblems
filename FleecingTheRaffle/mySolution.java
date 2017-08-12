
import java.util.Scanner;

public class f {

    static double[] preComputedFactorials = new double[3000000];
    
    static void preComputeFactorials()
    {
        preComputedFactorials[0] = Math.log(1);
        for(int i = 1; i < preComputedFactorials.length; i++)
        {
            preComputedFactorials[i] = preComputedFactorials[i-1]+Math.log(i);
        }
    }
    
    public static void main(String[] args) {
        preComputeFactorials();
        Scanner scan = new Scanner(System.in);

        int n = scan.nextInt();
        int p = scan.nextInt();

        int low = 0;
        int hi = 1500000;
        double max = 0;
        while (low < hi) {
            int mid1 = low + ((hi - low) / 3);
            int mid2 = low + (2 * ((hi - low) / 3));
            double r1 = probOfOneDraw(mid1, n, p);
            double r2 = probOfOneDraw(mid2, n, p);
            //System.out.println(r1 + ", " + r2);
            max = Math.max(max, Math.max(r1, r2));
            if (r1 < r2) {
                //Trash mid1
                low = mid1;
            } else {
                //Trash mid2
                hi = mid2;
            }
        }
        System.out.println(max);

    }
    
    
    static double probOfOneDraw(int x, int n, int p)
    {
        double result = Math.log(p)+Math.log(x);
        result = result + (preComputedFactorials[n] - preComputedFactorials[n-(p-1)]);
        result = result - (preComputedFactorials[n+x]-preComputedFactorials[n+x-p]);
        return Math.pow(Math.E, result);
    }
    
/*
    static double probOfOneDraw(int x, int n, int p) {
        double result = 1;
        int nextDenomSub = 0;

        //Deal with p
        result = (result * p) / (n + x - nextDenomSub);
        //Increment denominator
        nextDenomSub++;
        if (nextDenomSub > p - 1) {
            nextDenomSub = n + x - 1;
        }

        //Deal with x
        result = (result * x) / (n + x - nextDenomSub);
        //Increment denominator
        nextDenomSub++;
        if (nextDenomSub > p - 1) {
            nextDenomSub = n + x - 1;
        }

        //Deal with n*n-1*n-2*...*n-(p-2)
        for (int sub = 0; sub <= p - 2; sub++) {
            result = (result * (n - sub)) / (n + x - nextDenomSub);
            //Increment denominator
            nextDenomSub++;
            if (nextDenomSub > p - 1) {
                nextDenomSub = n + x - 1;
            }
        }

        return result;
    }
    */

    /*static BigDecimal probOfOneDraw(int x, int n, int p)
	{
		BigDecimal result = new BigDecimal("1");
		
		BigDecimal nextDenom = new BigDecimal((n+x)+"");
		
		//Deal with p
		result = result.multiply(BigDecimal.valueOf(p).divide(nextDenom, MathContext.DECIMAL128), MathContext.DECIMAL128);
		nextDenom = nextDenom.subtract(BigDecimal.ONE, MathContext.DECIMAL128);
		if (nextDenom.compareTo(BigDecimal.ONE) < 0)
			nextDenom = BigDecimal.ONE;
		
		//Deal with n's...
		for(int sub = 0; sub <= p-2; sub++)
		{
			BigDecimal numerator = BigDecimal.valueOf(n-sub);
			
			result = result.multiply(numerator.divide(nextDenom, MathContext.DECIMAL128), MathContext.DECIMAL128);
			
			nextDenom = nextDenom.subtract(BigDecimal.ONE, MathContext.DECIMAL128);
			if (nextDenom.compareTo(BigDecimal.ONE) < 0)
				nextDenom = BigDecimal.ONE;
		}
		
		//Deal with x...
		result = result.multiply(BigDecimal.valueOf(x).divide(nextDenom, MathContext.DECIMAL128), MathContext.DECIMAL128);
		
		nextDenom = nextDenom.subtract(BigDecimal.ONE, MathContext.DECIMAL128);
		if (nextDenom.compareTo(BigDecimal.ONE) < 0)
			nextDenom = BigDecimal.ONE;
		
		//Deal with a!
		for(int a = (x-1)+(n-(p-1)); a >= 1; a--)
		{
			result = result.multiply(BigDecimal.valueOf(a).divide(nextDenom, MathContext.DECIMAL128), MathContext.DECIMAL128);
			
			nextDenom = nextDenom.subtract(BigDecimal.ONE, MathContext.DECIMAL128);
			if (nextDenom.compareTo(BigDecimal.ONE) < 0)
				nextDenom = BigDecimal.ONE;
		}
		
		//dont forget to check nextDenom
		while (nextDenom.compareTo(BigDecimal.ONE) > 0)
		{
			result = result.divide(nextDenom, MathContext.DECIMAL128);
			nextDenom = nextDenom.subtract(BigDecimal.ONE, MathContext.DECIMAL128);
			if (nextDenom.compareTo(BigDecimal.ONE) < 0)
				nextDenom = BigDecimal.ONE;
		}
		
		return result;
	}*/
}
