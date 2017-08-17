// Arup Guha
// 5/21/2013
// Solution to 2006 NY Regional Problem C: Shuffle'm Up

import java.util.*;

public class shuffle {
	
	public static void main(String[] args) {
		
		Scanner stdin = new Scanner(System.in);
		int numCases = stdin.nextInt();
		
		// Go through each case.
		for (int loop=1; loop<=numCases; loop++) {
			
			int n = stdin.nextInt();
			String s1 = stdin.next();
			String s2 = stdin.next();
			String goal = stdin.next();
			
			int sol = solve(s1, s2, goal);
			System.out.println(loop+" "+sol);
		}
	}
	
	// Solve the problem via simulation.
	public static int solve(String s1, String s2, String goal) {
		
		int n = s1.length();
		String curStack = s1+s2;
			
		// Run sim for maximal cycle length of all the cards.
		for (int i=0; i<=2*n; i++) {
			
			// Success!
			if (curStack.equals(goal)) return i;
			
			// Do one more iteration.
			curStack = shuffle(curStack);
		}
		
		// Never matched.
		return -1;
	}
	
	// Shuffles.
	public static String shuffle(String s) {
		
		int n = s.length();
		String answer = "";
		
		// Shuffle, one card from each side...
		for (int i=0; i<n/2; i++) {
			answer = answer + s.charAt(n/2+i);
			answer = answer+s.charAt(i);
		}
		
		return answer;
		
	}
}