import java.util.Scanner;

public class d {
	public static void main(String[] args)
	{
		Scanner scan = new Scanner(System.in);
		int numDays = scan.nextInt();
		
		
		long[] price = new long[numDays];
		for(int d = 0; d < numDays; d++)
		{
			price[d] = scan.nextInt();
		}
		
		
		int stocksOwned = 0;
		long money = 100;
		for(int d = 0; d < numDays-1; d++)
		{
			long change = price[d+1]-price[d];
			if (change > 0) // increase
			{
				//buy if you can afford!
				if (money >= price[d])
				{
					long numBuy = Math.min(100000-stocksOwned, (money/price[d]));
					stocksOwned += numBuy;
					money -= price[d] * numBuy;
					//System.out.println("Buying " + numBuy + " stocks on day " + (d+1) + "; Stocks = " + stocksOwned + ", Money = " + money);
				}
			}
			else if (change < 0) // decrease
			{
				//sell all you have!
				if (stocksOwned > 0)
				{
					long allStocks = stocksOwned;
					money += allStocks*price[d];
					stocksOwned = 0;
					//System.out.println("Selling " + allStocks + " stocks on day " + (d+1) + "; Stocks = " + stocksOwned + ", Money = " + money);
				}
			}
			else //Exactly the same, do nothing?
			{
				//System.out.println("Do nothing day " + (d+1) + "; Stocks = " + stocksOwned + ", Money = " + money);
			}
		}
		//Sell all last day
		money += stocksOwned*price[numDays-1];
		
		System.out.println(money);
	}
}
