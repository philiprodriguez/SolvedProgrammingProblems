
import java.io.*;
import java.util.*;
import java.math.*;

/**
 * Solution to Integer Estate
 * 
 * @author vanb
 */
public class gold_vanb
{
    public Scanner sc;
    public PrintStream ps;
    
    public int gold[] = new int[40];
    public boolean road[][] = new boolean[40][40];
    public boolean visited[] = new boolean[40];
    public int n;
    
    public class State implements Comparable<State>
    {
        public int node, distance, wealth;
        
        public State( int node, int distance, int wealth )
        {
            this.node = node;
            this.distance = distance;
            this.wealth = wealth;
        }

        @Override
        public int compareTo( State s )
        {
            int diff = distance-s.distance;
            if( diff==0 ) diff = s.wealth - wealth;
            return diff;
        }
        
        public String toString()
        {
            return "[" + node + "," + distance + "," + wealth + "]";
        }
    }
    
    public boolean castle( int node )
    {
        boolean madeit = false;
        visited[node] = true;
        if( node==1 )
        {
            madeit = true;
        }
        else
        {
            for( int i=0; i<n && !madeit; i++ ) if( road[node][i] && !visited[i] )
            {
                madeit = castle(i);
            }
        }
        
        return madeit;
    }
        
    /**
     * Driver.
     * @throws Exception
     */
    public void doit() throws Exception
    {
        sc = new Scanner( System.in ); //new File( "gold.in" ) );
        ps = System.out; //new PrintStream( new FileOutputStream( "gold.vanb" ) );
        
        PriorityQueue<State> pq = new PriorityQueue<State>();
        
        for(;;)
        {
            n = sc.nextInt();
            int m = sc.nextInt();
            if( n==0 && m==0 ) break;
            
            for( int i=0; i<n; i++ )
            {
                Arrays.fill( road[i], false );
            }
            
            for( int i=2; i<n; i++ )
            {
                gold[i] = sc.nextInt();
            }
            
            for( int i=0; i<m; i++ )
            {
                int a = sc.nextInt()-1;
                int b = sc.nextInt()-1;
                road[a][b] = road[b][a] = true;
            }
            
            Arrays.fill( visited, false );
            pq.clear();
            pq.add( new State( 0, 0, 0 ) );
            int tribute = 0;
            while( !pq.isEmpty() )
            {
                State s = pq.poll();
                if( s.node==1 )
                {
                    tribute = s.wealth;
                    break;
                }
                
                visited[s.node] = true; 
                
                for( int i=0; i<n; i++ ) if( road[s.node][i] && !visited[i] )
                {
                    pq.add( new State( i, s.distance+1, s.wealth+gold[i] ) );    
                }
            }

            for( int i=2; i<n; i++ )
            {
                Arrays.fill( visited, false );
                visited[i] = true;
                if( !castle(0) ) tribute -= gold[i];
            }
            
            ps.println( tribute );
            
            
        }
    }
    
    /**
     * @param args
     */
    public static void main( String[] args ) throws Exception
    {
        //long start = System.currentTimeMillis();
        new gold_vanb().doit();     
        //System.out.println( System.currentTimeMillis() - start );
    }   
}
