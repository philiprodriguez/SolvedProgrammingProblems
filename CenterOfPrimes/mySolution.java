/*
  Alright, my plan here is to generate all primes up to 10,000 (the largest
  possible N) as preprocessing, and then for each query just binary search the
  list of primes for the start and the end and then find the middle and print
  the range etc...

  Runtime? Well, 2*log(2; N)  for finding the start and end of the interval
  I am looking for, and then I have to print the interval, which will have a
  worst case runtime of N if the interval is the entire list of primes. Thus,
  2*log(2; N)+N ~~ O(N)

  Could I probably get away with just a linear search for the start and end
  searches? Yeah probably, but then I wouldn't get extra practice whipping up
  binary search. I'll also use the sieve of eratosthenes because practice.

  If I didn't use the sieve, I could do a sqrt(N)*N or O(N^1.5) operation to
  create my list of primes. But that's boring.
*/

import java.util.*;

public class mySolution
{
  public static void main(String[] args)
  {
    //Generate a list of primes up to 10000 using sieve or eratosthenes!
    ArrayList<Integer> primes = new ArrayList<Integer>();
    boolean[] composite = new boolean[10000+1];

    //"Cross off 0 and 1" since we start at 2 as the first prime!
    composite[0]=true;
    composite[1]=true;

    //Since every composite has a prime factor less than or equal to it's square
    //root, we can stop checking after the square root...
    for(int i = 2; i < (int)(Math.sqrt(composite.length)+1); i++)
    {
      //If not crossed off...
      if (!composite[i])
      {
        //i must be prime

        //Loop through multiples and cross them off!
        //Move in steps of i starting at one step past i...
        for(int j = i+i; j < composite.length; j+=i)
        {
          composite[j] = true;
        }
      }
    }

    //Apparently 1 is included for this problem, for some reason...
    primes.add(1);

    //Now actually throw the primes into primes...
    for(int i = 0; i < composite.length; i++)
    {
      if (!composite[i])
        primes.add(i);
    }

    //Overall, we've done only O(N) work with a max N of 10000... not bad.



    Scanner scan = new Scanner(System.in);
    int numCases = scan.nextInt();
    for(int c = 0; c < numCases; c++)
    {
      int listMax = scan.nextInt();
      int backStep = scan.nextInt();

      int endPlace = findPlace(listMax, primes);
      //If the number of numbers we're talking about is even, then 2*backStep
      //otherwise, 2*backStep-1
      int numToPrint = ((endPlace+1)%2 == 0) ? 2*backStep : 2*backStep-1;

      int printStart = Math.max(endPlace/2 - backStep + 1, 0);
      System.out.print(listMax + ": ");
      for(int i = 0; i < numToPrint; i++)
      {
        if (primes.get(i) <= listMax)
          System.out.print(primes.get(printStart+i) + " ");
      }
      System.out.println();
      System.out.println();
    }
  }

  public static int findPlace(int num, ArrayList<Integer> primes)
  {
    //Binary search!
    int lo=0;
    int hi=primes.size()-1;
    while(lo<hi)
    {
      int mid = ((hi-lo)/2)+lo;
      if (primes.get(mid) == num)
        return mid;
      else if (primes.get(mid) > num)
      {
        //mid too high
        hi = mid-1;
      }
      else
      {
        //mid too low
        lo=mid+1;
      }
    }
    //So if we found a place that is above num, go back one (we dont want to include it)
    if (primes.get(lo) > num)
      return lo-1;
    else
      return lo;
  }
}
