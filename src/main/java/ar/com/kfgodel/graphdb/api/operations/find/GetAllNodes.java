package ar.com.kfgodel.graphdb.api.operations.find;

import ar.com.kfgodel.graphdb.api.concepts.GraphNode;
import ar.com.kfgodel.graphdb.api.operations.GraphDbTransaction;
import ar.com.kfgodel.graphdb.api.operations.TransactionOperation;
import ar.com.kfgodel.nary.api.Nary;

/**
 * This type represents the operation that allows access to all the nodes on the database
 *
 * Created by kfgodel on 25/03/17.
 */
public class GetAllNodes implements TransactionOperation<Nary<GraphNode>> {

  @Override
  public Nary<GraphNode> doWith(GraphDbTransaction transaction) {
    return transaction.getAllNodes();
  }

  public static GetAllNodes create() {
    GetAllNodes operation = new GetAllNodes();
    return operation;
  }

}
