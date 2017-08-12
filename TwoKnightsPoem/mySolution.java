
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 *
 * Author Philip
 */

/*
    This problem is really just a lot of grunt work. It is just a BFS.
    One potentially easy-to-miss thing is that going to the shift key
    types nothing, and thus doesn't increment how far you got in the
    string. Also, two knights cannot occupy the same space!
*/
public class j {
    public static char[][] keyboard = 
    {
        {'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p'},
        {'a', 's', 'd', 'f', 'g', 'h', 'j', 'k', 'l', ';'},
        {'z', 'x', 'c', 'v', 'b', 'n', 'm', ',', '.', '/'},
        {'6', '7', '0', '1', '2', '3', '4', '5', '8', '9'}
    };
    
    public static boolean inRange(int r, int c)
    {
        return r >= 0 && c >= 0 && r < keyboard.length && c < keyboard[r].length;
    }
    
    public static final HashMap<Character, ArrayList<Character>> nextKeys = new HashMap<>();
    
    public static final int[] dr = {1, 1, -1, -1, 2, -2, 2, -2};
    public static final int[] dc = {2, -2, 2, -2, 1, 1, -1, -1};
    public static void prepareNextKeys()
    {
        for(int r = 0; r < keyboard.length; r++)
        {
            for(int c = 0; c < keyboard[r].length; c++)
            {
                nextKeys.put(keyboard[r][c], new ArrayList<>());
                for(int d = 0; d < dr.length; d++)
                {
                    int newR = r+dr[d];
                    int newC = c+dc[d];
                    
                    if (inRange(newR, newC))
                    {
                        nextKeys.get(keyboard[r][c]).add(keyboard[newR][newC]);
                    }
                }
            }
        }
    }
    
    public static char toUpper(char c)
    {
        if (c == ';')
            return ':';
        if (c == ',')
            return '<';
        if (c == '.')
            return '>';
        if (c == '/')
            return '?';
        
        return Character.toUpperCase(c);
    }
    
    public static boolean isSpace(char c)
    {
        return c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5';
    }
    
    public static boolean isShift(char c)
    {
        return c == '6' || c == '7' || c == '8' || c == '9';
    }
    
    public static void main(String[] args)
    {
        prepareNextKeys();
        
        Scanner scan = new Scanner(System.in);
        
        String input;
        while(!(input = scan.nextLine()).equals("*"))
        {
            //We have valid input...
            
            if (canType(input))
                System.out.println("1");
            else
                System.out.println("0");
        }
    }
    
    public static boolean canType(String message)
    {
        Queue<State> q = new LinkedList<>();
        HashSet<State> visited = new HashSet<>();
        
        q.add(new State('6', '9', 0));
        visited.add(new State('6', '9', 0));
        
        while(!q.isEmpty())
        {
            State cur = q.poll();
            //System.out.println("Pulled " + cur);
            
            if (cur.index >= message.length())
            {
                return true;
            }
            
            //Consider moving knight 1...
            for(Character nextKey : nextKeys.get(cur.k1))
            {
                //Can't be on the same key!
                if (cur.k2 == nextKey)
                    continue;
                
                //If we haven't already visited the state AND
                //If the other knight is on shift, and the uppercase next key is what we need..
                //or the other knight is not on shift, and the lowercase next key is what we need...
                //or the next key is a space and we need a space next...
                if (!visited.contains(new State(nextKey, cur.k2, cur.index+1))
                        && ((isShift(cur.k2) && toUpper(nextKey) == message.charAt(cur.index))
                        || (!isShift(cur.k2) && nextKey == message.charAt(cur.index))
                        || (isSpace(nextKey) && message.charAt(cur.index) == ' ')))
                {
                    visited.add(new State(nextKey, cur.k2, cur.index+1));
                    q.add(new State(nextKey, cur.k2, cur.index+1));
                    //System.out.println("Enqueued1 " + new State(nextKey, cur.k2, cur.index+1));
                }
                
                //If the next key is a safe shift...
                if (!visited.contains(new State(nextKey, cur.k2, cur.index)) && isShift(nextKey))
                {
                    visited.add(new State(nextKey, cur.k2, cur.index));
                    q.add(new State(nextKey, cur.k2, cur.index));
                    //System.out.println("Enqueued1S " + new State(nextKey, cur.k2, cur.index));
                }
            }
            
            //Consider moving knight 2...
            for(Character nextKey : nextKeys.get(cur.k2))
            {
                //Can't be on the same key!
                if (cur.k1 == nextKey)
                    continue;
                
                //If we haven't already visited the state AND
                //If the other knight is on shift, and the uppercase next key is what we need..
                //or the other knight is not on shift, and the lowercase next key is what we need...
                //or the next key is a space and we need a space next...
                //or the next key is a shift (safe)
                if (!visited.contains(new State(cur.k1, nextKey, cur.index+1)) 
                        && ((isShift(cur.k1) && toUpper(nextKey) == message.charAt(cur.index))
                        || (!isShift(cur.k1) && nextKey == message.charAt(cur.index))
                        || (isSpace(nextKey) && message.charAt(cur.index) == ' ')))
                {
                    visited.add(new State(cur.k1, nextKey, cur.index+1));
                    q.add(new State(cur.k1, nextKey, cur.index+1));
                    //System.out.println("Enqueued2 " + new State(cur.k1, nextKey, cur.index+1));
                }
                
                //If the next key is a safe shift...
                if (!visited.contains(new State(cur.k1, nextKey, cur.index)) && isShift(nextKey))
                {
                    visited.add(new State(cur.k1, nextKey, cur.index));
                    q.add(new State(cur.k1, nextKey, cur.index));
                    //System.out.println("Enqueued2S " + new State(cur.k1, nextKey, cur.index));
                }
            }
        }
        return false;
    }
    
    private static class State
    {
        char k1, k2;
        int index;
        public State(char k1, char k2, int index)
        {
            this.k1 = k1;
            this.k2 = k2;
            this.index = index;
        }

        @Override
        public int hashCode() {
            int hash = 3;
            hash = 31 * hash + this.k1;
            hash = 31 * hash + this.k2;
            hash = 31 * hash + this.index;
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final State other = (State) obj;
            if (this.k1 != other.k1) {
                return false;
            }
            if (this.k2 != other.k2) {
                return false;
            }
            if (this.index != other.index) {
                return false;
            }
            return true;
        }
        
        public String toString()
        {
            return "[k1 = " + k1 + "; k2 = " + k2 + "; index = " + index + "]";
        }
    }
}
