package ar.com.kfgodel.graphdb.api.operations.find;

import ar.com.kfgodel.graphdb.api.concepts.GraphRelationship;
import ar.com.kfgodel.graphdb.api.operations.GraphDbTransaction;
import ar.com.kfgodel.graphdb.api.operations.TransactionOperation;
import ar.com.kfgodel.nary.api.optionals.Optional;

/**
 * Created by kfgodel on 02/04/17.
 */
public class GetRelationshipById implements TransactionOperation<Optional<GraphRelationship>> {

  private long relationshipId;

  @Override
  public Optional<GraphRelationship> doWith(GraphDbTransaction transaction) {
    return transaction.getRelationshipById(relationshipId);
  }

  public static GetRelationshipById create(long relationshipId) {
    GetRelationshipById operation = new GetRelationshipById();
    operation.relationshipId = relationshipId;
    return operation;
  }

}
