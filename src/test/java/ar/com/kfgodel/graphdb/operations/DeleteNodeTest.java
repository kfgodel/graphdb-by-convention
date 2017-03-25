package ar.com.kfgodel.graphdb.operations;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import ar.com.kfgodel.graphdb.GraphDbTestContext;
import ar.com.kfgodel.graphdb.api.concepts.GraphNode;
import ar.com.kfgodel.graphdb.api.operations.DeleteNode;
import ar.com.kfgodel.graphdb.api.operations.GraphDbTransaction;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static ar.com.kfgodel.graphdb.MockitoHelper.mockear;

/**
 * This type verifies the behavior for the node deletion action
 * Created by kfgodel on 12/03/17.
 */
@RunWith(JavaSpecRunner.class)
public class DeleteNodeTest extends JavaSpec<GraphDbTestContext> {
  @Override
  public void define() {
    describe("a node deletion operation", () -> {
      context().deleteNode(() -> DeleteNode.create(context().node()));

      describe("given a transaction", () -> {
        context().transaction(() -> mockear(GraphDbTransaction.class));

        it("removes the node from the graph", () -> {
          context().node(() -> mockear(GraphNode.class));

          context().deleteNode().doWith(context().transaction());

          Mockito.verify(context().transaction()).removeNode(context().node());
        });

      });
    });
  }
}