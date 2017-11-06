// Centers of Primes problem

// FIU High School Programming Competition, 2015
// Solution by Kip Irvine

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class center {

	boolean[] sieve;
	List<Integer> primeVals;
	final int largestPrime = 10000;
	
	void makeSieve() {
		// only needs to be done once
		sieve = new boolean[largestPrime+1]; 
		primeVals = new ArrayList<>();
		
		for(int i=0; i <= largestPrime; i++)
			sieve[i] = true;	
		
		int i = 2;
		int lasti = largestPrime / 2;
		
		while( i < lasti) {
			int j = i+i;							// mark all multiples of i
			while( j <= largestPrime) {
				sieve[j] = false;			
				j += i;
			}
			i++;
			while( !sieve[i] && i < lasti )   			// move i to next prime
				i++;
		} // while i

		// build the list of primes
		for(i=1; i< largestPrime; i++) {
			if( sieve[i] )
				primeVals.add(i);		
		}
				
	}  // makeSieve
		
	void start() //throws Exception 
	{
		makeSieve();
		Scanner in = new Scanner(System.in);
		//Scanner in = new Scanner(new File("centerprimes.judge.in.txt"));
		int numTestCases = in.nextInt();

		for(int t=0; t < numTestCases; t++) {
			
			int largestPrime = in.nextInt();
			int centerOffset = in.nextInt();
			// count the primes 1..largestPrime
			int count = 0;
			for( Integer pval : primeVals )			
				if( pval <= largestPrime)
					count++;		
			
			int mid = count / 2;
			int first, last;
			if( count % 2 == 0)
				first = mid - centerOffset;
			else
				first = mid - centerOffset + 1;
			
			last = mid + centerOffset - 1;		
			first = Math.max(0, first);
			last = Math.min(last, count-1);
			
			System.out.printf("%d:",largestPrime);
			for(int j=first; j <= last; j++) {
				System.out.printf(" %d", primeVals.get(j));
			}
			System.out.println("\n");
		} // for t
	} // start
		
	public static void main(String[] args) //throws Exception 
	{
		new CenterPrimes().start();
	}
}
