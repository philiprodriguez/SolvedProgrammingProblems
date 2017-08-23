// Arup Guha
// 9/9/2013
// Solution COP 4516 Week #1 Individual Contest Problem: Passwords

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define MAXLETTERS 20
#define ALPHASIZE 26

char* solve(int numletters, int size[], char choices[][ALPHASIZE+1], int rank);
void solveRec(int numletters, int size[], char choices[][ALPHASIZE+1], int rank, char* ans, int k);

int main() {

    int numCases, loop;
    scanf("%d", &numCases);

    // Go through each case.
    for (loop=0; loop<numCases; loop++) {

        int numletters;
        scanf("%d", &numletters);

        // Store input choices for each letter.
        int size[MAXLETTERS+1], i;
        char choices[MAXLETTERS][ALPHASIZE+1];
        for (i=0; i<numletters; i++) {
            scanf("%s", choices[i]);
            size[i] = strlen(choices[i]);
        }

        // Useful for our precomputation to avoid a special case.
        size[numletters] = 1;

        // A bit of precomputation here - we will store in size[i] the number of
        // passwords that can be formed with letters i...(numletters-1).
        for (i=numletters-1; i>=0; i--)
            size[i] *= size[i+1];

        // Better to use a zero based system.
        int rank;
        scanf("%d", &rank);
        rank--;

        char* ans = solve(numletters, size, choices, rank);
        printf("%s\n", ans);
        free(ans);
    }

    return 0;
}

// Wrapper function that returns the rank lexicographical password according to
// choices given for each letter.
char* solve(int numletters, int size[], char choices[][ALPHASIZE+1], int rank) {
    char* ans = malloc(sizeof(char)*(numletters+1));
    solveRec(numletters, size, choices, rank, ans, 0);
    return ans;
}

// Quick version which uses some math...
void solveRec(int numletters, int size[], char choices[][ALPHASIZE+1], int rank, char* ans, int k) {

    // We've finished filling in the word.
    if (k == numletters) {
        ans[k] = '\0';
        return;
    }

    // The size array tells us how many passwords we can create with the rest of the letters, so
    // we can just divide to get which of the current letters to use.
    int index = rank/size[k+1];
    ans[k] = choices[k][index];

    // Solve the rest recursively - note, this can easily be written with a for loop...
    solveRec(numletters, size, choices, rank - index*size[k+1], ans, k+1);
}
