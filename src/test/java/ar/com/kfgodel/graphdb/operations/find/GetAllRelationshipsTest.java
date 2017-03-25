package ar.com.kfgodel.graphdb.operations.find;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import ar.com.kfgodel.graphdb.GraphDbTestContext;
import ar.com.kfgodel.graphdb.api.operations.GraphDbTransaction;
import ar.com.kfgodel.graphdb.api.operations.find.GetAllRelationships;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static ar.com.kfgodel.graphdb.MockitoHelper.mockear;

/**
 * Created by kfgodel on 25/03/17.
 */
@RunWith(JavaSpecRunner.class)
public class GetAllRelationshipsTest extends JavaSpec<GraphDbTestContext> {
  @Override
  public void define() {
    describe("a get all relationships operation", () -> {
      context().getAllRelationships(GetAllRelationships::create);

      describe("given a transaction", () -> {
        context().transaction(() -> mockear(GraphDbTransaction.class));

        it("gets the relationships from the transaction", () -> {
          context().getAllRelationships().doWith(context().transaction());

          Mockito.verify(context().transaction()).getAllRelationships();
        });

      });

    });

  }
}