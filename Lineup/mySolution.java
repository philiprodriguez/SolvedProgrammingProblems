import java.util.Scanner;

public class g {
	public static void main(String[] args)
	{
		Scanner scan = new Scanner(System.in);
		int numCases = scan.nextInt();
		
		for(int curCase = 0; curCase < numCases; curCase++)
		{
			//Proficiency of player r for position c
			int[][] proficiency = new int[11][11];
			for(int r = 0; r < proficiency.length; r++)
			{
				for(int c = 0; c < proficiency[r].length; c++)
				{
					proficiency[r][c] = scan.nextInt();
				}
			}
			
			max = 0;
			maxLineup(0, 0, new boolean[proficiency.length], proficiency);
			System.out.println(max);
		}
	}
	
	static int max = 0;
	public static void maxLineup(int position, int sum, boolean[] used, int[][] proficiency)
	{
		if (position >= 11)
		{
			//Done!
			max = Math.max(max, sum);
			return;
		}
		
		//Suppose we take all posible players for this position
		for(int p = 0; p < proficiency.length; p++)
		{
			if (!used[p] && proficiency[p][position] > 0)
			{
				//Try taking this player for this position!
				used[p] = true;
				maxLineup(position+1, sum+proficiency[p][position], used, proficiency);
				used[p] = false;
			}
		}
	}
}
