package ar.com.kfgodel.graphdb.api;

/**
 * This type represents a graph based database in which operations can be done to create nodes, relationships,
 * query the state and remove them
 * <p>
 * Created by kfgodel on 11/03/17.
 */
public interface GraphDb {
  /**
   * Starts this instance to accept operations in it
   */
  void start();

  /**
   * Stops this instance freeing resources
   */
  void stop();

  /**
   * Creates a transaction for the given operation, executes it an resturns the result
   *
   * @param operation The operation to execute
   * @param <R>       The expected restult type
   * @return The result
   * @throws if the database isn't started or an error ocurred during the operation execution
   */
  <R> R ensureTransactionFor(TransactionOperation<R> operation) throws GraphDbException;
}
