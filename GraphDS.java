import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

/* Class for representing a Directed Graph data structure. 
 * Various exceptions are defined at the end 
 * - they are thrown for different kinds of illegal inputs to methods.
 */
public class GraphDS<T> {

	// _outEdges[x] is the set of nodes y such that (x,y) is an edge in the graph
	private Hashtable<T, Set<T>> _outEdges;   
	// _inEdges[x]  is the set of nodes y such that (y,x) is an edge in the graph
	private Hashtable<T, Set<T>> _inEdges;    

	public GraphDS() {
		_outEdges = new Hashtable<>();
		_inEdges = new Hashtable<>();
	}
	
	public GraphDS(T[] nodes, T[][] edges) throws NodeAlreadyExistsException, 
														IllegalEdgeInputException, EdgeAdditionForNonExistingNodeException, 
														EdgeAlreadyExistsException, NullInputException, NodeDoesNotExistException {
		for (T node : nodes) {
			addNode(node);
		}
		for (T[] pair : edges) {
			if (pair.length != 2) {
				throw new IllegalEdgeInputException();
			}
			addEdge(pair[0], pair[1]);
		}
	}

	public boolean hasNode(T x) {
		return _outEdges.containsKey(x);
	}
	
	public void addNode(T node) throws NodeAlreadyExistsException {
		if (hasNode(node)) {
			throw new NodeAlreadyExistsException(node);
		}
		_outEdges.put(node, new HashSet<T>());
		_inEdges.put(node, new HashSet<T>());
	}

	/* Deletes a node from the graph (exception if does not exist),
	 * as well as all the edges in which it participates */
	public void deleteNode(T node) throws NodeDoesNotExistException {
		if (!hasNode(node)) {
			throw new NodeDoesNotExistException(node);
		}
		_outEdges.remove(node);
		_inEdges.remove(node);
	}

	/* Returns true iff the graph has an edge between the two nodes */
	public boolean hasEdge(T node1, T node2) throws NodeDoesNotExistException {
		if (!hasNode(node1)) {
			throw new NodeDoesNotExistException(node1);
		}
		if (!hasNode(node2)) {
			throw new NodeDoesNotExistException(node2);
		}
		return _outEdges.get(node1).contains(node2);
	}

	/* Add edge to graph (exception if already exists, 
	 * or if one of the nodes does not exist) */
	public void addEdge(T node1, T node2) throws EdgeAdditionForNonExistingNodeException, EdgeAlreadyExistsException, NodeDoesNotExistException {
		if (!hasNode(node1)) {
			throw new EdgeAdditionForNonExistingNodeException(node1);
		}
		if (!hasNode(node2)) {
			throw new EdgeAdditionForNonExistingNodeException(node2);
		}
		if (hasEdge(node1, node2)) {
			throw new EdgeAlreadyExistsException(node1, node2);
		}
		_outEdges.get(node1).add(node2);
		_inEdges.get(node2).add(node1);
	}

	/* Delete edge from graph (exception if does not exist) */
	public void deleteEdge(T node1, T node2) throws IllegalCharacterNodeException, EdgeDoesNotExistException, NodeDoesNotExistException {
		if (!hasEdge(node1, node2)) {
			throw new EdgeDoesNotExistException(node1, node2);
		}
		_outEdges.get(node1).remove(node2);
		_inEdges.get(node2).remove(node1);
	}

	/* Returns a string representation of the graph in the form:
	 * "Graph<nodes, edges>", i.e.:
	 * "Graph<{node1, node2 ...}, {(node1,node2), (node3,node4),...}>"
	 */
	@Override
	public String toString() {
//		String nodesStr = "";
//		for (int i=0; i<NUM_POSSIBLE_NODES; ++i) {
//			if (_nodes[i]) {
//				if (!nodesStr.isEmpty()) {
//					nodesStr += ",";
//				}
//				nodesStr += nodeIdToChar(i);
//			}
//		}
//		nodesStr = "{"+nodesStr+"}";
//		
//		String edgesStr = "";
//		for (int i=0; i<NUM_POSSIBLE_NODES; ++i) {
//			for (int j=i; j<NUM_POSSIBLE_NODES; ++j) {
//				if (_edges[i][j]) {
//					if (!edgesStr.isEmpty()) {
//						edgesStr += ",";
//					}
//					edgesStr += "(" + nodeIdToChar(i) + "," + nodeIdToChar(j) + ")";
//				}
//			}
//		}
//		edgesStr = "{"+edgesStr+"}";
//		
//		return "Graph<"+nodesStr+" "+edgesStr+">";
		return "";   // TO DO
	}

	/* Two graphs are equal iff they have exactly the same nodes and same edges */
	@Override
	public boolean equals(Object other) {
		// TO DO
		return false;
	}

	/* The parent of all the exceptions that can be thrown from the Graph class */
	public static abstract class GraphException extends Exception {
		public GraphException(String message) {
			super(message);
		}
	}

	public static class NullInputException extends GraphException {
		public NullInputException() {
			super("Null input");
		}
	}

	public static class IllegalCharacterNodeException extends GraphException {
		public IllegalCharacterNodeException(char nodeId) {
			super("Illegal character id: "+nodeId);
		}
	}
	
	public static class NodeAlreadyExistsException extends GraphException {
		public NodeAlreadyExistsException(Object node) {
			super("Node "+node+" already in the graph");
		}
	}
	
	public static class NodeDoesNotExistException extends GraphException {
		public NodeDoesNotExistException(Object node) {
			super("Node "+node+" does not exist in the graph");
		}
	}

	public static class EdgeAdditionForNonExistingNodeException extends GraphException {
		public EdgeAdditionForNonExistingNodeException(Object node) {
			super("Cannot add edge on no-existing node "+node);
		}
	}
	
	public static class EdgeAlreadyExistsException extends GraphException {
		public EdgeAlreadyExistsException(Object node1, Object node2) {
			super("Edge ("+node1+","+node2+") already exists in the graph");
		}
	}

	public static class EdgeDoesNotExistException extends GraphException {
		public EdgeDoesNotExistException(Object node1, Object node2) {
			super("Edge ("+node1+","+node2+") does not exist in the graph");
		}
	}
	
	public static class IllegalEdgeInputException extends GraphException {
		public IllegalEdgeInputException() {
			super("Edge vector should have exactly two nodes");
		}
	}

}
