/*
    So first modification of this problem would be to shift all node numbers down
    by one to make indexing be less hellish. 
    
    Other than that, looks like a BFS with some state? Or a DFS, but I want to do 
    BFS so I have to use more C++ data structures.
*/

#include <iostream>
#include <vector>
#include <queue>
#include <memory>

using namespace std;

class State {
    private:
        int node;
        int catsSeen;
    public:
        State(int node, int catsSeen) {
            //cout << "Constructed" << endl;
            this->node = node;
            this->catsSeen = catsSeen;
        }
        ~State() {
            //cout << "Destructed" << endl;
        }
        
        int getNode() {
            return node;
        }
        
        int getCatsSeen() {
            return catsSeen;
        }
};

int main() {
    int numVertices;
    cin >> numVertices;
    
    int numConsecutiveCatsAllowed;
    cin >> numConsecutiveCatsAllowed;

    // Does node at index contain cats?
    vector<bool> hasCats(numVertices);
    
    // Adjacency list
    vector<vector<int>> connected(numVertices);
    
    for (int i = 0; i < numVertices; i++) {
        int hc;
        cin >> hc;
        hasCats.at(i) = hc == 1 ? true : false;
    }
    
    for (int i = 0; i < numVertices-1; i++) {
        int start, end;
        cin >> start;
        cin >> end;
        
        // Shift all node number down by one so they start at 0 instead of 1
        start--;
        end--;
        
        // Edges are bidirectional and not necessarily usefully ordered
        connected[start].push_back(end);
        connected[end].push_back(start);
    }
    
    // Now, we can BFS this boyyo!
    vector<bool> visited(numVertices, false);
    queue<shared_ptr<State>> q;
    q.push(make_shared<State>(0, hasCats.at(0) ? 1 : 0));
    visited.at(0) = true;
    
    int numLeavesReached = 0;
    
    while (!q.empty()) {
        shared_ptr<State> curState = q.front();
        q.pop();
        
        // Try to continue on our search, being careful not to go back to or parent
        for (int target : connected.at(curState->getNode())) {
            if (visited.at(target))
                continue;
            
            if (!hasCats.at(target)) {
                // We're golden, and we've broken any consecutive cats chain!
                q.push(make_shared<State>(target, 0));
                visited.at(target) = true;
            } else if (curState->getCatsSeen() < numConsecutiveCatsAllowed) {
                // We're good to go to child, but consecutive cats got worse!
                q.push(make_shared<State>(target, curState->getCatsSeen()+1));
                visited.at(target) = true;
            } else {
                // We're toast! Too many cats! Can't visit them!
            }
        }
        
        // We are a leaf only if we have 0 edges or one edge and we're not the root.
        if (connected.at(curState->getNode()).size() == 0 || (connected.at(curState->getNode()).size() == 1 && curState->getNode() != 0)) {
            // We're a leaf that was reached!
            numLeavesReached++;
        }
    }
    
    cout << numLeavesReached << endl;
    
    return 0;
}
