
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
public class energy {
    public static void main(String[] args)
    {
        Scanner scan = new Scanner(System.in);
        int initialRate = scan.nextInt();
        int extraRate = scan.nextInt();
        
        int numCases = scan.nextInt();
        for(int c = 0; c < numCases; c++)
        {
            int usage = scan.nextInt();
            int result = 0;
            if (usage <= 1000)
            {
                result += initialRate*usage;
            }
            else
            {
                result += initialRate*1000;
                int extraUsage = usage-1000;
                result += extraUsage*extraRate;
            }
            
            System.out.println(usage + " " + result);
        }
    }
}
