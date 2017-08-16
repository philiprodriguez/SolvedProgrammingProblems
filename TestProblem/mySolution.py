import sys

numLines = int(sys.stdin.readline())

while numLines > 0:
    line = sys.stdin.readline()
    line = line.upper()
    print(line, end="")
    numLines = numLines - 1
