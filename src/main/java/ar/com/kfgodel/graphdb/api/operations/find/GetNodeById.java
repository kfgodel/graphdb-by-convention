package ar.com.kfgodel.graphdb.api.operations.find;

import ar.com.kfgodel.graphdb.api.concepts.GraphNode;
import ar.com.kfgodel.graphdb.api.operations.GraphDbTransaction;
import ar.com.kfgodel.graphdb.api.operations.TransactionOperation;
import ar.com.kfgodel.nary.api.optionals.Optional;

/**
 * This type represents the operatin to get a single node using its id
 *
 * Created by kfgodel on 02/04/17.
 */
public class GetNodeById implements TransactionOperation<Optional<GraphNode>> {

  private long nodeId;

  @Override
  public Optional<GraphNode> doWith(GraphDbTransaction transaction) {
    return transaction.getNodeById(nodeId);
  }

  public static GetNodeById create(long nodeId) {
    GetNodeById operation = new GetNodeById();
    operation.nodeId = nodeId;
    return operation;
  }

}
