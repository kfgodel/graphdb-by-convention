package ar.com.kfgodel.graphdb.api.operations.find;

import ar.com.kfgodel.graphdb.api.concepts.GraphRelationship;
import ar.com.kfgodel.graphdb.api.operations.GraphDbTransaction;
import ar.com.kfgodel.graphdb.api.operations.TransactionOperation;
import ar.com.kfgodel.nary.api.Nary;

/**
 * This type represents the operation to get all the relationships in the database
 *
 * Created by kfgodel on 25/03/17.
 */
public class GetAllRelationships implements TransactionOperation<Nary<GraphRelationship>> {

  @Override
  public Nary<GraphRelationship> doWith(GraphDbTransaction transaction) {
    return transaction.getAllRelationships();
  }

  public static GetAllRelationships create() {
    GetAllRelationships operation = new GetAllRelationships();
    return operation;
  }

}
