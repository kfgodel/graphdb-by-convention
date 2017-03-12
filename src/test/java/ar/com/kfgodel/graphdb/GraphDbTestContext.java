package ar.com.kfgodel.graphdb;

import ar.com.dgarcia.javaspec.api.contexts.TestContext;
import ar.com.kfgodel.graphdb.api.GraphDb;
import ar.com.kfgodel.graphdb.api.GraphDbTransaction;
import ar.com.kfgodel.graphdb.api.concepts.GraphNode;
import ar.com.kfgodel.graphdb.api.operations.CreateNode;
import ar.com.kfgodel.graphdb.api.operations.CreateRelationship;
import ar.com.kfgodel.graphdb.impl.CleanUpThread;
import ar.com.kfgodel.graphdb.impl.EmbeddedNeo4jConfiguration;
import ar.com.kfgodel.graphdb.impl.EmbeddedNeo4jTransaction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;

import java.util.List;
import java.util.function.Supplier;

/**
 * This type defines the different variables used as context for the test
 * Created by kfgodel on 11/03/17.
 */
public interface GraphDbTestContext extends TestContext {
  EmbeddedNeo4jConfiguration embeddedConfig();

  void embeddedConfig(Supplier<EmbeddedNeo4jConfiguration> definition);

  CleanUpThread cleanupThread();

  void cleanupThread(Supplier<CleanUpThread> definition);

  GraphDb graphDb();

  void graphDb(Supplier<GraphDb> definition);

  EmbeddedNeo4jTransaction embeddedTransaction();

  void embeddedTransaction(Supplier<EmbeddedNeo4jTransaction> definition);

  Transaction neo4jTransaction();

  void neo4jTransaction(Supplier<Transaction> definition);

  GraphDatabaseService neo4jDb();

  void neo4jDb(Supplier<GraphDatabaseService> definition);

  CreateNode createNode();

  void createNode(Supplier<CreateNode> definition);

  GraphDbTransaction transaction();

  void transaction(Supplier<GraphDbTransaction> definition);

  CreateRelationship createRelationship();

  void createRelationship(Supplier<CreateRelationship> definition);

  GraphNode origin();

  void origin(Supplier<GraphNode> definition);

  GraphNode destination();

  void destination(Supplier<GraphNode> definition);

  String relationshipType();

  void relationshipType(Supplier<String> definition);

  Node neo4jOrigin();

  void neo4jOrigin(Supplier<Node> definition);

  Node neo4jDestination();

  void neo4jDestination(Supplier<Node> definition);

  GraphNode createdNode();

  void createdNode(Supplier<GraphNode> definition);

  List<String> nodeLabels();

  void nodeLabels(Supplier<List<String>> definition);


}