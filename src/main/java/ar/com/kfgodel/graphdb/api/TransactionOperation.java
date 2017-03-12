package ar.com.kfgodel.graphdb.api;

/**
 * This type represents an operation in a graph db that needs access to a transaction
 * Created by kfgodel on 11/03/17.
 */
@FunctionalInterface
public interface TransactionOperation<R> {

  /**
   * Executes this operation with the given transaction and returns a result
   *
   * @param transaction The transaction to use
   * @return A computed result
   */
  R doWith(GraphDbTransaction transaction);
}
