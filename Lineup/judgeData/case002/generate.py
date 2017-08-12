#!/usr/bin/python

N = 100 # number of testcases to generate

import random

def testcase():
    strengths = [[0 for i in xrange(11)] for j in xrange(11)]
    # init: randomly select one player for each position so that
    # a solution is guaranteed
    pos = [i for i in xrange(11)]
    random.shuffle(pos)
    for i in xrange(11):
        strengths[i][pos[i]] = random.randint(1, 100)

    # for each player, randomly generate up to 5 positions
    for player in strengths:
        n = random.randint(0, 4)
        for i in xrange(n):
            pos = random.randint(0, 10)
            player[pos] = random.randint(1, 100)


    # output
    for player in strengths:
        for pos in player:
            print pos,
        print


print N
for i in xrange(N): testcase()

