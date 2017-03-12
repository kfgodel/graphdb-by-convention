package ar.com.kfgodel.graphdb;

import ar.com.kfgodel.graphdb.api.GraphDb;
import ar.com.kfgodel.graphdb.api.GraphDbTransaction;
import ar.com.kfgodel.graphdb.api.concepts.GraphNode;
import ar.com.kfgodel.graphdb.api.concepts.GraphRelationship;
import ar.com.kfgodel.graphdb.api.operations.CreateNode;
import ar.com.kfgodel.graphdb.api.operations.CreateRelationship;
import ar.com.kfgodel.graphdb.impl.EmbeddedNeo4jConfiguration;
import ar.com.kfgodel.graphdb.impl.EmbeddedNeo4jDb;
import org.neo4j.graphdb.*;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.factory.GraphDatabaseSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * Created by kfgodel on 11/03/17.
 */
public class ProbarBase {
  public static Logger LOG = LoggerFactory.getLogger(ProbarBase.class);

  public static void main(String[] args) {
    baseEmbebida();
  }

  private static void baseEmbebida() {
    LOG.info("Iniciando");
    EmbeddedNeo4jConfiguration configuration = EmbeddedNeo4jConfiguration.create()
      .locatedIn(new File("/home/kfgodel/git/graphdb-by-convention/neodb"));
    GraphDb base = EmbeddedNeo4jDb.create(configuration);

    base.start();
    try {
      base.ensureTransactionFor(ProbarBase::acciones);
    } finally {
      base.stop();
    }

    GraphDatabaseService graphDb = new GraphDatabaseFactory()
      .newEmbeddedDatabaseBuilder(new File("/home/kfgodel/git/graphdb-by-convention/neodb"))
      .setConfig(GraphDatabaseSettings.pagecache_memory, "512M")
      .newGraphDatabase();
    try {
      LOG.info("Levanto");
      dentroDeTransaccion(graphDb);
    } finally {
      LOG.info("Cerrando");
      graphDb.shutdown();
    }
    LOG.info("Chau");
  }

  private static Object acciones(GraphDbTransaction graphDbTransaction) {
    GraphNode firstNode = CreateNode.create().doWith(graphDbTransaction);
    GraphNode secondNode = CreateNode.create().doWith(graphDbTransaction);
    GraphRelationship relationship = CreateRelationship.create(firstNode, "KNOWS", secondNode).doWith(graphDbTransaction);

    return null;
  }

  private static void dentroDeTransaccion(GraphDatabaseService graphDb) {
    try (Transaction tx = graphDb.beginTx()) {

      Node firstNode = graphDb.createNode();
      firstNode.setProperty("message", "Hello, ");
      Node secondNode = graphDb.createNode();
      secondNode.setProperty("message", "World!");

      Relationship relationship = firstNode.createRelationshipTo(secondNode, RelTypes.KNOWS);
      relationship.setProperty("message", "brave Neo4j ");

      LOG.info("{}", firstNode.getProperty("message"));
      LOG.info("{}", relationship.getProperty("message"));
      LOG.info("{}", secondNode.getProperty("message"));

      firstNode.getSingleRelationship(RelTypes.KNOWS, Direction.OUTGOING).delete();
      firstNode.delete();
      secondNode.delete();

      // Database operations go here
      tx.success();
    }
  }

  private static void registerShutdownHook(final GraphDatabaseService graphDb) {
    // Registers a shutdown hook for the Neo4j instance so that it
    // shuts down nicely when the VM exits (even if you "Ctrl-C" the
    // running example before it's completed)
    Runtime.getRuntime().addShutdownHook(new Thread() {
      @Override
      public void run() {
      }
    });
  }
}
