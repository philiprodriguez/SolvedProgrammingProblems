package Problem474B;

/*
    So this problem is really just finding what range a number is in. I think a first step to break it down is to see
    that if we simply create a cumulative sum array of our input we get something useful. Take the first sample input:

    5
    2 7 3 4 9
    3
    1 25 11

    Our cumulative sum array will look like {2, 9, 12, 16, 25}. Think of it this way. Worms in the a_1 pile are 1-2,
    worms in the a_2 pile are 3-9, worms in the a_3 pile are 10-12, worms in the a_4 pile are 13-16, and finally in
    the a_5 pile are 17-25. Out cumulative sum array at index i represents the last worm label in the ith pile!

    So then, to answer a query of "what pile is work j in?" we really just need to find the first pile whose last worm
    is greater than or equal to j.

    For this, we could use binary search on our cumulative sum array. We'd answer any query in O(log(n)) time and be
    using O(n) extra space. It'd take O(n) time to create our cumulative sum array, though this may be neglected because
    it's an equivalent task to just reading in the input anyways. I think this is the intended solution.

    However, we can also do this without binary search using a TreeSet and it's accompanying perfect ceiling method,
    which will find in O(log(n)) time the element in the set that is greater than or equal to the query---exactly
    what we need! We'll need the index or order of the pile as well, though. I've seen some hacky ways of forcing a
    TreeSet to give you this, and you can probably think of some too. Instead, though, I'd just go with inserting a
    pair into the TreeSet so each item in the set is paired with its index. That way, we just get our answer from the
    ceiling function.

    You also may have noticed at this point that the pre-processing work will take O(n*log(n)) time since we'll be
    inserting n items into the TreeSet. Why not just roll our own binary search at this point? Because, TreeSet
    is cool, and the fact that my brain though of it made me want to implement it anyways. Also, I'd argue the actual
    implementation is less subject to mistakes because it's just a use and abuse of an existing, established class.
    It's ok if you disagree. We'll implement both solutions!
 */

/*
Sample input:

5
2 7 3 4 9
3
1 25 11

 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.TreeSet;

public class Main {
    public static void main(String[] args) throws IOException {
        Solution2.main();
    }

    // TreeSet solution; BufferedReader necessary to meet time limits.
    private static class Solution1 {
        public static void main() throws IOException {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            TreeSet<PileWithIndex> allOfOurPiles = new TreeSet<>();
            int numWormPiles =  Integer.parseInt(bufferedReader.readLine());
            String[] inputs = bufferedReader.readLine().split(" ");
            int lastLast = 0;
            for (int p = 0; p < numWormPiles; p++) {
                int pileSize = Integer.parseInt(inputs[p]);
                int lastWormInThePile = lastLast+pileSize;
                allOfOurPiles.add(new PileWithIndex(p+1, lastWormInThePile));
                lastLast = lastWormInThePile;
            }

            // Answer our queries!
            PileWithIndex pileForQueries = new PileWithIndex(-1, -1);
            int numQueries = Integer.parseInt(bufferedReader.readLine());
            inputs = bufferedReader.readLine().split(" ");
            for (int q = 0; q < numQueries; q++) {
                int desiredWorm = Integer.parseInt(inputs[q]);
                // Find me the pileNumber of the lowest pile whose lastWormInThePile is >= desiredWorm.
                pileForQueries.lastWormInThePile = desiredWorm;
                System.out.println(allOfOurPiles.ceiling(pileForQueries).pileNumber);
            }
        }

        private static class PileWithIndex implements Comparable<PileWithIndex> {
            public int pileNumber;
            public int lastWormInThePile;

            public PileWithIndex(int pileNumber, int lastWormInThePile) {
                this.pileNumber = pileNumber;
                this.lastWormInThePile = lastWormInThePile;
            }

            @Override
            public int compareTo(PileWithIndex other) {
                // We want to order according to lastWormInPile
                return lastWormInThePile - other.lastWormInThePile;
            }
        }
    }

    // Better, binary search solution
    private static class Solution2 {
        public static void main() throws IOException {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            int numPiles = Integer.parseInt(bufferedReader.readLine());
            String[] inputs = bufferedReader.readLine().split(" ");
            int[] cumSum = new int[numPiles];
            cumSum[0] = Integer.parseInt(inputs[0]);
            for (int p = 1; p < numPiles; p++) {
                cumSum[p] = cumSum[p-1]+Integer.parseInt(inputs[p]);
            }

            int numQueries = Integer.parseInt(bufferedReader.readLine());
            inputs = bufferedReader.readLine().split(" ");
            for (int q = 0; q < numQueries; q++) {
                int desiredWorm = Integer.parseInt(inputs[q]);

                // Find that correct pile with binary search!
                System.out.println(findLowestGTE(cumSum, desiredWorm)+1);
            }
        }

        public static int findLowestGTE(int[] cumSum, int target) {
            int lo = 0, hi = cumSum.length-1, mid = 0;

            while (lo < hi) {
                mid = lo+((hi-lo) / 2);

                if (target > cumSum[mid]) {
                    lo = mid+1;
                } else if (target < cumSum[mid]) {
                    // Could be it, but only if thing left of mid is strictly less than target.
                    hi = mid;
                } else {
                    // They're equal, so it must be the one!
                    return mid;
                }
            }

            return lo;
        }
    }
}
