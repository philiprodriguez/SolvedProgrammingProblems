
import java.io.*;
import java.util.*;
import java.math.*;

/**
 * Solution to Diplomacy
 * 
 * @author vanb
 */
public class diplomacy_vanb
{
    public Scanner sc;
    public PrintStream ps;
    
    public boolean nedges[][] = new boolean[100][100];
    public boolean cedges[][] = new boolean[100][100];
    
    public int color[] = new int[100];  
    public int component[] = new int[100];
    
    public int dists[][] = new int[100][100];
    
    public int n;
    public int c;
    
    public void group( int k )
    {
        component[k] = c;
        
        for( int i=0; i<n; i++ ) if( nedges[i][k] )
        {
            if( component[i]<0 && color[i]==color[k] )
            {
                group( i );
            }
            else if( component[i]>=0 && color[i]!=color[k] )
            {
                cedges[component[i]][c] = cedges[c][component[i]] = true;
            }
        }
    }
    
    public int fw()
    {
        for( int i=0; i<c; i++ )
        {
            dists[i][i] = 0;
            for( int j=i+1; j<c; j++ )
            {
                dists[i][j] = dists[j][i] = (cedges[i][j] ? 1 : 1000);
            }
        }
        
        for( int k=0; k<c; k++ )for( int i=0; i<c; i++ )for( int j=0; j<c; j++ )
        {
            int d = dists[i][k] + dists[k][j];
            if( d<dists[i][j] ) dists[i][j] = d;
        }
        
        int result = Integer.MAX_VALUE;
        for( int i=0; i<c; i++ )
        {
            int max = 0;
            for( int j=0; j<c; j++ )
            {
                if( dists[i][j]>max && dists[i][j]<1000 ) max = dists[i][j];
            }
            if( max<result ) result = max;
        }
        
        return result;
    }
        
    /**
     * Driver.
     * @throws Exception
     */
    public void doit() throws Exception
    {
        sc = new Scanner( System.in ); //new FileReader( "diplomacy.in" ) );
        ps = System.out; //new PrintStream( new FileOutputStream( "diplomacy.vanb" ) );
        
        for(;;)
        {
            n = sc.nextInt();
            int m = sc.nextInt();
            if( n==0 ) break;
            
            for( int i=0; i<n; i++ )
            {
                color[i] = sc.nextInt();
                component[i] = -1;
                Arrays.fill( nedges[i], false );
                Arrays.fill( cedges[i], false );
            }
            
            for( int i=0; i<m; i++ )
            {
                int a = sc.nextInt()-1;
                int b = sc.nextInt()-1;
                nedges[a][b] = nedges[b][a] = true;
            }
            
            c = 0;
            for( int i=0; i<n; i++ ) if( component[i]<0 )
            {
                group( i );
                ++c;
            }
                              
            ps.println( fw() );
        }
    }
    
    /**
     * @param args
     */
    public static void main( String[] args ) throws Exception
    {
        //long start = System.currentTimeMillis();
        new diplomacy_vanb().doit();     
        //System.out.println( System.currentTimeMillis() - start );        
    }   
}
