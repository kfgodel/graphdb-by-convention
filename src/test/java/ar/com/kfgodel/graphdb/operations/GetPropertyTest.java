package ar.com.kfgodel.graphdb.operations;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import ar.com.kfgodel.graphdb.GraphDbTestContext;
import ar.com.kfgodel.graphdb.api.concepts.GraphNode;
import ar.com.kfgodel.graphdb.api.operations.GraphDbTransaction;
import ar.com.kfgodel.graphdb.api.operations.find.GetProperty;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static ar.com.kfgodel.graphdb.MockitoHelper.mockear;

/**
 * This type verifies the behavior for the property getter action
 * Created by kfgodel on 12/03/17.
 */
@RunWith(JavaSpecRunner.class)
public class GetPropertyTest extends JavaSpec<GraphDbTestContext> {
  @Override
  public void define() {
    describe("a property getter operation", () -> {
      context().getProperty(() -> GetProperty.create(context().node(), context().propertyName()));

      describe("given a transaction", () -> {
        context().transaction(() -> mockear(GraphDbTransaction.class));

        it("gets the value from the node", () -> {
          context().node(() -> mockear(GraphNode.class));
          context().propertyName(() -> "un nombre");

          context().getProperty().doWith(context().transaction());

          Mockito.verify(context().transaction()).getPropertyFrom(context().node(), context().propertyName());
        });

      });
    });
  }
}