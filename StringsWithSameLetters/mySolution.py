import sys

'''
A cute banger problem, figured I might as well use python to try to keep myself
at least somewhat familiar with how python does the things...

This is also my first real use of python in this repository, so i'll try to
start occasionally doing a problem (probably ones I think aren't too difficult)
in python.

Philip Rodriguez 8-16-2017
'''

case = 1
while True:
    first = sys.stdin.readline()
    first = first[0:len(first)-1]
    second = sys.stdin.readline()
    second = second[0:len(second)-1]

    if first == "END" and second == "END":
        break

    #We are guaranteed to only be dealing with only lowercase letters, so 26 is
    #enough space!
    firstcount = [0] * 26
    secondcount = [0] * 26

    for x in first:
        place = ord(x)-ord('a')
        firstcount[place] = firstcount[place] + 1

    for x in second:
        place = ord(x)-ord('a')
        secondcount[place] = secondcount[place] + 1

    #verify if both arrays are equivalent
    eq = True
    for x in range(0, len(firstcount)):
        if firstcount[x] != secondcount[x]:
            eq = False
            break

    if eq:
        print("Case", str(case) + ":", "same")
    else:
        print("Case", str(case) + ":", "different")

    case = case + 1
