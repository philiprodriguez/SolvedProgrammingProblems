
import java.util.HashSet;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Student
 */
public class typing {
    static char[][] keyboard = {
        {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i'},
        {'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r'},
        {'s', 't', 'u', 'v', 'w', 'x', 'y', 'z'}
    };
    
    public static void main(String[] args)
    {
        Scanner scan = new Scanner(System.in);
        
        int numCases = scan.nextInt();
        for(int c = 0; c < numCases; c++)
        {
            String first = scan.next();
            String second = scan.next();
            
            if (first.equals(second))
            {
                System.out.println("1");
            }
            else if (first.length() != second.length())
            {
                System.out.println("3");
            }
            else
            {
                //Are we similar or not? (2 or 3)
                
                boolean similar = true;
                for(int i = 0; i < first.length(); i++)
                {
                    if (!getNeighbors(first.charAt(i)).contains(second.charAt(i)) && (first.charAt(i) != second.charAt(i)))
                    {
                        similar = false;
                        break;
                    }
                }
                
                if (similar)
                {
                    System.out.println("2");
                }
                else
                {
                    System.out.println("3");
                }
            }
        }
    }
    
    static int[] dr = {0, 0, 1, -1, -1, 1, -1, 1};
    static int[] dc = {1, -1, 0, 0, -1, 1,  1, -1};
    public static HashSet<Character> getNeighbors(char theChar)
    {
        int or = -1, oc = -1;
        for(int r = 0; r < keyboard.length; r++)
        {
            for(int c = 0; c < keyboard[r].length; c++)
            {
                if (keyboard[r][c] == theChar)
                {
                    or = r;
                    oc = c;
                    break;
                }
            }
        }
        
        HashSet<Character> ans = new HashSet<Character>();
        for(int d = 0; d < dr.length; d++)
        {
            int newR = or+dr[d];
            int newC = oc+dc[d];
            
            if (inRange(newR, newC))
            {
                ans.add(keyboard[newR][newC]);
            }
        }
        return ans;
    }
    
    public static boolean inRange(int r, int c)
    {
        return r >= 0 && c >= 0 && r < keyboard.length && c < keyboard[r].length;
    }
}
