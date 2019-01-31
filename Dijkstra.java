import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * Implementation of the Dijkstra single-source shortest path algorithm. Returns an ArrayList of Nodes after shortest paths are calculated.
 */
public class Dijkstra
{
    /**
     * Manages execution of the Dijkstra algorithm.
     */
    public static ArrayList<Node> driver_Dijkstra(Graph pre_built_graph)
    {
        Node source;

        if(pre_built_graph._nodeMap.containsKey("500"))
        {
            source = pre_built_graph._nodeMap.get("500");
        }
        else
        {
            source = pre_built_graph._nodeMap.get("0");
        }

        ArrayList<Node> SSSP = new ArrayList<>();
        SSSP = DijkstraSP(pre_built_graph, SSSP, source);

        return SSSP;
    }

    /**
     * Initializes all Nodes except the source to the Maximum value.
     */
    private static void Initialize_Shortest_Path(Graph G, Node Source)
    {
        for(int i = 0; i < G._nodeMap.size(); i++)
        {
            Node currentNode = G._nodeMap.get(Integer.toString(i));
            currentNode.set_distance(Float.MAX_VALUE);
            currentNode.set_d_pred(null);
        }
        Source.set_distance(0);
        Source.set_d_pred(Source);
    }

    /**
     * Relaxes an Edge. This progresses the shortest path by checking if a better distance exists between two Nodes.
     */
    private static void Relax_Edge(Node activeNode, Node activeAdjacent, float weight)
    {
        if(activeAdjacent.get_distance() > activeNode.get_distance() + weight)
        {
            activeAdjacent.set_distance(activeNode.get_distance() + weight);
            activeAdjacent.set_d_pred(activeNode);
        }
    }

    /**
     * Chooses the cheapest Nodes to visit next.
     */
    private static Node Find_Closest(Collection<Node> unfinished_nodes)
    {
        Node currentCheapest = null;
        float lowestTrail = Float.MAX_VALUE;

        for(Node currentNode: unfinished_nodes)
        {
            if(currentNode.get_distance() <= lowestTrail)
            {
                lowestTrail = currentNode.get_distance();
                currentCheapest = currentNode;
            }
        }

        return currentCheapest;
    }

    /**
     * Ensure that there is no backtracking in undirected graphs.
     */
    private static void Remove_Reverse(Node node, HashMap<String, Node> nodeMap)
    {
        Node adjNode;
        Edge currentAdjEdge;
        ArrayList<Edge> Im_Adj_Array;
        ArrayList<Edge> Sec_Adj_Array;
        Im_Adj_Array = node.get_adjacentEdges();

        for(int i = 0; i < Im_Adj_Array.size(); i++)
        {
            adjNode = nodeMap.get(Im_Adj_Array.get(i).get_target());

            for(int x = 0; x < adjNode.get_adjacentEdges().size(); x++)
            {
                Sec_Adj_Array = adjNode.get_adjacentEdges();
                currentAdjEdge = Sec_Adj_Array.get(x);
                if(currentAdjEdge.get_target().equals(node.get_id()))
                {
                    adjNode.remove_adjacentEdge(currentAdjEdge);
                }
            }
        }
    }

    /**
     * Core Dijkstra algorithm.
     */
    private static ArrayList<Node> DijkstraSP(Graph theGraph, ArrayList<Node> exhausted_nodes, Node sourceNode)
    {
        Initialize_Shortest_Path(theGraph, sourceNode);
        Collection<Node> unfinished_nodes = theGraph._nodeMap.values();

        while(unfinished_nodes.size() != 0)
        {
            Node min_dist_node = Find_Closest(unfinished_nodes);
            exhausted_nodes.add(min_dist_node);
            unfinished_nodes.remove(min_dist_node);
            Remove_Reverse(min_dist_node, theGraph._nodeMap);

            for(int i = 0; i < min_dist_node.get_adjacentEdges().size(); i++)
            {
                Relax_Edge(min_dist_node, theGraph._nodeMap.get(min_dist_node.get_adjacentEdges().get(i).get_target()), min_dist_node.get_adjacentEdges().get(i).get_value());
            }
        }

        boolean errorFlag = false;

        for(int i = 0; i < exhausted_nodes.size(); i++)
        {
            Node temp = exhausted_nodes.get(i);
            if(temp.get_distance() == Float.MAX_VALUE)
            {
                errorFlag = true;
                //i = exhausted_nodes.size();
                break;
            }
        }

        //Completion messages.
        System.out.println("Dijkstra Completed.");
        if(errorFlag)
        {
            System.out.println("Dijkstra Shortest Path requires a completely connected graph. In this graph one or more Nodes could not be reached.");
        }
        else
        {
            System.out.println("This Graph is completely connected.");
        }

        return exhausted_nodes;
    }
}
