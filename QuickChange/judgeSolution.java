// Arup Guha
// 5/5/2013
// Written as solution for 2011 Jordan Contest Problem A. It's also 2006 NY Regional Problem A =)

import java.util.*;

public class change {

	public static void main(String[] args) {

		Scanner stdin = new Scanner(System.in);
		int numCases = stdin.nextInt();

		// Go through each case.
		for (int loop=1; loop<=numCases; loop++) {

			// Do Greedy.
			int cents = stdin.nextInt();
			int q = cents/25;
			cents -= (q*25);
			int d = cents/10;
			cents -= (d*10);
			int n = cents/5;
			int p = cents%5;

			// Output.
			System.out.print(loop+" ");
			System.out.println(q+" QUARTER(S), "+d+" DIME(S), "+n+" NICKEL(S), "+p+" PENNY(S)");
		}
	}
}