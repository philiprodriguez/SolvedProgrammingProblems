import java.util.*;

/*
    Took me about an hour and a half (I think more like 1:35) to solve this
    problem all the way through. It's just a counting problem, but boy can
    counting problems be scary. I took it slow on paper first and then once
    I though I had it down on paper I verified the sample case for 3 "manually"
    before committing to code up the thing.

    Then once I saw I could get the sample case for 4 I was pretty sure I was
    in business. I also was careful to try my algorithm for ALL inputs 1-11
    to check for any overflow-ish behavior, and alas the answer for 11 does not
    fit in an int, so I had to change things to longs. Then it worked fine.

    It should be noted that practically any semi-reasonable solution to this
    problem IS sufficient, because there are only 11 possible inputs! I could
    have hardcoded the answers if the time limit was a problem. Partially
    because of the small input size, though, my algorithm runs basically
    instantly for all possible inputs. I already KNOW I could optimize my
    answerAllButtons method by removing the first argument, and then even more
    probably by turning answerAllButtons into a memoized DP. But eh, it's just
    not necessary for this problem to do those things.

    Now on to explaining the actual approach here:

    The answer if I am allowed to use up to, say, 4 buttons is the number of
    ways to make a valid sequence involving exactly 4 buttons plus the number of
    ways to make a valid sequence involving up to 3 buttons time the number of
    ways to pick those three buttons from the 4 total buttons. Follow this
    pattern down and you get the whole answer.

    So that was my first realization and breakdown for the problem, but I'm
    really still left with the "harder" part of the problem: the number of ways
    I can make a sequence using all 4 buttons. I basically step through and
    imagine building the sequence one by one. I have 4 buttons. I can either
    split/close the combination im building OR not. I can't close if my combo
    is empty, though. So I can either close if my combo is not empty OR I can
    not end my combo here. If I dont close my combo, then my combo gains 1
    in size, and now I know that I have a non-empty combo.

    If I DO close my combo, then that means I have a combo of some size behind
    me. I had 4 available buttons, and I know the size of the combo behind me,
    so I can compute that the number of ways to make that combo behind me is
    nCr(4, sizeOfCombo), and I also then have availableButtons-sizeOfCombo
    buttons remaining to play with. Thus, I can just multply the nCr thing by
    the same problem on availableButtons-sizeOfCombo buttons, and I have my
    answer. Yay counting.

    Philip Rodriguez, 8-16-2017
*/

public class mySolution
{
    public static void main(String[] args)
    {
        Scanner scan = new Scanner(System.in);

        int numCases = scan.nextInt();

        for(int c = 1; c <= numCases; c++)
        {
            int maxButtons = scan.nextInt();
            System.out.println(c + " " + maxButtons + " " + answer(maxButtons));
        }

    }

    public static long answer(int maxButtons)
    {
        long result = 0;
        for(int i = 1; i <= maxButtons; i++)
        {
            result += choose(maxButtons, i) * answerAllButtons(true, 0, i);
        }
        return result;
    }

    //Returns an answer assuming you must use ALL [buttonsAvailable] buttons.
    public static long answerAllButtons(boolean dividerBefore, int prevSize, int buttonsAvailable)
    {
        //If this is the case, we use all buttons in 1 combo, so nCr(all, all) = 1
        if (prevSize == buttonsAvailable)
            return 1;

        //Otherwise, we can either choose to close a group here or not

        //Suppose we do not want to close the group here
        long dontClose = answerAllButtons(false, prevSize+1, buttonsAvailable);

        //Suppose we want to close the group here
        long close = 0;
        //Can we close here? We cannot close here if there's a divider before us!
        if (!dividerBefore)
        {
            //We can close.
            close = choose(buttonsAvailable, prevSize) * answerAllButtons(true, 0, buttonsAvailable-prevSize);
        }

        //We either dontClose OR close..
        return close + dontClose;
    }

    //The good old choose function... O(k)
    public static int choose(int n, int k)
    {
        int res = 1;
        if (k > n-k)
            k = n-k;
        for(int i = 0; i < k; i++)
        {
            res *= n-i;
            res /= i+1;
        }
        return res;
    }
}
