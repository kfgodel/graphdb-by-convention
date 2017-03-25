package ar.com.kfgodel.graphdb.operations;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import ar.com.kfgodel.graphdb.GraphDbTestContext;
import ar.com.kfgodel.graphdb.api.concepts.GraphRelationship;
import ar.com.kfgodel.graphdb.api.operations.GraphDbTransaction;
import ar.com.kfgodel.graphdb.api.operations.RemoveProperty;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static ar.com.kfgodel.graphdb.MockitoHelper.mockear;

/**
 * This type verifies the behavior for the property remover action
 * Created by kfgodel on 12/03/17.
 */
@RunWith(JavaSpecRunner.class)
public class RemovePropertyTest extends JavaSpec<GraphDbTestContext> {
  @Override
  public void define() {
    describe("a property remover operation", () -> {
      context().removeProperty(() -> RemoveProperty.create(context().relationship(), context().propertyName()));

      describe("given a transaction", () -> {
        context().transaction(() -> mockear(GraphDbTransaction.class));

        it("removes the property on the container", () -> {
          context().relationship(() -> mockear(GraphRelationship.class));
          context().propertyName(() -> "un nombre");

          context().removeProperty().doWith(context().transaction());

          Mockito.verify(context().transaction()).removePropertyFrom(context().relationship(), context().propertyName());
        });

      });
    });
  }
}