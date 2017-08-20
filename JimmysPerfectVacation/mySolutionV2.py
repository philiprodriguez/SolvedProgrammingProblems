import sys

'''

    Okay, he walks at 1 meter per second and each ride takes 120 seconds.

    I don't really understand why this program does not run in time. It is
    probably me missing something about python. Otherwise, 11! runtime should
    be like nothing to a CPU. Even 12! should fairly easily run in time. I'll
    come back to this in a little bit. I know this gets correct answers, but not
    in time.

    You'll see I have a Java solution now too. I literally copied this code
    as much as I knew how to for that Java solution, and that one runs in
    literally a second compared to this one's 18+ seconds. That is an insane
    runtime difference! 18x???? Are you kidding me python???? Geez. Anyways,
    I'm leaving all these python things here for anyone to look at if they want
    to.

'''

INF = 999999999.0

def distance(point1, point2):
    return ((point2[0]-point1[0])**2 + (point2[1]-point1[1])**2)**(1/2.0)

def bestTime(index, last, timeTaken, visited, nodeLoc, blocked):
    #Did we visit them all?
    if index >= len(nodeLoc):
        return timeTaken

    #Go to every possible place next!
    minTime = INF
    nextNode = 1
    while nextNode < len(nodeLoc):
        #Can we go to next from where we are?
        if (not(visited[nextNode]) and not(blocked[last][nextNode])):
            #We can! But does it make sense to?
            nextTimeTaken = timeTaken+120+distance(nodeLoc[last], nodeLoc[nextNode])
            if (nextTimeTaken < minTime):
                #Ok, it makes sense to try this way...
                visited[nextNode] = True
                minTime = min(minTime, bestTime(index+1, nextNode, nextTimeTaken, visited, nodeLoc, blocked))
                visited[nextNode] = False
        nextNode = nextNode + 1

    return minTime


numParks = int(input())

for c in range(0, numParks):
    values = input().split(" ")
    numRides = int(values[0])
    numBlockedPaths = int(values[1])

    #node 0 represents the entrance/exit
    nodeLoc = [(0, 0)]
    blocked = [[False] * (numRides+1) for _ in range(numRides+1)]

    #read in ride locations...
    for r in range(1, numRides+1):
        values = input().split(" ")
        x = int(values[0])
        y = int(values[1])
        nodeLoc.append((x, y));

    #read in blocked paths and update blocked set...
    for b in range(0, numBlockedPaths):
        values = input().split(" ")
        start = int(values[0])
        end = int(values[1])
        blocked[start][end] = True
        blocked[end][start] = True

    #get and print answer!
    visited = [False] * len(nodeLoc)
    visited[0] = True
    answer = bestTime(1, 0, 0, visited, nodeLoc, blocked)
    print("Vacation #" + str(c+1) + ":")
    if (answer >= INF):
        print("Jimmy should plan this vacation a different day.")
        print("")
    else:
        print("Jimmy can finish all of the rides in %.3f seconds." % (answer))
        print("")
