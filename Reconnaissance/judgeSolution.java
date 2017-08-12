
import java.io.*;
import java.util.*;
import java.math.*;

/**
 * Solution to Reconnaissance
 * 
 * @author vanb
 */
public class recon_vanb
{
    public Scanner sc;
    public PrintStream ps;

    /**
     * A Car, with initial position and velocity.
     * 
     * @author vanb
     */
    public class Car implements Comparable<Car>
    {
        /** Initial position */
        public int x;
        
        /** Velocity */
        public int v;
        
        /**
         * Create a Car.
         * 
         * @param x Initial position
         * @param v Velocity
         */
        public Car( int x, int v )
        {
            this.x = x;
            this.v = v;
        }
        
        /**
         * Compare this car to another, so they can be sorted
         * first by velocity (largest first) and in case of ties,
         * then by initial position (smallest first).
         * 
         * @param c Another Car
         * @return The norm for compareTo
         */
        public int compareTo( Car c )
        {
            int diff = c.v - v;
            if( diff==0 ) diff = x - c.x;
            return diff;
        }
        
        /**
         * Position of this Car at time t.
         * 
         * @param t A Time
         * @return Position of this car at time t
         */
        public double pos( double t )
        {
            return x+v*t;
        }
        
        /**
         * Time when two cars intersect, or Max Double
         * if they don't intersect.
         * 
         * @param c Another Car
         * @return Time of intersection, or Max Double if they don't intersect.
         */
        public double intersect( Car c )
        {
            // The only way they don't intersect is if they're traveling 
            // at the same velocity.
            return v==c.v ? Double.MAX_VALUE : (x-c.x)/(double)(c.v-v);   
        }
      
        /**
         * A pretty String for debugging.
         * 
         * @return A pretty String for debugging
         */
        public String toString()
        {
            return "[" + x + "," + v + "]";
        }
    }
    
    /**
     * Class to capture a Switch in the leftmost (or rightmost) car
     * 
     * @author vanb
     */
    public class Switch
    {
        /** Time of the switch */
        public double t;
        /** The Car that takes over as leftmost (or rightmost) at time t */
        public Car c;
        
        /**
         * Capture a Switch.
         * 
         * @param t Time of switch
         * @param c Car that becomes the leftmost (or rightmost) at time t
         */
        public Switch( double t, Car c )
        {
            this.t = t;
            this.c = c;
        }
 
        /**
         * A Pretty String for debugging
         * 
         * @return A pretty String for debugging
         */
        public String toString()
        {
            return "{" + t + "," + c + "}";
        }
    }
    
    /**
     * Driver.
     * @throws Exception
     */
    public void doit() throws Exception
    {
        sc = new Scanner( System.in ); //new File( "reconnaissance.in" ) );
        ps = System.out; //new PrintStream( new FileOutputStream( "reconnaissance.out" ) );
        
        // Create all of the arrays a priori to save time
        Car cars[] = new Car[100000];
        Switch lswitches[] = new Switch[100001];
        Switch rswitches[] = new Switch[100001];
        
        for(;;)
        {
            int n = sc.nextInt();
            if( n==0 ) break;
            
            for( int i=0; i<n; i++ )
            {
                int x = sc.nextInt();
                int v = sc.nextInt();
                cars[i] = new Car(x,v);
            }
            
            // Sort the cars by velocity
            Arrays.sort( cars, 0, n );
            
            // Find the indices of the leftmost and rightmost cars
            int lc = -1;
            int lx = Integer.MAX_VALUE;
            int rc = n;
            int rx = Integer.MIN_VALUE;           
            for( int c=0; c<n; c++ )
            {
                if( cars[c].x < lx )
                {
                    lx = cars[c].x;
                    lc = c;
                }
                if( cars[c].x >= rx )
                {
                    rx = cars[c].x;
                    rc = c;
                }
            }
            
            Arrays.fill( lswitches, null );
            int k = 0;
            lswitches[k++] = new Switch( 0.0, cars[lc] );
            for( int c=lc+1; c<n; c++ )
            {
                Switch sw = new Switch( 0.0, cars[c] );
                int i = k-1;
                while( i>=0 )
                {
                    sw.t = cars[c].intersect( lswitches[i].c );
                    if( sw.t >= lswitches[i].t ) break;
                    --i;
                }
                k=i+1;
                if( sw.t<Double.MAX_VALUE ) lswitches[k++] = sw;
                lswitches[k] = null;
            }
            
            Arrays.fill( rswitches, null );
            k = 0;
            rswitches[k++] = new Switch( 0.0, cars[rc] );
            for( int c=rc-1; c>=0; c-- )
            {
                Switch sw = new Switch( 0.0, cars[c] );
                int i = k-1;
                while( i>=0 )
                {
                    sw.t = cars[c].intersect( rswitches[i].c );
                    if( sw.t >= rswitches[i].t ) break;
                    --i;
                }
                k=i+1;
                if( sw.t<Double.MAX_VALUE ) rswitches[k++] = sw;
                rswitches[k] = null;
            }
            
            double mindist = Double.MAX_VALUE;
            int l=0, r=0;
            for(;;)
            {
                Switch ls = lswitches[l];
                Switch rs = rswitches[r];
                double t = Math.max( ls.t, rs.t );        
                double dist = Math.abs( ls.c.pos( t ) - rs.c.pos( t ) );
                if( dist<mindist ) mindist = dist;
                
                ls = lswitches[l+1];
                rs = rswitches[r+1];
                
                if( ls==null && rs==null ) break;
                
                if( ls==null ) r++;
                else if( rs==null ) l++;
                else if( ls.t < rs.t ) l++;
                else r++;
            }
            
            ps.printf( "%.2f", mindist );
            ps.println();    
            String answer = String.format( "%.4f", mindist );
            if( answer.endsWith( "49" ) || answer.endsWith( "50" ) ) 
            {
                System.err.println( "Too close to cusp!! " + mindist );
            }

        }
    }
    
    /**
     * @param args
     */
    public static void main( String[] args ) throws Exception
    {
        new recon_vanb().doit(); 
    }   
}
