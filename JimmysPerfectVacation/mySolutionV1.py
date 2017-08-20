import sys

'''

    Okay, he walks at 1 meter per second and each ride takes 120 seconds.

    I don't really understand why this program does not run in time. It is
    probably me missing something about python. Otherwise, 11! runtime should
    be like nothing to a CPU. Even 12! should fairly easily run in time. I'll
    come back to this in a little bit. I know this gets correct answers, but not
    in time.

'''

INF = 999999999.0

def distance(point1, point2):
    return ((point2[0]-point1[0])**2 + (point2[1]-point1[1])**2)**(1/2.0)

def bestTime(index, construction, visited, nodeLoc, blocked):
    #Did we visit them all?
    if index >= len(nodeLoc):
        #Calculate time taken and return!
        totalTime = 120*(len(nodeLoc)-1)
        for i in range(0, len(nodeLoc)-1):
            totalTime = totalTime + distance(nodeLoc[construction[i]], nodeLoc[construction[i+1]])

        #Sanity Check...
        #print("Trying " + str(construction) + ", " + str(totalTime))

        return totalTime

    #Go to every possible place next!
    minTime = INF
    for nextNode in range(0, len(nodeLoc)):
        #Can we go to next from where we are?
        if (not(nextNode in visited) and not((construction[index-1], nextNode) in blocked)):
            #We can! Suppose we do!
            construction.append(nextNode)
            visited.add(nextNode)
            minTime = min(minTime, bestTime(index+1, construction, visited, nodeLoc, blocked))
            construction.pop()
            visited.remove(nextNode)
        else:
            #We cannot
            pass

    return minTime


numParks = int(input())

for c in range(0, numParks):
    values = input().split(" ")
    numRides = int(values[0])
    numBlockedPaths = int(values[1])

    #node 0 represents the entrance/exit
    nodeLoc = [(0, 0)]
    blocked = set()

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
        blocked.add((start, end))
        blocked.add((end, start))

    #get and print answer!
    visited = set()
    visited.add(0)
    answer = bestTime(1, [0], visited, nodeLoc, blocked)
    print("Vacation #" + str(c+1) + ":")
    if (answer >= INF):
        print("Jimmy should plan this vacation a different day.")
        print("")
    else:
        print("Jimmy can finish all of the rides in %.3f seconds." % (answer))
        print("")
