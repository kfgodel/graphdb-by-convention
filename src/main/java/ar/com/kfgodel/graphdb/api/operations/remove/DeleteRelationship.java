package ar.com.kfgodel.graphdb.api.operations.remove;

import ar.com.kfgodel.graphdb.api.concepts.GraphRelationship;
import ar.com.kfgodel.graphdb.api.operations.GraphDbTransaction;
import ar.com.kfgodel.graphdb.api.operations.TransactionOperation;

/**
 * This type represents the operation of deleting a node from the database
 * Created by kfgodel on 12/03/17.
 */
public class DeleteRelationship implements TransactionOperation<GraphRelationship> {

  private GraphRelationship deletable;

  @Override
  public GraphRelationship doWith(GraphDbTransaction transaction) {
    transaction.removeRelationship(deletable);
    return deletable;
  }

  public static DeleteRelationship create(GraphRelationship relationship) {
    DeleteRelationship deletion = new DeleteRelationship();
    deletion.deletable = relationship;
    return deletion;
  }

}
