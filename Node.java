import java.util.ArrayList;

/**
 * Provides the basis of a Node (vertex) object. A Node has an ID, a Label, a list of Adjacent Edges, a Distance (from a source),
 * and a Predecessor.
 */
public class Node
{
	private String _id;
	private String _label;
	private ArrayList<Edge> _adjacentEdges;
	private float _distance;
	private Node d_pred;

	/**
	 * Node constructor function.
	 */
	public Node(String _id, String _label)
	{
		this._id = _id;
		this._label = _label;
		this._adjacentEdges = new ArrayList<>();
	}

	/**
	 * Retrieve a Node's ID.
	 */
	public String get_id()
	{
		return _id;
	}

	/**
	 * Retrieve the list of Adjacent Edges.
	 */
	public ArrayList<Edge> get_adjacentEdges()
	{
		return _adjacentEdges;
	}

	/**
	 * Retrieve the Ditance from the Source Node (shortest path).
	 */
	public float get_distance()
	{
		return _distance;
	}

	/**
	 * Set the Ditance from the Source Node (shortest path).
	 */
	public void set_distance(float _distance)
	{
		this._distance = _distance;
	}

	/**
	 * Retrieve the predecessor of this Node.
	 */
	public Node get_d_pred()
	{
		return d_pred;
	}

	/**
	 * Set the predecessor of this Node.
	 */
	public void set_d_pred(Node predecessor)
	{
		this.d_pred = predecessor;
	}

	/**
	 * Add an Edge as adjacent.
	 */
	public void add_adjacentEdge(Edge edge)
	{
		this._adjacentEdges.add(edge);
	}

	/**
	 * Remove an adjacent Edge.
	 */
	public void remove_adjacentEdge(Edge edge)
	{
		this._adjacentEdges.remove(edge);
	}
}
