package ar.com.kfgodel.graphdb.impl.concepts;

import ar.com.kfgodel.graphdb.api.concepts.GraphNode;
import ar.com.kfgodel.graphdb.api.concepts.GraphRelationship;
import org.neo4j.graphdb.PropertyContainer;
import org.neo4j.graphdb.Relationship;

/**
 * This type represents a neo4j relationship
 * Created by kfgodel on 12/03/17.
 */
public class EmbeddedNeo4jRelationship implements GraphRelationship, EmbeddedPropertyContainer {

  private Relationship neo4jRelationship;

  public Relationship getNeo4jRelationship() {
    return neo4jRelationship;
  }

  public static EmbeddedNeo4jRelationship create(Relationship neo4jRelationship) {
    EmbeddedNeo4jRelationship relationship = new EmbeddedNeo4jRelationship();
    relationship.neo4jRelationship = neo4jRelationship;
    return relationship;
  }

  @Override
  public PropertyContainer getNeo4jContainer() {
    return getNeo4jRelationship();
  }

  @Override
  public long getId() {
    return neo4jRelationship.getId();
  }

  @Override
  public GraphNode getOrigin() {
    return EmbeddedNeo4jNode.create(neo4jRelationship.getStartNode());
  }

  @Override
  public String getRelationshipType() {
    return neo4jRelationship.getType().name();
  }

  @Override
  public GraphNode getDestination() {
    return EmbeddedNeo4jNode.create(neo4jRelationship.getEndNode());
  }
}
