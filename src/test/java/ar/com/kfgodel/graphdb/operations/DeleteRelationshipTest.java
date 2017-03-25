package ar.com.kfgodel.graphdb.operations;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import ar.com.kfgodel.graphdb.GraphDbTestContext;
import ar.com.kfgodel.graphdb.api.concepts.GraphRelationship;
import ar.com.kfgodel.graphdb.api.operations.DeleteRelationship;
import ar.com.kfgodel.graphdb.api.operations.GraphDbTransaction;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static ar.com.kfgodel.graphdb.MockitoHelper.mockear;

/**
 * This type verifies the behavior for the relationship deletion action
 * Created by kfgodel on 12/03/17.
 */
@RunWith(JavaSpecRunner.class)
public class DeleteRelationshipTest extends JavaSpec<GraphDbTestContext> {
  @Override
  public void define() {
    describe("a relationship deletion operation", () -> {
      context().deleteRelationship(() -> DeleteRelationship.create(context().relationship()));

      describe("given a transaction", () -> {
        context().transaction(() -> mockear(GraphDbTransaction.class));

        it("removes the relatinship from the graph", () -> {
          context().relationship(() -> mockear(GraphRelationship.class));

          context().deleteRelationship().doWith(context().transaction());

          Mockito.verify(context().transaction()).removeRelationship(context().relationship());
        });

      });
    });
  }
}