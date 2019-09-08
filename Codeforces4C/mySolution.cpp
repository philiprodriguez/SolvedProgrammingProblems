/*
    Apparently I already solved this in 2014 using Java, but oh 
    well. I want to play with C++'s hashmap. 
*/

#include<iostream>
#include<string>
#include<unordered_map>

using namespace std;

int main() {
    unordered_map<string, int> nameMap;
    
    int numNames;
    cin >> numNames;
    
    string nameHolder;
    
    // Clear previous blank line
    getline(cin, nameHolder);
    
    // For each name, process it!
    for (int n = 0; n < numNames; n++) {
        // Get the name
        getline(cin, nameHolder);
        
        // Note "not more than 32 characters, which are all lowercase Latin letters"
        // so we will never be asked to look up a "numbered" name!
        
        if (nameMap.count(nameHolder) > 0) {
            // Name already in database!
            int newNameNumber = nameMap.at(nameHolder);
            cout << nameHolder << newNameNumber << endl;
            
            // Increment the value in the nameMap!
            nameMap.at(nameHolder) += 1;
        } else {
            // Name not yet in database!
            nameMap.emplace(nameHolder, 1);
            cout << "OK" << endl;
        }
    }
    
    return 0;
}
