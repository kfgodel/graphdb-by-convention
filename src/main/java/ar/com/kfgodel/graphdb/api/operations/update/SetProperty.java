package ar.com.kfgodel.graphdb.api.operations.update;

import ar.com.kfgodel.graphdb.api.concepts.PropertyContainer;
import ar.com.kfgodel.graphdb.api.operations.GraphDbTransaction;
import ar.com.kfgodel.graphdb.api.operations.TransactionOperation;

/**
 * This type represents the operation to set the value of a property on a node or relationship
 * Created by kfgodel on 12/03/17.
 */
public class SetProperty implements TransactionOperation<PropertyContainer> {

  private PropertyContainer node;
  private String propertyName;
  private Object propertyValue;

  @Override
  public PropertyContainer doWith(GraphDbTransaction transaction) {
    transaction.setPropertyOn(node, propertyName, propertyValue);
    return node;
  }

  public static SetProperty create(PropertyContainer node, String propertyName, Object propertyValue) {
    SetProperty action = new SetProperty();
    action.node = node;
    action.propertyName = propertyName;
    action.propertyValue = propertyValue;
    return action;
  }

}
