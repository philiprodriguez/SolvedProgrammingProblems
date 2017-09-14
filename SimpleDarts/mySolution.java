
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
public class darts {
    public static void main(String[] args)
    {
        Scanner scan = new Scanner(System.in);
        int numCases = scan.nextInt();
        for(int c = 0; c < numCases; c++)
        {
            int numWedges = scan.nextInt();
            
            //The size of each wedge in radians
            double step = Math.PI*2 / numWedges;
            
            int b = scan.nextInt();
            int d = scan.nextInt();
            int s = scan.nextInt();
            int numDarts = scan.nextInt();
            
            int totalScore = 0;
            for(int i = 0; i < numDarts; i++)
            {
                Point dart = new Point(scan.nextDouble(), scan.nextDouble());
                
                //Calculate point val for this dart!
                double radians = dart.getAngle();
                int dartWedge = ((int)(radians/step))+1;
                double dartRadius = dart.getMagnitude();
                //System.out.println(i + " in wedge " + wedge);
                
                //What circle are we in?
                if (dartRadius <= b)
                {
                    //Smallest one!
                    totalScore += 50;
                }
                else if (dartRadius <= d)
                {
                    //Middle one
                    totalScore += 2*dartWedge;
                }
                else if (dartRadius <= s)
                {
                    //Outermost
                    totalScore += dartWedge;
                }
                else
                {
                    //Miss! Add nothing!
                }
            }
            System.out.println(totalScore);
            
        }
    }
    
    private static class Point
    {
        double x, y;
        public Point(double x, double y)
        {
            this.x = x;
            this.y = y;
        }
        
        //Return the angle in radians of the point w.r.t. (0,0)
        public double getAngle()
        {
            double result = Math.atan2(y, x);
            if (result < 0)
                result = result+(Math.PI*2);
            return result;
        }
        
        public double getMagnitude()
        {
            return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        }
    }
}
