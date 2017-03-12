package ar.com.kfgodel.graphdb.impl;

import ar.com.kfgodel.graphdb.api.GraphDb;
import ar.com.kfgodel.graphdb.api.GraphDbException;
import ar.com.kfgodel.graphdb.api.TransactionOperation;
import ar.com.kfgodel.nary.api.optionals.Optional;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.factory.GraphDatabaseSettings;


/**
 * This type implements the graph db using a local instance of neo4j embedded in this
 * runtime
 * Created by kfgodel on 11/03/17.
 */
public class EmbeddedNeo4jDb implements GraphDb {

  private EmbeddedNeo4jConfiguration configuration;
  private Optional<GraphDatabaseService> neo4jDb;
  private Thread cleanupThread;

  public static EmbeddedNeo4jDb create(EmbeddedNeo4jConfiguration configuration) {
    EmbeddedNeo4jDb embedded = new EmbeddedNeo4jDb();
    embedded.configuration = configuration;
    embedded.neo4jDb = Optional.empty();
    embedded.cleanupThread = CleanUpThread.create(embedded);
    return embedded;
  }

  @Override
  public void start() {
    GraphDatabaseService createdDb = new GraphDatabaseFactory()
      .newEmbeddedDatabaseBuilder(configuration.getStorageDir())
      .setConfig(GraphDatabaseSettings.pagecache_memory, configuration.getPageCacheMemory())
      .newGraphDatabase();
    neo4jDb = Optional.of(createdDb);
    // Neo4j requires us to ensure the DB is closed correctly
    Runtime.getRuntime().addShutdownHook(cleanupThread);
  }

  @Override
  public void stop() {
    neo4jDb.ifPresent((createdDb) -> {
      createdDb.shutdown();
      Runtime.getRuntime().removeShutdownHook(cleanupThread);
    });
    neo4jDb = Optional.empty();
  }

  public Optional<GraphDatabaseService> getNeo4jDb() {
    return neo4jDb;
  }

  @Override
  public <R> R ensureTransactionFor(TransactionOperation<R> operation) throws GraphDbException {
    GraphDatabaseService createdDb = neo4jDb
      .orElseThrow(() -> new GraphDbException("The database isn't started"));
    return this.executeOperation(createdDb, operation);
  }

  private <R> R executeOperation(GraphDatabaseService neo4jDb, TransactionOperation<R> operation) {
    try (Transaction neo4jTransaction = neo4jDb.beginTx()) {
      EmbeddedNeo4jTransaction transaction = EmbeddedNeo4jTransaction.create(neo4jDb, neo4jTransaction);
      R result = operation.doWith(transaction);
      transaction.commit();
      return result;
    }
  }
}
