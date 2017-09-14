
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
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
public class editor {
    public static void main(String[] args)
    {
        Scanner scan = new Scanner(System.in);
        int numCases = scan.nextInt();
        for(int c = 0; c < numCases; c++)
        {
            int numLines = scan.nextInt();
            int[] lineLength = new int[numLines];
            for(int l = 0; l < numLines; l++)
            {
                lineLength[l] = scan.nextInt();
            }
            Pos start = new Pos(scan.nextInt()-1, scan.nextInt());
            Pos target = new Pos(scan.nextInt()-1, scan.nextInt());
            
            System.out.println(minMoves(start, target, lineLength));
            
        }
    }
    
    public static int minMoves(Pos start, Pos target, int lineLength[])
    {
        HashSet<Pos> visited = new HashSet<Pos>();
        Queue<Pos> qpos = new LinkedList<Pos>();
        Queue<Integer> qdist = new LinkedList<Integer>();
        
        //Enqueue start pos with 0 distance
        qpos.add(start);
        qdist.add(0);
        visited.add(start);
        
        while(!qpos.isEmpty())
        {
            Pos curPos = qpos.poll();
            int curDist = qdist.poll();
            
            //Are we done?
            if (curPos.equals(target))
                return curDist;
            
            //Either press left right up or down!
            //Left
            Pos newPos = null;
            if (curPos.c == 0)
            {
                //End of previous line
                if (curPos.r != 0)
                    newPos = new Pos(curPos.r-1, lineLength[curPos.r-1]);
                else
                    newPos = curPos;
            }
            else
            {
                //One char left!
                newPos = new Pos(curPos.r, curPos.c-1);
            }
            
            if (!visited.contains(newPos))
            {
                qpos.add(newPos);
                qdist.add(curDist+1);
                visited.add(newPos);
            }
            
            //Right
            if (curPos.c == lineLength[curPos.r])
            {
                //Beginning of next line
                if (curPos.r+1 < lineLength.length)
                {
                    //There is a next line
                    newPos = new Pos(curPos.r+1, 0);
                }
                else
                {
                    //No next line!
                    newPos = curPos;
                }
            }
            else
            {
                //One char right!
                newPos = new Pos(curPos.r, curPos.c+1);
            }
            
            if (!visited.contains(newPos))
            {
                qpos.add(newPos);
                qdist.add(curDist+1);
                visited.add(newPos);
            }
            
            //Up
            if (curPos.r == 0)
            {
                //No prev line!
                newPos = curPos;
            }
            else
            {
                //There is a previous line
                newPos = new Pos(curPos.r-1, Math.min(curPos.c, lineLength[curPos.r-1]));
            }
            
            if (!visited.contains(newPos))
            {
                qpos.add(newPos);
                qdist.add(curDist+1);
                visited.add(newPos);
            }
            
            //Down
            if (curPos.r == lineLength.length-1)
            {
                //No next line!
                newPos = curPos;
            }
            else
            {
                //There is a next line!
                newPos = new Pos(curPos.r+1, Math.min(curPos.c, lineLength[curPos.r+1]));
            }
            
            if (!visited.contains(newPos))
            {
                qpos.add(newPos);
                qdist.add(curDist+1);
                visited.add(newPos);
            }
        }
        
        return -1;
    }
    
    private static class Pos
    {
        int r, c;
        public Pos(int r, int c)
        {
            this.r = r;
            this.c = c;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 59 * hash + this.r;
            hash = 59 * hash + this.c;
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
            final Pos other = (Pos) obj;
            if (this.r != other.r) {
                return false;
            }
            if (this.c != other.c) {
                return false;
            }
            return true;
        }
        
        
    }
}
