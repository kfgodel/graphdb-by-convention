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

  /**
   * Removes the given node from the database
   *
   * @param node The node to delete
   */
  void removeNode(GraphNode node);

  /**
   * Removes the given relationship from the database
   *
   * @param relationship The relationship to delete
   */
  void removeRelationship(GraphRelationship relationship);

  /**
   * Sets a new property value replacing the previous if there was one
   *
   * @param node          The node to set the property on
   * @param propertyName  The name of the property
   * @param propertyValue The value to set
   */
  void setPropertyOn(GraphNode node, String propertyName, Object propertyValue);
}
