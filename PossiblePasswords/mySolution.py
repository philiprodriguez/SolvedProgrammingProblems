'''

Alright, we're going to solve this using the following observation:

This problem is a lot like converting from one base (base 10) to another base
(variable base). Normally, to convert say the number 10 to binary we do
something like:

 1   0   1   0
--- --- --- ---
2^3 2^2 2^1 2^0

We fill in from left to right, asking "how many 8's are there in 10? There is
1." and then seeing that there are 2 units left and thus no fours but one of
2^1.

Similarly, for the first sample input, observe that we can make something like:

 1   0   2
--- --- ---
2*4  4   1

However, note that there isn't a "zero" in this ranking land, so we subtract 1
from the rank (10 --> 9) for this to make sense before doing this:

 1   0   1
--- --- ---
2*4  4   1

Now, we know that we need to "advance" 1 place in abc, and 0 places in xy and
1 place in dmnr to ultimately get the string bxm. Try this for the second sample
and it will also work.

Overall this described algorithm runs in O(numStrings) time. My implementation
below runs in O(numStrings^2) I believe because of the bad string construction
things I do at the very end. Since numStrings <= 20 though, O(numStrings^2) is
way faster than we need for this problem, so I'll just leave it.

'''

from collections import deque

tokens = deque()
def next():
    while len(tokens) <= 0:
        values = input().split(" ")
        for v in values:
            if len(v) > 0:
                tokens.append(v)
    return tokens.popleft()


numCases = int(next())

for curCase in range(numCases):
    numStrings = int(next())
    countValues = [0] * numStrings
    incValues = [0] * numStrings
    strings = []
    for s in range(numStrings):
        strings.append(next())

    rank = int(next())-1

    #For sanity in working with incValues, we reverse the strings
    strings.reverse()

    #Fill in the "increment values"
    for s in range(0, numStrings):
        if s == 0:
            incValues[s] = 1
        else:
            incValues[s] = incValues[s-1]*len(strings[s-1])

    #Reverse both arrays back! We should now have set up our problem like
    #described in the top comment...
    incValues.reverse()
    strings.reverse()

    #Now, we just do it like we would on paper!

    for i in range(len(incValues)):
        #How many of incValues[i] do we have?
        countValues[i] = rank // incValues[i]
        rank = rank - countValues[i]*incValues[i]

    #Now just construct the string based on countValues...
    #This could have been done above without a need for countValues I guess, but
    #eh, I'll just stick to it for readability since runtime should not be an
    #issue at all
    result = ""
    for i in range(len(incValues)):
        result = result + strings[i][countValues[i]:countValues[i]+1]

    print(result)
