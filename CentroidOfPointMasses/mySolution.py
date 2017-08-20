from collections import deque

'''

Alright, if you do the maths to solve for a and b, you see it's literally
just a weighted sum divided by the total mass. Pretty sure this is literally
the center of mass I learned in physics 1. Easy mode.

Yep, that was pretty easymode. Hardest part was taking in the input! I like
the little next method I built here, so I'll probably recreate that when I
use python in the future.

Philip Rodriguez, 8-20-2017

'''


#I literally cannot find good easy input parsing online for python, so I wrote
#this... Assuming I was actually going to use python in the real world for
#competitive programming, I'd hackpack this and have it memorized so I could
#whip it out in a second.
tokens = deque()
def next():
    while len(tokens) <= 0:
        values = input().split(" ")
        for v in values:
            if len(v) > 0:
                tokens.append(v)
    return tokens.popleft()

def centroid(xval, yval, mval):
    a = 0
    b = 0
    for i in range(len(mval)):
        a = a + mval[i]*xval[i]
        b = b + mval[i]*yval[i]
    totalMass = sum(mval)
    a = a / totalMass
    b = b / totalMass
    return (a, b)


curCase = 1
while True:
    #Home much input will there be for this case?
    numVals = int(next())

    if numVals < 0:
        break

    #Make me some lists to store input...
    xval = []
    yval = []
    mval = []

    # Read in that delicious input
    for i in range(numVals):
        xval.append(int(next()))
        yval.append(int(next()))
        mval.append(int(next()))

    #Now actually compute and print the answer!
    result = centroid(xval, yval, mval)
    print("Case " + str(curCase) + ": %.2f %.2f" % (result[0], result[1]))
    curCase = curCase + 1
