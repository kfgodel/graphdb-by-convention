package ar.com.kfgodel.graphdb.api.operations.find;

import ar.com.kfgodel.graphdb.api.operations.GraphDbTransaction;
import ar.com.kfgodel.graphdb.api.operations.TransactionOperation;
import ar.com.kfgodel.nary.api.Nary;

import java.util.Map;

/**
 * This type represents a free query that returns results as rows of key value pairs
 * Created by kfgodel on 25/03/17.
 */
public class GetResultRows implements TransactionOperation<Nary<Map<String, Object>>> {
  private String query;

  @Override
  public Nary<Map<String, Object>> doWith(GraphDbTransaction transaction) {
    return transaction.getResultsFor(query);
  }

  public static GetResultRows create(String query) {
    GetResultRows operation = new GetResultRows();
    operation.query = query;
    return operation;
  }

}
