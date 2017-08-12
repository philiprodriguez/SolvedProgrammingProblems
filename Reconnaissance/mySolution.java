import java.util.Scanner;

public class h {
	public static void main(String[] args)
	{
		Scanner scan = new Scanner(System.in);
		int numVehicles;
		while((numVehicles = scan.nextInt()) != 0)
		{
			//We have a positive number of vehicles for this case...
			int[] vpos = new int[numVehicles];
			int[] vvel = new int[numVehicles];
			for(int vehicle = 0; vehicle < numVehicles; vehicle++)
			{
				int x = scan.nextInt();
				int v = scan.nextInt();
				vpos[vehicle] = x;
				vvel[vehicle] = v;
			}
			
			//Ternary search!!11!1
			double hiTime = 1000000;
			double loTime = 0;
			while(!doubleEquals(hiTime, loTime))//for(int i = 0; i < 10; i++)
			{
				//System.out.println("loTime = " + loTime + "; hiTime = " + hiTime);
				double midTime1 = loTime+((1.0/3.0)*(hiTime-loTime));
				double midTime2 = loTime+((2.0/3.0)*(hiTime-loTime));
				//System.out.println("Trying times " + midTime1 + " and " + midTime2);
				
				
				double result1 = maxDist(midTime1, vpos, vvel);
				double result2 = maxDist(midTime2, vpos, vvel);
				
				//System.out.println("Got " + result1 + " and " + result2);
				
				if (result1 > result2)
				{
					//kill result 1
					loTime = midTime1;
				}
				else
				{
					//kill result 2
					hiTime = midTime2;
				}
			}
			System.out.format("%.2f\n", maxDist(loTime, vpos, vvel));
		}
	}
	
	//Returns the maximum distance at time t
	public static double maxDist(double time, int[] vpos, int[] vvel)
	{
		double minX = 1000000, maxX = -1000000;
		
		//For each vehicle i, compute the new position...
		for(int i = 0; i < vpos.length; i++)
		{
			double newX = vpos[i]+(vvel[i]*time);
			minX = Math.min(minX, newX);
			maxX = Math.max(maxX, newX);
		}
		
		return maxX-minX;
	}
	
	public static final double eps = 1e-8;
	public static boolean doubleEquals(double d1, double d2)
	{
		return Math.abs(d1-d2) <= eps;
	}
}
