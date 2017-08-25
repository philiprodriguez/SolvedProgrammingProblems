// Arup Guha
// 1/17/2014
// Solution to 2009 UCF Local Contest Problem: Tautobots and Contradicticons
// Used to illustrate bitwise operators and application of logic for COT 3100

import java.util.*;

public class logotron {

	public static void main(String[] args) {

		Scanner stdin = new Scanner(System.in);
		int numCases = stdin.nextInt();

		// Go through each case.
		for (int loop=1; loop<=numCases; loop++) {

			int numPeople = stdin.nextInt();
			int numFacts = stdin.nextInt();
			fact[] stmts = new fact[numFacts];

			// Read all input.
			for (int i=0; i<numFacts; i++) {
				int author = stdin.nextInt() - 1;
				int subject = stdin.nextInt() - 1;
				char input = stdin.next().charAt(0);
				stmts[i] = new fact(author, subject, input);
			}

			// Loop through each truth setting set in the mask i.
			int cnt = 0;
			for (int i=0; i<(1 << numPeople); i++) {

				// Check consistency of each fact with truth setting i.
				boolean okay = true;
				for (int j=0; j<stmts.length; j++) {
					if (!stmts[j].consistent(i)) {
						okay = false;
						break;
					}
				}

				// Count if all facts were consistent.
				if (okay) cnt++;
			}

			// Output our result.
			System.out.println("Case #"+loop+": "+cnt);
		}
	}
}

class fact {

	public int author;
	public int subject;
	public int truthTeller;

	public fact(int a, int s, char input) {
		author = a;
		subject = s;
		if (input == 'T') truthTeller = 1;
		else			  truthTeller = 0;
	}

	// mask = each person's truth setting in a bit mask. This function returns if this
	// fact is consistent with this set of truth settings.
	public boolean consistent(int mask) {
		return 1 == ( ((mask >> author) & 1)  ^ ((mask >> subject) & 1) ^ truthTeller );
	}
}