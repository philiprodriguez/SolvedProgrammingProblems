'''

Because this is using US coins, this is a banger as far as I can tell.

'''


from collections import deque

tokenQ = deque()
def next():
    while len(tokenQ) <= 0:
        values = input().split(" ")
        for v in values:
            if len(v) > 0:
                tokenQ.append(v)
    return tokenQ.popleft()

numCases = int(next())

for c in range(numCases):
    remaining = int(next())

    quarters = 0
    dimes = 0
    nickles = 0
    pennies = 0

    while remaining > 0:
        if remaining >= 25:
            remaining = remaining - 25
            quarters = quarters + 1
        elif remaining >= 10:
            remaining = remaining - 10
            dimes = dimes+1
        elif remaining >= 5:
            remaining = remaining - 5
            nickles = nickles + 1
        else:
            remaining = remaining - 1
            pennies = pennies + 1

    print(str(c+1) + " " + str(quarters) + " QUARTER(S), " + str(dimes) + " DIME(S), " + str(nickles) + " NICKEL(S), " + str(pennies) + " PENNY(S)")
