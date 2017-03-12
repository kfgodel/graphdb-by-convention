package ar.com.kfgodel.graphdb.api.operations;

import ar.com.kfgodel.graphdb.api.GraphDbTransaction;
import ar.com.kfgodel.graphdb.api.TransactionOperation;
import ar.com.kfgodel.graphdb.api.concepts.PropertyContainer;

/**
 * This type represents the operation to remove the value of a property on a node or relationship
 * Created by kfgodel on 12/03/17.
 */
public class RemoveProperty implements TransactionOperation<PropertyContainer> {

  private PropertyContainer node;
  private String propertyName;

  @Override
  public PropertyContainer doWith(GraphDbTransaction transaction) {
    transaction.removePropertyFrom(node, propertyName);
    return node;
  }

  public static RemoveProperty create(PropertyContainer node, String propertyName) {
    RemoveProperty action = new RemoveProperty();
    action.node = node;
    action.propertyName = propertyName;
    return action;
  }

}
