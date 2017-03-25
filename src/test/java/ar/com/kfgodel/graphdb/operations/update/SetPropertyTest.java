package ar.com.kfgodel.graphdb.operations.update;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import ar.com.kfgodel.graphdb.GraphDbTestContext;
import ar.com.kfgodel.graphdb.api.concepts.GraphNode;
import ar.com.kfgodel.graphdb.api.operations.GraphDbTransaction;
import ar.com.kfgodel.graphdb.api.operations.update.SetProperty;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static ar.com.kfgodel.graphdb.MockitoHelper.mockear;

/**
 * This type verifies the behavior for the property setter action
 * Created by kfgodel on 12/03/17.
 */
@RunWith(JavaSpecRunner.class)
public class SetPropertyTest extends JavaSpec<GraphDbTestContext> {
  @Override
  public void define() {
    describe("a property setter operation", () -> {
      context().setProperty(() -> SetProperty.create(context().node(), context().propertyName(), context().propertyValue()));

      describe("given a transaction", () -> {
        context().transaction(() -> mockear(GraphDbTransaction.class));

        it("sets the property on the node with the given value", () -> {
          context().node(() -> mockear(GraphNode.class));
          context().propertyName(() -> "un nombre");
          context().propertyValue(() -> "un value");

          context().setProperty().doWith(context().transaction());

          Mockito.verify(context().transaction()).setPropertyOn(context().node(), context().propertyName(), context().propertyValue());
        });

      });
    });
  }
}