package ar.com.kfgodel.graphdb.api.operations;

import ar.com.kfgodel.graphdb.api.concepts.GraphNode;

/**
 * This type represents the operation of deleting a node from the database
 * Created by kfgodel on 12/03/17.
 */
public class DeleteNode implements TransactionOperation<GraphNode> {

  private GraphNode deletable;

  @Override
  public GraphNode doWith(GraphDbTransaction transaction) {
    transaction.removeNode(deletable);
    return deletable;
  }

  public static DeleteNode create(GraphNode node) {
    DeleteNode deletion = new DeleteNode();
    deletion.deletable = node;
    return deletion;
  }

}
