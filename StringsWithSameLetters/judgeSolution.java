// Arup Guha
// 9/6/2016
// Solution to 2013 NCNA Problem H: Strings with Same Letters

import java.util.*;

public class letters {
	
	public static void main(String[] args) {
		
		Scanner stdin = new Scanner(System.in);
		String s1 = stdin.next();
		String s2 = stdin.next();
		int loop = 1;
		
		// We only end if both are END.
		while (!s1.equals("END") || !s2.equals("END")) {
			
			// Let Java sort the letters for us =)
			char[] list1 = s1.toCharArray();
			char[] list2 = s2.toCharArray();
			Arrays.sort(list1);
			Arrays.sort(list2);
			s1 = new String(list1);
			s2 = new String(list2);
			
			// Output accordingly strings are equal iff we want same for output.
			if (s1.equals(s2))
				System.out.println("Case "+loop+": same");
			else
				System.out.println("Case "+loop+": different");
			
			// Get next case.
			s1 = stdin.next();
			s2 = stdin.next();
			loop++;
		}	
	}
}