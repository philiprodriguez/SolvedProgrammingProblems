// Arup Guha
// 1/29/2014
// Solution to 2013 SI@UCF Final Contest Question: Upwards
// Used for COP 2014 COP 4516 Class

import java.util.*;

public class upwards {
	
	public static int curRank;
	public static int skip;
	public static int len;
	public static int rank;
	
	public static void main(String[] args) {
		
		Scanner stdin = new Scanner(System.in);
		int numCases = stdin.nextInt();
		
		// Go through each case.
		for (int loop=0; loop<numCases; loop++) {
			
			// Read in this case and set up our current rank.
			skip = stdin.nextInt();
			len = stdin.nextInt();
			rank = stdin.nextInt();
			curRank = 0;
			
			// Solve and output.
			System.out.println(getRank());
		}
	}
	
	// Wrapper function.
	public static String getRank() {
		char[] ans = new char[len];
		return getRankRec(ans, 0);
	}
	
	public static String getRankRec(char[] cur, int k) {
		
		// Base case, finished a string, see if it's the right one.
		if (k == len) {
			curRank++;
			if (curRank == rank) return new String(cur);
			else return null;
		}
		
		// Find the first possible character for slot k.
		char start = 'a';
		if (k > 0) 
			start = (char)(cur[k-1] + skip + 1);
		
		// Try each possible letter in slot k.
		for (char c=start; c<='z'; c++) {
			cur[k] = c;
			String tmp = getRankRec(cur, k+1);
			if (tmp != null) return tmp;
		}
		
		// No string of the proper rank was found.
		return null;
	}
}