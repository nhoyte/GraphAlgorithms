# GraphAlgorithms (Group Collaboration)
Implementation of Kruskal's MST and Dijkstra's Shortest Path
By: Novia H., Chris C., Ryan M.


IDE: Eclipse Jee Photon 64-bit (Using JDK 10.0.2) Platform: Windows 10 Pro 64-bit

Graph Algorithms: Kruskal (Minimum Spanning Tree) & Dijkstra (Single-Source Shortest Path)

Program Design:
For this project, we chose the data set Coauthorships in Network Science(netScience.gml). To create a graph from the data 
set we first created a Graph class, Node class, and an Edge class to hold the relevant information for each. We have a 
Kruskal class to implement Kruskal's algorithm, a Dijkstra class to implement Dijkstra's algorithm, and a Driver class 
which the program is run from. When the program is run, the Driver class prints a list of options for the 
user: graph, kruskal, dijkstra, print, performance, quit. When the user enters 'graph' the driver class reads the GML 
file using the BufferedReader method. The input is stored as Edge and Node objects, with reverse edges also being stored 
to create an undirected graph. When 'Kruskal' is entered, Kruskal's algorithm is run on the graph. When 'Dijkstra' is 
entered, Dijkstra's algorithm is run on the graph. When'print' is entered, the results of Kruskal's and Dijkstra's algorithms 
are printed. When 'performance' is entered, the execution time of Kruskal's and Dijkstra's algorithms is printed in miliseconds 
and nanoseconds. When 'quit' is entered, the program terminates.

Kruskal:
Kruskal's algorithm finds a minimum spanning tree for a connected, weighted, undirected graph. Kruskal's works by treating each node as a subtree and finding an edge of least possible weight that connects any two trees in the forest. Since our graph is not completely 
connected, the MST will be less than optimal.

Dijkstra:
Dijkstra's algorithm finds the shortest path to all nodes reachable from a single node chosen as the source. Dijkstra's works by 
first initializing all node distances to the maximum float value and then setting the distance of the source node to zero. The 
algorithm continuously removes the node with the smallest distance and relaxes all adjacent edges of a node until all nodes have been visited. 

The relaxation process updates the distance of the target node if a shorter path is found. When the process terminates, the 
shortest path to all nodes that are reachable from the source have been found. If a node is not reachable from the source, the 
program will print "node not reachable from the source" instead of a distance.

Data Structure Design:
The key data structures in this program include HashMap, Collection, and ArrayList. The HashMap stores the ID and Label values for each node. The Collection is used to implement Dijkstra's algorithm. It holds all nodes for which the shortest path have yet to be found. ArrayList is used to hold an adjacency list of edges.

Achievements/Failures: 
This program meets the requirements of the project. The minimum spanning tree and shortest path are correctly calculated. 
The chosen data set has many unconnected nodes which produces an unconnected graph. Therefore, the program fails to achieve an optimal MST and shortest paths to all nodes since all nodes are not reachable from the source (no matter which node is chosen as the source).
