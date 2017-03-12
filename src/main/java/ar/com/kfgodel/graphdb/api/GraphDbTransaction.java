package ar.com.kfgodel.graphdb.api;

import ar.com.kfgodel.graphdb.api.concepts.GraphNode;
import ar.com.kfgodel.graphdb.api.concepts.GraphRelationship;

import java.util.List;

/**
 * This type represents a database transaction for a graph db
 * Created by kfgodel on 11/03/17.
 */
public interface GraphDbTransaction {
  /**
   * Ensures the current modifications affect the database
   */
  void commit();

  /**
   * Creates a new node on the graph
   *
   * @param labels An optional list of lables (cannot be null)
   * @return The isolated node
   */
  GraphNode createNode(List<String> labels);

  /**
   * Creates a new relationship between the given nodes, with the specified type name
   *
   * @param origin           The nodeo that is source of the relationship
   * @param relationshipType The string that identifies the type
   * @param destination      The node that is the end of teh relationship
   * @return The created relationship
   */
  GraphRelationship createRelationship(GraphNode origin, String relationshipType, GraphNode destination);
}
