/**
 * Provides the basis of an Edge object. Each Edge has a source, a target, and a weight(value).
 */
public class Edge implements Comparable<Edge>
{
	private String _source;
	private String _target;
	private float _value;

	/**
	 * Edge constructor function.
	 */
	public Edge(String _source, String _target, float _value)
	{
		this._source = _source;
		this._target = _target;
		this._value = _value;
	}

	/**
	 * Retrieve the source of an Edge.
	 */
	public String get_source()
	{
		return _source;
	}

	/**
	 * Retrive the target of an Edge.
	 */
	public String get_target()
	{
		return _target;
	}

	/**
	 * Retrieve the weight of an Edge.
	 */
	public float get_value()
	{
		return _value;
	}

	/**
	 * Provides a basis of comparing Edges based on weight.
	 */
	public int compareTo(Edge passedEdge)
	{
		return Float.compare(this._value, passedEdge._value);
	}
}
