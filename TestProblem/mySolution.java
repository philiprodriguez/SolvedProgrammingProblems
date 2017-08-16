import java.util.*;
public class mySolution
{
    public static void main(String[] args)
    {
        Scanner scan = new Scanner(System.in);

        int numLines = scan.nextInt();
        scan.nextLine();
        for(int l = 0; l < numLines; l++)
        {
            String line = scan.nextLine();
            System.out.println(line.toUpperCase());
        }
    }
}
