package ar.com.kfgodel.graphdb.api.operations;

import ar.com.kfgodel.graphdb.api.concepts.GraphNode;
import ar.com.kfgodel.graphdb.api.concepts.GraphRelationship;
import ar.com.kfgodel.graphdb.api.concepts.PropertyContainer;
import ar.com.kfgodel.nary.api.Nary;
import ar.com.kfgodel.nary.api.optionals.Optional;

import java.util.List;
import java.util.Map;

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
   * @param container     The node or relationship to set the property on
   * @param propertyName  The name of the property
   * @param propertyValue The value to set
   */
  void setPropertyOn(PropertyContainer container, String propertyName, Object propertyValue);

  /**
   * Removes the property on teh given container
   * @param container The node or relationship to remove the property from
   * @param propertyName The name of the property to remove
   */
  void removePropertyFrom(PropertyContainer container, String propertyName);

  /**
   * Retrieves the property value for the giben container
   * @param container The node or relationship to retrieve the value from
   * @param propertyName The name of the property
   */
  <T> Optional<T> getPropertyFrom(PropertyContainer container, String propertyName);

  /**
   * Retrieves all the nodes in the database
   * @return The nodes on the database
   */
  Nary<GraphNode> getAllNodes();

  /**
   * Retrieves all the relationships from the database
   * @return The relationships to iterate
   */
  Nary<GraphRelationship> getAllRelationships();

  /**
   * Executes the given query on the database and returns an unstructured set of resutls
   * @param query The query to execute
   * @return The results
   */
  Nary<Map<String,Object>> getResultsFor(String query);

  /**
   * Returns a node identified by its id, if present
   * @param nodeId The node identifier
   * @return The node or empty optional
   */
  Optional<GraphNode> getNodeById(long nodeId);

  /**
   * Returns the existing relationship identified by the given number
   * @param relationshipId The number that currently identifies the relationship
   * @return An optional with the relationship or empty if none exists
   */
  Optional<GraphRelationship> getRelationshipById(long relationshipId);
}
