package ar.com.kfgodel.graphdb.api.operations.find;

import ar.com.kfgodel.graphdb.api.concepts.PropertyContainer;
import ar.com.kfgodel.graphdb.api.operations.GraphDbTransaction;
import ar.com.kfgodel.graphdb.api.operations.TransactionOperation;
import ar.com.kfgodel.nary.api.optionals.Optional;

/**
 * This type represents the operation to get the value of a property on a node or relationship
 * Created by kfgodel on 12/03/17.
 */
public class GetProperty<T> implements TransactionOperation<Optional<T>> {

  private PropertyContainer node;
  private String propertyName;

  @Override
  public Optional<T> doWith(GraphDbTransaction transaction) {
    Optional<T> value = transaction.getPropertyFrom(node, propertyName);
    return value;
  }

  public static<T> GetProperty<T> create(PropertyContainer node, String propertyName) {
    GetProperty<T> action = new GetProperty<>();
    action.node = node;
    action.propertyName = propertyName;
    return action;
  }

}
