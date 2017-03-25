package ar.com.kfgodel.graphdb.api.operations;

import ar.com.kfgodel.graphdb.api.concepts.GraphNode;

import java.util.ArrayList;
import java.util.List;

/**
 * This type represents the action to create a new node on a database
 * Created by kfgodel on 12/03/17.
 */
public class CreateNode implements TransactionOperation<GraphNode> {

  private List<String> labels;

  @Override
  public GraphNode doWith(GraphDbTransaction transaction) {
    return transaction.createNode(getLabels());
  }

  public static CreateNode create() {
    CreateNode creation = new CreateNode();
    return creation;
  }

  public List<String> getLabels() {
    if (labels == null) {
      labels = new ArrayList<>();
    }
    return labels;
  }

  public CreateNode labeledWith(List<String> labels) {
    labels.forEach(getLabels()::add);
    return this;
  }
}
