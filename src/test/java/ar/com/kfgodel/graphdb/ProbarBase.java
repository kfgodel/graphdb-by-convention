package ar.com.kfgodel.graphdb;

import ar.com.kfgodel.graphdb.api.GraphDb;
import ar.com.kfgodel.graphdb.api.GraphDbTransaction;
import ar.com.kfgodel.graphdb.api.concepts.GraphNode;
import ar.com.kfgodel.graphdb.api.concepts.GraphRelationship;
import ar.com.kfgodel.graphdb.api.operations.*;
import ar.com.kfgodel.graphdb.impl.EmbeddedNeo4jConfiguration;
import ar.com.kfgodel.graphdb.impl.EmbeddedNeo4jDb;
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
      LOG.info("Levanto");
      base.ensureTransactionFor(ProbarBase::acciones);
    } finally {
      LOG.info("Cerrando");
      base.stop();
    }
    LOG.info("Chau");
  }

  private static Object acciones(GraphDbTransaction graphDbTransaction) {
    GraphNode firstNode = CreateNode.create().doWith(graphDbTransaction);
    SetProperty.create(firstNode, "message", "Hello, ").doWith(graphDbTransaction);

    GraphNode secondNode = CreateNode.create().doWith(graphDbTransaction);
    SetProperty.create(firstNode, "message", "World!").doWith(graphDbTransaction);

    GraphRelationship relationship = CreateRelationship.create(firstNode, "KNOWS", secondNode).doWith(graphDbTransaction);
    SetProperty.create(relationship, "message", "brave Neo4j ").doWith(graphDbTransaction);

    LOG.info("{}", GetProperty.create(firstNode, "message").doWith(graphDbTransaction).get());
    LOG.info("{}", GetProperty.create(relationship, "message").doWith(graphDbTransaction).get());
    LOG.info("{}", GetProperty.create(secondNode, "message").doWith(graphDbTransaction).get());

    DeleteRelationship.create(relationship).doWith(graphDbTransaction);
    DeleteNode.create(secondNode).doWith(graphDbTransaction);
    DeleteNode.create(firstNode).doWith(graphDbTransaction);
    return null;
  }

}
