/*
    Trying to learn C++. Something something pre-processing dp and then brute force. Should be O(n).
*/

#include<iostream>
#include<string>

using namespace std;

int main() {
    string input;
    getline(cin, input);
    
    if (input.length() < 4) {
        // Not even possible!
        cout << "NO" << endl;
        return 0;
    }
    
    // Pre-processing step will be a backwards linear walk where we note down in a cumulative array "the count of BA's from this character to the right". This is so we can determine in O(1), after having located an AB, "is there a BA to the right of the AB"?
    
    int * baToRightOf = new int[input.length()];
    
    // Cannot possibly be BA starting at the last character!
    baToRightOf[input.length()-1] = 0;
    
    // Walk backwards!
    for (int i = input.length()-2; i >= 0; i--) {
        baToRightOf[i] = baToRightOf[i+1];
        // Is there a BA here?
        if (input[i] == 'B' && input[i+1] == 'A') {
            // Yup
            baToRightOf[i]++;
        }
    }
    
    // Now with that prepared, let's "brute force" over all AB's and then ask in O(1) if there is an acceptable BA!
    for (int i = 0; i < input.length()-1; i++) {
        // Is there an AB here?
        if (input[i] == 'A' && input[i+1] == 'B') {
            // We've got an AB, now do we also have a BA elsewhere?
         
            // Check right, then left
            bool hasBA = false;
            if (i < input.length()-2 && baToRightOf[i+2] > 0) {
                // We're golden
                hasBA = true;
            } else if (i > 1 && baToRightOf[0]-baToRightOf[i-1] > 0) {
                // We're golden
                hasBA = true;
            } else {
                // Uh oh! No BA!
            }
            
            if (hasBA) {
                cout << "YES" << endl;
                return 0;
            }
        }
    }
    
    // If we're here, we found no suitable solution.
    cout << "NO" << endl;
    
    delete [] baToRightOf;
    
    return 0;
}
