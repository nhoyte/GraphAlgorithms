/* 
 * 	Created by: Ryan McPeck, Novia Hoyte, and Christopher Carroll
 * 
 * 	ITCS 6114/8114 Algorithms and Data Structures
 * 	Group project: Programming Project 4
 *
 */

/*
 *	IDE: Eclipse Jee Photon 64-bit (Using JDK 10.0.2) Platform: Windows 10 Pro 64-bit
 *
 * 	Graph Algorithms: Kruskal (Minimum Spanning Tree) & Dijkstra (Single-Source Shortest Path)
 *
 *	Program Design:
 *	For this project, we chose the data set Coauthorships in Network Science(netScience.gml). To create a graph from the data
 *	set we first created a Graph class, Node class, and an Edge class to hold the relevant information for each. We have a
 *	Kruskal class to implement Kruskal's algorithm, a Dijkstra class to implement Dijkstra's algorithm, and a Driver class
 *	which the program is run from. When the program is run, the Driver class prints a list of options for the
 *	user: graph, kruskal, dijkstra, print, performance, quit. When the user enters 'graph' the driver class reads the GML
 *	file using the BufferedReader method. The input is stored as Edge and Node objects, with reverse edges also being stored
 *	to create an undirected graph. When 'Kruskal' is entered, Kruskal's algorithm is run on the graph. When 'Dijkstra' is
 *	entered, Dijkstra's algorithm is run on the graph. When'print' is entered, the results of Kruskal's and Dijkstra's algorithms
 *	are printed. When 'performance' is entered, the execution time of Kruskal's and Dijkstra's algorithms is printed in miliseconds
 *	and nanoseconds. When 'quit' is entered, the program terminates.
 *
 *	Kruskal:
 *	Kruskal's algorithm finds a minimum spanning tree for a connected, weighted, undirected graph. Kruskal's works by treating each node as a
 *	subtree and finding an edge of least possible weight that connects any two trees in the forest. Since our graph is not completely
 *	connected, the MST will be less than optimal.
 *
 *	Dijkstra:
 *	Dijkstra's algorithm finds the shortest path to all nodes reachable from a single node chosen as the source. Dijkstra's works by
 *	first initializing all node distances to the maximum float value and then setting the distance of the source node to zero. The
 *	algorithm continuously removes the node with the smallest distance and relaxes all adjacent edges of a node until all nodes have been visited.
 *	The relaxation process updates the distance of the target node if a shorter path is found. When the process terminates, the
 *	shortest path to all nodes that are reachable from the source have been found. If a node is not reachable from the source, the
 *	program will print "node not reachable from the source" instead of a distance.
 *
 *	Data Structure Design:
 *	The key data structures in this program include HashMap, Collection, and ArrayList. The HashMap stores the ID and Label values for each node.
 *	The Collection is used to implement Dijkstra's algorithm. It holds all nodes for which the shortest path have yet to be found. ArrayList is used to
 *	hold an adjacency list of edges.
 *
 *	Achievements/Failures: This program meets the requirements of the project. The minimum spanning tree and shortest path are correctly calculated.
 *	The chosen data set has many unconnected nodes which produces an unconnected graph. Therefore, the program fails to achieve an optimal MST
 *	and shortest paths to all nodes since all nodes are not reachable from the source (no matter which node is chosen as the source).
 */


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;

/**
 * The Driver class parses the GML file, applies the information to data structures, provides the command menu,
 * and tasks the different class files to solve specific graph problems.
 */
public class Driver
{
	/**
	 * The basis of execution. Houses path to read GML file.
	 */
	public static void main(String[] args)
	{
		// this file path works as long as the file is in src folder -- change this if file is in different path
		final String FILE_PATH = "netscience.gml";

		Graph graph = new Graph();
		handleInput(graph, FILE_PATH);
	}

	/**
	 * Parses the GML file and builds data structures to house the objects.
	 */
	private static void parseGMLFile(String inputFile, HashMap<String, Node> nodeMap, ArrayList<Edge> edgeList)
	{
		String thisLine;

		try
		{
			BufferedReader br = new BufferedReader(new FileReader(inputFile)); //Instantiate BufferedReader using the passed file
	         
			while((thisLine = br.readLine()) != null) //While there are still lines in the file, build graph
			{
				if(thisLine.contains("node"))
				{
					br.readLine();
					String idLine = br.readLine();
					String idString = idLine.substring(7, idLine.length());
					
					String labelLine = br.readLine();
					String labelString = labelLine.substring(11, labelLine.length() - 1);
					
					nodeMap.put(idString, new Node(idString, labelString));
				}
				else if(thisLine.contains("edge"))
				{
					br.readLine();
					String sourceLine = br.readLine();
					String sourceString = sourceLine.substring(11, sourceLine.length());
					
					String targetLine = br.readLine();
					String targetString = targetLine.substring(11, targetLine.length());
					
					String valueLine = br.readLine();
					String valueString = valueLine.substring(10, valueLine.length());

					float value = -100.0f;
					if(tryToParseFloat(valueString))
					{
						value = Float.parseFloat(valueString);
					}

					Edge newEdge = new Edge(sourceString, targetString, value);
					Edge newEdgeReverse = new Edge(targetString, sourceString, value);

					edgeList.add(newEdge);
					edgeList.add(newEdgeReverse);

					nodeMap.get(sourceString).add_adjacentEdge(newEdge);
					nodeMap.get(targetString).add_adjacentEdge(newEdgeReverse);
				}
			}
	         
			br.close();  //End reading from the file
			System.out.println("Graph Successfully Built!");
		}
		catch(Exception e)
		{
			System.out.print("ERROR: Possible input file issue!");
			System.exit(-1);
		}
	}

	/**
	 * Ensure Floats are suitable to parse.
	 */
	private static boolean tryToParseFloat(String string)
	{
        // this makes sure that we can parse a float from a given string
    	try
		{
            Float.parseFloat(string);  
            return true;  
		}
		catch(NumberFormatException e)
		{
            return false;  
		}
    }

	/**
	 * Retrieves command input from the user and tasks the appropriate algorithm.
	 */
	private static void handleInput(Graph graph, String inputFilePath)
	{
		ArrayList<Edge> MST_KRUSKAL = null;
		ArrayList<Node> SSSP_Dijkstra = null;
		long mst_time = 0;
		long mst_start = 0;
		long mst_end = 0;
		long da_time = 0;
		long da_start = 0;
		long da_end = 0;
		boolean keep_order = false;

		System.out.println("Welcome to the Graph Algorithms Assignment\n");
		System.out.println("Available Commands:");
		System.out.println("graph");
		System.out.println("kruskal");
		System.out.println("dijkstra");
		System.out.println("print");
		System.out.println("performance");
		System.out.println("quit\n");

		
		Scanner scanner = new Scanner(System.in);
		
		boolean waitForInput = true;
		
		while(waitForInput)
		{
			System.out.print(">> ");
			String input = scanner.nextLine();
			
			if(input.toLowerCase().contains("graph"))
			{
				//call method to parse file and build graph
				if(graph._edgeList.isEmpty())
					parseGMLFile(inputFilePath, graph._nodeMap, graph._edgeList);
				else
					System.out.println("Graph is already built");
				
			}
			else if(input.toLowerCase().contains("kruskal"))
			{
				if(graph._edgeList.isEmpty())
				{ // to make sure the user entered command to build graph first
					noGraphBuiltErrorMessage();
				}
				else
				{
					mst_start = System.nanoTime();  //Begin timing
					MST_KRUSKAL = Kruskal.driver_Kruskal(graph);
					mst_end = System.nanoTime();  //End timing
					keep_order = true;
				}
				
			}
			else if(input.toLowerCase().contains("dijkstra"))
			{
				if(graph._edgeList.isEmpty())
				{ // to make sure the user entered command to build graph first
					noGraphBuiltErrorMessage();
				}
				else if(!keep_order)
				{
					System.out.println("Kruskal must be run before Dijkstra.");
				}
				else
				{
					da_start = System.nanoTime();  //Begin timing
					SSSP_Dijkstra = Dijkstra.driver_Dijkstra(graph);
					da_end = System.nanoTime();  //End timing
				}
				
			}
			else if(input.toLowerCase().contains("print"))
			{
				if(graph._edgeList.isEmpty())
				{ // to make sure the user entered command to build graph first
					noGraphBuiltErrorMessage();
				}
				else
				{
					mst_time = mst_end - mst_start;
					da_time = da_end - da_start;

					if(mst_time > 0)
					{
						System.out.println("Kruskal Output:");
						for(int i = 0; i < MST_KRUSKAL.size(); i++)
						{
							Edge edge = MST_KRUSKAL.get(i);
							System.out.println("MST Edge #" + i + " |Source Node: " + edge.get_source() + "| |Destination Node: " + edge.get_target() + "| |Weight: " + edge.get_value() + "|");
						}
					}
					else
					{
						System.out.println("Kruskal has yet to be run.");
					}

					if(da_time > 0)
					{
						System.out.println("\nDijkstra Output:");
						for(int i = 0; i < SSSP_Dijkstra.size(); i++)
						{
							Node currentNode = SSSP_Dijkstra.get(i);
							float distance = currentNode.get_distance();
							String id = currentNode.get_id();

							if (distance == Float.MAX_VALUE)
								System.out.println("Node[" + id + "]: Distance from Source: Node is not reachable from the source.");
							else
								System.out.println("Node[" + id + "]: |Distance from Source: " + distance + "| |Predecessor Node:[" + currentNode.get_d_pred().get_id() + "]|");
						}
					}
					else
					{
						System.out.println("Dijkstra has yet to be run.");
					}
				}
				
			}
			else if (input.toLowerCase().contains("performance"))
			{
				if (graph._edgeList.isEmpty())
				{ // to make sure the user entered command to build graph first
					noGraphBuiltErrorMessage();
				}
				else
				{
					mst_time = mst_end - mst_start;
					da_time = da_end - da_start;

					if(mst_time > 0)
					{
						System.out.println("MST (Kruskal) Execution time: " + mst_time + "ns (" + mst_time / 1000000 + "ms)");
					}
					else
					{
						System.out.println("Kruskal has yet to be run.");
					}

					if(da_time > 0)
					{
						System.out.println("Dijkstra Execution time: " + da_time + "ns (" + da_time / 1000000 + "ms)");
					}
					else
					{
						System.out.println("Dijkstra has yet to be run.");
					}
				}
				
			}
			else if (input.toLowerCase().contains("quit"))
			{
				// exit while loop and will end program
				waitForInput = false;
			}
			else
			{
				// User didn't enter proper command
				System.out.println("Please Enter Proper Command");
			}
		}
		scanner.close();
	}

	/**
	 * Reminds users that the Graph must be built to proceed.
	 */
	private static void noGraphBuiltErrorMessage()
	{
		System.out.println("***************** Graph Not Built Yet *****************\n"
				+ "******* Please enter 'graph' to build the Graph *******");
	}
}
