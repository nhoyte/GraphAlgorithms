import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * Implementation of Kruskal Minimum Spanning Tree Algorithm. Returns an ArrayList of Edges in the MST.
 */
public class Kruskal
{
    /**
     * A Subtree is a subset of the MST.
     */
    static class Subtree
    {
        String parent;
        int priority;
    }

    /**
     * Driver of Kruskal algorithm.
     */
    public static ArrayList<Edge> driver_Kruskal(Graph pre_built_graph)
    {
        ArrayList<Edge> MST_FINAL = new ArrayList<>();  //Represents the final Edge list which is the MST.
        KruskalMST(pre_built_graph, MST_FINAL);  //Run the MST algorithm with read-in graph.
        return MST_FINAL;  //Return the MST to Driver for printing and performance analysis.
    }

    /**
     * Initializes a Subtree for each Node, where each Node starts as its own parent.
     */
    private static void Init_Subtrees(Subtree[] init_subtrees)
    {
        for(int i = 0; i < init_subtrees.length; i++)
        {
            init_subtrees[i] = new Subtree();
            init_subtrees[i].parent = Integer.toString(i);
            init_subtrees[i].priority = 0;
        }
    }

    /**
     * Finds the root of a Subtree.
     */
    private static String find(Subtree subtrees[], String node)
    {
        int int_vertex = Integer.parseInt(node);

        if(!subtrees[int_vertex].parent.equals(node))  //Recurse until the root of the tree is found.
        {
            subtrees[int_vertex].parent = find(subtrees, subtrees[int_vertex].parent);
        }

        return subtrees[int_vertex].parent;  //Return root of SubTree for a given Node.
    }

    /**
     * Combines two Subtrees. Always attaches the smaller onto the larger for less writes.
     */
    private static void union(Subtree subtrees[], String node1, String node2)
    {
        String node1_root = find(subtrees, node1);
        String node2_root = find(subtrees, node2);
        int int_node1_root = Integer.parseInt(node1_root);
        int int_node2_root = Integer.parseInt(node2_root);

        if(subtrees[int_node1_root].priority < subtrees[int_node2_root].priority)  //When first SubTree is larger.
        {
            subtrees[int_node1_root].parent = node2_root;
        }
        else if(subtrees[int_node1_root].priority > subtrees[int_node2_root].priority)  //When second SubTree is larger.
        {
            subtrees[int_node2_root].parent = node1_root;
        }
        else  //When both SubTrees are the same size.
        {
            subtrees[int_node2_root].parent = node1_root;
            subtrees[int_node1_root].priority++;
        }
    }

    /**
     * Core Kruskal algorithm.
     */
    private static void KruskalMST(Graph kruskalGraph, ArrayList<Edge> MST_ACTUAL)
    {
        int V = kruskalGraph._nodeMap.size();  //Number of Nodes (vertices).
        Edge currentEdge;

        Subtree MST_Subtrees[] = new Subtree[V];  //Array of Subtrees

        PriorityQueue<Edge> minHeap = new PriorityQueue<>();  //MinHeap to have lowest weight edge on root.
        minHeap.addAll(kruskalGraph._edgeList);  //Add all the edges to the MinHeap.

        Init_Subtrees(MST_Subtrees);

        while(MST_ACTUAL.size() < V-1 && !minHeap.isEmpty())  //Continue adding to MST until MST is V-1 or Heap is empty.
        {
            currentEdge = minHeap.remove();
            String firstNodeRoot = find(MST_Subtrees, currentEdge.get_source());
            String secondNodeRoot = find(MST_Subtrees, currentEdge.get_target());

            if(!firstNodeRoot.equals(secondNodeRoot))
            {
                MST_ACTUAL.add(currentEdge);
                union(MST_Subtrees, firstNodeRoot, secondNodeRoot);
            }
        }

        //Completion messages.
        System.out.println("Kruskal Completed.");
        if(MST_ACTUAL.size() < V-1)
        {
            System.out.println("Kruskal MST requires a completely connected graph. Expect a less than optimal MST.");
        }
        else
        {
            System.out.println("This Graph should give an optimal MST.");
        }
    }
}