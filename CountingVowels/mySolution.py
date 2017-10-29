# Clearly a banger, so I figured I'd do it in python

from collections import deque

tokens = deque()
def next():
    while len(tokens) <= 0:
        newTokens = input().split(" ")
        for token in newTokens:
            if len(token) > 0:
                tokens.append(token)
    return tokens.popleft()


numCases = int(next())
for c in range(numCases):
    line = input().lower()

    #Frequency map
    freq = {"a":0,
            "e":0,
            "i":0,
            "o":0,
            "u":0}


    #Loop over every character in the line
    for lc in line:
        #If lc is a key in freq (is a vowel), increment...
        if lc in freq:
            freq[lc] = freq[lc]+1

    #Format answer and print it!
    print("Case " + str(c+1) + ": a=" + str(freq["a"]) + " e=" + str(freq["e"]) + " i=" + str(freq["i"]) + " o=" + str(freq["o"]) + " u=" + str(freq["u"]))
