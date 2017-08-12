
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;

/**
 *
 * Author Philip
 */
/*
    An approach that uses cumulative sums... this is the closest to the failed
    solution I had in contest, just because I tried to leverage cumulative sums
    in contest as well, though in a bit of a different manner.
*/
public class g {
    public static void main(String[] args) throws IOException
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        //Position i represents the sum of costs of houses 1 thru i.
        long[] cumSum = new long[1000001];
        cumSum[1] = 2;
        for(int i = 2; i < cumSum.length; i++)
        {
            cumSum[i] = cumSum[i-1]+(i+1);
        }
        
        int[] results = new int[10000001];
        
        for(int start = 1; start < cumSum.length; start++)
        {
            for(int end = start; end < cumSum.length; end++)
            {
                //Consider the range from start to end inclusive...
                if (cumSum[end]-cumSum[start-1] <= 1000000)
                {
                    results[(int)(cumSum[end]-cumSum[start-1])]++;
                }
                else
                {
                    //We are out of range of 1000000. There is no use in incrementing
                    //end anymore, since it will only make us MORE out of range from
                    //our possible max input of 1000000.
                    break;
                }
            }
        }
        
        int input;
        while((input = Integer.parseInt(br.readLine())) != 0)
        {
            //There is input to be had!
            System.out.println(results[input]);
        }
    }
}
