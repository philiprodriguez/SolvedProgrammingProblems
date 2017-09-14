
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
public class singing {
    public static void main(String[] args)
    {
        Scanner scan = new Scanner(System.in);
        int numCases = scan.nextInt();
        for(int c = 0; c < numCases; c++)
        {
            long cdSize = scan.nextInt();
            int seqSize = scan.nextInt();
            long[] tracks = new long[seqSize];
            for(int i = 0; i < seqSize; i++)
            {
                tracks[i] = scan.nextInt()-1;
            }
            
            long total = 0;
            for(int i = 0; i < tracks.length-1; i++)
            {
                total += minDistBetween(tracks[i], tracks[i+1], cdSize);
            }
            System.out.println(total);
        }
    }
    
    public static long minDistBetween(long lastPlayed, long target, long cdSize)
    {
        long playingNext = (lastPlayed + 1) % cdSize;
        if (playingNext == target)
            return 0;
        if (playingNext < target)
        {
            long forward = target-playingNext;
            long back = playingNext + cdSize-target;
            return Math.min(forward, back);
        }
        else
        {
            //playingNext > target
            long forward = cdSize-playingNext + target;
            long back = playingNext-target;
            return Math.min(forward, back);
        }
    }
}
