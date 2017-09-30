// Arup Guha
// Written originally on 10/25/03 at SE Regional (not in contest though)
// Edited later and checked on 10/5/04 for COP 3503H Fall 2004 Homework

import java.io.*;
import java.util.*;

public class party
{
	public static void main(String[] args) throws Exception {
	
	  int i, n;
	  Scanner fIn = new Scanner(new File("party.in"));
	  int set = 1;
	  n = fIn.nextInt();
	
	  // Read in all the data sets.
	  while (n > 0) {
	
	    // Read in all the rules into the rule array.
	    char[][] rules = new char[n][];
	    for (i=0; i<n; i++) {
	    	rules[i] = new char[4];
			rules[i] = fIn.next().toCharArray();
	    }
	
	    // Fill in the two groups.
	    char[] males, females;
	    males = new char[9];
	    females = new char[9];
	    for (i=0; i<9; i++) {
	      males[i]=' ';
	      females[i]=' ';
	    }
	    int numm = fill(males, rules, n, true);
	    int numf = fill(females, rules, n, false);
	
	    // Do recursive trace here.    
	    System.out.print("Party " + set + " has a maximum happiness quotient of ");
	    System.out.println(scoregroup(males, females, numm, numf, rules, n));
	
	    n = fIn.nextInt();
	    set++;
	  }
	  fIn.close();
	}
	
	// This recursive function tabulates the maximal score for the list
	// of males and females inputted, using the first numm males and the
	// first numf females. n is the number of rules.
	public static int scoregroup(char males[], char females[], int numm, int numf,
		       char[][] rules, int n) {
	
	  // If there is no one to match, then the score is 0.
	  if (numm == 0 || numf == 0)
	    return 0;
	
	  // Less males than females. We'll keep the males in place and permute
	  // the females.
	  else if (numm <= numf) {
	
	    // Place each female with each male[0] and calculate all the scores,
	    // saving the maximum.
	    int max = 0;
	
	    // Loop through each female here.
	    for (int i=0; i<numf; i++) {
	
	      // Swap the two people you are matching up to the end of the array.
	      swap(males, 0, numm-1);
	      swap(females, i, numf-1);
	
	      // Score this couple you have just matched up.
	      int val = score(males[numm-1],females[numf-1], rules, n);
	
	      // Add to this score, the maximal score of matching up the rest
	      // of the people left, recursively.
	      val +=scoregroup(males, females, numm-1, numf-1, rules, n);
	
	      // If you get a better score here, save it as the maximum!
	      if (val > max) 
	        max = val;
	
	      // Swap everyone back to place before trying the next permutation.
	      swap(males, 0, numm-1);
	      swap(females, i, numf-1);
	    }
	
	    // Return the best score calculated.
	    return max;
	  }
	  else {
	
	    // place each female with each male[0] and take max.
	    int max = 0;
	
	    // Loop through each possible male here.
	    for (int i=0; i<numm; i++) {
	
	      // Swap the appropriate couple to the end of the arrays.
	      swap(males, i, numm-1);
	      swap(females, 0, numf-1);
	
	      // Calculate the score for that couple.
	      int val = score(males[numm-1],females[numf-1], rules, n);
	
	      // Recursively add the best score of the remaining people to match
	      // to this value to get the total score.
	      val +=scoregroup(males, females, numm-1, numf-1, rules, n);
	
	      // Update if you see a better score.
	      if (val > max) 
	        max = val;
	
	      // Swap the original match back to place to try the next permutation.
	      swap(males, i, numm-1);
	      swap(females, 0, numf-1);
	
	    }
	
	    // Return the best score seen.
	    return max;
	  }
	
	}
	
	// This function takes in a pair(male and female) and returns the score
	// the add to the group if matched together.
	public static int score(char m, char f, char[][] rules, int n) {
	  
	  char[][] r = new char[2][4];
	  int j=0;
	
	  // Go through the rules, copying into r only the pertinent rules for
	  // this couple.
	  for (int i=0; i<n; i++) {
	    if ((rules[i][0] == m && rules[i][2] == f) ||
	        (rules[i][0] == f && rules[i][2] == m)) {
	      r[j] = rules[i];
	      j++;
	    }
	  }
	
	  // If no rules apply, no points!
	  if (j==0)
	    return 0;
	
	  // One rule, so we'll give either 1 or 2 points.
	  if (j==1) {
	
	    // Here the male either tolerates or likes the female, so 1 point.
	    if (r[0][0] >= 'a' && r[0][0] <= 'z')
	      return 1;
	
	    // Otherwise the female tolerates or likes the male, so 2 points.
	    else
	      return 2; 
	  }
	
	  // Two rules, so either 3 or 4 points, since no rules are repeated or
	  // redundant.
	  if (j==2) {
	
	    // They like each other - 4 points =)
	    if (r[0][1] == 'L' && r[1][1] == 'L') 
	      return 4;
	
	    // At least they both tolerate each other!
	    else
	      return 3;
	  }
	  return 0;
	}
	
	// Given the list of rules, this stores the list of males or females
	// depending on the value of the boolean flag male.
	public static int fill(char males[], char[][] rules, int n, boolean male) {
	  int found = 0;
	  char first='A', last='Z';
	
	  // Set the letters to lowercase for males.
	  if (male) {
	    first ='a';
	    last = 'z';
	  }
	
	  // Read through all the rules.
	  for (int i=0; i<n; i++) {
	    char one = rules[i][0];
	    char two = rules[i][2];
	
	    // If you've found a person of the given gender, see if they should be
	    // added to the list.
	    if (one >= first && one <= last) {
	
	      // If they aren't in the list yet, put them in!
	      if (!inlist(males, one, found)) {
	        males[found] = one;
	        found++;
	      }
	    }
	
	    // This is if two is the person of the correct gender, which will
	    // automatically be true if one wasn't.
	    else {
	
	      // Add them if necessary!
	      if (!inlist(males, two, found)) {
	        males[found] = two;
	        found++;
	      }
	    } 
	  }
	
	  // This the total number of males/females found in the whole list.
	  return found;
	}
	
	// Returns true iff look is in the array list in between index 0 and num-1.
	public static boolean inlist(char list[], char look, int num) {
	  for (int i=0; i<num; i++)
	    if (list[i] == look)
	      return true;
	  return false;
	}
	
	// Swaps the addresses that a and b point to.
	public static void swap(char[] arr, int a, int b) {
	  
	  char temp = arr[a];
	  arr[a] = arr[b];
	  arr[b] = temp;
	}
}