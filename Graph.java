import java.util.ArrayList;
import java.util.HashMap;

/**
 * Provides the basis of a Graph object. A Graph consists of an ArrayList of Edges and a HashMap of Nodes.
 */
public class Graph
{
	protected ArrayList<Edge> _edgeList;
	protected HashMap<String, Node> _nodeMap;

	/**
	 * Graph constructor function.
	 */
	public Graph()
	{
		this._edgeList = new ArrayList<>();
		this._nodeMap = new HashMap<>();
	}
}
