package ar.com.kfgodel.graphdb.api.operations;

import ar.com.kfgodel.graphdb.api.concepts.GraphNode;
import ar.com.kfgodel.graphdb.api.concepts.GraphRelationship;

/**
 * This type represents the action to create a relationship between nodes
 * Created by kfgodel on 12/03/17.
 */
public class CreateRelationship implements TransactionOperation<GraphRelationship> {

  private GraphNode originNode;
  private String relationshipType;
  private GraphNode destinationNode;

  @Override
  public GraphRelationship doWith(GraphDbTransaction transaction) {
    return transaction.createRelationship(originNode, relationshipType, destinationNode);
  }

  public static CreateRelationship create(GraphNode originNode, String relationshipType, GraphNode destinationNode) {
    CreateRelationship creation = new CreateRelationship();
    creation.originNode = originNode;
    creation.relationshipType = relationshipType;
    creation.destinationNode = destinationNode;
    return creation;
  }

}
