package ar.com.kfgodel.graphdb.impl;

import ar.com.kfgodel.graphdb.api.GraphDbTransaction;
import ar.com.kfgodel.graphdb.api.concepts.GraphNode;
import ar.com.kfgodel.graphdb.api.concepts.GraphRelationship;
import ar.com.kfgodel.graphdb.impl.concepts.EmbeddedNeo4jNode;
import ar.com.kfgodel.graphdb.impl.concepts.EmbeddedNeo4jRelationship;
import org.neo4j.graphdb.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * This type represents a transaction for an embedded neo4j instance.<br>
 * Because transactions for embedded and remote database have different protocol,
 * this type helps unifying it with a remote transaction
 * Created by kfgodel on 11/03/17.
 */
public class EmbeddedNeo4jTransaction implements GraphDbTransaction {

  private Transaction neo4jTransaction;
  private GraphDatabaseService neo4jDb;

  public static EmbeddedNeo4jTransaction create(GraphDatabaseService internalDb, Transaction neo4jTransaction) {
    EmbeddedNeo4jTransaction transaction = new EmbeddedNeo4jTransaction();
    transaction.neo4jTransaction = neo4jTransaction;
    transaction.neo4jDb = internalDb;
    return transaction;
  }

  @Override
  public void commit() {
    neo4jTransaction.success();
  }

  @Override
  public GraphNode createNode(List<String> labels) {
    Label[] nodeLabels = convertLabels(labels);
    Node node = neo4jDb.createNode(nodeLabels);
    return EmbeddedNeo4jNode.create(node);
  }

  @Override
  public GraphRelationship createRelationship(GraphNode origin, String relationshipTypeName, GraphNode destination) {
    Node neo4jOrigin = ((EmbeddedNeo4jNode) origin).getNeo4jNode();
    Node neo4jDestination = ((EmbeddedNeo4jNode) destination).getNeo4jNode();
    RelationshipType relationshipType = convertRelationshipType(relationshipTypeName);
    Relationship neo4jRelationship = neo4jOrigin.createRelationshipTo(neo4jDestination, relationshipType);
    return EmbeddedNeo4jRelationship.create(neo4jRelationship);
  }

  private RelationshipType convertRelationshipType(String relationshipTypeName) {
    return RelationshipType.withName(relationshipTypeName);
  }

  private Label[] convertLabels(List<String> labels) {
    return labels.stream()
      .map(Label::label)
      .collect(Collectors.toList())
      .toArray(new Label[0]);
  }

}
