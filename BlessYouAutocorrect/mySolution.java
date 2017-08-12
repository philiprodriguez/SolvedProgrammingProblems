import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 *
 * @author Philip
 */

/*
    Bless you autocorrect!

    A key optimization in this problem was only BFSing the graph one time. If 
    you BFS once per query, it will not run in time.

    Additionally, ensure that your parent and tab edges are separate from your
    normal child edges, which becomes important while building the trie. If they 
    are not separate, you risk running into an issue with a case such as the 
    following:

    3 1
    kjusahgshrui
    dsajfheuhfgak
    dsajfhkjusahgshrui
    dsajfhkjusahgshr

    whose correct answer is 10, not 6.
*/
public class b {
    public static StringTokenizer st;
    public static void main(String[] args) throws IOException, Exception
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());
        int dictSize = Integer.parseInt(st.nextToken());
        int numQueries = Integer.parseInt(st.nextToken());
        
        //Prepare to make a trie!
        TrieNode root = new TrieNode('.', null);
        
        //Insert all dictionary words 
        for(int dw = 0; dw < dictSize; dw++)
        {
            //It's one word per line of input, so this should work...
            String dictWord = br.readLine();
            insertWord(root, dictWord, true);
        }
        
        //Now, insert all query words... save their ends in order!
        ArrayList<TrieNode> goals = new ArrayList<TrieNode>();
        for(int qw = 0; qw < numQueries; qw++)
        {
            String queryWord = br.readLine();
            TrieNode goal = insertWord(root, queryWord, false);
            goals.add(goal);
        }
        
        //Perform a single BFS to get answers for all goals...
        HashMap<TrieNode, Integer> results = minKeys(root, goals);
        
        //Print out all the results in proper order!
        for(TrieNode goal : goals)
        {
            System.out.println(results.get(goal));
        }
    }
    
    //This method finds the minimum key presses required from root to get to each
    //TrieNode in goals, all in the runtime of one BFS. Cool stuff!
    public static HashMap<TrieNode, Integer> minKeys(TrieNode root, ArrayList<TrieNode> goals) throws Exception
    {
        //Prepare for fast isGoal checking!
        HashSet<TrieNode> goalSet = new HashSet<>();
        goalSet.addAll(goals);
        
        //Get a place to store the answer...
        HashMap<TrieNode, Integer> results = new HashMap<>();
        
        //A queue for the nodes and the distance it took to get to them...
        Queue<TrieNode> q = new LinkedList<>();
        Queue<Integer> dq = new LinkedList<>();
        
        //A visited set, which we want to be based on the memory addresses...
        HashSet<TrieNode> visited = new HashSet<>();
        
        //We got to root at 0 key presses
        q.add(root);
        dq.add(0);
        visited.add(root);
        
        while(!q.isEmpty() && results.size() < goalSet.size())
        {
            TrieNode curNode = q.poll();
            int distance = dq.poll();
            
            //Hey, we're done!
            if (goalSet.contains(curNode) && !results.containsKey(curNode))
            {
                results.put(curNode, distance);
            }
            
            for(TrieNode dest : curNode.children)
            {
                if (!visited.contains(dest))
                {
                    q.add(dest);
                    dq.add(distance+1);
                    visited.add(dest);
                }
            }
            //Don't forget about parents and tabs!
            if (curNode.parent != null && !visited.contains(curNode.parent))
            {
                q.add(curNode.parent);
                dq.add(distance+1);
                visited.add(curNode.parent);
            }
            if (curNode.tab != null && !visited.contains(curNode.tab))
            {
                q.add(curNode.tab);
                dq.add(distance+1);
                visited.add(curNode.tab);
            }
        }
        
        return results;
    }
    
    //Inserts a word into the trie rooted at [root]. Adds tab edges if [isDictionaryWord] is
    //true. Returns the last node of the word that was inserted.
    public static TrieNode insertWord(TrieNode root, String word, boolean isDictionaryWord)
    {
        ArrayList<TrieNode> newNodes = new ArrayList<TrieNode>();
        
        //Start at the root
        TrieNode curNode = root;
        
        //For each character, go through our trie!
        for(int c = 0; c < word.length(); c++)
        {
            TrieNode requiredChild = curNode.getChild(word.charAt(c));
            if (requiredChild != null)
            {
                //The node already exists, so just move to it
                //System.out.println("Moved to " + word.charAt(c));
                curNode = requiredChild;
            }
            else
            {
                //The node is new!
                //System.out.println("Added node for " + word.charAt(c));
                requiredChild = new TrieNode(word.charAt(c), curNode);
                curNode.children.add(requiredChild);
                curNode = requiredChild;
                newNodes.add(curNode);
            }
        }
        
        //Now, curNode should hold the end of the added string! Update all new nodes... only if it was a dictionary word!
        if (isDictionaryWord)
        {
            for(int n = 0; n < newNodes.size(); n++)
            {
                //Object comparison with equals is on purpose in this case
                if (curNode != newNodes.get(n))
                {
                    newNodes.get(n).tab = curNode;
                    //System.out.println("Added tab edge from " + newNodes.get(n).value + " to " + curNode.value);
                }
            }
        }
        return curNode;
    }
    
    private static class TrieNode
    {
        char value;
        TrieNode parent;
        TrieNode tab;
        ArrayList<TrieNode> children;
        
        public TrieNode(char value, TrieNode parent)
        {
            this.value = value;
            this.parent = parent;
            this.children = new ArrayList<TrieNode>();
        }
        
        public TrieNode getChild(char c)
        {
            for(int i = 0; i < children.size(); i++)
                if (children.get(i).value == c)
                    return children.get(i);
            return null;
        }
    }
}
