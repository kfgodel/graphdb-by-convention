package ar.com.kfgodel.graphdb.impl;

import ar.com.kfgodel.graphdb.api.concepts.GraphNode;
import ar.com.kfgodel.graphdb.api.concepts.GraphRelationship;
import ar.com.kfgodel.graphdb.api.concepts.PropertyContainer;
import ar.com.kfgodel.graphdb.api.operations.GraphDbTransaction;
import ar.com.kfgodel.graphdb.impl.concepts.EmbeddedNeo4jNode;
import ar.com.kfgodel.graphdb.impl.concepts.EmbeddedNeo4jRelationship;
import ar.com.kfgodel.graphdb.impl.concepts.EmbeddedPropertyContainer;
import ar.com.kfgodel.nary.api.Nary;
import ar.com.kfgodel.nary.api.optionals.Optional;
import org.neo4j.graphdb.*;

import java.util.List;
import java.util.Map;
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
    Node node = neo4jDb.createNode(asNeo4jLabels(labels));
    return EmbeddedNeo4jNode.create(node);
  }

  @Override
  public GraphRelationship createRelationship(GraphNode origin, String relationshipTypeName, GraphNode destination) {
    Relationship neo4jRelationship = asNeo4jNode(origin).createRelationshipTo(asNeo4jNode(destination), asNeo4jRelationshipType(relationshipTypeName));
    return EmbeddedNeo4jRelationship.create(neo4jRelationship);
  }

  private Node asNeo4jNode(GraphNode aNode) {
    EmbeddedNeo4jNode embeddedNode = (EmbeddedNeo4jNode) aNode;
    return embeddedNode.getNeo4jNode();
  }

  @Override
  public void removeNode(GraphNode node) {
    asNeo4jNode(node).delete();
  }

  @Override
  public void removeRelationship(GraphRelationship relationship) {
    asNeo4jRelationship(relationship).delete();
  }

  @Override
  public void setPropertyOn(PropertyContainer container, String propertyName, Object propertyValue) {
    asNeo4jpropertyContainer(container).setProperty(propertyName, propertyValue);
  }

  @Override
  public void removePropertyFrom(PropertyContainer container, String propertyName) {
    asNeo4jpropertyContainer(container).removeProperty(propertyName);
  }

  @Override
  public <T> Optional<T> getPropertyFrom(PropertyContainer container, String propertyName) {
    org.neo4j.graphdb.PropertyContainer neo4jContainer = asNeo4jpropertyContainer(container);
    if(neo4jContainer.hasProperty(propertyName)){
      T propertyValue = (T) neo4jContainer.getProperty(propertyName);
      return Optional.of(propertyValue);
    }
    return Optional.empty();
  }

  @Override
  public Nary<GraphNode> getAllNodes() {
    return Nary.create(neo4jDb.getAllNodes())
      .mapNary(EmbeddedNeo4jNode::create);
  }

  @Override
  public Nary<GraphRelationship> getAllRelationships() {
    return Nary.create(neo4jDb.getAllRelationships())
      .mapNary(EmbeddedNeo4jRelationship::create);
  }

  @Override
  public Nary<Map<String, Object>> getResultsFor(String query) {
    Result result = neo4jDb.execute(query);
    return Nary.create(result);
  }

  @Override
  public Optional<GraphNode> getNodeById(long nodeId) {
    try {
      Node neo4jNode = neo4jDb.getNodeById(nodeId);
      return Optional.of(EmbeddedNeo4jNode.create(neo4jNode));
    } catch (NotFoundException e) {
      return Optional.empty();
    }
  }

  @Override
  public Optional<GraphRelationship> getRelationshipById(long relationshipId) {
    try {
      Relationship neo4jRelationship = neo4jDb.getRelationshipById(relationshipId);
      return Optional.of(EmbeddedNeo4jRelationship.create(neo4jRelationship));
    } catch (NotFoundException e) {
      return Optional.empty();
    }
  }

  private org.neo4j.graphdb.PropertyContainer asNeo4jpropertyContainer(PropertyContainer container) {
    EmbeddedPropertyContainer embeddedContainer = (EmbeddedPropertyContainer) container;
    return embeddedContainer.getNeo4jContainer();
  }

  public Transaction getNeo4jTransaction() {
    return neo4jTransaction;
  }

  public GraphDatabaseService getNeo4jDb() {
    return neo4jDb;
  }

  private Relationship asNeo4jRelationship(GraphRelationship relationship) {
    EmbeddedNeo4jRelationship embeddedRelationship = (EmbeddedNeo4jRelationship) relationship;
    return embeddedRelationship.getNeo4jRelationship();
  }

  private RelationshipType asNeo4jRelationshipType(String relationshipTypeName) {
    return RelationshipType.withName(relationshipTypeName);
  }

  private Label[] asNeo4jLabels(List<String> labels) {
    return labels.stream()
      .map(Label::label)
      .collect(Collectors.toList())
      .toArray(new Label[labels.size()]);
  }

}
